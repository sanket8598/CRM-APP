package ai.rnt.crm.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.rnt.crm.validation.impl.DisqualifiedAsStatusValidator;

class DisqualifiedAsStatusValidatorTest {

	private DisqualifiedAsStatusValidator validator;
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
        validator = new DisqualifiedAsStatusValidator();
        constraintValidatorContext =mock(ConstraintValidatorContext.class);
    }

    @Test
    void isValid_WithValidStatus_ReturnsTrue() {
        String validStatus = "Lost";
        boolean isValid = validator.isValid(validStatus, constraintValidatorContext);
        assertTrue(isValid);
    }

    @Test
    void isValid_WithInvalidStatus_ReturnsFalse() {
        String invalidStatus = "INVALID_STATUS";
        boolean isValid = validator.isValid(invalidStatus, constraintValidatorContext);
        assertFalse(isValid);
    }

    @Test
    void isValid_WithNullStatus_ReturnsTrue() {
        String nullStatus = null;
        boolean isValid = validator.isValid(nullStatus, constraintValidatorContext);
        assertTrue(isValid);
    }

    @Test
    void isValid_WithEmptyStatus_ReturnsTrue() {
        String emptyStatus = "";
        boolean isValid = validator.isValid(emptyStatus, constraintValidatorContext);
        assertFalse(isValid);
    }
}
