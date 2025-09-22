package com.gc.api.payload.status;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtErrorStatus implements ErrorReason {

	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4001", "유효하지 않은 토큰입니다."),
	REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "TOKEN4002", "리프레시 토큰을 입력해야 합니다."),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4003", "만료된 토큰입니다."),
	LOGOUT_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4004", "로그아웃한 액세스 토큰입니다."),
	MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4005", "토큰 구조가 잘못됐습니다."),
	UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4006", "지원하지 않는 토큰 형식입니다."),
	INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "TOKEN4007", "토큰의 서명이 잘못됐습니다."),
	TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "TOKEN4008", "토큰이 없습니다."),
	WRONG_SIGNATURE(HttpStatus.BAD_REQUEST, "TOKEN4009", "서명이 유효하지 않습니다."),
	TOKEN_RESOLVE_FAIL(HttpStatus.BAD_REQUEST, "TOKEN4010", "헤더에서 토큰을 추출하지 못했습니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
