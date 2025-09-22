package com.gc.api.security.jwt.redis;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.gc.api.security.jwt.provider.JwtProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LogoutAccessTokenManager {

	private static final String LOGOUT_ACCESS_TOKEN_PREFIX = "Logout:";
	private final StringRedisTemplate stringRedisTemplate;
	private final JwtProvider jwtProvider;

	public void saveLogoutAccessToken(String loginId, String accessToken) {
		long expirationMillis = jwtProvider.getExpiration(accessToken);
		long nowMillis = System.currentTimeMillis();
		long durationMillis = expirationMillis - nowMillis;

		String key = LOGOUT_ACCESS_TOKEN_PREFIX + loginId;
		stringRedisTemplate.opsForValue().set(key, accessToken, Duration.ofMillis(durationMillis));
	}

	public boolean findLogoutAccessToken(String loginId) {
		String key = LOGOUT_ACCESS_TOKEN_PREFIX + loginId;
		return stringRedisTemplate.hasKey(key);
	}

	public String getLogoutAccessToken(String loginId) {
		String key = LOGOUT_ACCESS_TOKEN_PREFIX + loginId;
		return stringRedisTemplate.opsForValue().get(key);
	}

	public void deleteLogoutAccessToken(String loginId) {
		String key = LOGOUT_ACCESS_TOKEN_PREFIX + loginId;
		stringRedisTemplate.delete(key);
	}
}
