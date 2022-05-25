package com.mcecelja.pocket.services.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationCodeDTO;
import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.common.dto.organization.OrganizationMemberDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationMemberService {

	Page<OrganizationMemberDTO> getOrganizationMembers(Long organizationId, Pageable pageable);

	OrganizationDTO joinOrganizationByCode(OrganizationCodeDTO organizationCodeDTO) throws PocketException;
}
