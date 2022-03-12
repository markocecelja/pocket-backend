package com.mcecelja.pocket.services.common;

import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.codebook.OrganizationRoleEnum;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.domain.user.codebook.RoleEnum;

public interface PermissionCheckerService {

	boolean checkUserHasRole(User user, RoleEnum role);

	boolean checkUserHasOrganizationRole(User user, Organization organization, OrganizationRoleEnum organizationMemberRole);
}
