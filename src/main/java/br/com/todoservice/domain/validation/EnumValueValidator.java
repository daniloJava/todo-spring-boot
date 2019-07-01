package br.com.todoservice.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class EnumValueValidator implements ConstraintValidator<Enum, String> {

	private Enum annotation;

	@Override
	public void initialize(Enum annotation) {
		this.annotation = annotation;
	}

	@Override
	public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
		boolean result = false;

		Object[] enumValues = annotation.enumClass().getEnumConstants();

		if (annotation.ignoreEmpty() && StringUtils.isBlank(valueForValidation)) {
			return true;
		}

		if (enumValues != null) {
			for (Object enumValue : enumValues) {
				if (StringUtils.equals(valueForValidation, enumValue.toString()) || annotation.ignoreCase() && StringUtils.equalsIgnoreCase(valueForValidation, enumValue.toString())) {
					result = true;
					break;
				}
			}
		}

		return result;
	}
}