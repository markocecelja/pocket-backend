package com.mcecelja.pocket.managers.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.organization.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationManager {

	Page<Organization> getOrganizations(Pageable pageable);

	Organization getOrganization(Long id) throws PocketException;

	Organization createOrganization(OrganizationDTO organizationDTO);

	void updateOrganization(Organization organization, OrganizationDTO organizationDTO);
}
