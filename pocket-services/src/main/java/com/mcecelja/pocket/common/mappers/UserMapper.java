package com.mcecelja.pocket.common.mappers;

import com.mcecelja.pocket.common.dto.user.UserDTO;
import com.mcecelja.pocket.common.mappers.codebook.CodeBookMapper;
import com.mcecelja.pocket.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
		unmappedTargetPolicy = ReportingPolicy.IGNORE,
		uses = {CodeBookMapper.class})
public abstract class UserMapper {

	@Mappings({
			@Mapping(target = "firstName", source = "entity.firstName"),
			@Mapping(target = "lastName", source = "entity.lastName"),
			@Mapping(target = "roles", source = "entity.roles"),
	})
	public abstract UserDTO userToUserDTO(User entity);
}
