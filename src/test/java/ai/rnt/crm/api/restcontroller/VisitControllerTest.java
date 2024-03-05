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

import ai.rnt.crm.dto.GetVisitTaskDto;
import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.dto.VisitTaskDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.VisitService;

class VisitControllerTest {

	@Mock
	private VisitService visitService;

	@InjectMocks
	private VisitController visitController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void saveVisitTest() {
		VisitService visitService = mock(VisitService.class);
		VisitDto dto = new VisitDto();
		VisitController controller = new VisitController(visitService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(visitService.saveVisit(dto)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.saveVisit(dto);
		verify(visitService).saveVisit(dto);
	}

	@Test
	void editVisitTest() {
		VisitService visitService = mock(VisitService.class);
		Integer visitId = 1;
		VisitController controller = new VisitController(visitService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(visitService.editVisit(visitId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.editVisit(visitId);
		verify(visitService).editVisit(visitId);
	}

	@Test
	void updateVisitTest() {
		VisitService visitService = mock(VisitService.class);
		VisitDto dto = new VisitDto();
		Integer visitId = 1;
		String status = "Save";
		VisitController controller = new VisitController(visitService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(visitService.updateVisit(dto, visitId, status)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.updateVisit(dto, visitId, status);
		verify(visitService).updateVisit(dto, visitId, status);
	}

	@Test
	void assignVisitTest() {
		VisitService visitService = mock(VisitService.class);
		Map<String, Integer> map = new HashMap<>();
		map.put("visitId", 1);
		VisitController controller = new VisitController(visitService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(visitService.assignVisit(map)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.assignVisit(map);
		verify(visitService).assignVisit(map);
	}

	@Test
	void deleteVisitTest() {
		VisitService visitService = mock(VisitService.class);
		Integer visitId = 1;
		VisitController controller = new VisitController(visitService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(visitService.deleteVisit(visitId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.deleteVisit(visitId);
		verify(visitService).deleteVisit(visitId);
	}

	@Test
	void addVisitTaskTest() {
		VisitService visitService = mock(VisitService.class);
		VisitTaskDto dto = new VisitTaskDto();
		Integer leadsId = 1;
		Integer visitId = 1;
		VisitController controller = new VisitController(visitService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(visitService.addVisitTask(dto, leadsId, visitId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.addVisitTask(dto, leadsId, visitId);
		verify(visitService).addVisitTask(dto, leadsId, visitId);
	}

	@Test
	void getVisitTaskTest() {
		VisitService visitService = mock(VisitService.class);
		Integer taskId = 1;
		VisitController controller = new VisitController(visitService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(visitService.getVisitTask(taskId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.getVisitTask(taskId);
		verify(visitService).getVisitTask(taskId);
	}

	@Test
	void updateVisitTaskTest() {
		VisitService visitService = mock(VisitService.class);
		GetVisitTaskDto dto = new GetVisitTaskDto();
		Integer taskId = 1;
		VisitController controller = new VisitController(visitService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(visitService.updateVisitTask(dto, taskId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.updateVisitTask(dto, taskId);
		verify(visitService).updateVisitTask(dto, taskId);
	}

	@Test
	void assignVisitTaskTest() {
		VisitService visitService = mock(VisitService.class);
		Map<String, Integer> map = new HashMap<>();
		map.put("taskId", 1);
		VisitController controller = new VisitController(visitService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(visitService.assignVisitTask(map)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.assignVisitTask(map);
		verify(visitService).assignVisitTask(map);
	}

	@Test
	void deleteVisitTaskTest() {
		VisitService visitService = mock(VisitService.class);
		Integer taskId = 1;
		VisitController controller = new VisitController(visitService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(visitService.deleteVisitTask(taskId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.deleteVisitTask(taskId);
		verify(visitService).deleteVisitTask(taskId);
	}

	@Test
	void visitMarkAsCompletedTest() {
		VisitService visitService = mock(VisitService.class);
		Integer visitId = 1;
		VisitController controller = new VisitController(visitService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(visitService.visitMarkAsCompleted(visitId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.visitMarkAsCompleted(visitId);
		verify(visitService).visitMarkAsCompleted(visitId);
	}
}
