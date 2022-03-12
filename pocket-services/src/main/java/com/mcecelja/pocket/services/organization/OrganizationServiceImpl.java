package com.mcecelja.pocket.services.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.common.mappers.organization.OrganizationMapper;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.codebook.OrganizationRoleEnum;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.domain.user.codebook.RoleEnum;
import com.mcecelja.pocket.managers.organization.OrganizationManager;
import com.mcecelja.pocket.services.common.PermissionCheckerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationServiceImpl implements OrganizationService {

	private final PermissionCheckerService permissionCheckerService;

	private final OrganizationManager organizationManager;

	private final OrganizationMapper organizationMapper;

	@Override
	public OrganizationDTO getOrganization(Long organizationId) throws PocketException {
		return organizationMapper.organizationToOrganizationDTO(organizationManager.getOrganization(organizationId));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrganizationDTO createOrganization(OrganizationDTO organizationDTO) throws PocketException {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		if (!permissionCheckerService.checkUserHasRole(currentUser, RoleEnum.SYSTEM_ADMIN)) {
			log.warn("Create organization failed: user {} doesn't have permission to create organization!", currentUser.getId());
			throw new PocketException(PocketError.UNAUTHORIZED);
		}

		return organizationMapper.organizationToOrganizationDTO(organizationManager.createOrganization(organizationDTO));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrganizationDTO updateOrganization(Long organizationId, OrganizationDTO organizationDTO) throws PocketException {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		Organization organization = organizationManager.getOrganization(organizationId);

		if (!permissionCheckerService.checkUserHasRole(currentUser, RoleEnum.SYSTEM_ADMIN) && !permissionCheckerService.checkUserHasOrganizationRole(currentUser, organization, OrganizationRoleEnum.ADMIN)) {
			log.warn("Create organization failed: user {} doesn't have permission to create organization!", currentUser.getId());
			throw new PocketException(PocketError.UNAUTHORIZED);
		}

		organizationManager.updateOrganization(organization, organizationDTO);

		return organizationMapper.organizationToOrganizationDTO(organization);
	}
}
