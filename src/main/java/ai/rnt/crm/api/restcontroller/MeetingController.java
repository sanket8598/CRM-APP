package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.MEETING;

import java.util.EnumMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@CrossOrigin("*")
@RequiredArgsConstructor
public class MeetingController {

	private final MeetingService meetingService;

	@PostMapping("/add/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addMeeting(@RequestBody @Valid MeetingDto dto,
			@PathVariable(name = "leadId") Integer leadsId) {
		return meetingService.addMeeting(dto, leadsId);
	}

	@GetMapping("/edit/{meetingId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> editMeeting(@PathVariable Integer meetingId) {
		return meetingService.editMeeting(meetingId);
	}

	@PostMapping("/addTask/{leadId}/{meetingId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addMeetingTask(@RequestBody @Valid MeetingTaskDto dto,
			@PathVariable(name = "leadId") Integer leadsId, @PathVariable(name = "meetingId") Integer meetingId) {
		return meetingService.addMeetingTask(dto, leadsId, meetingId);
	}
}
