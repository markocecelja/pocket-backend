package com.mcecelja.pocket.common.mappers;

import com.mcecelja.pocket.common.dto.user.UserDTO;
import com.mcecelja.pocket.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

	public abstract UserDTO userToUserDTO(User entity);
}
