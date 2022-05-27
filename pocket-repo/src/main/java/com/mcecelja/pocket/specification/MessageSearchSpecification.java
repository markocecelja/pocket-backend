package com.mcecelja.pocket.specification;

import com.mcecelja.pocket.domain.chat.Chat;
import com.mcecelja.pocket.domain.chat.Message;
import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.OrganizationMember;
import com.mcecelja.pocket.domain.post.Post;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.domain.user.codebook.RoleEnum;
import com.mcecelja.pocket.specification.criteria.MessageSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class MessageSearchSpecification {

	public static Specification<Message> findMessages(final MessageSearchCriteria criteria) {

		return (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> restrictions = new ArrayList<>();

			Long chatId = criteria.getChatId();
			User currentUser = criteria.getCurrentUser();

			if (chatId != null) {
				restrictions.add(
						criteriaBuilder.equal(root.get("chat").get("id"), chatId)
				);
			}

			if(currentUser.getRoles().stream().noneMatch(role -> role.getId().equals(RoleEnum.SYSTEM_ADMIN.getId()))) {

				Join<Message, Chat> messageChatJoin = root.join("chat", JoinType.LEFT);
				Join<Chat, Post> chatPostJoin = messageChatJoin.join("post", JoinType.LEFT);
				Join<Post, Organization> postOrganizationJoin = chatPostJoin.join("organization", JoinType.LEFT);
				Join<Organization, OrganizationMember> organizationOrganizationMemberJoin = postOrganizationJoin.join("members", JoinType.LEFT);

				restrictions.add(
						criteriaBuilder.or(
								criteriaBuilder.equal(messageChatJoin.get("user").get("id"), currentUser.getId()),
								criteriaBuilder.equal(organizationOrganizationMemberJoin.get("organizationMemberPK").get("user").get("id"), currentUser.getId())
						)
				);
			}

			criteriaQuery.distinct(true);

			return criteriaBuilder.and(restrictions.toArray(new Predicate[0]));
		};
	}
}
