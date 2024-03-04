package ai.rnt.crm.api.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.GetMeetingTaskDto;
import ai.rnt.crm.dto.MeetingDto;
import ai.rnt.crm.dto.MeetingTaskDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.MeetingService;

class MeetingControllerTest {

	@Mock
	private MeetingService meetingService;

	@InjectMocks
	private MeetingController meetingController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void addMeetingTest() {
		MeetingService meetingService = mock(MeetingService.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(meetingService.addMeeting(any(MeetingDto.class), anyInt())).thenReturn(expectedResponse);
		MeetingController meetingController = new MeetingController(meetingService);
		MeetingDto dto = new MeetingDto();
		Integer leadsId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingController.addMeeting(dto, leadsId);
		verify(meetingService).addMeeting(dto, leadsId);
		assertEquals(expectedResponse, response);
	}

	@Test
	void editMeetingTest() {
		MeetingService meetingService = mock(MeetingService.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(meetingService.editMeeting(anyInt())).thenReturn(expectedResponse);
		MeetingController meetingController = new MeetingController(meetingService);
		Integer meetingId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingController.editMeeting(meetingId);
		verify(meetingService).editMeeting(meetingId);
		assertEquals(expectedResponse, response);
	}

	@Test
	void updateMeetingTest() {
		MeetingService meetingService = mock(MeetingService.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(meetingService.updateMeeting(any(), anyInt(), anyString())).thenReturn(expectedResponse);
		MeetingController meetingController = new MeetingController(meetingService);
		MeetingDto dto = new MeetingDto();
		Integer meetingId = 1;
		String status = "Scheduled";
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingController.updateMeeting(dto, meetingId, status);
		verify(meetingService).updateMeeting(dto, meetingId, status);
		assertEquals(expectedResponse, response);
	}

	@Test
	void assignMeetingTest() {
		MeetingService meetingService = mock(MeetingService.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(meetingService.assignMeeting(anyMap())).thenReturn(expectedResponse);
		MeetingController meetingController = new MeetingController(meetingService);
		Map<String, Integer> map = new HashMap<>();
		map.put("meetingId", 1);
		map.put("userId", 2);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingController.assignMeeting(map);
		verify(meetingService).assignMeeting(map);
		assertEquals(expectedResponse, response);
	}

	@Test
	void deleteMeetingTest() {
		MeetingService meetingService = mock(MeetingService.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(meetingService.deleteMeeting(anyInt())).thenReturn(expectedResponse);
		MeetingController meetingController = new MeetingController(meetingService);
		int meetingId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingController.deleteMeeting(meetingId);
		verify(meetingService).deleteMeeting(meetingId);
		assertEquals(expectedResponse, response);
	}

	@Test
	void addMeetingTaskTest() {
		MeetingService meetingService = mock(MeetingService.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(meetingService.addMeetingTask(any(), anyInt(), anyInt())).thenReturn(expectedResponse);
		MeetingController meetingController = new MeetingController(meetingService);
		MeetingTaskDto dto = new MeetingTaskDto();
		int leadsId = 1;
		int meetingId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingController.addMeetingTask(dto, leadsId,
				meetingId);
		verify(meetingService).addMeetingTask(dto, leadsId, meetingId);
		assertEquals(expectedResponse, response);
	}

	@Test
	void getMeetingTaskTest() {
		Integer taskId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(meetingService.getMeetingTask(anyInt())).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingController.getMeetingTask(taskId);
		verify(meetingService).getMeetingTask(taskId);
	}

	@Test
	void updateMeetingTaskTest() {
		Integer taskId = 1;
		GetMeetingTaskDto dto = new GetMeetingTaskDto();
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(meetingService.updateMeetingTask(any(GetMeetingTaskDto.class), any(Integer.class)))
				.thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingController.updateMeetingTask(dto, taskId);
		verify(meetingService).updateMeetingTask(dto, taskId);
	}

	@Test
	void assignMeetingTaskTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("key", 1);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(meetingService.assignMeetingTask(any(Map.class))).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingController.assignMeetingTask(map);
		verify(meetingService).assignMeetingTask(map);
	}

	@Test
	void deleteMeetingTaskTest() {
		Integer taskId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		MeetingService meetingService = Mockito.mock(MeetingService.class);
		when(meetingService.deleteMeetingTask(taskId)).thenReturn(expectedResponse);
		MeetingController meetingController = new MeetingController(meetingService);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = meetingController.deleteMeetingTask(taskId);
		verify(meetingService).deleteMeetingTask(taskId);
	}
}
