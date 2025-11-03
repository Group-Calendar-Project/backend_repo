package com.gc.api.security.jwt.redis;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.gc.api.security.jwt.enums.RedisTokenType;
import com.gc.api.security.jwt.provider.JwtProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenManager {

	private final JwtProvider jwtProvider;
	private final StringRedisTemplate stringRedisTemplate;

	private String getTokenPrefix(RedisTokenType tokenType, String loginId, String token) {
		String socialProvider = jwtProvider.getSocialProvider(token).name();
		return tokenType.name() + ":" + socialProvider + ":" + loginId;
	}

	public void saveToken(RedisTokenType tokenType, String loginId, String token) {
		long expirationMillis = jwtProvider.getExpiration(token);
		long nowMillis = System.currentTimeMillis();
		long durationMillis = expirationMillis - nowMillis;

		String key = getTokenPrefix(tokenType, loginId, token);
		stringRedisTemplate.opsForValue().set(key, token, Duration.ofMillis(durationMillis));
	}

	public boolean findToken(RedisTokenType tokenType, String loginId, String token) {
		String key = getTokenPrefix(tokenType, loginId, token);
		return stringRedisTemplate.hasKey(key);
	}

	public String getToken(RedisTokenType tokenType, String loginId, String token) {
		String key = getTokenPrefix(tokenType, loginId, token);
		return stringRedisTemplate.opsForValue().get(key);
	}

	public void removeToken(RedisTokenType tokenType, String loginId, String token) {
		String key = getTokenPrefix(tokenType, loginId, token);
		stringRedisTemplate.delete(key);
	}
}
