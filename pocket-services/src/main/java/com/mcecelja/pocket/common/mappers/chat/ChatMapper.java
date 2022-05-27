package com.mcecelja.pocket.common.mappers.chat;

import com.mcecelja.pocket.common.dto.chat.ChatDTO;
import com.mcecelja.pocket.common.mappers.UserMapper;
import com.mcecelja.pocket.common.mappers.post.PostMapper;
import com.mcecelja.pocket.domain.chat.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, PostMapper.class})
public abstract class ChatMapper {

	@Mappings({
			@Mapping(target = "id", source = "id"),
			@Mapping(target = "user", source = "user"),
			@Mapping(target = "post", source = "post")
	})
	public abstract ChatDTO chatToChatDTO(Chat entity);
}
