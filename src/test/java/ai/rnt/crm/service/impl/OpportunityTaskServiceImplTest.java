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

import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.OpportunityTaskDaoService;
import ai.rnt.crm.dto.opportunity.GetOpportunityTaskDto;
import ai.rnt.crm.dto.opportunity.OpportunityTaskDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.OpportunityTask;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;

@ExtendWith(MockitoExtension.class)
class OpportunityTaskServiceImplTest {

	@Mock
	private OpportunityTaskDto opportunityTaskDto;

	@Mock
	private OpportunityTask opportunityTask;

	@Mock
	private Opportunity opportunity;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@Mock
	private OpportunityDaoService opportunityDaoService;

	@Mock
	private OpportunityTaskDaoService opportunityTaskDaoService;

	@InjectMocks
	private OpportunityTaskServiceImpl opportunityTaskServiceImpl;

	Integer taskId = 1;
	Integer leadsId = 1;

	@Test
	void addOpportunityTaskTest() throws Exception {
		OpportunityTaskDto dto = new OpportunityTaskDto();
		when(opportunityDaoService.findOpportunity(leadsId)).thenReturn(Optional.of(new Opportunity()));
		when(employeeService.getById(any())).thenReturn(Optional.of(new EmployeeMaster()));
		when(opportunityTaskDaoService.addOptyTask(any())).thenReturn(new OpportunityTask());
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityTaskServiceImpl.addOpportunityTask(dto,
				leadsId);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void addOpportunityTaskNotAddedTest() {
		OpportunityTaskDto dto = new OpportunityTaskDto();
		when(opportunityDaoService.findOpportunity(leadsId)).thenReturn(Optional.of(new Opportunity()));
		when(employeeService.getById(any())).thenReturn(Optional.of(new EmployeeMaster()));
		when(opportunityTaskDaoService.getAllTask()).thenReturn(new ArrayList<>());
		when(opportunityTaskDaoService.addOptyTask(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityTaskServiceImpl.addOpportunityTask(dto,
				leadsId);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(false, response.getBody().get(SUCCESS));
		assertEquals("Task Not Added", response.getBody().get(MESSAGE));
	}

	@Test
	void addOpportunityTaskTestException() {
		OpportunityTaskDto dto = new OpportunityTaskDto();
		when(opportunityDaoService.findOpportunity(leadsId)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> opportunityTaskServiceImpl.addOpportunityTask(dto, leadsId));
	}

	@Test
	void getOpportunityTaskTest() {
		OpportunityTask leadTask = new OpportunityTask();
		when(opportunityTaskDaoService.getOptyTaskById(taskId)).thenReturn(Optional.of(leadTask));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityTaskServiceImpl.getOpportunityTask(taskId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertNotNull(response.getBody().get(DATA));
	}

	@Test
	void getOpportunityTaskTestException() {
		when(opportunityTaskDaoService.getOptyTaskById(taskId)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> opportunityTaskServiceImpl.getOpportunityTask(taskId));
	}

	@Test
	void assignOpportunityTaskTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("taskId", 1);
		OpportunityTask opportunityTask = new OpportunityTask();
		EmployeeMaster employee = new EmployeeMaster();
		when(opportunityTaskDaoService.getOptyTaskById(1)).thenReturn(Optional.of(opportunityTask));
		when(employeeService.getById(1477)).thenReturn(Optional.of(employee));
		when(opportunityTaskDaoService.addOptyTask(opportunityTask)).thenReturn(opportunityTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityTaskServiceImpl.assignOpportunityTask(map);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertEquals("Task Assigned SuccessFully", response.getBody().get(MESSAGE));
	}

	@Test
	void assignOpportunityTaskNotAssignedTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("taskId", 2);
		OpportunityTask opportunityTask = new OpportunityTask();
		when(opportunityTaskDaoService.getOptyTaskById(2)).thenReturn(Optional.of(opportunityTask));
		EmployeeMaster employee = new EmployeeMaster();
		when(employeeService.getById(1477)).thenReturn(Optional.of(employee));
		when(opportunityTaskDaoService.addOptyTask(opportunityTask)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityTaskServiceImpl.assignOpportunityTask(map);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(false, response.getBody().get(SUCCESS));
		assertEquals("Task Not Assigned", response.getBody().get(MESSAGE));
	}

	@Test
	void testAssignOpportunityTaskException() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("taskId", 1);
		int id = 1;
		when(opportunityTaskDaoService.getOptyTaskById(id)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> opportunityTaskServiceImpl.assignOpportunityTask(map));
	}

	@Test
	void updateOpportunityTaskTest() {
		GetOpportunityTaskDto dto = new GetOpportunityTaskDto();
		OpportunityTask opportunityTask = new OpportunityTask();
		when(opportunityTaskDaoService.getOptyTaskById(taskId)).thenReturn(Optional.of(opportunityTask));
		when(opportunityTaskDaoService.addOptyTask(opportunityTask)).thenReturn(opportunityTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityTaskServiceImpl.updateOpportunityTask(dto,
				taskId);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertEquals("Task Updated Successfully", response.getBody().get(MESSAGE));
	}

	@Test
	void updateOpportunityTaskNotUpdatedTest() {
		GetOpportunityTaskDto dto = new GetOpportunityTaskDto();
		OpportunityTask opportunityTask = new OpportunityTask();
		when(opportunityTaskDaoService.getOptyTaskById(taskId)).thenReturn(Optional.of(opportunityTask));
		when(opportunityTaskDaoService.addOptyTask(opportunityTask)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityTaskServiceImpl.updateOpportunityTask(dto,
				taskId);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(false, response.getBody().get(SUCCESS));
		assertEquals("Task Not Updated", response.getBody().get(MESSAGE));
	}

	@Test
	void updateOpportunityTaskTestException() {
		GetOpportunityTaskDto dto = new GetOpportunityTaskDto();
		when(opportunityTaskDaoService.getOptyTaskById(taskId)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> opportunityTaskServiceImpl.updateOpportunityTask(dto, taskId));
	}

	@Test
	void deleteOpportunityTaskTaskTest() {
		OpportunityTask opportunityTask = new OpportunityTask();
		when(opportunityTaskDaoService.getOptyTaskById(taskId)).thenReturn(Optional.of(opportunityTask));
		when(opportunityTaskDaoService.addOptyTask(opportunityTask)).thenReturn(opportunityTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityTaskServiceImpl
				.deleteOpportunityTask(taskId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertEquals("Task deleted SuccessFully", response.getBody().get(MESSAGE));
	}

	@Test
	void deleteOpportunityTaskNotDeletedTest() {
		OpportunityTask opportunityTask = new OpportunityTask();
		when(opportunityTaskDaoService.getOptyTaskById(taskId)).thenReturn(Optional.of(opportunityTask));
		when(opportunityTaskDaoService.addOptyTask(opportunityTask)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityTaskServiceImpl
				.deleteOpportunityTask(taskId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(false, response.getBody().get(SUCCESS));
		assertEquals("Task Not delete.", response.getBody().get(MESSAGE));
	}

	@Test
	void deleteOpportunityTaskTestException() {
		when(opportunityTaskDaoService.getOptyTaskById(taskId)).thenThrow(RuntimeException.class);
		assertThrows(CRMException.class, () -> opportunityTaskServiceImpl.deleteOpportunityTask(taskId));
	}
}
