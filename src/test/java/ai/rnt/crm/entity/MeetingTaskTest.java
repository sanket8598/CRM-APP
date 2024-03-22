package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class MeetingTaskTest {

	@Test
	void testMeetingTaskObject() throws ParseException {
		MeetingTask task = new MeetingTask();
		task.setMeetingTaskId(1);
		task.setSubject("Sample Task");
		task.setStatus("On Hold");
		task.setPriority("High");
		task.setDueDate(LocalDate.of(2024, 3, 1));
		task.setDueTime("09:00:00");
		task.setDescription("Sample Description");
		task.setRemainderOn(true);
		task.setRemainderVia("Email");
		task.setRemainderDueAt("14:30:00");
		task.setRemainderDueOn(LocalDate.of(2024, 3, 1));
		task.setTaskNotifications(new ArrayList<>());
		task.getTaskNotifications();
		Meetings meeting = new Meetings();
		meeting.setMeetingId(1);
		task.setMeetings(meeting);
		EmployeeMaster assignTo = new EmployeeMaster();
		assignTo.setStaffId(1);
		task.setAssignTo(assignTo);
		assertEquals(1, task.getMeetingTaskId());
		assertEquals("Sample Task", task.getSubject());
		assertEquals("On Hold", task.getStatus());
		assertEquals("High", task.getPriority());
		assertEquals(LocalDate.of(2024, 3, 1), task.getDueDate());
		assertEquals("09:00:00", task.getDueTime());
		assertEquals("Sample Description", task.getDescription());
		assertTrue(task.isRemainderOn());
		assertEquals("Email", task.getRemainderVia());
		assertEquals("02:30 PM", task.getRemainderDueAt12Hours()); // Check converted time
		assertEquals(LocalDate.of(2024, 3, 1), task.getRemainderDueOn());
		assertEquals(meeting, task.getMeetings());
		assertEquals(assignTo, task.getAssignTo());
	}

	@Test
	void testGetDueTime12Hours() throws ParseException {
		MeetingTask task = new MeetingTask();
		task.setDueTime("13:30:00");
		assertEquals("01:30 PM", task.getDueTime12Hours());
		task.setDueTime(null);
		assertNull(task.getDueTime12Hours());
		task.setDueTime("invalid_time_format");
		assertEquals("invalid_time_format", task.getDueTime12Hours());
		task.setDueTime("01:30 PM");
		assertEquals("01:30 AM", task.getDueTime12Hours());
	}

	@Test
	void testGetRemainderDueAt12Hours() throws ParseException {
		MeetingTask task = new MeetingTask();
		task.setRemainderDueAt("18:45:00");
		assertEquals("06:45 PM", task.getRemainderDueAt12Hours());
		task.setRemainderDueAt(null);
		assertNull(task.getRemainderDueAt12Hours());
		task.setRemainderDueAt("invalid_time_format");
		assertEquals("invalid_time_format", task.getRemainderDueAt12Hours());
		task.setRemainderDueAt("06:45 PM");
		assertEquals("06:45 AM", task.getRemainderDueAt12Hours());
	}
}
