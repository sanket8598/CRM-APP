package ai.rnt.crm.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.util.PhoneNumberValidateApi;

class PhoneNumberValidatorTest {

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	private PhoneNumberValidator phoneNumberValidator;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(phoneNumberValidator).build();
	}

	@Test
	void isValidPhoneNumber() {
		Map<String, Object> obj = new HashMap<>();
		obj.put("Success", true);
		PhoneNumberValidateApi phoneNumberValidateApi = mock(PhoneNumberValidateApi.class);
		when(phoneNumberValidateApi.checkPhoneNumberInfo(ArgumentMatchers.anyString())).thenReturn(obj);
		boolean isValid = phoneNumberValidator.isValid("+1234567890", mock(ConstraintValidatorContext.class));
		assertTrue(isValid);
	}

	@Test
	void isInvalidPhoneNumber() {
		Map<String, Object> obj = new HashMap<>();
		obj.put("Success", false);
		PhoneNumberValidateApi phoneNumberValidateApi = mock(PhoneNumberValidateApi.class);
		when(phoneNumberValidateApi.checkPhoneNumberInfo(ArgumentMatchers.anyString())).thenReturn(obj);
		boolean isValid = phoneNumberValidator.isValid(null, mock(ConstraintValidatorContext.class));
		assertFalse(isValid);
	}
}
