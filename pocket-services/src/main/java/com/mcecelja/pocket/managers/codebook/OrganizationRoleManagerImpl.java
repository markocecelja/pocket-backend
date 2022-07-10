package com.mcecelja.pocket.managers.codebook;

import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.AbstractCodeBookEntity;
import com.mcecelja.pocket.domain.organization.codebook.OrganizationRole;
import com.mcecelja.pocket.repositories.organization.codebook.OrganizationRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("organizationRoleManagerImpl")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationRoleManagerImpl implements CodeBookManager {

	private final OrganizationRoleRepository organizationRoleRepository;

	@Override
	public AbstractCodeBookEntity getCodeBookEntity(Long id) throws PocketException {

		Optional<OrganizationRole> organizationRoleOptional = organizationRoleRepository.findById(id);

		if (!organizationRoleOptional.isPresent()) {
			log.warn("Organization role {} doesn't exist!", id);
			throw new PocketException(PocketError.NON_EXISTING_ORGANIZATION_ROLE);
		}

		return organizationRoleOptional.get();
	}
}
