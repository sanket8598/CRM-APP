package ai.rnt.crm.validation;

import static java.util.Arrays.stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ai.rnt.crm.enums.TaskPriority;

public class TaskPriorityValidator implements ConstraintValidator<ValidTaskPriority, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return stream(TaskPriority.values()).anyMatch(priority -> priority.getPriority().equalsIgnoreCase(value));
	}

}
