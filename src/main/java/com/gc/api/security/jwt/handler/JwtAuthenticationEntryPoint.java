package com.gc.api.security.jwt.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.gc.api.payload.CommonResponse;
import com.gc.api.payload.status.CommonErrorStatus;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {

		CommonResponse.setErrorResponse(response, CommonErrorStatus._UNAUTHORIZED, "액세스 토큰을 입력해 주세요.");
	}
}
