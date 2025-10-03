package com.gc.api.security.auth.service;

import com.gc.api.member.domain.Member;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
	Member logout(HttpServletRequest request);

	void inactivateMember(HttpServletRequest request);
}
