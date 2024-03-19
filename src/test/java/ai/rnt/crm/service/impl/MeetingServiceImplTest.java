package ai.rnt.crm.service.impl;

import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import ai.rnt.crm.dao.service.MeetingAttachmentDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dto.GetMeetingTaskDto;
import ai.rnt.crm.dto.MeetingAttachmentsDto;
import ai.rnt.crm.dto.MeetingDto;
import ai.rnt.crm.dto.MeetingTaskDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.MeetingAttachments;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.repository.MeetingAttachmentRepository;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;
import ai.rnt.crm.util.MeetingUtil;

@ExtendWith(MockitoExtension.class)
class MeetingServiceImplTest {

	@Mock
	private MeetingDaoService meetingDaoService;

	@Mock
	private MeetingAttachmentDaoService meetingAttachmentDaoService;

	@Mock
	private MeetingUtil meetingUtil;

	@Mock
	private LeadDaoService leadDaoService;

	@Mock
	private EmployeeService employeeService;
	
	@Mock
	private MeetingAttachmentDaoService meetingAttachmetDaoService;
	@Mock
	private MeetingAttachmentRepository meetingAttachmentRepository;

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@InjectMocks
	private MeetingServiceImpl meetingServiceImpl;

	@Test
	void addMeetingTest() {
		MeetingDto dto = new MeetingDto();
		Meetings meetings = new Meetings();
		List<String> participants = Arrays.asList("s.wakankar@rnt.ai", "t.pagare@rnt.ai");
		dto.setParticipates(participants);
		dto.setAllDay(true);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(employeeService.getById(anyInt())).thenReturn(java.util.Optional.of(new EmployeeMaster()));
		when(leadDaoService.getLeadById(anyInt())).thenReturn(java.util.Optional.of(new Leads()));
		when(meetingDaoService.addMeeting(any())).thenReturn(meetings);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = meetingServiceImpl.addMeeting(dto, 1);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertTrue((Boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Meeting Added Successfully..!!", responseEntity.getBody().get(ApiResponse.MESSAGE));
	}
	
	@Test
	void addMeetingWithAttachmentTest() {
		MeetingDto dto = new MeetingDto();
		List<MeetingAttachmentsDto>  dtoAttachList=new ArrayList<>();
		MeetingAttachmentsDto dtoAttach=new MeetingAttachmentsDto();
		dtoAttachList.add(dtoAttach);
		dto.setMeetingAttachments(dtoAttachList);
		MeetingAttachments mettingAttch=new MeetingAttachments();
		List<String> participants = Arrays.asList("s.wakankar@rnt.ai", "t.pagare@rnt.ai");
		dto.setParticipates(participants);
		dto.setAllDay(true);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(employeeService.getById(anyInt())).thenReturn(java.util.Optional.of(new EmployeeMaster()));
		when(leadDaoService.getLeadById(anyInt())).thenReturn(java.util.Optional.of(new Leads()));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = meetingServiceImpl.addMeeting(dto, 1);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertFalse((Boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Meeting Not Added..!!", responseEntity.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void addMeetingTestException() {
		MeetingDto dto = new MeetingDto();
		Mockito.lenient().when(leadDaoService.getLeadById(0)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> meetingServiceImpl.addMeeting(dto, 1));
	}

	@Test
	void editMeetingTest() {
		Integer meetingId = 1;
		Meetings meeting = new Meetings();
		when(meetingDaoService.getMeetingById(meetingId)).thenReturn(Optional.of(meeting));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.editMeeting(meetingId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertNotNull(response.getBody().get(DATA));
	}

	@Test
	void editMeetingTestException() {
		Integer meetingId = 1;
		when(meetingDaoService.getMeetingById(meetingId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> meetingServiceImpl.editMeeting(meetingId));
	}

	@Test
	void deleteMeetingTest() {
		Integer meetingId = 1;
		Meetings meeting = new Meetings();
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(meetingDaoService.getMeetingById(meetingId)).thenReturn(Optional.of(meeting));
		when(meetingDaoService.addMeeting(meeting)).thenReturn(meeting);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.deleteMeeting(meetingId);
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Meeting deleted SuccessFully.", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void notDeleteMeetingTest() {
		Integer meetingId = 1;
		Meetings meeting = new Meetings();
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(meetingDaoService.getMeetingById(meetingId)).thenReturn(Optional.of(meeting));
		when(meetingDaoService.addMeeting(meeting)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.deleteMeeting(meetingId);
		assertFalse((boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Meeting Not delete.", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void deleteMeetingTestWithException() {
		Integer meetingId = 1;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1477);
		when(meetingDaoService.getMeetingById(anyInt())).thenThrow(new RuntimeException("Simulated exception"));
		assertThrows(CRMException.class, () -> meetingServiceImpl.deleteMeeting(meetingId));
		verify(auditAwareUtil, times(1)).getLoggedInStaffId();
		verify(meetingDaoService, times(1)).getMeetingById(anyInt());
	}

	@Test
	void addMeetingTaskTest() {
		MeetingTaskDto dto = new MeetingTaskDto();
		Integer leadsId = 1;
		Integer meetingId = 1;
		Leads lead = new Leads();
		lead.setEmployee(new EmployeeMaster());
		when(leadDaoService.getLeadById(leadsId)).thenReturn(Optional.of(lead));
		when(meetingDaoService.getMeetingById(meetingId)).thenReturn(Optional.of(new Meetings()));
		when(meetingDaoService.getAllMeetingTask()).thenReturn(Collections.emptyList());
		when(meetingDaoService.addMeetingTask(any())).thenReturn(new MeetingTask());
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.addMeetingTask(dto, leadsId,
				meetingId);
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Task Added Successfully..!!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void notAddMeetingTaskTest() {
		MeetingTaskDto dto = new MeetingTaskDto();
		Integer leadsId = 1;
		Integer meetingId = 1;
		Leads lead = new Leads();
		lead.setEmployee(new EmployeeMaster());
		when(leadDaoService.getLeadById(leadsId)).thenReturn(Optional.of(lead));
		when(meetingDaoService.getMeetingById(meetingId)).thenReturn(Optional.of(new Meetings()));
		when(meetingDaoService.getAllMeetingTask()).thenReturn(Collections.emptyList());
		when(meetingDaoService.addMeetingTask(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.addMeetingTask(dto, leadsId,
				meetingId);
		assertFalse((boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Task Not Added", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void addMeetingTaskTestException() {
		MeetingTaskDto dto = new MeetingTaskDto();
		Integer leadsId = 1;
		Integer meetingId = 2;
		when(meetingDaoService.getMeetingById(meetingId)).thenThrow(new ResourceNotFoundException());
		assertThrows(CRMException.class, () -> meetingServiceImpl.addMeetingTask(dto, leadsId, meetingId));
	}

	@Test
	void getMeetingTaskTest() {
		Integer meetingTaskId = 1;
		MeetingTask meetingTask = new MeetingTask();
		when(meetingDaoService.getMeetingTaskById(meetingTaskId)).thenReturn(Optional.of(meetingTask));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.getMeetingTask(meetingTaskId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(SUCCESS));
		assertNotNull(response.getBody().get(DATA));
	}

	@Test
	void getMeetingTaskTestException() {
		Integer meetingTaskId = 1;
		when(meetingDaoService.getMeetingTaskById(meetingTaskId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> meetingServiceImpl.getMeetingTask(meetingTaskId));
	}

	@Test
	void updateMeetingTaskTest() {
		Integer taskId = 1;
		GetMeetingTaskDto dto = new GetMeetingTaskDto();
		MeetingTask meetingTaskEntity = new MeetingTask();
		when(meetingDaoService.getMeetingTaskById(taskId)).thenReturn(Optional.of(meetingTaskEntity));
		when(meetingDaoService.addMeetingTask(meetingTaskEntity)).thenReturn(meetingTaskEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.updateMeetingTask(dto, taskId);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Task Updated Successfully..!!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void tnotUpdateMeetingTask() {
		Integer taskId = 1;
		GetMeetingTaskDto dto = new GetMeetingTaskDto();
		MeetingTask meetingTaskEntity = new MeetingTask();
		when(meetingDaoService.getMeetingTaskById(taskId)).thenReturn(Optional.of(meetingTaskEntity));
		when(meetingDaoService.addMeetingTask(meetingTaskEntity)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.updateMeetingTask(dto, taskId);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertFalse((boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Task Not Updated", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void updateMeetingTaskTestException() {
		Integer taskId = 1;
		GetMeetingTaskDto dto = new GetMeetingTaskDto();
		when(meetingDaoService.getMeetingTaskById(taskId)).thenThrow(new RuntimeException("Some error"));
		assertThrows(CRMException.class, () -> meetingServiceImpl.updateMeetingTask(dto, taskId));
	}

	@Test
	void assignMeetingTaskTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1);
		map.put("taskId", 1);
		EmployeeMaster employee = new EmployeeMaster();
		MeetingTask meetingTask = new MeetingTask();
		when(meetingDaoService.getMeetingTaskById(1)).thenReturn(Optional.of(meetingTask));
		when(employeeService.getById(1)).thenReturn(Optional.of(employee));
		when(meetingDaoService.addMeetingTask(meetingTask)).thenReturn(meetingTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = meetingServiceImpl.assignMeetingTask(map);
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(true, responseBody.get(ApiResponse.SUCCESS));
		assertEquals("Task Assigned SuccessFully", responseBody.get(ApiResponse.MESSAGE));
	}

	@Test
	void notAssignMeetingTaskTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1);
		map.put("taskId", 1);
		EmployeeMaster employee = new EmployeeMaster();
		MeetingTask meetingTask = new MeetingTask();
		when(meetingDaoService.getMeetingTaskById(1)).thenReturn(Optional.of(meetingTask));
		when(employeeService.getById(1)).thenReturn(Optional.of(employee));
		when(meetingDaoService.addMeetingTask(meetingTask)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = meetingServiceImpl.assignMeetingTask(map);
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(false, responseBody.get(ApiResponse.SUCCESS));
		assertEquals("Task Not Assigned", responseBody.get(ApiResponse.MESSAGE));
	}

	@Test
	void assignMeetingTaskTestException() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1);
		map.put("taskId", 1);
		when(meetingDaoService.getMeetingTaskById(1)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> meetingServiceImpl.assignMeetingTask(map));
	}

	@Test
	void testDeleteMeetingTask() {
		Integer taskId = 1;
		MeetingTask meetingTask = new MeetingTask();
		meetingTask.setMeetingTaskId(taskId);
		when(meetingDaoService.getMeetingTaskById(taskId)).thenReturn(java.util.Optional.of(meetingTask));
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(123);
		when(meetingDaoService.addMeetingTask(any(MeetingTask.class))).thenReturn(meetingTask);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.deleteMeetingTask(taskId);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Meeting Task Deleted SuccessFully.", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void notDeleteMeetingTask() {
		Integer taskId = 1;
		MeetingTask meetingTask = new MeetingTask();
		meetingTask.setMeetingTaskId(taskId);
		when(meetingDaoService.getMeetingTaskById(taskId)).thenReturn(java.util.Optional.of(meetingTask));
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(123);
		when(meetingDaoService.addMeetingTask(any(MeetingTask.class))).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.deleteMeetingTask(taskId);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Meeting Task Not delete.", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void deleteMeetingTaskTestException() {
		Integer taskId = 1;
		when(meetingDaoService.getMeetingTaskById(taskId)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> meetingServiceImpl.deleteMeetingTask(taskId));
	}

	@Test
	void assignMeetingTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("meetingId", 1);
		Meetings meetings = new Meetings();
		EmployeeMaster employee = new EmployeeMaster();
		when(meetingDaoService.getMeetingById(1)).thenReturn(java.util.Optional.of(meetings));
		when(employeeService.getById(1477)).thenReturn(java.util.Optional.of(employee));
		when(meetingDaoService.addMeeting(any(Meetings.class))).thenReturn(meetings);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.assignMeeting(map);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Meeting Assigned SuccessFully", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void notAssignMeetingTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("meetingId", 1);
		Meetings meetings = new Meetings();
		EmployeeMaster employee = new EmployeeMaster();
		when(meetingDaoService.getMeetingById(1)).thenReturn(java.util.Optional.of(meetings));
		when(employeeService.getById(1477)).thenReturn(java.util.Optional.of(employee));
		when(meetingDaoService.addMeeting(any(Meetings.class))).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.assignMeeting(map);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Meeting Not Assigned", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void assignMeetingTestException() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("meetingId", 1);
		when(meetingDaoService.getMeetingById(1)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> meetingServiceImpl.assignMeeting(map));
	}
	
	 @Test
	    void updateMeetingSaveTest() {
	        MeetingDto dto = new MeetingDto(); 
	        List<String> participants = Arrays.asList("s.wakankar@rnt.ai", "t.pagare@rnt.ai");
			dto.setParticipates(participants);
			dto.setAllDay(true);
	        Integer meetingId = 1;
	        String status = "Save";
	        Meetings meetings = new Meetings(); 
	        meetings.setMeetingStatus("Save");
	        when(meetingDaoService.getMeetingById(meetingId)).thenReturn(java.util.Optional.of(meetings));
	        when(meetingDaoService.addMeeting(any(Meetings.class))).thenReturn(meetings);
	        ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.updateMeeting(dto, meetingId, status);
	        assertNotNull(response);
	        assertEquals(HttpStatus.CREATED, response.getStatusCode());
	        assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
	        assertEquals("Meeting Updated Successfully", response.getBody().get(ApiResponse.MESSAGE));
	    }
	 
	 @Test
	 void notUpdateMeetingTest() {
		 MeetingDto dto = new MeetingDto(); 
		 List<String> participants = Arrays.asList("s.wakankar@rnt.ai", "t.pagare@rnt.ai");
		 dto.setParticipates(participants);
		 Integer meetingId = 1;
		 String status = null;
		 Meetings meetings = new Meetings(); 
		 when(meetingDaoService.getMeetingById(meetingId)).thenReturn(java.util.Optional.of(meetings));
		 when(meetingDaoService.addMeeting(any(Meetings.class))).thenReturn(meetings);
		 ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingServiceImpl.updateMeeting(dto, meetingId, status);
		 assertNotNull(response);
		 assertEquals(HttpStatus.CREATED, response.getStatusCode());
		 assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		 assertEquals("Meeting Not Updated", response.getBody().get(ApiResponse.MESSAGE));
	 }

	 @Test
		void updateMeetingTestException() {
			MeetingDto dto = new MeetingDto();
			Integer meetingId = 1;
			String status = "Save";
			when(meetingDaoService.getMeetingById(meetingId)).thenThrow(ResourceNotFoundException.class);
			assertThrows(CRMException.class, () -> meetingServiceImpl.updateMeeting(dto, meetingId, status));
		}
}
