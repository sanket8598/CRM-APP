package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.ParseException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class LeadTaskTest {

	LeadTask leadTask = new LeadTask();

	@Test
	void getterTest() {
		leadTask.getLeadTaskId();
		leadTask.getSubject();
		leadTask.getStatus();
		leadTask.getPriority();
		leadTask.getDueDate();
		leadTask.getDueTime();
		leadTask.getDescription();
		leadTask.isRemainderOn();
		leadTask.getRemainderVia();
		leadTask.getRemainderDueAt();
		leadTask.getRemainderDueOn();
		leadTask.getAssignTo();
		leadTask.getLead();
	}

	Leads lead = new Leads();
	EmployeeMaster assignTo = new EmployeeMaster();

	@Test
	void setterTest() {
		leadTask.setLeadTaskId(1);
		leadTask.setSubject("test");
		leadTask.setStatus("On Hold");
		leadTask.setPriority("High");
		LocalDate dueDate = LocalDate.of(2024, 3, 10);
		LocalDate remainderDueOn = LocalDate.of(2024, 3, 9);
		leadTask.setDueDate(dueDate);
		leadTask.setDueTime("10:10");
		leadTask.setDescription("test desc");
		leadTask.isRemainderOn();
		leadTask.setRemainderVia("Email");
		leadTask.setRemainderDueAt("11:11");
		leadTask.setRemainderDueOn(remainderDueOn);
		leadTask.setAssignTo(assignTo);
		leadTask.setLead(lead);
	}

	@Test
	void testGetDueTime12Hours() throws ParseException {
		leadTask.setDueTime("14:30:00");
		String expected = "02:30 PM";
		String actual = leadTask.getDueTime12Hours();
		assertEquals(expected.toLowerCase(), actual.toLowerCase());
		leadTask.setDueTime(null);
		assertNull(leadTask.getDueTime12Hours());
		leadTask.setDueTime("invalid_time_format");
		assertEquals("invalid_time_format", leadTask.getDueTime12Hours());
		leadTask.setDueTime("02:30 AM");
		String expectedTime = "02:30 AM";
		String actualTime = leadTask.getDueTime12Hours();
		assertEquals(expectedTime.toLowerCase(), actualTime.toLowerCase());
	}

	@Test
	void testGetRemainderDueAt12Hours() throws ParseException {
		leadTask.setRemainderDueAt("17:30:00");
		String expected = "05:30 PM";
		String actual = leadTask.getRemainderDueAt12Hours();
		assertEquals(expected.toLowerCase(), actual.toLowerCase());
		leadTask.setRemainderDueAt(null);
		assertNull(leadTask.getRemainderDueAt12Hours());
		leadTask.setRemainderDueAt("invalid_time_format");
		assertEquals("invalid_time_format", leadTask.getRemainderDueAt12Hours());
		leadTask.setRemainderDueAt("05:30 AM");
		String expectedTime = "05:30 AM";
		String actualTime = leadTask.getRemainderDueAt12Hours();
		assertEquals(expectedTime.toLowerCase(), actualTime.toLowerCase());
	}
}
