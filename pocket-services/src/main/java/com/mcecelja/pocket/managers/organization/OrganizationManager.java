package com.mcecelja.pocket.managers.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.organization.Organization;

public interface OrganizationManager {

	Organization getOrganization(Long id) throws PocketException;

	Organization createOrganization(OrganizationDTO organizationDTO);

	void updateOrganization(Organization organization, OrganizationDTO organizationDTO);
}
