package ai.rnt.crm.validation;

import static java.util.Arrays.stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ai.rnt.crm.enums.TaskStatus;

public class TaskStatusValidator implements ConstraintValidator<ValidTaskStatus, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return stream(TaskStatus.values()).anyMatch(status -> status.getStatus().equalsIgnoreCase(value));
	}

}
