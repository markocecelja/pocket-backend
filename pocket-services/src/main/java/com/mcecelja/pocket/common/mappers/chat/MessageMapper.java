package com.mcecelja.pocket.common.mappers.chat;

import com.mcecelja.pocket.common.dto.chat.MessageCreatorDTO;
import com.mcecelja.pocket.common.dto.chat.MessageDTO;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
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
			@Mapping(target = "createdBy", expression = "java(mapCreatedBy(entity))"),
			@Mapping(target = "createdDateTime", source = "createdDateTime")
	})
	public abstract MessageDTO messageToMessageDTO(Message entity);

	protected MessageCreatorDTO mapCreatedBy(Message message) {

		if (permissionCheckerService.checkUserHasRole(message.getCreatedBy(), RoleEnum.ORGANIZATION_MEMBER)) {
			return MessageCreatorDTO.builder()
					.id(message.getCreatedBy().getId().toString())
					.name(message.getChat().getPost().getOrganization().getName())
					.type("ORGANIZATION")
					.build();
		} else {
			return MessageCreatorDTO.builder()
					.id(message.getCreatedBy().getId().toString())
					.name(String.format("%s %s", message.getCreatedBy().getFirstName(), message.getCreatedBy().getLastName()))
					.type("USER")
					.build();
		}
	}
}
