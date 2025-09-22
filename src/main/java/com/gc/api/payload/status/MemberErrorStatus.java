package com.gc.api.payload.status;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorStatus implements ErrorReason {

	EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "해당 이메일 사용자를 찾을 수 없습니다."),

	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
