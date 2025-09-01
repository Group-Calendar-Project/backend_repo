package com.gc.api.common.validation.validator;

import com.gc.api.common.validation.annotation.ValidSize;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SizeValidator implements ConstraintValidator<ValidSize, Integer> {

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return value != null && value >= 1;
	}
}
