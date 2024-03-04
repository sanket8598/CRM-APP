package ai.rnt.crm.api.restcontroller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.EmailService;

class EmailControllerTest {

	@Mock
	private EmailService emailService;

	@InjectMocks
	private EmailController emailController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void addEmailTest() {
		EmailDto emailDto = new EmailDto();
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(emailService.addEmail(emailDto, 1, null)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailController.addEmail(emailDto, 1, null);
		verify(emailService).addEmail(emailDto, 1, null);
	}

	@Test
	void getEmailTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(emailService.getEmail(1)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailController.getEmail(1);
		verify(emailService).getEmail(1);
	}

	@Test
	void updateEmailTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(emailService.updateEmail(any(EmailDto.class), anyString(), anyInt())).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailController.updateEmail(new EmailDto(), "status",
				1);
		verify(emailService).updateEmail(any(EmailDto.class), eq("status"), eq(1));
	}

	@Test
	void assignEmailTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(emailService.assignEmail(any(Map.class))).thenReturn(expectedResponse);
		Map<String, Integer> requestBody = new HashMap<>();
		requestBody.put("key", 1);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailController.assignEmail(requestBody);
		verify(emailService).assignEmail(requestBody);
	}

	@Test
	void deleteEmailTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(emailService.deleteEmail(any(Integer.class))).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailController.deleteEmail(1);
		verify(emailService).deleteEmail(1);
	}

	@Test
	void checkMailIdTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(emailService.checkMailId(any(Integer.class), any(Integer.class))).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailController.checkMailId(1, 2);
		verify(emailService).checkMailId(1, 2);
	}
}
