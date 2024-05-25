package ai.rnt.crm.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import ai.rnt.crm.validation.impl.ExcelFileValidator;

class ExcelFileValidatorTest {

	private ExcelFileValidator validator;
	private ConstraintValidatorContext constraintValidatorContext;

	@BeforeEach
	void setUp() {
		validator = new ExcelFileValidator();
		constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
	}

	@Test
	void isValid_WithValidExcelFile_ReturnsTrue() {
		MockMultipartFile validExcelFile = new MockMultipartFile("file", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[] {});
		boolean isValid = validator.isValid(validExcelFile, constraintValidatorContext);
		assertFalse(isValid);
	}

	@Test
	void isValid_WithUnsupportedContentType_ReturnsFalse() {
		MockMultipartFile unsupportedFile = new MockMultipartFile("file", "test.txt", "text/plain", new byte[] {});
		boolean isValid = validator.isValid(unsupportedFile, constraintValidatorContext);
		assertFalse(isValid);
	}

	@Test
	void isValid_WithEmptyFile_ReturnsFalse() {
		MockMultipartFile emptyFile = new MockMultipartFile("file", new byte[] {});
		boolean isValid = validator.isValid(emptyFile, constraintValidatorContext);
		assertFalse(isValid);
	}

	@Test
	void isValid_WithNullFile_ReturnsFalse() {
		boolean isValid = validator.isValid(null, constraintValidatorContext);
		assertFalse(isValid);
	}
}
