package com.mcecelja.pocket.services.common;

import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.codebook.OrganizationRoleEnum;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.domain.user.codebook.RoleEnum;
import org.springframework.stereotype.Service;

@Service
public class PermissionCheckerServiceImpl implements PermissionCheckerService {

	@Override
	public boolean checkUserHasRole(User user, RoleEnum role) {
		return user.getRoles().stream().anyMatch(userRole -> userRole.getId().equals(role.getId()));
	}

	@Override
	public boolean checkUserHasOrganizationRole(User user, Organization organization, OrganizationRoleEnum organizationMemberRole) {
		return organization.getMembers().stream().anyMatch(organizationMember -> organizationMember.getOrganizationMemberPK().getUser().getId().equals(user.getId()) &&
				organizationMember.getOrganizationRole().getId().equals(organizationMemberRole.getId()));
	}
}
