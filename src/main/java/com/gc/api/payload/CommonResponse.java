package com.gc.api.payload;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gc.api.payload.status.ErrorReason;
import com.gc.api.payload.status.SuccessStatus;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class CommonResponse<T> {

	private static final ObjectMapper mapper = new ObjectMapper();

	@JsonProperty("isSuccess")
	private final Boolean isSuccess;
	private final String code;
	private final String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final T result;

	// 요청 성공 - 201 OK
	public static <T> CommonResponse<T> onSuccess(T result) {
		return new CommonResponse<>(true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getCode(), result);
	}

	// 리소스 생성 - 201 CREATED
	public static <T> CommonResponse<T> created(T result) {
		return new CommonResponse<>(true, SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(),
			result);
	}

	// 요청 접수 - 202 ACCEPTED
	public static <T> CommonResponse<T> accepted(T result) {
		return new CommonResponse<>(true, SuccessStatus._ACCEPTED.getCode(), SuccessStatus._ACCEPTED.getMessage(),
			result);
	}

	// 요청 실패 - ExceptionAdvice에서 사용
	public static <T> CommonResponse<T> onFailure(ErrorReason errorReason, T data) {
		return new CommonResponse<>(false, errorReason.getCode(), errorReason.getMessage(), data);
	}

	public static void setErrorResponse(HttpServletResponse response,
		ErrorReason errorReason, String message) throws IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setStatus(errorReason.getHttpStatus().value());

		CommonResponse<String> res = CommonResponse
			.onFailure(errorReason, message);
		response.getWriter().write(mapper.writeValueAsString(res));
	}
}
