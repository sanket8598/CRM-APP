package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class VisitTaskTest {

	@Test
	void testGettersAndSetters() {
		VisitTask visitTask = new VisitTask();
		visitTask.setVisitTaskId(1);
		visitTask.setSubject("Test Subject");
		visitTask.setStatus("Save");
		visitTask.setPriority("High");
		visitTask.setDueDate(LocalDate.now());
		visitTask.setDueTime("12:30:00");
		visitTask.setDescription("tesing Description");
		visitTask.setRemainderOn(true);
		visitTask.setRemainderVia("Email");
		visitTask.setRemainderDueAt("18:45:00");
		visitTask.setRemainderDueOn(LocalDate.now());
		assertEquals(1, visitTask.getVisitTaskId());
		assertEquals("Test Subject", visitTask.getSubject());
		assertEquals("Save", visitTask.getStatus());
		assertEquals("High", visitTask.getPriority());
		assertEquals(LocalDate.now(), visitTask.getDueDate());
		assertEquals("12:30:00", visitTask.getDueTime());
		assertEquals("tesing Description", visitTask.getDescription());
		assertEquals(true, visitTask.isRemainderOn());
		assertEquals("Email", visitTask.getRemainderVia());
		assertEquals("18:45:00", visitTask.getRemainderDueAt());
		assertEquals(LocalDate.now(), visitTask.getRemainderDueOn());
	}

	@Test
	void testGetDueTime12Hours() {
		VisitTask visitTask = new VisitTask();
		visitTask.setDueTime("14:30:00");
		assertEquals("02:30 PM", visitTask.getDueTime12Hours());
		visitTask.setDueTime(null);
		assertNull(visitTask.getDueTime12Hours());
		visitTask.setDueTime("invalid time format");
		assertEquals("invalid time format", visitTask.getDueTime12Hours());
	}

	@Test
	void testGetRemainderDueAt12Hours() {
		VisitTask visitTask = new VisitTask();
		visitTask.setRemainderDueAt("16:45:00");
		assertEquals("04:45 PM", visitTask.getRemainderDueAt12Hours());
		visitTask.setRemainderDueAt(null);
		assertNull(visitTask.getRemainderDueAt12Hours());
		visitTask.setRemainderDueAt("invalid time format");
		assertEquals("invalid time format", visitTask.getRemainderDueAt12Hours());
	}
}
