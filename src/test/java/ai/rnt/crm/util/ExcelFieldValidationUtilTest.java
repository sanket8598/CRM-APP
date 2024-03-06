package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ExcelFieldValidationUtilTest {

	@Test
	void testValidEmail() {
		String validEmail = "test@example.com";
		assertTrue(ExcelFieldValidationUtil.isValidEmail(validEmail));
	}

	@Test
	void testInvalidEmail() {
		String invalidEmail = "invalid-email";
		assertFalse(ExcelFieldValidationUtil.isValidEmail(invalidEmail));
	}

	@Test
	void testNullEmail() {
		assertFalse(ExcelFieldValidationUtil.isValidEmail(null));
	}

	@Test
	void testEmptyEmail() {
		String emptyEmail = "";
		assertFalse(ExcelFieldValidationUtil.isValidEmail(emptyEmail));
	}

	@Test
	void testValidName() {
		String validName = "John Doe";
		assertFalse(ExcelFieldValidationUtil.isValidFnameLname(validName));
	}

	@Test
	void testInvalidName() {
		String invalidName = "1234";
		assertFalse(ExcelFieldValidationUtil.isValidFnameLname(invalidName));
	}

	@Test
	void testNullName() {
		assertFalse(ExcelFieldValidationUtil.isValidFnameLname(null));
	}

	@Test
	void testEmptyName() {
		String emptyName = "";
		assertFalse(ExcelFieldValidationUtil.isValidFnameLname(emptyName));
	}

	@Test
	void testValidString() {
		String validString = "Hello World";
		assertTrue(ExcelFieldValidationUtil.isValidString(validString));
	}

	@Test
	void testInvalidString() {
		String invalidString = "Hello123";
		assertFalse(ExcelFieldValidationUtil.isValidString(invalidString));
	}

	@Test
	void testNullString() {
		assertFalse(ExcelFieldValidationUtil.isValidString(null));
	}

	@Test
	void testEmptyString() {
		String emptyString = "";
		assertFalse(ExcelFieldValidationUtil.isValidString(emptyString));
	}

	@Test
	void testValidPhoneNumber() {
		String validPhoneNumber = "1234567890";
		assertTrue(ExcelFieldValidationUtil.isValidPhoneNumber(validPhoneNumber));
	}

	@Test
	void testInvalidPhoneNumber() {
		String invalidPhoneNumber = "1234";
		assertFalse(ExcelFieldValidationUtil.isValidPhoneNumber(invalidPhoneNumber));
	}

	@Test
	void testNullPhoneNumber() {
		assertFalse(ExcelFieldValidationUtil.isValidPhoneNumber(null));
	}

	@Test
	void testEmptyPhoneNumber() {
		String emptyPhoneNumber = "";
		assertFalse(ExcelFieldValidationUtil.isValidPhoneNumber(emptyPhoneNumber));
	}

	@Test
	void testValidDesignation() {
		String validDesignation = "Software Engineer";
		assertTrue(ExcelFieldValidationUtil.isValidDesignation(validDesignation));
	}

	@Test
	void testInvalidDesignation() {
		String invalidDesignation = "Senior *&^ Developer"; // Invalid characters
		assertFalse(ExcelFieldValidationUtil.isValidDesignation(invalidDesignation));
	}

	@Test
	void testNullDesignation() {
		assertFalse(ExcelFieldValidationUtil.isValidDesignation(null));
	}

	@Test
	void testEmptyDesignation() {
		String emptyDesignation = "";
		assertFalse(ExcelFieldValidationUtil.isValidDesignation(emptyDesignation));
	}

	@Test
	void testValidServiceFalls() {
		String validServiceFalls = "Service Falls";
		assertTrue(ExcelFieldValidationUtil.isValidServiceFalls(validServiceFalls));
	}

	@Test
	void testInvalidServiceFalls() {
		String invalidServiceFalls = "Invalid *&^ Service Falls";
		assertFalse(ExcelFieldValidationUtil.isValidServiceFalls(invalidServiceFalls));
	}

	@Test
	void testNullServiceFalls() {
		assertFalse(ExcelFieldValidationUtil.isValidServiceFalls(null));
	}

	@Test
	void testEmptyServiceFalls() {
		String emptyServiceFalls = "";
		assertFalse(ExcelFieldValidationUtil.isValidServiceFalls(emptyServiceFalls));
	}

	@Test
	void testValidBudgetAmount() {
		String validAmount = "1000.50";
		assertTrue(ExcelFieldValidationUtil.isValidBudgetAmount(validAmount));
	}

	@Test
	void testInvalidBudgetAmount() {
		String invalidAmount = "Invalid";
		assertFalse(ExcelFieldValidationUtil.isValidBudgetAmount(invalidAmount));
	}

	@Test
	void testNullBudgetAmount() {
		assertFalse(ExcelFieldValidationUtil.isValidBudgetAmount(null));
	}

	@Test
	void testEmptyBudgetAmount() {
		String emptyAmount = "";
		assertFalse(ExcelFieldValidationUtil.isValidBudgetAmount(emptyAmount));
	}

}
