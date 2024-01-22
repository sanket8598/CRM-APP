package ai.rnt.crm.validation;

import static ai.rnt.crm.enums.CallDirection.values;
import static java.util.Arrays.stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CallDirectionValidator implements ConstraintValidator<ValidCallDirection, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return stream(values()).anyMatch(direction -> direction.getDirection().equalsIgnoreCase(value));
	}
}
