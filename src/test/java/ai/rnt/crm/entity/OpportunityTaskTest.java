package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class OpportunityTaskTest {

	@Test
	void testOpportunityTaskObject() {
		OpportunityTask opportunityTask = new OpportunityTask();
		opportunityTask.setOptyTaskId(1);
		opportunityTask.setSubject("Sample Subject");
		opportunityTask.setStatus("InProgress");
		opportunityTask.setPriority("High");
		opportunityTask.setDueDate(LocalDate.of(2024, 3, 1));
		opportunityTask.setDueTime("13:30");
		opportunityTask.setDescription("Sample Description");
		opportunityTask.setRemainderOn(true);
		opportunityTask.setRemainderVia("Email");
		opportunityTask.setRemainderDueAt("15:00");
		opportunityTask.setRemainderDueOn(LocalDate.of(2024, 3, 1));
		EmployeeMaster employee = new EmployeeMaster();
		employee.setStaffId(1);
		opportunityTask.setAssignTo(employee);
		Opportunity opportunity = new Opportunity();
		opportunity.setOpportunityId(1);
		opportunityTask.setOpportunity(opportunity);
		assertEquals(1, opportunityTask.getOptyTaskId());
		assertEquals("Sample Subject", opportunityTask.getSubject());
		assertEquals("InProgress", opportunityTask.getStatus());
		assertEquals("High", opportunityTask.getPriority());
		assertEquals(LocalDate.of(2024, 3, 1), opportunityTask.getDueDate());
		assertEquals("13:30", opportunityTask.getDueTime());
		assertEquals("Sample Description", opportunityTask.getDescription());
		assertTrue(opportunityTask.isRemainderOn());
		assertEquals("Email", opportunityTask.getRemainderVia());
		assertEquals("15:00", opportunityTask.getRemainderDueAt());
		assertEquals(LocalDate.of(2024, 3, 1), opportunityTask.getRemainderDueOn());
		assertEquals(employee, opportunityTask.getAssignTo());
		assertEquals(opportunity, opportunityTask.getOpportunity());
	}

	@Test
	void testGetDueTime12Hours() {
		OpportunityTask opportunityTask = new OpportunityTask();
		opportunityTask.setDueTime("14:30:00");
		assertEquals("02:30 PM", opportunityTask.getDueTime12Hours());
		opportunityTask.setDueTime(null);
		assertNull(opportunityTask.getDueTime12Hours());
		opportunityTask.setDueTime("invalid time format");
		assertEquals("invalid time format", opportunityTask.getDueTime12Hours());
	}

	@Test
	void testGetRemainderDueAt12Hours() {
		OpportunityTask opportunityTask = new OpportunityTask();
		opportunityTask.setRemainderDueAt("16:45:00");
		assertEquals("04:45 PM", opportunityTask.getRemainderDueAt12Hours());
		opportunityTask.setRemainderDueAt(null);
		assertNull(opportunityTask.getRemainderDueAt12Hours());
		opportunityTask.setRemainderDueAt("invalid time format");
		assertEquals("invalid time format", opportunityTask.getRemainderDueAt12Hours());
	}
}
