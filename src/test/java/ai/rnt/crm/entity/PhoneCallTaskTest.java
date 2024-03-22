package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class PhoneCallTaskTest {

	@Test
	void testPhoneCallTaskObject() {
		PhoneCallTask task = new PhoneCallTask();
		task.setCallTaskId(1);
		task.setSubject("Sample subject");
		task.setStatus("Sample status");
		task.setPriority("Sample priority");
		task.setDueDate(LocalDate.now());
		task.setDueTime("12:00 PM");
		task.setDescription("Sample description");
		task.setRemainderOn(true);
		task.setRemainderVia("Sample remainder via");
		task.setRemainderDueAt("12:00 PM");
		task.setRemainderDueOn(LocalDate.now());
		task.setTaskNotifications(new ArrayList<>());
		task.getTaskNotifications();
		EmployeeMaster assignTo = new EmployeeMaster();
		assignTo.setStaffId(1);
		task.setAssignTo(assignTo);
		Call call = new Call();
		call.setCallId(1);
		task.setCall(call);
		assertEquals(1, task.getCallTaskId());
		assertEquals("Sample subject", task.getSubject());
		assertEquals("Sample status", task.getStatus());
		assertEquals("Sample priority", task.getPriority());
		assertEquals(LocalDate.now(), task.getDueDate());
		assertEquals("12:00 PM", task.getDueTime());
		assertEquals("Sample description", task.getDescription());
		assertTrue(task.isRemainderOn());
		assertEquals("Sample remainder via", task.getRemainderVia());
		assertEquals("12:00 PM", task.getRemainderDueAt());
		assertEquals(LocalDate.now(), task.getRemainderDueOn());
		assertEquals(assignTo, task.getAssignTo());
		assertEquals(call, task.getCall());
	}

	@Test
	void testGetDueTime12Hours() {
		PhoneCallTask phoneCallTask = new PhoneCallTask();
		phoneCallTask.setDueTime("14:30:00");
		assertEquals("02:30 PM", phoneCallTask.getDueTime12Hours());
		phoneCallTask.setDueTime(null);
		assertNull(phoneCallTask.getDueTime12Hours());
		phoneCallTask.setDueTime("invalid time format");
		assertEquals("invalid time format", phoneCallTask.getDueTime12Hours());
	}

	@Test
	void testGetRemainderDueAt12Hours() {
		PhoneCallTask phoneCallTask = new PhoneCallTask();
		phoneCallTask.setRemainderDueAt("16:45:00");
		assertEquals("04:45 PM", phoneCallTask.getRemainderDueAt12Hours());
		phoneCallTask.setRemainderDueAt(null);
		assertNull(phoneCallTask.getRemainderDueAt12Hours());
		phoneCallTask.setRemainderDueAt("invalid time format");
		assertEquals("invalid time format", phoneCallTask.getRemainderDueAt12Hours());
	}

}
