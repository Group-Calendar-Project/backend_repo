package com.gc.api.security.jwt.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gc.api.common.enums.SocialProvider;
import com.gc.api.payload.exception.GeneralException;
import com.gc.api.payload.status.JwtErrorStatus;
import com.gc.api.security.jwt.provider.JwtProvider;
import com.gc.api.security.jwt.redis.LogoutAccessTokenManager;
import com.gc.api.security.jwt.token.CustomAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
	private final AuthenticationManager authenticationManager;
	private final LogoutAccessTokenManager logoutAccessTokenManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		log.debug("requestURI: {}, httpMethod: {}", request.getRequestURI(), request.getMethod());

		String token = jwtProvider.resolveToken(request);

		if (StringUtils.hasText(token)) {
			String subject = jwtProvider.getSubject(token);
			SocialProvider socialProvider = jwtProvider.getSocialProvider(token);

			if (logoutAccessTokenManager.findLogoutAccessToken(subject)) {
				throw new GeneralException(JwtErrorStatus.LOGOUT_ACCESS_TOKEN);
			}

			CustomAuthenticationToken authRequest = new CustomAuthenticationToken(token, socialProvider);
			Authentication authResponse = authenticationManager.authenticate(authRequest);

			SecurityContextHolder.getContext().setAuthentication(authResponse);
		}

		filterChain.doFilter(request, response);
	}
}
