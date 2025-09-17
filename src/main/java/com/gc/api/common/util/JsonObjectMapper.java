package com.gc.api.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gc.api.payload.exception.GeneralException;
import com.gc.api.payload.status.CommonErrorStatus;

public class JsonObjectMapper {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new GeneralException(CommonErrorStatus.JSON_PARSE_ERROR);
		}
	}
}
