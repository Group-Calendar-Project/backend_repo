package com.gc.api.security.jwt.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.gc.api.payload.CommonResponse;
import com.gc.api.payload.status.CommonErrorStatus;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {

		CommonResponse.setErrorResponse(response, CommonErrorStatus._FORBIDDEN, "권한이 없는 사용자의 접근입니다.");
	}
}
