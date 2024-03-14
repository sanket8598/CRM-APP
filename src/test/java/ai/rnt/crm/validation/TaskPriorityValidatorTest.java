package ai.rnt.crm.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskPriorityValidatorTest {

	private final TaskPriorityValidator validator = new TaskPriorityValidator();

    @Test
    void isValid_WithValidPriority_ReturnsTrue() {
        String validPriority = "HIGH";
        boolean isValid = validator.isValid(validPriority, null);
        assertTrue(isValid);
    }

    @Test
    void isValid_WithInvalidPriority_ReturnsFalse() {
        String invalidPriority = "INVALID";
        boolean isValid = validator.isValid(invalidPriority, null);
        assertFalse(isValid);
    }

    @Test
    void isValid_WithNullValue_ReturnsFalse() {
        boolean isValid = validator.isValid(null, null);
        assertFalse(isValid);
    }
}
