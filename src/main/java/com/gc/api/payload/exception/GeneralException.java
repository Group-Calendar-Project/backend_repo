package com.gc.api.payload.exception;

import com.gc.api.payload.status.ErrorStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

	private final ErrorStatus errorStatus;
}
