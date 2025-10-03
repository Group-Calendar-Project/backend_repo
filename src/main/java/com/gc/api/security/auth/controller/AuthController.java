package com.gc.api.security.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gc.api.payload.CommonResponse;
import com.gc.api.security.auth.docs.AuthControllerDocs;
import com.gc.api.security.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController implements AuthControllerDocs {

	private final AuthService authService;

	@PostMapping("/logout")
	public ResponseEntity<CommonResponse<Object>> logout(HttpServletRequest request) {
		authService.logout(request);
		return ResponseEntity.ok().body(CommonResponse.onSuccess(null));
	}

	@PatchMapping("/me")
	public ResponseEntity<CommonResponse<Object>> deleteMember(HttpServletRequest request) {
		authService.inactivateMember(request);
		return ResponseEntity.ok().body(CommonResponse.onSuccess(null));
	}
}
