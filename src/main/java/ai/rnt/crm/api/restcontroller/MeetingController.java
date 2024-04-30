package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ADD_MEETING;
import static ai.rnt.crm.constants.ApiConstants.ADD_MEETING_TASK;
import static ai.rnt.crm.constants.ApiConstants.ASSIGN_MEETING;
import static ai.rnt.crm.constants.ApiConstants.ASSIGN_MEETING_TASK;
import static ai.rnt.crm.constants.ApiConstants.DELETE_MEETING;
import static ai.rnt.crm.constants.ApiConstants.DELETE_MEETING_TASK;
import static ai.rnt.crm.constants.ApiConstants.EDIT_MEETING;
import static ai.rnt.crm.constants.ApiConstants.GET_MEETING_TASK;
import static ai.rnt.crm.constants.ApiConstants.MEETING;
import static ai.rnt.crm.constants.ApiConstants.UPDATE_MEETING;
import static ai.rnt.crm.constants.ApiConstants.UPDATE_MEETING_TASK;
import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.GetMeetingTaskDto;
import ai.rnt.crm.dto.MeetingDto;
import ai.rnt.crm.dto.MeetingTaskDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.MeetingService;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 * @return metting data
 */
@RestController
@RequestMapping(MEETING)
@RequiredArgsConstructor
public class MeetingController {

	private final MeetingService meetingService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping(ADD_MEETING)
	public ResponseEntity<EnumMap<ApiResponse, Object>> addMeeting(@RequestBody @Valid MeetingDto dto,
			@PathVariable(name = "leadId") Integer leadsId) {
		return meetingService.addMeeting(dto, leadsId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(EDIT_MEETING)
	public ResponseEntity<EnumMap<ApiResponse, Object>> editMeeting(@PathVariable Integer meetingId) {
		return meetingService.editMeeting(meetingId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping(UPDATE_MEETING)
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateMeeting(@RequestBody MeetingDto dto,
			@PathVariable(name = "meetingId") Integer meetingId, @PathVariable(name = "status") String status) {
		return meetingService.updateMeeting(dto, meetingId, status);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping(ASSIGN_MEETING)
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignMeeting(@RequestBody Map<String, Integer> map) {
		return meetingService.assignMeeting(map);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@DeleteMapping(DELETE_MEETING)
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteMeeting(@PathVariable Integer meetingId) {
		return meetingService.deleteMeeting(meetingId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping(ADD_MEETING_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> addMeetingTask(@RequestBody @Valid MeetingTaskDto dto,
			@PathVariable(name = "leadId") Integer leadsId, @PathVariable(name = "meetingId") Integer meetingId) {
		return meetingService.addMeetingTask(dto, leadsId, meetingId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(GET_MEETING_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getMeetingTask(@PathVariable Integer taskId) {
		return meetingService.getMeetingTask(taskId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping(UPDATE_MEETING_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateMeetingTask(@RequestBody GetMeetingTaskDto dto,
			@PathVariable Integer taskId) {
		return meetingService.updateMeetingTask(dto, taskId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping(ASSIGN_MEETING_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignMeetingTask(@RequestBody Map<String, Integer> map) {
		return meetingService.assignMeetingTask(map);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@DeleteMapping(DELETE_MEETING_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteMeetingTask(@PathVariable Integer taskId) {
		return meetingService.deleteMeetingTask(taskId);
	}
}
