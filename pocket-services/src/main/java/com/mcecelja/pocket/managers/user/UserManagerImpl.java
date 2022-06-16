package com.mcecelja.pocket.managers.user;

import com.mcecelja.pocket.common.dto.authentication.RegistrationRequestDTO;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.domain.user.UserLogin;
import com.mcecelja.pocket.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerImpl implements UserManager {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User createUser(RegistrationRequestDTO registrationRequestDTO) {

		User user = new User();

		user.setFirstName(registrationRequestDTO.getFirstName());
		user.setLastName(registrationRequestDTO.getLastName());
		user.setUserLogin(new UserLogin(null, registrationRequestDTO.getUsername(), bCryptPasswordEncoder.encode(registrationRequestDTO.getPassword()), user));

		userRepository.save(user);

		return user;
	}
}
