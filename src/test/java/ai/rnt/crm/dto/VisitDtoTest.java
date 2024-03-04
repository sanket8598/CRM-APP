package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class VisitDtoTest {

	VisitDto dto = new VisitDto();
	VisitDto dto1 = new VisitDto();

	@Test
	void testSetAndGetVisitData() {
		VisitDto visitDto = new VisitDto();
		visitDto.setVisitId(1);
		visitDto.setLocation("Location");
		visitDto.setSubject("Subject");
		visitDto.setContent("Content");
		visitDto.setComment("Comment");
		visitDto.setDuration("Duration");
		visitDto.setStartDate(new Date());
		visitDto.setEndDate(new Date());
		List<String> participants = new ArrayList<>();
		participants.add("Participant 1");
		participants.add("Participant 2");
		visitDto.setParticipates(participants);
		visitDto.setStartTime("10:00 AM");
		visitDto.setEndTime("12:00 PM");
		visitDto.setAllDay(false);
		visitDto.setLeadId(123);
		visitDto.setVisitBy(456);
		visitDto.setIsOpportunity(true);
		Integer visitId = visitDto.getVisitId();
		String location = visitDto.getLocation();
		String subject = visitDto.getSubject();
		String content = visitDto.getContent();
		String comment = visitDto.getComment();
		String duration = visitDto.getDuration();
		Date startDate = visitDto.getStartDate();
		Date endDate = visitDto.getEndDate();
		List<String> retrievedParticipants = visitDto.getParticipates();
		String startTime = visitDto.getStartTime();
		String endTime = visitDto.getEndTime();
		boolean allDay = visitDto.isAllDay();
		Integer leadId = visitDto.getLeadId();
		Integer visitBy = visitDto.getVisitBy();
		boolean isOpportunity = visitDto.getIsOpportunity();
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertNotNull(visitId);
		assertEquals(1, visitId);
		assertEquals("Location", location);
		assertEquals("Subject", subject);
		assertEquals("Content", content);
		assertEquals("Comment", comment);
		assertEquals("Duration", duration);
		assertNotNull(startDate);
		assertNotNull(endDate);
		assertNotNull(retrievedParticipants);
		assertEquals(2, retrievedParticipants.size());
		assertEquals("10:00 AM", startTime);
		assertEquals("12:00 PM", endTime);
		assertEquals(false, allDay);
		assertEquals(123, leadId);
		assertEquals(456, visitBy);
		assertEquals(true, isOpportunity);
	}
}
