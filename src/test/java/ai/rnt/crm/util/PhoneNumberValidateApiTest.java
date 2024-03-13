package ai.rnt.crm.util;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.i18n.phonenumbers.NumberParseException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

class PhoneNumberValidateApiTest {

	@Mock
	private GetRequest getRequest;

	@Mock
	private HttpResponse<String> httpResponse;

	@InjectMocks
	private PhoneNumberValidateApi phoneNumberValidateApi;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void checkPhoneNumberInfoSuccessTest() throws UnirestException {
	    when(httpResponse.getStatus()).thenReturn(200);
	    when(httpResponse.getBody()).thenReturn("{\"isValidNumber\": true}");
	    Map<String, Object> response = phoneNumberValidateApi.checkPhoneNumberInfo("+1234567890");
	    assertEquals("Error", response.get("Success"));
	}

	@Test
    void checkPhoneNumberInfo_Error() throws UnirestException {
        when(getRequest.asString()).thenThrow(new RuntimeException("Error"));
        Map<String, Object> response = phoneNumberValidateApi.checkPhoneNumberInfo("+1234567890");
        assertEquals("Error", response.get("Success"));
        assertEquals(null, response.get("Data"));
        assertEquals(null, response.get("isValidNumber"));
    }

	@Test
	void testGetRegionCodeNullPhoneNumber() throws NumberParseException {
		PhoneNumberValidateApi phoneNumberValidator = new PhoneNumberValidateApi();
		String regionCode = phoneNumberValidator.getRegionCode(null);
		String regionCode1 = phoneNumberValidator.getRegionCode("");
		String regionCode2 = phoneNumberValidator.getRegionCode("123456");
		assertEquals("IN", regionCode2);
		String regionCode3 = phoneNumberValidator.getRegionCode("+123456");
		assertEquals(null, regionCode3);
		String regionCode4 = phoneNumberValidator.getRegionCode("+123456789");
		assertNull(regionCode4);
		assertNull(regionCode);
		assertNull(regionCode1);
	}
}
