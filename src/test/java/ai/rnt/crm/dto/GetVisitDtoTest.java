package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class GetVisitDtoTest {

	@Test
	void testGetVisitDto() {
		GetVisitDto visitDto = new GetVisitDto();
		GetVisitDto dto = new GetVisitDto();
		GetVisitDto dto1 = new GetVisitDto();
		Integer visitId = 1;
		String location = "Client Office";
		String subject = "Meeting with Client";
		String duration = "1 hour";
		String content = "Discuss project progress";
		String comment = "Client was cooperative";
		Date startDate = new Date();
		Date endDate = new Date();
		List<String> participants = new ArrayList<>();
		participants.add("John Doe");
		participants.add("Jane Smith");
		String startTime = "09:00";
		String endTime = "10:00";
		String startTime12Hours = "09:00 AM";
		String endTime12Hours = "10:00 AM";
		boolean allDay = false;
		List<GetVisitTaskDto> visitTasks = new ArrayList<>();
		visitDto.setVisitId(visitId);
		visitDto.setLocation(location);
		visitDto.setSubject(subject);
		visitDto.setDuration(duration);
		visitDto.setContent(content);
		visitDto.setComment(comment);
		visitDto.setStartDate(startDate);
		visitDto.setEndDate(endDate);
		visitDto.setParticipants(participants);
		visitDto.setStartTime(startTime);
		visitDto.setEndTime(endTime);
		visitDto.setStartTime12Hours(startTime12Hours);
		visitDto.setEndTime12Hours(endTime12Hours);
		visitDto.setAllDay(allDay);
		visitDto.setVisitTasks(visitTasks);
		dto1.setVisitId(visitId);
		dto1.setLocation(location);
		dto1.setSubject(subject);
		dto1.setDuration(duration);
		dto1.setContent(content);
		dto1.setComment(comment);
		dto1.setStartDate(startDate);
		dto1.setEndDate(endDate);
		dto1.setParticipants(participants);
		dto1.setStartTime(startTime);
		dto1.setEndTime(endTime);
		dto1.setStartTime12Hours(startTime12Hours);
		dto1.setEndTime12Hours(endTime12Hours);
		dto1.setAllDay(allDay);
		dto1.setVisitTasks(visitTasks);
		visitDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertEquals(visitId, visitDto.getVisitId());
		assertEquals(location, visitDto.getLocation());
		assertEquals(subject, visitDto.getSubject());
		assertEquals(duration, visitDto.getDuration());
		assertEquals(content, visitDto.getContent());
		assertEquals(comment, visitDto.getComment());
		assertEquals(startDate, visitDto.getStartDate());
		assertEquals(endDate, visitDto.getEndDate());
		assertEquals(participants, visitDto.getParticipants());
		assertEquals(startTime, visitDto.getStartTime());
		assertEquals(endTime, visitDto.getEndTime());
		assertEquals(startTime12Hours, visitDto.getStartTime12Hours());
		assertEquals(endTime12Hours, visitDto.getEndTime12Hours());
		assertEquals(allDay, visitDto.isAllDay());
		assertEquals(visitTasks, visitDto.getVisitTasks());
	}
}
