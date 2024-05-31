package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CallTest {

	Call call = new Call();

	@Test
	void getterTest() {
		call.getCallId();
		call.getCallFrom();
		call.getCallTo();
		call.getSubject();
		call.getDirection();
		call.getPhoneNo();
		call.getComment();
		call.getDuration();
		call.getStartDate();
		call.getEndDate();
		call.getStartTime();
		call.getEndTime();
		call.isAllDay();
		call.getStatus();
		call.getLead();
		call.getIsOpportunity();
		call.getCallTasks();
		assertNull(call.getCallId());
	}

	EmployeeMaster employeeMaster = new EmployeeMaster();
	Leads lead = new Leads();

	@Test
	void setterTest() {
		call.setCallId(1);
		call.setCallFrom(employeeMaster);
		call.setCallTo("Client");
		call.setSubject("Test Call");
		call.setDirection("Outgoing");
		call.setPhoneNo("+918976453782");
		call.setComment("Test");
		call.setDuration("05:10");
		LocalDate localDate = LocalDate.of(2024, 1, 1);
		Date date = Date.valueOf(localDate);
		call.setStartDate(date);
		call.setEndDate(date);
		call.setStartTime("10:10");
		call.setEndTime("10:30");
		call.isAllDay();
		call.setStatus("On Hold");
		call.setLead(lead);
		call.setIsOpportunity(false);
		List<PhoneCallTask> callTasks = new ArrayList<>();
		call.setCallTasks(callTasks);
		assertEquals(1, call.getCallId());
	}

	@Test
	void testGetStartTime12Hours() throws ParseException {
		call.setStartTime("14:30:00");
		String expected = "02:30 PM";
		String actual = call.getStartTime12Hours();
		assertEquals(expected.toLowerCase(), actual.toLowerCase());
		call.setStartTime(null);
		assertNull(call.getStartTime12Hours());
		call.setStartTime("invalid_time_format");
		assertEquals("invalid_time_format", call.getStartTime12Hours());
		call.setStartTime("02:30 PM");
		assertEquals("02:30 AM", call.getStartTime12Hours());
	}

	@Test
	void testGetEndTime12Hours() throws ParseException {
		call.setEndTime("20:45:00");
		String expected = "08:45 PM";
		String actual = call.getEndTime12Hours();
		assertEquals(expected.toLowerCase(), actual.toLowerCase());
		call.setEndTime(null);
		assertNull(call.getEndTime12Hours());
		call.setEndTime("invalid_time_format");
		assertEquals("invalid_time_format", call.getEndTime12Hours());
		call.setEndTime("08:45 PM");
		assertEquals("08:45 AM", call.getEndTime12Hours());
	}
}
