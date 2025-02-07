package ai.rnt.crm.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ai.rnt.crm.util.PhoneNumberValidateApi;
import ai.rnt.crm.validation.impl.PhoneNumberValidator;

class PhoneNumberValidatorTest {

	@InjectMocks
	private PhoneNumberValidator phoneNumberValidator;
	
	@Mock
	private PhoneNumberValidateApi numberValidateApi;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void isValidPhoneNumber() {
		Map<String, Object> obj = new HashMap<>();
		obj.put("Success", "OK");
		obj.put("isValidNumber", true);
		when(numberValidateApi.checkPhoneNumberInfo(anyString())).thenReturn(obj);
		boolean isValid = phoneNumberValidator.isValid("+1234567890", mock(ConstraintValidatorContext.class));
		assertTrue(isValid);
	}
	@Test
	void inValidPhoneNumberScenarion1() {
		Map<String, Object> obj = new HashMap<>();
		obj.put("Success", "OK");
		obj.put("isValidNumber", false);
		when(numberValidateApi.checkPhoneNumberInfo(anyString())).thenReturn(obj);
		boolean isValid = phoneNumberValidator.isValid("+1234567890", mock(ConstraintValidatorContext.class));
		assertFalse(isValid);
	}

	@Test
	void isNullPhoneNumber() {
		Map<String, Object> obj = new HashMap<>();
		obj.put("Success", "Error");
		when(numberValidateApi.checkPhoneNumberInfo(anyString())).thenReturn(obj);
		boolean isValid = phoneNumberValidator.isValid(null, mock(ConstraintValidatorContext.class));
		assertFalse(isValid);
	}
	@Test
	void isInvalidPhoneNumber() {
		Map<String, Object> obj = new HashMap<>();
		obj.put("Success", "Error");
		when(numberValidateApi.checkPhoneNumberInfo(anyString())).thenReturn(obj);
		boolean isValid = phoneNumberValidator.isValid("+1234532", mock(ConstraintValidatorContext.class));
		assertTrue(isValid);
	}
}
