package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.DateFormatterConstant.END_TIME;
import static ai.rnt.crm.constants.DateFormatterConstant.START_TIME;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
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

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.CallDto;
import ai.rnt.crm.dto.CallTaskDto;
import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.dto.GetCallTaskDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;

@ExtendWith(MockitoExtension.class)
class CallServiceImplTest {

	@InjectMocks
	private CallServiceImpl callServiceImpl;

	@Mock
	private LeadDaoService leadDaoService;

	@Mock
	private CallDaoService callDaoService;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@Test
	void addCallTest() {
		CallDto dto = new CallDto();
		dto.setAllDay(true);
		dto.setStartTime(START_TIME);
		dto.setEndTime(END_TIME);
		Integer leadsId = 1;

		Leads lead = new Leads();
		lead.setLeadId(leadsId);

		EmployeeMaster employee = new EmployeeMaster();
		employee.setStaffId(1477);
		EmployeeDto empDto = new EmployeeDto();
		empDto.setStaffId(1477);
		dto.setCallFrom(empDto);

		Call mockedCall = mock(Call.class);

		when(leadDaoService.getLeadById(leadsId)).thenReturn(of(lead));
		when(employeeService.getById(employee.getStaffId())).thenReturn(of(employee));
		when(callDaoService.call(any(Call.class))).thenReturn(mockedCall);

		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.addCall(dto, leadsId);

		assertEquals(CREATED, responseEntity.getStatusCode());
		assertEquals("Call Added Successfully", responseEntity.getBody().get(MESSAGE));
		assertEquals(true, responseEntity.getBody().get(SUCCESS));

		verify(leadDaoService, times(1)).getLeadById(leadsId);
		verify(employeeService, times(1)).getById(employee.getStaffId());
		verify(callDaoService, times(1)).call(any(Call.class));
	}

	@Test
	void addCallTestElseBlock() {
		CallDto callDto = new CallDto();
		Integer leadsId = 1;

		EmployeeDto empDto = new EmployeeDto();
		empDto.setStaffId(1477);
		callDto.setCallFrom(empDto);

		when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(new Leads()));
		when(employeeService.getById(anyInt())).thenReturn(Optional.of(new EmployeeMaster()));
		when(callDaoService.call(any(Call.class))).thenReturn(null);

		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.addCall(callDto, leadsId);
		assertNotNull(responseEntity);
		assertEquals(CREATED, responseEntity.getStatusCode());

		EnumMap<ApiResponse, Object> result = responseEntity.getBody();
		assertNotNull(result);

		assertTrue((Boolean) result.get(SUCCESS));
		assertEquals("Call Not Added", result.get(MESSAGE));

