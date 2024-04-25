package ai.rnt.crm.service.impl;

import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.dto.GetVisitTaskDto;
import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.dto.VisitTaskDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;

@ExtendWith(MockitoExtension.class)
class VisitServiceImplTest {

	@Mock
	private Leads lead;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@Mock
	private LeadDaoService leadDaoService;

	@Mock
	private VisitDaoService visitDaoService;

	@InjectMocks
	private VisitServiceImpl visitServiceImpl;

	@Test
	void saveVisitTest() {
		VisitDto dto = new VisitDto();
		dto.setLeadId(1);
		List<String> participants = Arrays.asList("s.wakankar@rnt.ai", "t.pagare@rnt.ai");
		dto.setParticipates(participants);
		dto.setAllDay(true);
		Leads lead = new Leads();
		when(leadDaoService.getLeadById(1)).thenReturn(Optional.of(lead));
		EmployeeMaster employee = new EmployeeMaster();
		when(employeeService.getById(any())).thenReturn(Optional.of(employee));
		Visit visit = new Visit();
		when(visitDaoService.saveVisit(any())).thenReturn(visit);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = visitServiceImpl.saveVisit(dto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertEquals("Visit Added Successfully", response.getBody().get(MESSAGE));
	}

	@Test
	void failureVisitTest() {
		VisitDto dto = new VisitDto();
		dto.setLeadId(1);
		List<String> participants = Arrays.asList("s.wakankar@rnt.ai", "t.pagare@rnt.ai");
		dto.setParticipates(participants);
		when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(new Leads()));
		when(employeeService.getById(anyInt())).thenReturn(Optional.of(new EmployeeMaster()));
		when(visitDaoService.saveVisit(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = visitServiceImpl.saveVisit(dto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(false, response.getBody().get(SUCCESS));
		assertEquals("Visit Not Added", response.getBody().get(MESSAGE));
	}

	@Test
	void saveVisitTestException() {
		VisitDto dto = new VisitDto();
		Mockito.lenient().when(leadDaoService.getLeadById(0)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> visitServiceImpl.saveVisit(dto));
	}

	@Test
	void editVisitTest() {
		Integer visitId = 1;
		Visit visit = new Visit();
		when(visitDaoService.getVisitsByVisitId(visitId)).thenReturn(Optional.of(visit));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = visitServiceImpl.editVisit(visitId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertNotNull(response.getBody().get(DATA));
	}

	@Test
	void editVisitTestException() {
		Integer visitId = 1;
		when(visitDaoService.getVisitsByVisitId(visitId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> visitServiceImpl.editVisit(visitId));
	}

	@Test
	void updateVisitTest() {
		VisitDto dto = new VisitDto();
		dto.setLocation("Ho");
		dto.setSubject("Test");
		dto.setAllDay(true);
		List<String> participants = Arrays.asList("s.wakankar@rnt.ai", "t.pagare@rnt.ai");
		dto.setParticipates(participants);
		Integer visitId = 1;
		String status = "Save";
		Visit visit = new Visit();
		when(visitDaoService.getVisitsByVisitId(visitId)).thenReturn(Optional.of(visit));
		when(visitDaoService.saveVisit(any())).thenReturn(visit);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = visitServiceImpl.updateVisit(dto, visitId, status);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertEquals("Visit Updated Successfully", response.getBody().get(MESSAGE));
	}

	@Test
	void updateVisitTestElse() {
		VisitDto dto = new VisitDto();
		dto.setLocation("Ho");
		dto.setSubject("Test");
		List<String> participants = Arrays.asList("s.wakankar@rnt.ai", "t.pagare@rnt.ai");
		dto.setParticipates(participants);
		Integer visitId = 1;
		String status = "Save";
		Visit visit = new Visit();
		when(visitDaoService.getVisitsByVisitId(visitId)).thenReturn(Optional.of(visit));
		when(visitDaoService.saveVisit(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = visitServiceImpl.updateVisit(dto, visitId, status);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(false, response.getBody().get(SUCCESS));
		assertEquals("Visit Not Updated", response.getBody().get(MESSAGE));
	}

	@Test
	void updateVisitTestException() {
		VisitDto dto = new VisitDto();
		Integer visitId = 1;
		String status = "Save";
		when(visitDaoService.getVisitsByVisitId(visitId)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> visitServiceImpl.updateVisit(dto, visitId, status));
	}

	@Test
	void assignVisitTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("visitId", 1);
		Visit visit = new Visit();
		EmployeeMaster employee = new EmployeeMaster();
		when(visitDaoService.getVisitsByVisitId(1)).thenReturn(Optional.of(visit));
		when(employeeService.getById(1477)).thenReturn(Optional.of(employee));
		when(visitDaoService.saveVisit(any())).thenReturn(visit);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = visitServiceImpl.assignVisit(map);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertEquals("Visit Assigned Successfully", response.getBody().get(MESSAGE));
	}

	@Test
	void notAssignVisitTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("visitId", 1);
		Visit visit = new Visit();
		EmployeeMaster employee = new EmployeeMaster();
		when(visitDaoService.getVisitsByVisitId(1)).thenReturn(Optional.of(visit));
		when(employeeService.getById(1477)).thenReturn(Optional.of(employee));
		when(visitDaoService.saveVisit(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = visitServiceImpl.assignVisit(map);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(false, response.getBody().get(SUCCESS));
		assertEquals("Visit Not Assigned", response.getBody().get(MESSAGE));
	}

	@Test
	void assignVisitTestException() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("visitId", 1);
		when(visitDaoService.getVisitsByVisitId(1)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> visitServiceImpl.assignVisit(map));
	}

	@Test
	void visitMarkAsCompletedTest() {
		Integer visitId = 1;
		Visit visit = new Visit();
		visit.setVisitId(visitId);
		when(visitDaoService.getVisitsByVisitId(visitId)).thenReturn(Optional.of(visit));
		when(visitDaoService.saveVisit(visit)).thenReturn(visit);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = visitServiceImpl.visitMarkAsCompleted(visitId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(true, responseEntity.getBody().get(SUCCESS));
		assertEquals("Visit updated Successfully", responseEntity.getBody().get(MESSAGE));
	}

	@Test
	void visitNotMarkAsCompletedTest() {
		Integer visitId = 1;
		Visit visit = new Visit();
		visit.setVisitId(visitId);
		when(visitDaoService.getVisitsByVisitId(visitId)).thenReturn(Optional.of(visit));
		when(visitDaoService.saveVisit(visit)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = visitServiceImpl.visitMarkAsCompleted(visitId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(false, responseEntity.getBody().get(SUCCESS));
		assertEquals("Visit Not updated", responseEntity.getBody().get(MESSAGE));
	}

	@Test
	void visitMarkAsCompletedTestException() {
		Integer visitId = 1;
		when(visitDaoService.getVisitsByVisitId(visitId)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> visitServiceImpl.visitMarkAsCompleted(visitId));
	}

	@Test
	void deleteVisitTest() {
	    when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
	    VisitTask visitTask = new VisitTask();
	    Visit visit = new Visit();
	    visit.setVisitId(1);
	    visit.getVisitTasks().add(visitTask);
	    when(visitDaoService.getVisitsByVisitId(1)).thenReturn(java.util.Optional.of(visit));
	    when(visitDaoService.saveVisit(any(Visit.class))).thenReturn(visit);
	    ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = visitServiceImpl.deleteVisit(1);
	    EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertEquals(true, responseBody.get(ApiResponse.SUCCESS));
	    assertEquals("Visit deleted Successfully.", responseBody.get(ApiResponse.MESSAGE));
	}

	@Test
	 void notDeleteVisitTest() {
	     when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1477);
	     Visit visit = new Visit(); 
	     when(visitDaoService.getVisitsByVisitId(1)).thenReturn(Optional.of(visit)); 
	     when(visitDaoService.saveVisit(visit)).thenReturn(null); 
	     ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = visitServiceImpl.deleteVisit(1);
	     EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
	     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	     assertEquals(false, responseBody.get(ApiResponse.SUCCESS));
	     assertEquals("Visit Not delete.", responseBody.get(ApiResponse.MESSAGE));
	 }

	@Test
	    void deleteVisitTestException() {
	        when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1477);
	        when(visitDaoService.getVisitsByVisitId(1)).thenThrow(RuntimeException.class);
	        assertThrows(CRMException.class, () -> visitServiceImpl.deleteVisit(1));
	    }

	@Test
	void addVisitTaskTest() {
		VisitTaskDto dto = new VisitTaskDto();
		Integer leadsId = 1;
		Integer visitId = 2;
		VisitTask visitTask = new VisitTask();
		when(visitDaoService.getVisitsByVisitId(visitId)).thenReturn(Optional.of(new Visit()));
		when(leadDaoService.getLeadById(leadsId)).thenReturn(Optional.of(new Leads()));
		when(visitDaoService.addVisitTask(any(VisitTask.class))).thenReturn(visitTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = visitServiceImpl.addVisitTask(dto, leadsId, visitId);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Task Added Successfully..!!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void notAddVisitTaskTest() {
		VisitTaskDto dto = new VisitTaskDto();
		Integer leadsId = 1;
		Integer visitId = 2;
		when(visitDaoService.getVisitsByVisitId(visitId)).thenReturn(Optional.of(new Visit()));
		when(leadDaoService.getLeadById(leadsId)).thenReturn(Optional.of(new Leads()));
		when(visitDaoService.addVisitTask(any(VisitTask.class))).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = visitServiceImpl.addVisitTask(dto, leadsId, visitId);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(false, response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Task Not Added", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void addVisitTaskTestException() {
		VisitTaskDto dto = new VisitTaskDto();
		Integer leadsId = 1;
		Integer visitId = 2;
		when(visitDaoService.getVisitsByVisitId(visitId)).thenThrow(new ResourceNotFoundException());
		assertThrows(CRMException.class, () -> visitServiceImpl.addVisitTask(dto, leadsId, visitId));
	}

	@Test
	void getVisitTaskTest() {
		Integer visitTaskId = 1;
		VisitTask visitTask = new VisitTask();
		when(visitDaoService.getVisitTaskById(visitTaskId)).thenReturn(Optional.of(visitTask));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = visitServiceImpl.getVisitTask(visitTaskId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertNotNull(response.getBody().get(DATA));
	}

	@Test
	void getVisitTaskTestException() {
		Integer visitTaskId = 1;
		when(visitDaoService.getVisitTaskById(visitTaskId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> visitServiceImpl.getVisitTask(visitTaskId));
	}

	@Test
	void updateVisitTaskTest() {
		int taskId = 1;
		GetVisitTaskDto dto = new GetVisitTaskDto();
		VisitTask visitTask = new VisitTask();
		when(visitDaoService.getVisitTaskById(taskId)).thenReturn(Optional.of(visitTask));
		when(visitDaoService.addVisitTask(visitTask)).thenReturn(visitTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = visitServiceImpl.updateVisitTask(dto, taskId);
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(true, responseBody.get(ApiResponse.SUCCESS));
		assertEquals("Task Updated Successfully..!!", responseBody.get(ApiResponse.MESSAGE));
	}

	@Test
	void notUpdateVisitTaskTest() {
		int taskId = 1;
		GetVisitTaskDto dto = new GetVisitTaskDto();
		VisitTask visitTask = new VisitTask();
		when(visitDaoService.getVisitTaskById(taskId)).thenReturn(Optional.of(visitTask));
		when(visitDaoService.addVisitTask(visitTask)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = visitServiceImpl.updateVisitTask(dto, taskId);
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(false, responseBody.get(ApiResponse.SUCCESS));
		assertEquals("Task Not Updated", responseBody.get(ApiResponse.MESSAGE));
	}

	@Test
	void updateVisitTaskTestException() {
		int taskId = 1;
		GetVisitTaskDto dto = new GetVisitTaskDto();
		when(visitDaoService.getVisitTaskById(taskId)).thenThrow(RuntimeException.class);
		CRMException exception = assertThrows(CRMException.class, () -> visitServiceImpl.updateVisitTask(dto, taskId));
	}

	@Test
	void assignVisitTaskTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1);
		map.put("taskId", 1);
		EmployeeMaster employee = new EmployeeMaster();
		VisitTask visitTask = new VisitTask();
		when(visitDaoService.getVisitTaskById(1)).thenReturn(Optional.of(visitTask));
		when(employeeService.getById(1)).thenReturn(Optional.of(employee));
		when(visitDaoService.addVisitTask(visitTask)).thenReturn(visitTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = visitServiceImpl.assignVisitTask(map);
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(true, responseBody.get(ApiResponse.SUCCESS));
		assertEquals("Task Assigned Successfully", responseBody.get(ApiResponse.MESSAGE));
	}

	@Test
	void notAssignVisitTaskTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1);
		map.put("taskId", 1);
		EmployeeMaster employee = new EmployeeMaster();
		VisitTask visitTask = new VisitTask();
		when(visitDaoService.getVisitTaskById(1)).thenReturn(Optional.of(visitTask));
		when(employeeService.getById(1)).thenReturn(Optional.of(employee));
		when(visitDaoService.addVisitTask(visitTask)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = visitServiceImpl.assignVisitTask(map);
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(false, responseBody.get(ApiResponse.SUCCESS));
		assertEquals("Task Not Assigned", responseBody.get(ApiResponse.MESSAGE));
	}

	@Test
	void assignVisitTaskTestException() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1);
		map.put("taskId", 1);
		when(visitDaoService.getVisitTaskById(1)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> visitServiceImpl.assignVisitTask(map));
	}

	@Test
	void deleteVisitTaskTest() {
		Integer taskId = 1;
		VisitTask visitTask = new VisitTask();
		when(visitDaoService.getVisitTaskById(taskId)).thenReturn(Optional.of(visitTask));
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(visitDaoService.addVisitTask(visitTask)).thenReturn(visitTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = visitServiceImpl.deleteVisitTask(taskId);
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(true, responseBody.get(ApiResponse.SUCCESS));
		assertEquals("Visit Task Deleted Successfully.", responseBody.get(ApiResponse.MESSAGE));
	}

	@Test
	void notDeleteVisitTaskTest() {
		Integer taskId = 1;
		VisitTask visitTask = new VisitTask();
		when(visitDaoService.getVisitTaskById(taskId)).thenReturn(Optional.of(visitTask));
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(visitDaoService.addVisitTask(visitTask)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = visitServiceImpl.deleteVisitTask(taskId);
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(false, responseBody.get(ApiResponse.SUCCESS));
		assertEquals("Visit Task Not delete.", responseBody.get(ApiResponse.MESSAGE));
	}

	@Test
	void deleteVisitTaskTestException() {
		Integer taskId = 1;
		when(visitDaoService.getVisitTaskById(taskId)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> visitServiceImpl.deleteVisitTask(taskId));
	}
}
