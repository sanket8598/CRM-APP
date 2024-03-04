package ai.rnt.crm.service.impl;

import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadTaskDaoService;
import ai.rnt.crm.dto.GetLeadTaskDto;
import ai.rnt.crm.dto.LeadTaskDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;

@ExtendWith(MockitoExtension.class)
class LeadTaskServiceImplTest {

	@Mock
	private LeadTaskDto leadTaskDto;

	@Mock
	private LeadTask leadTask;

	@Mock
	private Leads lead;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@Mock
	private LeadDaoService leadDaoService;

	@Mock
	private LeadTaskDaoService leadTaskDaoService;

	@InjectMocks
	private LeadTaskServiceImpl leadTaskServiceImpl;

	Integer taskId = 1;
	Integer leadsId = 1;

	@Test
	void addLeadTaskTest() {
		LeadTaskDto dto = new LeadTaskDto();
		when(leadDaoService.getLeadById(leadsId)).thenReturn(Optional.of(new Leads()));
		when(employeeService.getById(any())).thenReturn(Optional.of(new EmployeeMaster()));
		when(leadTaskDaoService.getAllTask()).thenReturn(new ArrayList<>());
		when(leadTaskDaoService.addTask(any())).thenReturn(new LeadTask());
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskServiceImpl.addLeadTask(dto, leadsId);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void addLeadTaskNotAddedTest() {
		LeadTaskDto dto = new LeadTaskDto();
		when(leadDaoService.getLeadById(leadsId)).thenReturn(Optional.of(new Leads()));
		when(employeeService.getById(any())).thenReturn(Optional.of(new EmployeeMaster()));
		when(leadTaskDaoService.getAllTask()).thenReturn(new ArrayList<>());
		when(leadTaskDaoService.addTask(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskServiceImpl.addLeadTask(dto, leadsId);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(false, response.getBody().get(SUCCESS));
		assertEquals("Task Not Added", response.getBody().get(MESSAGE));
	}

	@Test
	void addLeadTaskTestException() {
		LeadTaskDto dto = new LeadTaskDto();
		when(leadDaoService.getLeadById(leadsId)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> leadTaskServiceImpl.addLeadTask(dto, leadsId));
	}

	@Test
	void getLeadTaskTest() {
		LeadTask leadTask = new LeadTask();
		when(leadTaskDaoService.getTaskById(taskId)).thenReturn(Optional.of(leadTask));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskServiceImpl.getLeadTask(taskId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertNotNull(response.getBody().get(DATA));
	}

	@Test
	void getLeadTaskTestException() {
		when(leadTaskDaoService.getTaskById(taskId)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> leadTaskServiceImpl.getLeadTask(taskId));
	}

	@Test
	void assignLeadTaskTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("taskId", 1);
		LeadTask leadTask = new LeadTask();
		EmployeeMaster employee = new EmployeeMaster();
		when(leadTaskDaoService.getTaskById(1)).thenReturn(Optional.of(leadTask));
		when(employeeService.getById(1477)).thenReturn(Optional.of(employee));
		when(leadTaskDaoService.addTask(leadTask)).thenReturn(leadTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskServiceImpl.assignLeadTask(map);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertEquals("Task Assigned SuccessFully", response.getBody().get(MESSAGE));
	}

	@Test
	void assignLeadTaskNotAssignedTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("taskId", 2);
		LeadTask leadTask = new LeadTask();
		when(leadTaskDaoService.getTaskById(2)).thenReturn(Optional.of(leadTask));
		EmployeeMaster employee = new EmployeeMaster();
		when(employeeService.getById(1477)).thenReturn(Optional.of(employee));
		when(leadTaskDaoService.addTask(leadTask)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskServiceImpl.assignLeadTask(map);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(false, response.getBody().get(SUCCESS));
		assertEquals("Task Not Assigned", response.getBody().get(MESSAGE));
	}

	//@Test
	void assignLeadTaskTestException() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("taskId", 1);
		when(leadTaskDaoService.getTaskById(1)).thenThrow(ResourceNotFoundException.class);
		when(employeeService.getById(1477)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> leadTaskServiceImpl.assignLeadTask(map));
	}

	@Test
	void deleteLeadTaskTest() {
		LeadTask leadTask = new LeadTask();
		when(leadTaskDaoService.getTaskById(taskId)).thenReturn(Optional.of(leadTask));
		when(leadTaskDaoService.addTask(leadTask)).thenReturn(leadTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskServiceImpl.deleteLeadTask(taskId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertEquals("Task deleted SuccessFully", response.getBody().get(MESSAGE));
	}

	@Test
	void deleteLeadTaskNotDeletedTest() {
		LeadTask leadTask = new LeadTask();
		when(leadTaskDaoService.getTaskById(taskId)).thenReturn(Optional.of(leadTask));
		when(leadTaskDaoService.addTask(leadTask)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskServiceImpl.deleteLeadTask(taskId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(false, response.getBody().get(SUCCESS));
		assertEquals("Task Not delete.", response.getBody().get(MESSAGE));
	}

	@Test
	void deleteLeadTaskTestException() {
		when(leadTaskDaoService.getTaskById(taskId)).thenThrow(RuntimeException.class);
		assertThrows(CRMException.class, () -> leadTaskServiceImpl.deleteLeadTask(taskId));
	}

	@Test
	void updateLeadTaskTest() {
		GetLeadTaskDto dto = new GetLeadTaskDto();
		LeadTask leadTask = new LeadTask();
		when(leadTaskDaoService.getTaskById(taskId)).thenReturn(Optional.of(leadTask));
		when(leadTaskDaoService.addTask(leadTask)).thenReturn(leadTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskServiceImpl.updateLeadTask(dto, taskId);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertEquals("Task Updated Successfully", response.getBody().get(MESSAGE));
	}

	@Test
	void updateLeadTaskNotUpdatedTest() {
		GetLeadTaskDto dto = new GetLeadTaskDto();
		LeadTask leadTask = new LeadTask();
		when(leadTaskDaoService.getTaskById(taskId)).thenReturn(Optional.of(leadTask));
		when(leadTaskDaoService.addTask(leadTask)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadTaskServiceImpl.updateLeadTask(dto, taskId);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(false, response.getBody().get(SUCCESS));
		assertEquals("Task Not Updated", response.getBody().get(MESSAGE));
	}

	@Test
	void updateLeadTaskTestException() {
		GetLeadTaskDto dto = new GetLeadTaskDto();
		when(leadTaskDaoService.getTaskById(taskId)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> leadTaskServiceImpl.updateLeadTask(dto, taskId));
	}
}
