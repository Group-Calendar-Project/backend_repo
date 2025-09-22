package com.gc.api.security.jwt.filter;

import java.io.IOException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gc.api.payload.CommonResponse;
import com.gc.api.payload.exception.GeneralException;
import com.gc.api.payload.status.CommonErrorStatus;
import com.gc.api.payload.status.MemberErrorStatus;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (GeneralException e) {
			CommonResponse.setErrorResponse(response, e.getErrorReason(), e.getMessage());
		} catch (UsernameNotFoundException e) {
			CommonResponse.setErrorResponse(response, MemberErrorStatus.EMAIL_NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			log.error("Unexpected error: {}", e.getMessage());
			CommonResponse.setErrorResponse(response, CommonErrorStatus._INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
