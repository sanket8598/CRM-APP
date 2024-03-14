package ai.rnt.crm.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CallDirectionValidatorTest {

	private CallDirectionValidator validator;
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
        validator = new CallDirectionValidator();
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
    }

    @Test
    void isValid_WithValidDirection_ReturnsTrue() {
        String validDirection = "Incoming";
        boolean isValid = validator.isValid(validDirection, constraintValidatorContext);
        assertTrue(isValid);
    }

    @Test
    void isValid_WithInvalidDirection_ReturnsFalse() {
        String invalidDirection = "INVALID_DIRECTION";
        boolean isValid = validator.isValid(invalidDirection, constraintValidatorContext);
        assertFalse(isValid);
    }

    @Test
    void isValid_WithNullDirection_ReturnsFalse() {
        String nullDirection = null;
        boolean isValid = validator.isValid(nullDirection, constraintValidatorContext);
        assertFalse(isValid);
    }

    @Test
    void isValid_WithEmptyDirection_ReturnsFalse() {
        String emptyDirection = "";
        boolean isValid = validator.isValid(emptyDirection, constraintValidatorContext);
        assertFalse(isValid);
    }
}
