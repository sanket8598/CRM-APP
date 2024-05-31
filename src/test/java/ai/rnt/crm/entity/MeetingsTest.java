package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

class MeetingsTest {

	@Test
	void testMeetingsObject() {
		Meetings meeting = new Meetings();
		meeting.setMeetingId(1);
		meeting.setMeetingTitle("Sample Meeting");
		meeting.setParticipates("John, Jane");
		meeting.setStartDate(new Date());
		meeting.setEndDate(new Date());
		meeting.setDuration("1 hour");
		meeting.setStartTime("09:00:00");
		meeting.setEndTime("10:00:00");
		meeting.setAllDay(false);
		meeting.setLocation("Sample Location");
		meeting.setDescription("Sample Description");
		meeting.setMeetingMode("Virtual");
		meeting.setMeetingStatus("Scheduled");
		meeting.setMeetingTasks(new ArrayList<>());
		EmployeeMaster assignTo = new EmployeeMaster();
		assignTo.setStaffId(1);
		meeting.setAssignTo(assignTo);
		Leads lead = new Leads();
		lead.setLeadId(1);
		meeting.setLead(lead);
		meeting.getMeetingTasks();
		assertEquals(1, meeting.getMeetingId());
		assertEquals("Sample Meeting", meeting.getMeetingTitle());
		assertEquals("John, Jane", meeting.getParticipates());
		assertEquals("09:00:00", meeting.getStartTime());
		assertEquals("10:00:00", meeting.getEndTime());
		assertFalse(meeting.isAllDay());
		assertEquals("Sample Location", meeting.getLocation());
		assertEquals("Sample Description", meeting.getDescription());
		assertEquals("Virtual", meeting.getMeetingMode());
		assertEquals("Scheduled", meeting.getMeetingStatus());
		assertEquals(assignTo, meeting.getAssignTo());
		assertEquals(lead, meeting.getLead());
	}

	@Test
	void testGetStartTime12Hours() throws ParseException {
		Meetings meeting = new Meetings();
		meeting.setStartTime("13:30:00");
		String expected = "01:30 PM";
		String actual = meeting.getStartTime12Hours();
		assertEquals(expected.toLowerCase(), actual.toLowerCase());
		meeting.setStartTime(null);
		assertNull(meeting.getStartTime12Hours());
		meeting.setStartTime("invalid_time_format");
		assertEquals("invalid_time_format", meeting.getStartTime12Hours());
		meeting.setStartTime("01:30 PM");
		assertEquals("01:30 AM", meeting.getStartTime12Hours().toUpperCase());
	}

	@Test
	void testGetEndTime12Hours() throws ParseException {
		Meetings meeting = new Meetings();
		meeting.setEndTime("18:45:00");
		String expected = "06:45 PM";
		String actual = meeting.getEndTime12Hours();
		assertEquals(expected.toLowerCase(), actual.toLowerCase());
		meeting.setEndTime(null);
		assertNull(meeting.getEndTime12Hours());
		meeting.setEndTime("invalid_time_format");
		assertEquals("invalid_time_format", meeting.getEndTime12Hours());
		meeting.setEndTime("06:45 PM");
		assertEquals("06:45 AM", meeting.getEndTime12Hours());
	}
}
