package com.mcecelja.pocket.specification;

import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.OrganizationMember;
import com.mcecelja.pocket.domain.post.Post;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.domain.user.codebook.RoleEnum;
import com.mcecelja.pocket.specification.criteria.PostSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostSearchSpecification {

	public static Specification<Post> findPosts(final PostSearchCriteria criteria) {

		return (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> restrictions = new ArrayList<>();

			Long id = criteria.getId();
			Long organizationId = criteria.getOrganizationId();
			Long categoryId = criteria.getCategoryId();
			User currentUser = criteria.getCurrentUser();

			if (id != null) {
				restrictions.add(
						criteriaBuilder.equal(root.get("id"), id)
				);
			}

			if (organizationId != null) {
				restrictions.add(
						criteriaBuilder.equal(root.get("organization").get("id"), organizationId)
				);
			}

			if (categoryId != null) {
				restrictions.add(
						criteriaBuilder.equal(root.get("category").get("id"), categoryId)
				);
			}

			if (currentUser.getRoles().stream().anyMatch(role -> role.getId().equals(RoleEnum.STUDENT.getId()))) {
				restrictions.add(
						criteriaBuilder.equal(root.get("active"), criteriaBuilder.literal(true))
				);
			} else if (currentUser.getRoles().stream().anyMatch(role -> role.getId().equals(RoleEnum.ORGANIZATION_MEMBER.getId()))) {

				Join<Post, Organization> postOrganizationJoin = root.join("organization", JoinType.LEFT);
				Join<Organization, OrganizationMember> organizationOrganizationMemberJoin = postOrganizationJoin.join("members", JoinType.LEFT);

				restrictions.add(
						criteriaBuilder.equal(organizationOrganizationMemberJoin.get("organizationMemberPK").get("user").get("id"), currentUser.getId())
				);
			}

			criteriaQuery.distinct(true);

			return criteriaBuilder.and(restrictions.toArray(new Predicate[0]));
		};
	}
}
