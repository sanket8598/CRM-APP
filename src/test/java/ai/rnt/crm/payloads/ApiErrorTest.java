package ai.rnt.crm.payloads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;

import ai.rnt.crm.exception.CRMException;

class ApiErrorTest {

	@Test
	public void testApiError_DefaultConstructor() {
		ApiError apiError = new ApiError();
		List<String> asList = Arrays.asList("Error 1", "Error 2");
		apiError.setErrors(asList);
		apiError.setHttpStatus(HttpStatus.BAD_GATEWAY);
		apiError.setMessage("hjsahfds");
		apiError.setStatus(false);
		apiError.setMessages(asList);
		apiError.setTimestamp(LocalDateTime.now());
		apiError.getErrors();
		apiError.getHttpStatus();
		apiError.getMessage();
		apiError.isStatus();
		apiError.getMessages();
		apiError.getTimestamp();
		assertNotNull(apiError);
		assertFalse(apiError.isStatus());
	}

	@Test
	void testApiError_ConstructorWithHttpStatus() {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ApiError apiError = new ApiError(status);
		assertNotNull(apiError);
		assertFalse(apiError.isStatus());
		assertNull(apiError.getMessage());
		assertNotNull(apiError.getTimestamp());
		assertTrue(apiError.getMessages().isEmpty());
		assertTrue(apiError.getErrors().isEmpty());
		assertEquals(status, apiError.getHttpStatus());
	}

	@Test
	void testApiError_ConstructorWithMessageAndMessages() {
		String message = "Test error message";
		List<String> messages = Arrays.asList("Error 1", "Error 2");
		ApiError apiError = new ApiError(message, messages);
		assertNotNull(apiError);
		assertFalse(apiError.isStatus());
		assertEquals(message, apiError.getMessage());
	}

	@Test
	void testApiError_ConstructorWithHttpStausAndThrowable() {
		ApiError apiError = new ApiError(HttpStatus.BAD_GATEWAY, new CRMException("Msg"));
		assertNotNull(apiError);
		assertFalse(apiError.isStatus());
	}

	@Test
	void testApiError_ConstructorWithHttpStausAndMessage() {
		ApiError apiError = new ApiError("Message", new CRMException("Msg"));
		assertNotNull(apiError);
		assertFalse(apiError.isStatus());
	}

	@Test
	void testApiError_ConstructorWithHttpStausAndMessageWithThrowable() {
		ApiError apiError = new ApiError(HttpStatus.BAD_GATEWAY, "Message", new CRMException("Msg"));
		assertNotNull(apiError);
		assertFalse(apiError.isStatus());
	}

	@Test
	void testApiError_ConstructorWithHttpStausAndMessageWithError() {
		List<String> messages = Arrays.asList("Error 1", "Error 2");
		ApiError apiError = new ApiError(HttpStatus.BAD_GATEWAY, "Message", messages);
		assertNotNull(apiError);
		assertFalse(apiError.isStatus());
	}

	@Test
	void testApiError_ConstructorWithStatusandMessage() {
		ApiError apiError = new ApiError(false, "Message");
		assertNotNull(apiError);
		assertFalse(apiError.isStatus());
	}

	@Test
	void testApiError_ConstructorWithAllArgs() {
		List<String> messages = Arrays.asList("Error 1", "Error 2");
		ApiError apiError = new ApiError(false, "Message", messages, HttpStatus.BAD_GATEWAY);
		assertNotNull(apiError);
		assertFalse(apiError.isStatus());
	}

	@Test
	void jwtAuthRequestTest() {
		JwtAuthRequest j = new JwtAuthRequest();
		CsrfToken t=mock(CsrfToken.class);
		JwtAuthResponse js = new JwtAuthResponse(false, "tokne",t);
		js.setStatus(false);
		js.setToken("sfdfd");
		j.setFromCorp(false);
		j.setPassword(null);
		j.setUserId("asdf");
		js.isStatus();
		js.setCsrfToken(t);
		js.getCsrfToken();
		js.getToken();
		j.isFromCorp();
		j.getPassword();
		j.getUserId();
		JwtAuthResponse build = JwtAuthResponse.builder().status(false).token("sdfds").csrfToken(t).build();
		assertNotNull(js);
		assertNotNull(build);
		assertNotNull(JwtAuthResponse.builder().toString());

	}
}
