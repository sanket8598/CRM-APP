package ai.rnt.crm.validation;

import static java.util.Arrays.stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ai.rnt.crm.enums.ReminderVia;

public class ReminderViaValidator implements ConstraintValidator<ValidReminderVia, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		else
			return stream(ReminderVia.values())
					.anyMatch(reminderVia -> reminderVia.getReminderVia().equalsIgnoreCase(value));
	}
}
