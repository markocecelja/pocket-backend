package com.mcecelja.pocket.common.mappers.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationCodeDTO;
import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.common.dto.organization.OrganizationMemberDTO;
import com.mcecelja.pocket.common.mappers.UserMapper;
import com.mcecelja.pocket.common.mappers.codebook.CodeBookMapper;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.OrganizationMember;
import com.mcecelja.pocket.domain.organization.codebook.OrganizationRoleEnum;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.domain.user.codebook.RoleEnum;
import com.mcecelja.pocket.services.common.PermissionCheckerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {AuthorizedRequestContext.class},
		uses = {OrganizationCodeMapper.class, UserMapper.class, CodeBookMapper.class})
public abstract class OrganizationMapper {

	@Autowired
	protected PermissionCheckerService permissionCheckerService;

	@Autowired
	protected OrganizationCodeMapper organizationCodeMapper;

	@Mappings({
			@Mapping(target = "organizationCode", expression = "java(mapOrganizationCode(entity))"),
			@Mapping(target = "currentUserMember", expression = "java(AuthorizedRequestContext.getCurrentUser() != null ? organizationMemberToOrganizationMemberDTO(entity.getMembers().stream().filter(member -> member.getOrganizationMemberPK().getUser().getId().equals(AuthorizedRequestContext.getCurrentUser().getId())).findFirst().orElse(null)) : null)")
	})
	public abstract OrganizationDTO organizationToOrganizationDTO(Organization entity);

	@Mappings({
			@Mapping(target = "user", source = "entity.organizationMemberPK.user"),
			@Mapping(target = "role", source = "entity.organizationRole"),
	})
	public abstract OrganizationMemberDTO organizationMemberToOrganizationMemberDTO(OrganizationMember entity);

	protected OrganizationCodeDTO mapOrganizationCode(Organization organization) {
		User currentUser = AuthorizedRequestContext.getCurrentUser();

		if(permissionCheckerService.checkUserHasRole(currentUser, RoleEnum.SYSTEM_ADMIN) ||
				permissionCheckerService.checkUserHasOrganizationRole(currentUser, organization, OrganizationRoleEnum.ADMIN)) {
			return organizationCodeMapper.organizationCodeToOrganizationCodeDTO(organization.getOrganizationCode());
		}

		return null;
	}
}
