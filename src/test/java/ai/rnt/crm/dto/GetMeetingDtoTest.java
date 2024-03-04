package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class GetMeetingDtoTest {

	GetMeetingDto dto = new GetMeetingDto();
	GetMeetingDto dto1 = new GetMeetingDto();

	@Test
	void testGetMeetingDto() {
		GetMeetingDto meetingDto = new GetMeetingDto();
		Integer meetingId = 1;
		String meetingTitle = "Project Review Meeting";
		List<String> participants = new ArrayList<>();
		participants.add("John Doe");
		participants.add("Jane Smith");
		Date startDate = new Date();
		Date endDate = new Date();
		String startTime = "09:00";
		String endTime = "10:00";
		String startTime12Hours = "09:00 AM";
		String endTime12Hours = "10:00 AM";
		boolean allDay = false;
		String location = "Conference Room";
		String description = "Discuss project progress and upcoming tasks.";
		String meetingMode = "In-person";
		List<MeetingAttachmentsDto> meetingAttachments = new ArrayList<>();
		List<GetMeetingTaskDto> meetingTasks = new ArrayList<>();
		meetingDto.setMeetingId(meetingId);
		meetingDto.setMeetingTitle(meetingTitle);
		meetingDto.setParticipants(participants);
		meetingDto.setStartDate(startDate);
		meetingDto.setEndDate(endDate);
		meetingDto.setStartTime(startTime);
		meetingDto.setEndTime(endTime);
		meetingDto.setStartTime12Hours(startTime12Hours);
		meetingDto.setEndTime12Hours(endTime12Hours);
		meetingDto.setAllDay(allDay);
		meetingDto.setLocation(location);
		meetingDto.setDescription(description);
		meetingDto.setMeetingMode(meetingMode);
		meetingDto.setMeetingAttachments(meetingAttachments);
		meetingDto.setMeetingTasks(meetingTasks);
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(meetingId, meetingDto.getMeetingId());
		assertEquals(meetingTitle, meetingDto.getMeetingTitle());
		assertEquals(participants, meetingDto.getParticipants());
		assertEquals(startDate, meetingDto.getStartDate());
		assertEquals(endDate, meetingDto.getEndDate());
		assertEquals(startTime, meetingDto.getStartTime());
		assertEquals(endTime, meetingDto.getEndTime());
		assertEquals(startTime12Hours, meetingDto.getStartTime12Hours());
		assertEquals(endTime12Hours, meetingDto.getEndTime12Hours());
		assertEquals(allDay, meetingDto.isAllDay());
		assertEquals(location, meetingDto.getLocation());
		assertEquals(description, meetingDto.getDescription());
		assertEquals(meetingMode, meetingDto.getMeetingMode());
		assertEquals(meetingAttachments, meetingDto.getMeetingAttachments());
		assertEquals(meetingTasks, meetingDto.getMeetingTasks());
	}
}
