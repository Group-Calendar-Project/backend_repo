package com.gc.api.security.auth.service;

import com.gc.api.member.domain.Member;
import com.gc.api.security.auth.dto.AuthResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
	Member logout(HttpServletRequest request);

	void inactivateMember(HttpServletRequest request);

	AuthResponseDTO.LoginResultDTO reissue(String refreshToken, HttpServletResponse response);
}
