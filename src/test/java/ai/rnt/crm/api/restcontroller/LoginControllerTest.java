package ai.rnt.crm.api.restcontroller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.payloads.JwtAuthRequest;
import ai.rnt.crm.payloads.JwtAuthResponse;
import ai.rnt.crm.security.JWTTokenHelper;
import ai.rnt.crm.security.config.CustomUserDetails;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.JwtTokenDecoder;
import ai.rnt.crm.util.PhoneNumberValidateApi;

class LoginControllerTest {

	@Mock
	private EmployeeService employeeService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private CustomUserDetails customUserDetails;

	@Mock
	private PhoneNumberValidateApi api;

	@Mock
	private JwtTokenDecoder jwtTokenDecoder;

	@Mock
	private JWTTokenHelper helper;

	@InjectMocks
	private LoginController loginController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void createAuthenticationTokenTest() {
		JwtAuthRequest jwtAuthRequest = new JwtAuthRequest();
		jwtAuthRequest.setUserId("Ng1477");
		jwtAuthRequest.setPassword("NG@@1477");
		jwtAuthRequest.setFromCorp(false);
		String token = "testToken";
		when(helper.generateToken(any())).thenReturn(token);
		when(authenticationManager.authenticate(any())).thenReturn(null);
		ResponseEntity<JwtAuthResponse> response = loginController.createAuthenticationToken(jwtAuthRequest);
		verify(authenticationManager).authenticate(any());
		verify(helper).generateToken(any());
		assertAll(() -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				() -> assertTrue(response.getBody().isStatus()),
				() -> assertEquals(token, response.getBody().getToken()));
	}

	//@Test
	void tokenDecodeTest() throws Exception {
		// Prepare test data
		String tokenValue = "testTokenValue";
		Map<String, String> requestBody = new LinkedHashMap<>();
		requestBody.put("token", tokenValue);
		String fullName = "John Doe";
		String role = "admin";
		String staffId = "1477";
		String emailId = "john.doe@example.com";
		JsonNode jsonNode = new ObjectMapper().createObjectNode().put("fullName", fullName).put("role", role)
				.put("staffId", staffId).put("emailId", emailId);
		String decodedToken = "{ \"fullName\": \"John Doe\", \"role\": \"admin\", \"staffId\": \"1477\", \"emailId\": \"john.doe@example.com\" }";

		// Mock the service response
		when(jwtTokenDecoder.testDecodeJWT(tokenValue)).thenReturn(decodedToken);

		// Call the controller method
		ResponseEntity<Map<String, Object>> response = loginController.tokenDecode(requestBody);

		// Verify that the service method is called with the correct argument
		verify(jwtTokenDecoder).testDecodeJWT(tokenValue);

		// Check the response status and body
		assertAll(() -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				() -> assertEquals(fullName, response.getBody().get("fullName")),
				() -> assertEquals(role, response.getBody().get("role")),
				() -> assertEquals(staffId, response.getBody().get("staffId")),
				() -> assertEquals(emailId, response.getBody().get("emailId")));
	}
}
