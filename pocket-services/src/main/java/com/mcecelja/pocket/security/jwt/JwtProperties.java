package com.mcecelja.pocket.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtProperties {

	String secret;

	long expirationInMs;

	String header;

	String bearer;

	String sid;
}
