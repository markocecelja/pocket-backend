package com.mcecelja.pocket.services.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationService {

	Page<OrganizationDTO> getOrganizations(Pageable pageable);

	OrganizationDTO getOrganization(Long organizationId) throws PocketException;

	OrganizationDTO createOrganization(OrganizationDTO organizationDTO) throws PocketException;

	OrganizationDTO updateOrganization(Long organizationId, OrganizationDTO organizationDTO) throws PocketException;
}
