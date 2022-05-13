package com.mcecelja.pocket.common.mappers.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.common.dto.organization.OrganizationMemberDTO;
import com.mcecelja.pocket.common.mappers.UserMapper;
import com.mcecelja.pocket.common.mappers.codebook.CodeBookMapper;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.OrganizationMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {AuthorizedRequestContext.class},
		uses = {OrganizationCodeMapper.class, UserMapper.class, CodeBookMapper.class})
public abstract class OrganizationMapper {

	@Mappings({
			@Mapping(target = "organizationCode", source = "organizationCode"),
			@Mapping(target = "currentUserMember", expression = "java(AuthorizedRequestContext.getCurrentUser() != null ? organizationMemberToOrganizationMemberDTO(entity.getMembers().stream().filter(member -> member.getOrganizationMemberPK().getUser().getId().equals(AuthorizedRequestContext.getCurrentUser().getId())).findFirst().orElse(null)) : null)")
	})
	public abstract OrganizationDTO organizationToOrganizationDTO(Organization entity);

	@Mappings({
			@Mapping(target = "user", source = "entity.organizationMemberPK.user"),
			@Mapping(target = "role", source = "entity.organizationRole"),
	})
	public abstract OrganizationMemberDTO organizationMemberToOrganizationMemberDTO(OrganizationMember entity);
}
