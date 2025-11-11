package com.gc.api.security.jwt.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.gc.api.payload.status.CommonErrorStatus;
import com.gc.api.security.auth.service.AuthResponseWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final AuthResponseWriter authResponseWriter;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {

		authResponseWriter.setErrorResponse(response, CommonErrorStatus._FORBIDDEN, "권한이 없는 사용자의 접근입니다.");
	}
}
