package com.mcecelja.pocket.services;

import com.mcecelja.pocket.common.dto.authentication.LoginResponseDTO;
import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.security.UserPrincipal;
import com.mcecelja.pocket.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserService userService;

	private final JwtTokenProvider jwtTokenProvider;

	private final AuthenticationManager authenticationManager;

	@Override
	public LoginResponseDTO authenticateUser(String username, String password) throws PocketException {
		try {
			User user = userService.findUserByUsername(username);

			if (user != null) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								username,
								password
						)
				);

				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				String jwt = jwtTokenProvider.generateSessionToken(userPrincipal.getId());
				return new LoginResponseDTO(jwt);

			} else {
				log.warn("Bad credentials!");
				throw new PocketException(PocketError.BAD_CREDENTIALS);
			}
		} catch (Exception e) {
			log.warn("Bad credentials!");
			throw new PocketException(PocketError.BAD_CREDENTIALS);
		}
	}
}
