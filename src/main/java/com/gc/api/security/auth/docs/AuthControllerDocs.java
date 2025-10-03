package com.gc.api.security.auth.docs;

import org.springframework.http.ResponseEntity;

import com.gc.api.payload.CommonResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Auth", description = "인증/인가 API")
public interface AuthControllerDocs {

	@Operation(
		summary = "로그아웃 API",
		description = "사용자의 액세스 토큰과 리프레시 토큰을 블랙리스트화하는 API",
		responses = {
			@ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
			@ApiResponse(responseCode = "MEMBER4001", description = "해당 이메일 사용자가 존재하지 않습니다.")
		}
	)
	ResponseEntity<CommonResponse<Object>> logout(HttpServletRequest request);

	@Operation(
		summary = "회원탈퇴 API",
		description = "사용자의 액세스 토큰과 리프레시 토큰을 블랙리스트화하고, 사용자를 비활성화하는 API",
		responses = {
			@ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
			@ApiResponse(responseCode = "MEMBER4001", description = "해당 이메일 사용자가 존재하지 않습니다.")
		}
	)
	ResponseEntity<CommonResponse<Object>> deleteMember(HttpServletRequest request);
}
