package com.gc.api.apiPayload.exception;

import com.gc.api.apiPayload.status.ErrorStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

	private final ErrorStatus errorStatus;
}
