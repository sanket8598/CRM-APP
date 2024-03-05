package ai.rnt.crm.api.restcontroller;

import static org.mockito.Mockito.mock;
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

import ai.rnt.crm.dto.opportunity.GetOpportunityTaskDto;
import ai.rnt.crm.dto.opportunity.OpportunityTaskDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.OpportunityTaskService;

class OpportunityTaskControllerTest {

	@Mock
	private OpportunityTaskService opportunityTaskService;

	@InjectMocks
	private OpportunityTaskController opportunityTaskController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void addOpportunityTaskTest() {
		OpportunityTaskService opportunityTaskService = mock(OpportunityTaskService.class);
		Integer optyId = 1;
		OpportunityTaskDto dto = new OpportunityTaskDto();
		OpportunityTaskController controller = new OpportunityTaskController(opportunityTaskService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityTaskService.addOpportunityTask(dto, optyId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.addOpportunityTask(dto, optyId);
		verify(opportunityTaskService).addOpportunityTask(dto, optyId);
	}

	@Test
	void getOpportunityTaskTest() {
		OpportunityTaskService opportunityTaskService = mock(OpportunityTaskService.class);
		Integer taskId = 1;
		OpportunityTaskController controller = new OpportunityTaskController(opportunityTaskService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityTaskService.getOpportunityTask(taskId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.getOpportunityTask(taskId);
		verify(opportunityTaskService).getOpportunityTask(taskId);
	}

	@Test
	void updateOpportunityTaskTest() {
		OpportunityTaskService opportunityTaskService = mock(OpportunityTaskService.class);
		Integer taskId = 1;
		GetOpportunityTaskDto dto = new GetOpportunityTaskDto();
		OpportunityTaskController controller = new OpportunityTaskController(opportunityTaskService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityTaskService.updateOpportunityTask(dto, taskId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.updateOpportunityTask(dto, taskId);
		verify(opportunityTaskService).updateOpportunityTask(dto, taskId);
	}

	@Test
	void assignOpportunityTaskTest() {
		OpportunityTaskService opportunityTaskService = mock(OpportunityTaskService.class);
		Map<String, Integer> map = new HashMap<>();
		map.put("taskId", 1);
		OpportunityTaskController controller = new OpportunityTaskController(opportunityTaskService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityTaskService.assignOpportunityTask(map)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.assignOpportunityTask(map);
		verify(opportunityTaskService).assignOpportunityTask(map);
	}

	@Test
	void deleteOpportunityTaskTest() {
		OpportunityTaskService opportunityTaskService = mock(OpportunityTaskService.class);
		Integer taskId = 1;
		OpportunityTaskController controller = new OpportunityTaskController(opportunityTaskService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityTaskService.deleteOpportunityTask(taskId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.deleteOpportunityTask(taskId);
		verify(opportunityTaskService).deleteOpportunityTask(taskId);
	}
}
