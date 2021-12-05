package com.mcecelja.pocket.services;

import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.domain.user.UserLogin;
import com.mcecelja.pocket.repositories.user.UserLoginRepository;
import com.mcecelja.pocket.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userService")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserLoginRepository userLoginRepository;

	@Override
	public UserDetails findUserDetailsByUserId(Long userId) {
		UserLogin userLogin = userLoginRepository.findUserLoginByUserId(userId);
		return UserPrincipal.create(userLogin);
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		UserLogin userLogin = userLoginRepository.findUserLoginByUsernameIgnoreCase(s);
		return UserPrincipal.create(userLogin);
	}

	@Override
	public User findUserByUsername(String username) {
		UserLogin userLogin = userLoginRepository.findUserLoginByUsernameIgnoreCase(username);

		if (userLogin == null) {
			return null;
		}

		return userLogin.getUser();
	}
}
