package com.mcecelja.pocket.services;

import com.mcecelja.pocket.domain.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

	UserDetails findUserDetailsByUserId(Long userId);

	User findUserByUsername(String username);
}
