package com.mcecelja.pocket.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;

@Component
public class JwtUtil {

	private final JwtProperties jwtProperties;

	@Autowired
	public JwtUtil(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	public Long getUserIdFromSessionToken(String token) {
		return Long.parseLong(Jwts.parser().setSigningKey(getSessionEncodedKey()).parseClaimsJws(token).getBody().getSubject());
	}

	public boolean validateSessionToken(String token) {
		try {
			Jwts.parser().setSigningKey(getSessionEncodedKey()).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new JwtException("Expired or invalid JWT token");
		}
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader(jwtProperties.header);
		String sid = req.getCookies() != null ? Arrays.stream(req.getCookies())
				.filter(c -> c.getName().equals(jwtProperties.sid))
				.findFirst()
				.map(Cookie::getValue)
				.orElse(null) : null;

		if (bearerToken != null && bearerToken.startsWith(jwtProperties.bearer)) {
			return bearerToken.substring(7);
		} else return sid;

	}

	private String getSessionEncodedKey() {
		return Base64.getEncoder().encodeToString(jwtProperties.secret.getBytes());
	}
}
