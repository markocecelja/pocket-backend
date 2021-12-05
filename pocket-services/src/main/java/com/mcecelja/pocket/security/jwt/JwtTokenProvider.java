package com.mcecelja.pocket.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

	private final JwtProperties jwtProperties;

	@Autowired
	public JwtTokenProvider(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	public String generateSessionToken(Long userId) {

		Claims claims = Jwts.claims().setSubject(Long.toString(userId));

		Date validFrom = new Date();
		Date validTo = new Date(validFrom.getTime() + jwtProperties.expirationInMs);

		String encodedSecretKey = Base64.getEncoder().encodeToString(jwtProperties.secret.getBytes());

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(validFrom)
				.setExpiration(validTo)
				.signWith(SignatureAlgorithm.HS256, encodedSecretKey)
				.compact();
	}
}
