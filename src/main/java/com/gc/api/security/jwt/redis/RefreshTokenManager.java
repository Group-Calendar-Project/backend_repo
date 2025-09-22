package com.gc.api.security.jwt.redis;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.gc.api.security.jwt.provider.JwtProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenManager {

	private static final String REFRESH_TOKEN_PREFIX = "RT:";
	private final StringRedisTemplate stringRedisTemplate;
	private final JwtProvider jwtProvider;

	public void saveRefreshToken(String loginId, String refreshToken) {
		long expirationMillis = jwtProvider.getExpiration(refreshToken);
		long nowMillis = System.currentTimeMillis();
		long durationMillis = expirationMillis - nowMillis;

		String key = REFRESH_TOKEN_PREFIX + loginId;
		stringRedisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(durationMillis));
	}

	public boolean findRefreshToken(String loginId) {
		String key = REFRESH_TOKEN_PREFIX + loginId;
		return stringRedisTemplate.hasKey(key);
	}

	public String getRefreshToken(String loginId) {
		String key = REFRESH_TOKEN_PREFIX + loginId;
		return stringRedisTemplate.opsForValue().get(key);
	}

	public void deleteRefreshToken(String loginId) {
		String key = REFRESH_TOKEN_PREFIX + loginId;
		stringRedisTemplate.delete(key);
	}
}
