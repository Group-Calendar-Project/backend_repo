package com.gc.api.security.jwt.provider;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.gc.api.common.enums.SocialProvider;
import com.gc.api.payload.exception.GeneralException;
import com.gc.api.payload.status.JwtErrorStatus;
import com.gc.api.security.jwt.dto.JwtProperties;
import com.gc.api.security.jwt.userDetails.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

	private final Long accessTokenExpiredMS;
	private final Long refreshTokenExpiredMS;
	private final Key signingKey;

	public JwtProvider(JwtProperties jwtProperties) {
		this.accessTokenExpiredMS = jwtProperties.accessTokenExpiration().toMillis();
		this.refreshTokenExpiredMS = jwtProperties.refreshTokenExpiration().toMillis();
		this.signingKey = Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes());
	}

	private String createToken(String subject, Long userId, String nickname, String socialProvider,
		Collection<? extends GrantedAuthority> authorities, Long expirationMS) {
		Claims claims = Jwts.claims().setSubject(subject);
		claims.put("userId", userId);
		if (nickname != null) {
			claims.put("nickname", nickname);
		}
		if (socialProvider != null) {
			claims.put("socialProvider", socialProvider);
		}
		if (authorities != null) {
			claims.put("authorities",
				authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		}

		Date now = new Date();
		Date expiration = new Date(now.getTime() + expirationMS);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expiration)
			.signWith(signingKey)
			.compact();
	}

	public String createAccessToken(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		String subject = userDetails.getUsername();
		Long userId = userDetails.getId();
		String nickname = userDetails.getNickname();
		String socialProvider = userDetails.getSocialProvider().name();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		return createToken(subject, userId, nickname, socialProvider, authorities, accessTokenExpiredMS);
	}

	public String createRefreshToken(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		String subject = userDetails.getUsername();
		Long userId = userDetails.getId();

		return createToken(subject, userId, null, null, null, refreshTokenExpiredMS);
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.EXPIRED_TOKEN);
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.UNSUPPORTED_TOKEN);
		} catch (MalformedJwtException e) {
			log.error("Malformed JWT token: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.MALFORMED_TOKEN);
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty or null: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.TOKEN_NOT_FOUND);
		} catch (SignatureException e) {
			log.error("Wrong JWT Signature: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.WRONG_SIGNATURE);
		} catch (JwtException e) {
			log.error("Unhandled JWT exception: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.INVALID_TOKEN);
		}
	}

	public SocialProvider getSocialProvider(String token) {
		return parseClaims(token).get("socialProvider", SocialProvider.class);
	}

	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}

	public Long getExpiration(String token) {
		return parseClaims(token).getExpiration().getTime();
	}
}
