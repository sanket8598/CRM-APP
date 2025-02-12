package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

class VisitTest {

	@Test
	void testGetAndSetData() throws ParseException {
		Visit visit = new Visit();
		visit.setVisitId(1);
		visit.setLocation("Pune");
		visit.setSubject("test");
		visit.setContent("visit content");
		visit.setComment("this is visit");
		visit.setDuration("10 min");
		visit.setStartDate(new Date());
		visit.setEndDate(new Date());
		visit.setStartTime("08:00:00");
		visit.setEndTime("12:00:00");
		visit.setAllDay(false);
		visit.setStatus("Save");
		visit.setParticipates("Participants");
		visit.setIsOpportunity(false);
		EmployeeMaster visitBy = new EmployeeMaster();
		visitBy.setStaffId(1);
		visit.setVisitBy(visitBy);
		visit.setVisitTasks(new ArrayList<>());
		visit.getVisitTasks();

		Leads lead = new Leads();
		lead.setLeadId(1);
		visit.setLead(lead);
		int visitId = visit.getVisitId();
		visit.getLead();
		String location = visit.getLocation();
		String subject = visit.getSubject();
		String content = visit.getContent();
		String comment = visit.getComment();
		String duration = visit.getDuration();
		Date startDate = visit.getStartDate();
		Date endDate = visit.getEndDate();
		String startTime = visit.getStartTime();
		String endTime = visit.getEndTime();
		boolean allDay = visit.isAllDay();
		String status = visit.getStatus();
		String participates = visit.getParticipates();
		Boolean isOpportunity = visit.getIsOpportunity();
		assertEquals(1, visitId);
		assertEquals("Pune", location);
		assertEquals("test", subject);
		assertEquals("visit content", content);
		assertEquals("this is visit", comment);
		assertEquals("10 min", duration);
		assertNotNull(startDate);
		assertNotNull(endDate);
		assertEquals("08:00:00", startTime);
		assertEquals("12:00:00", endTime);
		assertEquals(false, allDay);
		assertEquals("Save", status);
		assertEquals("Participants", participates);
		assertEquals(false, isOpportunity);
	}

	@Test
	void testGetStartTime12Hours() throws ParseException {
		Visit visit = new Visit();
		visit.setStartTime("14:30:00");
		String expected = "02:30 PM";
		String actual = visit.getStartTime12Hours();
		assertEquals(expected.toLowerCase(), actual.toLowerCase());
		visit.setStartTime(null);
		assertNull(visit.getStartTime12Hours());
		visit.setStartTime("invalid_time_format");
		assertEquals("invalid_time_format", visit.getStartTime12Hours());
		visit.setStartTime("02:30 AM");
		String expectedTime = "02:30 AM";
		String actualTime = visit.getStartTime12Hours();
		assertEquals(expectedTime.toLowerCase(), actualTime.toLowerCase());
	}

	@Test
	void testGetEndTime12Hours() throws ParseException {
		Visit visit = new Visit();
		visit.setEndTime("20:45:00");
		String expected = "08:45 PM";
		String actual = visit.getEndTime12Hours();
		assertEquals(expected.toLowerCase(), actual.toLowerCase());
		visit.setEndTime(null);
		assertNull(visit.getEndTime12Hours());
		visit.setEndTime("invalid_time_format");
		assertEquals("invalid_time_format", visit.getEndTime12Hours());
		visit.setEndTime("08:45 AM");
		String expectedTime = "08:45 AM";
		String actualTime = visit.getEndTime12Hours();
		assertEquals(expectedTime.toLowerCase(), actualTime.toLowerCase());
	}
}
