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
import com.mcecelja.pocket.specification.criteria.OrganizationSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<OrganizationDTO> getOrganizations(OrganizationSearchCriteria organizationSearchCriteria, Pageable pageable) {
		organizationSearchCriteria.setCurrentUser(AuthorizedRequestContext.getCurrentUser());
		return organizationManager.getOrganizations(organizationSearchCriteria, pageable).map(organizationMapper::organizationToOrganizationDTO);
	}

	@Override
	public OrganizationDTO getOrganization(Long organizationId) throws PocketException {
		OrganizationSearchCriteria organizationSearchCriteria = OrganizationSearchCriteria.builder().id(organizationId).build();
		return organizationMapper.organizationToOrganizationDTO(organizationManager.getOrganization(organizationSearchCriteria));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrganizationDTO createOrganization(OrganizationDTO organizationDTO) throws PocketException {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		if (permissionCheckerService.checkUserHasRole(currentUser, RoleEnum.STUDENT)) {
			log.warn("Create organization failed: user {} doesn't have permission to create organization!", currentUser.getId());
			throw new PocketException(PocketError.UNAUTHORIZED);
		}

		return organizationMapper.organizationToOrganizationDTO(organizationManager.createOrganization(organizationDTO));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrganizationDTO updateOrganization(Long organizationId, OrganizationDTO organizationDTO) throws PocketException {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		OrganizationSearchCriteria organizationSearchCriteria = OrganizationSearchCriteria.builder().id(organizationId).build();
		Organization organization = organizationManager.getOrganization(organizationSearchCriteria);

		if (!permissionCheckerService.checkUserHasRole(currentUser, RoleEnum.SYSTEM_ADMIN) && !permissionCheckerService.checkUserHasOrganizationRole(currentUser, organization, OrganizationRoleEnum.ADMIN)) {
			log.warn("Create organization failed: user {} doesn't have permission to create organization!", currentUser.getId());
			throw new PocketException(PocketError.UNAUTHORIZED);
		}

		organizationManager.updateOrganization(organization, organizationDTO);

		return organizationMapper.organizationToOrganizationDTO(organization);
	}
}