		verify(leadDaoService, times(1)).getLeadById(anyInt());
		verify(employeeService, times(1)).getById(anyInt());
		verify(callDaoService, times(1)).call(any(Call.class));
	}

	@Test
	void addCallTestWithException() {
		CallDto callDto = new CallDto();
		Integer leadsId = 1;
		when(leadDaoService.getLeadById(anyInt())).thenThrow(new ResourceNotFoundException("Lead", "leadId", leadsId));
		assertThrows(CRMException.class, () -> callServiceImpl.addCall(callDto, leadsId));
		verify(leadDaoService, times(1)).getLeadById(anyInt());
	}

	@Test
	void editCallTest() {
		Integer callId = 1;
		Call expectedCall = new Call();
		when(callDaoService.getCallById(anyInt())).thenReturn(of(expectedCall));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.editCall(callId);
		assertNotNull(responseEntity);
		assertEquals(OK, responseEntity.getStatusCode());

		EnumMap<ApiResponse, Object> result = responseEntity.getBody();

		assertNotNull(result);
		assertTrue((Boolean) result.get(SUCCESS));
		assertNotNull(result.get(DATA));
		verify(callDaoService, times(1)).getCallById(anyInt());
	}

	@Test
	void editCallTestException() {
		Integer callId = 1;
		when(callDaoService.getCallById(anyInt())).thenThrow(new RuntimeException("Simulated exception"));
		assertThrows(CRMException.class, () -> callServiceImpl.editCall(callId));
		verify(callDaoService, times(1)).getCallById(anyInt());
	}

	@Test
	void updateCallTest() {
		CallDto callDto = new CallDto();
		Integer callId = 1;
		String status = "Save";

		EmployeeDto empDto = new EmployeeDto();
		empDto.setStaffId(1477);
		callDto.setCallFrom(empDto);
		callDto.setAllDay(true);
		Call existingCall = mock(Call.class);
		when(callDaoService.getCallById(anyInt())).thenReturn(of(existingCall));
		when(callDaoService.call(any(Call.class))).thenReturn(existingCall);
		when(employeeService.getById(anyInt())).thenReturn(of(new EmployeeMaster()));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.updateCall(callDto, callId,
				status);

		assertNotNull(responseEntity);
		assertEquals(CREATED, responseEntity.getStatusCode());

		EnumMap<ApiResponse, Object> result = responseEntity.getBody();
		assertNotNull(result);
		assertTrue((Boolean) result.get(SUCCESS));
		assertEquals("Call Updated Successfully", result.get(MESSAGE));

		verify(callDaoService, times(1)).getCallById(anyInt());
		verify(callDaoService, times(1)).call(any(Call.class));
		verify(employeeService, times(1)).getById(anyInt());
	}

	@Test
	void updateCallTestElse() {
		CallDto callDto = new CallDto();
		Integer callId = 1;
		String status = "Completed";

		EmployeeDto empDto = new EmployeeDto();
		empDto.setStaffId(1477);
		callDto.setCallFrom(empDto);
		callDto.setAllDay(false);
		Call existingCall = mock(Call.class);
		when(callDaoService.getCallById(anyInt())).thenReturn(of(existingCall));
		when(callDaoService.call(any(Call.class))).thenReturn(existingCall);
		when(employeeService.getById(anyInt())).thenReturn(of(new EmployeeMaster()));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.updateCall(callDto, callId,
				status);

		assertNotNull(responseEntity);
		assertEquals(CREATED, responseEntity.getStatusCode());

		EnumMap<ApiResponse, Object> result = responseEntity.getBody();
		assertNotNull(result);
		assertTrue((Boolean) result.get(SUCCESS));
		assertEquals("Call Updated And Completed Successfully", result.get(MESSAGE));

		verify(callDaoService, times(1)).getCallById(anyInt());
		verify(callDaoService, times(1)).call(any(Call.class));
		verify(employeeService, times(1)).getById(anyInt());
	}

	@Test
	void updateCallTestException() {
		CallDto callDto = new CallDto();
		Integer callId = 1;
		String status = "Save";
		when(callDaoService.getCallById(anyInt())).thenThrow(new RuntimeException("Simulated exception"));
		assertThrows(CRMException.class, () -> callServiceImpl.updateCall(callDto, callId, status));
		verify(callDaoService, times(1)).getCallById(anyInt());
	}

	@Test
	void assignCallTest() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		EmployeeMaster employee = new EmployeeMaster();

		map.put("staffId", 1477);
		map.put("addCallId", 1);

		Call existingCall = mock(Call.class);
		when(callDaoService.getCallById(anyInt())).thenReturn(of(existingCall));
		when(employeeService.getById(anyInt())).thenReturn(of(employee));
		when(callDaoService.call(any(Call.class))).thenReturn(existingCall);

		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.assignCall(map);

		assertNotNull(responseEntity);
		assertEquals(OK, responseEntity.getStatusCode());

		EnumMap<ApiResponse, Object> result = responseEntity.getBody();
		assertNotNull(result);
		assertTrue((Boolean) result.get(SUCCESS));
		assertEquals("Call Assigned SuccessFully", result.get(MESSAGE));

		verify(callDaoService, times(1)).getCallById(anyInt());
		verify(employeeService, times(1)).getById(anyInt());
		verify(callDaoService, times(1)).call(any(Call.class));
	}

	@Test
	void assignCallTestException() {
		Map<String, Integer> map = createTestMap();
		when(callDaoService.getCallById(anyInt())).thenThrow(CRMException.class);
		CRMException thrownException = assertThrows(CRMException.class, () -> callServiceImpl.assignCall(map));
		assertNotEquals("Simulated exception", thrownException.getMessage());
		verify(callDaoService, times(1)).getCallById(anyInt());
		verify(employeeService, never()).getById(anyInt());
		verify(callDaoService, never()).call(any(Call.class));
	}

	private Map<String, Integer> createTestMap() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("addCallId", 1);
		return map;
	}

	@Test
	void markAsCompletedTest() {
		int callId = 1;
		Call call = new Call();
		when(callDaoService.getCallById(callId)).thenReturn(java.util.Optional.of(call));
		when(callDaoService.call(call)).thenReturn(call);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.markAsCompleted(callId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue((Boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Call updated SuccessFully", responseEntity.getBody().get(ApiResponse.MESSAGE));
	}

	//@Test
	void notMarkAsCompletedTest() {
		int callId = 1;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(callDaoService.getCallById(callId)).thenReturn(java.util.Optional.of(new Call()));
		when(callDaoService.call(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.markAsCompleted(callId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertFalse((Boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Call Not updated", responseEntity.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void markAsCompletedTestException() {
		int callId = 1;
		when(callDaoService.getCallById(callId)).thenThrow(new RuntimeException("Test Exception"));
		assertThrows(CRMException.class, () -> callServiceImpl.markAsCompleted(callId));
	}

	@Test
	void deleteCallTest() {
		Integer callId = 1;
		Call existingCall = Mockito.mock(Call.class);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1477);
		when(callDaoService.getCallById(Mockito.anyInt())).thenReturn(Optional.of(existingCall));
		when(callDaoService.call(Mockito.any(Call.class))).thenReturn(existingCall);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.deleteCall(callId);
		assertNotNull(responseEntity);
		assertEquals(OK, responseEntity.getStatusCode());
		EnumMap<ApiResponse, Object> result = responseEntity.getBody();
		assertNotNull(result);
		assertTrue((Boolean) result.get(SUCCESS));
		assertEquals("Call deleted SuccessFully.", result.get(MESSAGE));
		verify(auditAwareUtil, times(1)).getLoggedInStaffId();
		verify(callDaoService, times(1)).getCallById(anyInt());
		verify(callDaoService, times(1)).call(any(Call.class));
	}

	@Test
	void notDeleteCallTest() {
		int callId = 1;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(callDaoService.getCallById(callId)).thenReturn(java.util.Optional.of(new Call()));
		when(callDaoService.call(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.deleteCall(callId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertFalse((Boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Call Not delete.", responseEntity.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void deleteCallTestException() {
		Integer callId = 1;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(123);
		when(callDaoService.getCallById(anyInt())).thenThrow(new RuntimeException("Simulated exception"));
		assertThrows(CRMException.class, () -> callServiceImpl.deleteCall(callId));
		verify(auditAwareUtil, times(1)).getLoggedInStaffId();
		verify(callDaoService, times(1)).getCallById(anyInt());
		verify(callDaoService, never()).call(any(Call.class));
	}

	@Test
	void addCallTaskTest() {
		CallTaskDto dto = new CallTaskDto();
		Integer leadsId = 1;
		Integer callId = 2;
		PhoneCallTask phoneCallTask = new PhoneCallTask();
		when(leadDaoService.getLeadById(anyInt())).thenReturn(of(new Leads()));
		when(callDaoService.getCallById(anyInt())).thenReturn(of(new Call()));
		when(callDaoService.addCallTask(any(PhoneCallTask.class))).thenReturn(phoneCallTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.addCallTask(dto, leadsId, callId);
		assertNotNull(responseEntity);
		assertEquals(CREATED, responseEntity.getStatusCode());
		EnumMap<ApiResponse, Object> result = responseEntity.getBody();
		assertNotNull(result);
		assertTrue((Boolean) result.get(SUCCESS));
		assertEquals("Task Added Successfully..!!", result.get(MESSAGE));
		verify(leadDaoService, times(1)).getLeadById(anyInt());
		verify(callDaoService, times(1)).getCallById(anyInt());
		verify(callDaoService, times(1)).getAllTask();
		verify(callDaoService, times(1)).addCallTask(any(PhoneCallTask.class));
	}

	@Test
	void notAddCallTaskTest() {
		CallTaskDto dto = new CallTaskDto();
		Integer leadsId = 1;
		Integer callId = 2;
		when(leadDaoService.getLeadById(leadsId)).thenReturn(java.util.Optional.of(new Leads()));
		when(callDaoService.getCallById(callId)).thenReturn(java.util.Optional.of(new Call()));
		when(callDaoService.getAllTask()).thenReturn(Collections.emptyList());
		when(callDaoService.addCallTask(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.addCallTask(dto, leadsId, callId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertFalse((Boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Task Not Added", responseEntity.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void addCallTaskTestException() {
		CallTaskDto dto = new CallTaskDto();
		Integer leadsId = 1;
		Integer callId = 2;
		when(leadDaoService.getLeadById(leadsId)).thenThrow(new RuntimeException("Test Exception"));
		assertThrows(CRMException.class, () -> callServiceImpl.addCallTask(dto, leadsId, callId));
	}

	@Test
	void getCallTaskTest() {
		Integer taskId = 1;
		PhoneCallTask callTaskEntity = new PhoneCallTask();
		when(callDaoService.getCallTaskById(taskId)).thenReturn(of(callTaskEntity));

		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.getCallTask(taskId);
		assertEquals(200, responseEntity.getStatusCodeValue());
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertTrue((Boolean) responseBody.get(SUCCESS));
		assertNotNull(responseBody.get(DATA));
		verify(callDaoService, times(1)).getCallTaskById(taskId);
	}

	@Test
	void getCallTaskTestException() {
		Integer taskId = 1;
		when(callDaoService.getCallTaskById(taskId)).thenReturn(empty());
		assertThrows(CRMException.class, () -> callServiceImpl.getCallTask(taskId));
		verify(callDaoService, times(1)).getCallTaskById(taskId);
	}

	@Test
	void updateCallTaskTest() {
		Integer taskId = 1;
		GetCallTaskDto dto = new GetCallTaskDto();
		PhoneCallTask existingTask = new PhoneCallTask();
		PhoneCallTask savedTask = new PhoneCallTask();
		when(callDaoService.getCallTaskById(taskId)).thenReturn(of(existingTask));
		when(callDaoService.addCallTask(any(PhoneCallTask.class))).thenReturn(savedTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.updateCallTask(dto, taskId);
		assertEquals(201, responseEntity.getStatusCodeValue());
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertTrue((Boolean) responseBody.get(SUCCESS));
		assertEquals("Task Updated Successfully..!!", responseBody.get(MESSAGE));
		verify(callDaoService, times(1)).getCallTaskById(taskId);
		verify(callDaoService, times(1)).addCallTask(any(PhoneCallTask.class));
	}

	@Test
	void notUpdateCallTaskTest() {
		GetCallTaskDto dto = new GetCallTaskDto();
		Integer taskId = 1;
		when(callDaoService.getCallTaskById(taskId)).thenReturn(java.util.Optional.of(new PhoneCallTask()));
		when(callDaoService.addCallTask(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.updateCallTask(dto, taskId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertFalse((Boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Task Not Updated", responseEntity.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void updateCallTaskTestException() {
		Integer taskId = 1;
		GetCallTaskDto dto = new GetCallTaskDto();
		when(callDaoService.getCallTaskById(taskId)).thenReturn(empty());
		CRMException exception = assertThrows(CRMException.class, () -> callServiceImpl.updateCallTask(dto, taskId));
		assertNotEquals("PhoneCallTask not found with callTaskId : " + taskId, exception.getMessage());
		verify(callDaoService, times(1)).getCallTaskById(taskId);
		verify(callDaoService, never()).addCallTask(any(PhoneCallTask.class));
	}

	@Test
	void assignCallTaskTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1);
		map.put("taskId", 2);
		PhoneCallTask callTask = new PhoneCallTask();
		when(callDaoService.getCallTaskById(2)).thenReturn(of(callTask));
		EmployeeMaster employee = new EmployeeMaster();
		when(employeeService.getById(1)).thenReturn(of(employee));
		when(callDaoService.addCallTask(callTask)).thenReturn(callTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.assignCallTask(map);
		assertEquals(OK, responseEntity.getStatusCode());
		assertTrue((Boolean) responseEntity.getBody().get(SUCCESS));
		assertEquals("Task Assigned SuccessFully", responseEntity.getBody().get(MESSAGE));
		verify(callDaoService, times(1)).getCallTaskById(2);
		verify(employeeService, times(1)).getById(1);
		verify(callDaoService, times(1)).addCallTask(callTask);
	}

	@Test
	void notAssignCallTaskTest() {
		Integer taskId = 2;
		Integer staffId = 1;
		PhoneCallTask callTask = new PhoneCallTask();
		Map<String,Integer> map=new HashMap<>();
		map.put("taskId", taskId);
		map.put("staffId", staffId);
		EmployeeMaster employee = new EmployeeMaster();
		when(callDaoService.getCallTaskById(taskId)).thenReturn(java.util.Optional.of(callTask));
		when(employeeService.getById(staffId)).thenReturn(java.util.Optional.of(employee));
		when(callDaoService.addCallTask(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl
				.assignCallTask(map);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertFalse((Boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Task Not Assigned", responseEntity.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void assignCallTaskTestException() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1);
		map.put("taskId", 2);
		when(callDaoService.getCallTaskById(2)).thenReturn(empty());
		assertThrows(CRMException.class, () -> callServiceImpl.assignCallTask(map));
		verify(callDaoService, times(1)).getCallTaskById(2);
		verify(employeeService, never()).getById(anyInt());
		verify(callDaoService, never()).addCallTask(any());
	}

	@Test
	void deleteCallTaskTest() {
		Integer taskId = 1;
		PhoneCallTask callTask = new PhoneCallTask();
		when(callDaoService.getCallTaskById(taskId)).thenReturn(java.util.Optional.of(callTask));
		when(callDaoService.addCallTask(any())).thenReturn(callTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.deleteCallTask(taskId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue((Boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	void deleteCallTaskTestElse() {
		Integer taskId = 1;
		when(callDaoService.getCallTaskById(taskId)).thenReturn(Optional.of(new PhoneCallTask()));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = callServiceImpl.deleteCallTask(taskId);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		EnumMap<ApiResponse, Object> responseMap = responseEntity.getBody();
		assertNotNull(responseMap);
		verify(callDaoService, times(1)).getCallTaskById(taskId);
		verify(callDaoService, times(1)).addCallTask(any());
	}

	@Test
	void deleteCallTaskTestException() {
		Integer taskId = 1;
		when(callDaoService.getCallTaskById(taskId)).thenThrow(new RuntimeException("Test Exception"));
		assertThrows(CRMException.class, () -> callServiceImpl.deleteCallTask(taskId));
	}
}
