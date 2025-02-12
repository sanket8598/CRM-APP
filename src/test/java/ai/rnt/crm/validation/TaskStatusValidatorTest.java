package ai.rnt.crm.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaskStatusValidatorTest {

	private final TaskStatusValidator validator = new TaskStatusValidator();

	@Test
	void isValidWithValidStatusReturnsTrue() {
		String validStatus = "Not Started";
		boolean isValid = validator.isValid(validStatus, null);
		assertTrue(isValid);
	}

	@Test
	void isValidWithInvalidStatusReturnsFalse() {
		String invalidStatus = "INVALID";
		boolean isValid = validator.isValid(invalidStatus, null);
		assertFalse(isValid);
	}

	@Test
	void isValidWithNullValueReturnsFalse() {
		boolean isValid = validator.isValid(null, null);
		assertFalse(isValid);
	}
}
