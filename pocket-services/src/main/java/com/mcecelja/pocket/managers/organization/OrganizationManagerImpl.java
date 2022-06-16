package com.mcecelja.pocket.managers.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.OrganizationCode;
import com.mcecelja.pocket.domain.user.codebook.RoleEnum;
import com.mcecelja.pocket.repositories.organization.OrganizationCodeRepository;
import com.mcecelja.pocket.repositories.organization.OrganizationRepository;
import com.mcecelja.pocket.services.common.PermissionCheckerService;
import com.mcecelja.pocket.specification.OrganizationSearchSpecification;
import com.mcecelja.pocket.specification.criteria.OrganizationSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class OrganizationManagerImpl implements OrganizationManager {

	private final OrganizationRepository organizationRepository;

	private final OrganizationCodeRepository organizationCodeRepository;

	private final PermissionCheckerService permissionCheckerService;

	@Override
	public Page<Organization> getOrganizations(OrganizationSearchCriteria organizationSearchCriteria, Pageable pageable) {
		return organizationRepository.findAll(OrganizationSearchSpecification.findOrganizations(organizationSearchCriteria), pageable);
	}

	@Override
	public Organization getOrganization(OrganizationSearchCriteria criteria) throws PocketException {

		criteria.setCurrentUser(AuthorizedRequestContext.getCurrentUser());

		Optional<Organization> organizationOptional = organizationRepository.findOne(OrganizationSearchSpecification.findOrganizations(criteria));

		if (!organizationOptional.isPresent()) {
			log.warn("Organization doesn't exist!");
			throw new PocketException(PocketError.NON_EXISTING_ORGANIZATION);
		}

		return organizationOptional.get();
	}

	@Override
	public Organization createOrganization(OrganizationDTO organizationDTO) {

		Organization organization = new Organization();
		organization.setName(organizationDTO.getName());
		organization.setDescription(organizationDTO.getDescription());
		organization.setActive(true);

		String code;

		do {
			code = RandomStringUtils.random(10, true, true);
		} while (organizationCodeRepository.existsByValue(code));

		OrganizationCode organizationCode = new OrganizationCode(null, code, LocalDateTime.now().plusDays(30), organization);
		organization.setOrganizationCode(organizationCode);

		organizationRepository.save(organization);

		return organization;
	}

	@Override
	public void updateOrganization(Organization organization, OrganizationDTO organizationDTO) {

		organization.setName(organizationDTO.getName());
		organization.setDescription(organizationDTO.getDescription());

		if (permissionCheckerService.checkUserHasRole(AuthorizedRequestContext.getCurrentUser(), RoleEnum.SYSTEM_ADMIN)) {

			if(!organizationDTO.isActive()) {
				organization.getPosts().forEach(post -> post.setActive(false));
			}

			organization.setActive(organizationDTO.isActive());
		}

		organizationRepository.save(organization);
	}
}
