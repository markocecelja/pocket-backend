package com.mcecelja.pocket.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.domain.user.UserLogin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

	private final Long id;

	private final String username;

	@JsonIgnore
	private final String password;


	private final Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrincipal create(UserLogin userLogin) {
		User user = userLogin.getUser();

		List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
				new SimpleGrantedAuthority(role.getName())
		).collect(Collectors.toList());

		return new UserPrincipal(
				user.getId(),
				userLogin.getUsername(),
				userLogin.getPassword(),
				authorities
		);
	}

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
