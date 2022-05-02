package com.mcecelja.pocket.services.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationMemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationMemberService {

	Page<OrganizationMemberDTO> getOrganizationMembers(Long organizationId, Pageable pageable);
}
