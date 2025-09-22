package com.gc.api.security.jwt.dto;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt.token")
public record JwtProperties(
	String secretKey,
	Duration accessTokenExpiration,
	Duration refreshTokenExpiration) {
}
