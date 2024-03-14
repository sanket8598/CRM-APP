package ai.rnt.crm.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskStatusValidatorTest {

	private final TaskStatusValidator validator = new TaskStatusValidator();

	@Test
	void isValid_WithValidStatus_ReturnsTrue() {
		String validStatus = "Not Started";
		boolean isValid = validator.isValid(validStatus, null);
		assertTrue(isValid);
	}

	@Test
	void isValid_WithInvalidStatus_ReturnsFalse() {
		String invalidStatus = "INVALID";
		boolean isValid = validator.isValid(invalidStatus, null);
		assertFalse(isValid);
	}

	@Test
	void isValid_WithNullValue_ReturnsFalse() {
		boolean isValid = validator.isValid(null, null);
		assertFalse(isValid);
	}
}
