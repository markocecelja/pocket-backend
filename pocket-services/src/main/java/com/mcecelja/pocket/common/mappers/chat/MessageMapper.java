package com.mcecelja.pocket.common.mappers.chat;

import com.mcecelja.pocket.common.dto.chat.ChatDTO;
import com.mcecelja.pocket.common.dto.chat.MessageDTO;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
import com.mcecelja.pocket.domain.chat.Chat;
import com.mcecelja.pocket.domain.chat.Message;
import com.mcecelja.pocket.domain.user.codebook.RoleEnum;
import com.mcecelja.pocket.services.common.PermissionCheckerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ChatMapper.class}, imports = AuthorizedRequestContext.class)
public abstract class MessageMapper {

	@Autowired
	protected PermissionCheckerService permissionCheckerService;

	@Mappings({
			@Mapping(target = "id", source = "id"),
			@Mapping(target = "chat", source = "chat"),
			@Mapping(target = "creator", expression = "java(mapCreator(entity))"),
			@Mapping(target = "createdByCurrentUser", expression = "java(entity.getCreatedBy().getId().equals(AuthorizedRequestContext.getCurrentUser().getId()))"),
			@Mapping(target = "createdDateTime", source = "createdDateTime")
	})
	public abstract MessageDTO messageToMessageDTO(Message entity);

	protected String mapCreator(Message message) {
		if (permissionCheckerService.checkUserHasRole(message.getCreatedBy(), RoleEnum.ORGANIZATION_MEMBER)) {
			return message.getChat().getPost().getOrganization().getName();
		} else {
			return String.format("%s %s", message.getCreatedBy().getFirstName(), message.getCreatedBy().getLastName());
		}
	}
}
