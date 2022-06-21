package com.mcecelja.pocket.specification;

import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.OrganizationMember;
import com.mcecelja.pocket.domain.user.codebook.RoleEnum;
import com.mcecelja.pocket.specification.criteria.OrganizationSearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrganizationSearchSpecification {

	public static Specification<Organization> findOrganizations(final OrganizationSearchCriteria criteria) {

		return (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> restrictions = new ArrayList<>();

			Long id = criteria.getId();
			String code = criteria.getCode();
			Long memberId = criteria.getMemberId();
			String name = criteria.getName();
			Boolean verified = criteria.getVerified();

			if (id != null) {
				restrictions.add(
						criteriaBuilder.equal(root.get("id"), id)
				);
			}

			if (!StringUtils.isEmpty(code)) {
				restrictions.add(
						criteriaBuilder.and(
								criteriaBuilder.equal(root.get("organizationCode").get("value"), code),
								criteriaBuilder.greaterThan(root.get("organizationCode").get("expirationDate"), LocalDateTime.now())
						)
				);
			}

			if (criteria.getCurrentUser() != null &&
					criteria.getCurrentUser().getRoles().stream().noneMatch(role -> role.getId().equals(RoleEnum.SYSTEM_ADMIN.getId()))) {
				restrictions.add(criteriaBuilder.isTrue(root.get("active")));
			}

			if (memberId != null) {
				Join<Organization, OrganizationMember> organizationOrganizationMemberJoin = root.join("members", JoinType.LEFT);

				restrictions.add(
						criteriaBuilder.equal(organizationOrganizationMemberJoin.get("organizationMemberPK").get("user").get("id"), memberId)
				);
			}

			if (name != null && !name.trim().isEmpty()) {
				restrictions.add(
						criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%")
				);
			}

			if(verified != null) {
				restrictions.add(
						criteriaBuilder.equal(root.get("verified"), verified)
				);
			}

			criteriaQuery.distinct(true);

			return criteriaBuilder.and(restrictions.toArray(new Predicate[0]));
		};
	}
}
