package com.mcecelja.pocket.managers.organization.member;

import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.OrganizationMember;
import com.mcecelja.pocket.domain.organization.codebook.OrganizationRoleEnum;
import com.mcecelja.pocket.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationMemberManager {

	Page<OrganizationMember> getOrganizationMembers(Long organizationId, Pageable pageable);

	void addMemberToOrganization(Organization organization, User user, OrganizationRoleEnum organizationRoleEnum) throws PocketException;
}
