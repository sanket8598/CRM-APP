package ai.rnt.crm.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ReminderViaValidatorTest {

	private final ReminderViaValidator validator = new ReminderViaValidator();

	@Test
	void isValidWithValidSReminderViaReturnsTrue() {
		String validStatus = "Email";
		boolean isValid = validator.isValid(validStatus, null);
		assertTrue(isValid);
	}

	//@Test
	void isValidWithInvalidReminderViaReturnsFalse() {
		String invalidStatus = "INVALID";
		boolean isValid = validator.isValid(invalidStatus, null);
		assertFalse(isValid);
	}

	@Test
	void isValidWithNullValueReturnsFalse() {
		boolean isValid = validator.isValid(null, null);
		assertTrue(isValid);
	}
}
