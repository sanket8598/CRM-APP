package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.GetMeetingTaskDto;
import ai.rnt.crm.dto.MeetingDto;
import ai.rnt.crm.dto.MeetingTaskDto;
import ai.rnt.crm.enums.ApiResponse;

public interface MeetingService {

	ResponseEntity<EnumMap<ApiResponse, Object>> addMeeting(@Valid MeetingDto dto, Integer leadsId);

	ResponseEntity<EnumMap<ApiResponse, Object>> editMeeting(Integer meetingId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateMeeting(MeetingDto dto, Integer meetingId, String status);

	ResponseEntity<EnumMap<ApiResponse, Object>> assignMeeting(Map<String, Integer> map);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteMeeting(Integer meetingId);

	ResponseEntity<EnumMap<ApiResponse, Object>> addMeetingTask(@Valid MeetingTaskDto dto, Integer leadsId,
			Integer meetingId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getMeetingTask(Integer taskId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateMeetingTask(GetMeetingTaskDto dto, Integer taskId);

	ResponseEntity<EnumMap<ApiResponse, Object>> assignMeetingTask(Map<String, Integer> map);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteMeetingTask(Integer taskId);
}
