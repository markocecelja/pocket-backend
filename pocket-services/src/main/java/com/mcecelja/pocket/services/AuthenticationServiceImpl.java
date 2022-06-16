package com.mcecelja.pocket.services;

import com.mcecelja.pocket.common.dto.authentication.LoginResponseDTO;
import com.mcecelja.pocket.common.dto.authentication.RegistrationRequestDTO;
import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.domain.user.UserLogin;
import com.mcecelja.pocket.managers.user.UserManager;
import com.mcecelja.pocket.repositories.user.UserLoginRepository;
import com.mcecelja.pocket.security.UserPrincipal;
import com.mcecelja.pocket.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserLoginRepository userLoginRepository;

	private final UserService userService;

	private final UserManager userManager;

	private final AuthenticationManager authenticationManager;

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public LoginResponseDTO authenticateUser(String username, String password) throws PocketException {

		User user = userService.findUserByUsername(username);

		if (user == null) {
			log.warn("Bad credentials!");
			throw new PocketException(PocketError.BAD_CREDENTIALS);
		}

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							username,
							password
					)
			);

			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			String jwt = jwtTokenProvider.generateSessionToken(userPrincipal.getId());
			return new LoginResponseDTO(jwt);
		} catch (Exception e) {
			log.warn("Bad credentials!");
			throw new PocketException(PocketError.BAD_CREDENTIALS);
		}
	}

	@Override
	public void registerUser(RegistrationRequestDTO registrationRequestDTO) throws PocketException {

		if (userLoginRepository.existsByUsernameIgnoreCase(registrationRequestDTO.getUsername())) {
			log.warn("User registration failed: username already in use!");
			throw new PocketException(PocketError.USERNAME_ALREADY_IN_USE);
		}

		if (!registrationRequestDTO.getPassword().equals(registrationRequestDTO.getConfirmationPassword())) {
			log.warn("User registration failed: password mismatch!");
			throw new PocketException(PocketError.PASSWORD_MISMATCH);
		}

		userManager.createUser(registrationRequestDTO);
	}
}
