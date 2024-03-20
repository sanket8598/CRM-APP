package ai.rnt.crm.api.restcontroller;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
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

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void createAuthenticationTokenTest() {
		JwtAuthRequest jwtAuthRequest = new JwtAuthRequest();
		jwtAuthRequest.setUserId("Ng1477");
		HttpServletRequest req=mock(HttpServletRequest.class);
		jwtAuthRequest.setPassword("NG@@1477");
		jwtAuthRequest.setFromCorp(false);
		String token = "testToken";
		when(helper.generateToken(any())).thenReturn(token);
		when(authenticationManager.authenticate(any())).thenReturn(null);
		ResponseEntity<JwtAuthResponse> response = loginController.createAuthenticationToken(req,jwtAuthRequest);
		verify(authenticationManager).authenticate(any());
		verify(helper).generateToken(any());
		assertAll(() -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				() -> assertTrue(response.getBody().isStatus()),
				() -> assertEquals(token, response.getBody().getToken()));
	}
	@Test
	void createNullTokenTest() {
		JwtAuthRequest jwtAuthRequest = new JwtAuthRequest();
		jwtAuthRequest.setUserId("Ng1477");
		jwtAuthRequest.setPassword("NG@@1477");
		jwtAuthRequest.setFromCorp(true);
		when(helper.generateToken(any())).thenReturn(null);
		when(authenticationManager.authenticate(any())).thenReturn(null);
		HttpServletRequest req=mock(HttpServletRequest.class);
		ResponseEntity<JwtAuthResponse> response = loginController.createAuthenticationToken(req,jwtAuthRequest);
		verify(authenticationManager).authenticate(any());
		verify(helper).generateToken(any());
		assertAll(() -> assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()),
				() -> assertFalse(response.getBody().isStatus()),
				() -> assertEquals(null, response.getBody().getToken()));
	}
	@Test
	void createTokenExceptionTest() {
		JwtAuthRequest jwtAuthRequest = new JwtAuthRequest();
		jwtAuthRequest.setUserId("Ng1477");
		jwtAuthRequest.setPassword("NG@@1477");
		jwtAuthRequest.setFromCorp(false);
		when(authenticationManager.authenticate(any())).thenThrow(CRMException.class);
		HttpServletRequest req=mock(HttpServletRequest.class);
		assertThrows(CRMException.class,() -> loginController.createAuthenticationToken(req,jwtAuthRequest));
	}
	
	
	@Test
	void tokenDecodeTest() throws Exception {
		String tokenValue = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
		Map<String, String> requestBody = new LinkedHashMap<>();
		requestBody.put("token", tokenValue);
		String decodedToken = "{ \"fullName\": \"John Doe\", \"role\": \"admin\", \"staffId\": \"1477\", \"emailId\": \"john.doe@example.com\" }";
		when(jwtTokenDecoder.testDecodeJWT(tokenValue)).thenReturn(decodedToken);
		ResponseEntity<Map<String, Object>> response = loginController.tokenDecode(requestBody);
		assertAll(() -> assertEquals(HttpStatus.OK, response.getStatusCode()));
	}
	@Test
	void tokenDecodeExceptionTest() throws Exception {
		String tokenValue = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
		Map<String, String> requestBody = new LinkedHashMap<>();
		requestBody.put("token", tokenValue);
		assertThrows(CRMException.class,()->loginController.tokenDecode(requestBody));
	}
	@Test
	void tokenNullTest() throws Exception {
		Map<String, String> requestBody = new LinkedHashMap<>();
		requestBody.put("token", null);
		assertThrows(CRMException.class,()->loginController.tokenDecode(requestBody));
	}
	@Test
	void tokenEmptyTest() throws Exception {
		Map<String, String> requestBody = new LinkedHashMap<>();
		requestBody.put("token", "");
		assertThrows(CRMException.class,()->loginController.tokenDecode(requestBody));
	}
	@Test
	void getgetCRMUserTest() {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		when(employeeService.getCRMUser()).thenReturn(ResponseEntity.ok(resultMap));
		ResponseEntity<EnumMap<ApiResponse, Object>> adminAndUser = loginController.getCRMUser();
		assertEquals(HttpStatus.OK,adminAndUser.getStatusCode());
	}
	@Test
	void getAdminAndUserTest() {
		when(employeeService.getAdminAndUser(null)).thenReturn(any());
		ResponseEntity<EnumMap<ApiResponse, Object>> adminAndUser = loginController.getAdminAndUser(null);
		assertNull(adminAndUser);
	}
	@Test
	void validatePhoneNoTest() {
		when(api.checkPhoneNumberInfo(null)).thenReturn(any());
		Map<String, Object> adminAndUser = loginController.validatePhoneNo(null);
		assertNull(adminAndUser);
	}
}
