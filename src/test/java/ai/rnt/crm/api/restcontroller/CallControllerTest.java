package ai.rnt.crm.api.restcontroller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
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

import ai.rnt.crm.dto.CallDto;
import ai.rnt.crm.dto.CallTaskDto;
import ai.rnt.crm.dto.GetCallTaskDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CallService;

class CallControllerTest {

	@Mock
	private CallService callService;

	@InjectMocks
	private CallController callController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void addCallTest() {
		CallDto dto = new CallDto();
		Integer leadsId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(callService.addCall(any(CallDto.class), anyInt())).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = callController.addCall(dto, leadsId);
		verify(callService).addCall(dto, leadsId);
	}

	@Test
	void editCallTest() {
		Integer callId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(callService.editCall(anyInt())).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = callController.editCall(callId);
		verify(callService).editCall(callId);
	}

	@Test
	void updateCallTest() {
		Integer callId = 1;
		String status = "Save";
		CallDto dto = new CallDto();
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(callService.updateCall(any(CallDto.class), anyInt(), anyString())).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = callController.updateCall(dto, callId, status);
		verify(callService).updateCall(dto, callId, status);
	}

	@Test
	void assignCallTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("key", 1);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(callService.assignCall(anyMap())).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = callController.assignCall(map);
		verify(callService).assignCall(map);
	}

	@Test
	void markAsCompletedTest() {
		Integer callId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(callService.markAsCompleted(anyInt())).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = callController.markAsCompleted(callId);
		verify(callService).markAsCompleted(callId);
	}

	@Test
	void deleteCallTest() {
		Integer callId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(callService.deleteCall(anyInt())).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = callController.deleteCall(callId);
		verify(callService).deleteCall(callId);
	}

	@Test
	void addCallTaskTest() {
		CallTaskDto callTaskDto = new CallTaskDto();
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(callService.addCallTask(any(CallTaskDto.class), anyInt(), anyInt())).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = callController.addCallTask(callTaskDto, 1, 2);
		verify(callService).addCallTask(callTaskDto, 1, 2);
	}

	@Test
	void getCallTaskTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(callService.getCallTask(anyInt())).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = callController.getCallTask(1);
		verify(callService).getCallTask(1);
	}

	@Test
	void updateCallTaskTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(callService.updateCallTask(any(GetCallTaskDto.class), anyInt())).thenReturn(expectedResponse);
		GetCallTaskDto dto = new GetCallTaskDto();
		ResponseEntity<EnumMap<ApiResponse, Object>> response = callController.updateCallTask(dto, 1);
		verify(callService).updateCallTask(dto, 1);
	}

	@Test
	void assignCallTaskTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(callService.assignCallTask(any(Map.class))).thenReturn(expectedResponse);
		Map<String, Integer> map = new HashMap<>();
		map.put("key", 1); // Add any key-value pair as per your requirement
		ResponseEntity<EnumMap<ApiResponse, Object>> response = callController.assignCallTask(map);
		verify(callService).assignCallTask(map);
	}

	@Test
	void deleteCallTaskTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(callService.deleteCallTask(anyInt())).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = callController.deleteCallTask(1);
		verify(callService).deleteCallTask(1);
	}
}
