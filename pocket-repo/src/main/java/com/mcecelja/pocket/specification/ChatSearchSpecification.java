package com.mcecelja.pocket.specification;

import com.mcecelja.pocket.domain.chat.Chat;
import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.OrganizationMember;
import com.mcecelja.pocket.domain.post.Post;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.domain.user.codebook.RoleEnum;
import com.mcecelja.pocket.specification.criteria.ChatSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ChatSearchSpecification {

	public static Specification<Chat> findChats(final ChatSearchCriteria criteria) {

		return (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> restrictions = new ArrayList<>();

			Long id = criteria.getId();
			Long postId = criteria.getPostId();
			Long userId = criteria.getUserId();
			User currentUser = criteria.getCurrentUser();

			if(id != null) {
				restrictions.add(
						criteriaBuilder.equal(root.get("id"), id)
				);
			}

			if (postId != null) {
				restrictions.add(
						criteriaBuilder.equal(root.get("post").get("id"), postId)
				);
			}

			if (userId != null) {
				restrictions.add(
						criteriaBuilder.equal(root.get("user").get("id"), userId)
				);
			}

			if(currentUser.getRoles().stream().noneMatch(role -> role.getId().equals(RoleEnum.SYSTEM_ADMIN.getId()))) {

				Join<Chat, Post> chatPostJoin = root.join("post", JoinType.LEFT);
				Join<Post, Organization> postOrganizationJoin = chatPostJoin.join("organization", JoinType.LEFT);
				Join<Organization, OrganizationMember> organizationOrganizationMemberJoin = postOrganizationJoin.join("members", JoinType.LEFT);

				restrictions.add(
						criteriaBuilder.or(
								criteriaBuilder.equal(root.get("user").get("id"), currentUser.getId()),
								criteriaBuilder.equal(organizationOrganizationMemberJoin.get("organizationMemberPK").get("user").get("id"), currentUser.getId())
						)
				);
			}

			criteriaQuery.distinct(true);

			return criteriaBuilder.and(restrictions.toArray(new Predicate[0]));
		};
	}
}
