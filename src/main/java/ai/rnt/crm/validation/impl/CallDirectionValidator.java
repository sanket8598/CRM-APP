package ai.rnt.crm.validation.impl;

import static ai.rnt.crm.enums.CallDirection.values;
import static java.util.Arrays.stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ai.rnt.crm.validation.ValidCallDirection;

public class CallDirectionValidator implements ConstraintValidator<ValidCallDirection, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return stream(values()).anyMatch(direction -> direction.getDirection().equalsIgnoreCase(value));
	}
}
