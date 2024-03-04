package ai.rnt.crm.api.restcontroller;

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

import ai.rnt.crm.dto.GetLeadTaskDto;
import ai.rnt.crm.dto.LeadTaskDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.LeadTaskService;

class LeadTaskControllerTest {

	@Mock
	private LeadTaskService leadTaskService;

	@InjectMocks
	private LeadTaskController leadTaskController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void addLeadTaskTest() {
		LeadTaskDto dto = new LeadTaskDto();
		Integer leadsId = 1;
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadTaskService.addLeadTask(dto, leadsId)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskController.addLeadTask(dto, leadsId);
		verify(leadTaskService).addLeadTask(dto, leadsId);
	}

	@Test
	void getLeadTaskTest() {
		Integer taskId = 1;
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadTaskService.getLeadTask(taskId)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskController.getLeadTask(taskId);
		verify(leadTaskService).getLeadTask(taskId);
	}

	@Test
	void updateLeadTaskTest() {
		Integer taskId = 1;
		GetLeadTaskDto dto = new GetLeadTaskDto();
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadTaskService.updateLeadTask(dto, taskId)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskController.updateLeadTask(dto, taskId);
		verify(leadTaskService).updateLeadTask(dto, taskId);
	}

	@Test
	void assignLeadTaskTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("taskId", 1);
		map.put("userId", 2);
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadTaskService.assignLeadTask(map)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskController.assignLeadTask(map);
		verify(leadTaskService).assignLeadTask(map);
	}

	@Test
	void deleteLeadTaskTest() {
		int taskId = 1;
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadTaskService.deleteLeadTask(taskId)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskController.deleteLeadTask(taskId);
		verify(leadTaskService).deleteLeadTask(taskId);
	}

}
