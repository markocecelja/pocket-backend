package com.mcecelja.pocket.managers.organization.member;

import com.mcecelja.pocket.domain.organization.OrganizationMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationMemberManager {

	Page<OrganizationMember> getOrganizationMembers(Long organizationId, Pageable pageable);
}
