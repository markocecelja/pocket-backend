package com.mcecelja.pocket.managers.codebook;

import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.AbstractCodeBookEntity;
import com.mcecelja.pocket.domain.user.codebook.Role;
import com.mcecelja.pocket.repositories.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("roleManagerImpl")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleManagerImpl implements CodeBookManager {

	private final RoleRepository roleRepository;

	@Override
	public AbstractCodeBookEntity getCodeBookEntity(Long id) throws PocketException {

		Optional<Role> roleOptional = roleRepository.findById(id);

		if (!roleOptional.isPresent()) {
			log.warn("Role {} doesn't exist!", id);
			throw new PocketException(PocketError.NON_EXISTING_ROLE);
		}

		return roleOptional.get();
	}
}
