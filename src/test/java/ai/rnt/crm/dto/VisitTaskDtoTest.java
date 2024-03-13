package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class VisitTaskDtoTest {

	@Test
	void testSetAndGetVisitTaskData() {
		VisitTaskDto visitTaskDto = new VisitTaskDto();
		VisitTaskDto dto = new VisitTaskDto();
		VisitTaskDto dto1 = new VisitTaskDto();

		visitTaskDto.setVisitTaskId(1);
		visitTaskDto.setSubject("Subject");
		visitTaskDto.setStatus("Status");
		visitTaskDto.setPriority("Priority");
		visitTaskDto.setDueDate(LocalDate.now());
		visitTaskDto.setDueTime("10:00 AM");
		visitTaskDto.setDescription("Description");
		visitTaskDto.setRemainderOn(true);
		visitTaskDto.setRemainderVia("Email");
		visitTaskDto.setRemainderDueAt("09:00 AM");
		visitTaskDto.setRemainderDueOn(LocalDate.now());
		visitTaskDto.setAssinTo(new EmployeeDto());
		dto1.setVisitTaskId(1);
		dto1.setSubject("Subject");
		dto1.setStatus("Status");
		dto1.setPriority("Priority");
		dto1.setDueDate(LocalDate.now());
		dto1.setDueTime("10:00 AM");
		dto1.setDescription("Description");
		dto1.setRemainderOn(true);
		dto1.setRemainderVia("Email");
		dto1.setRemainderDueAt("09:00 AM");
		dto1.setRemainderDueOn(LocalDate.now());
		dto1.setAssinTo(new EmployeeDto());
		Integer visitTaskId = visitTaskDto.getVisitTaskId();
		String subject = visitTaskDto.getSubject();
		String status = visitTaskDto.getStatus();
		String priority = visitTaskDto.getPriority();
		LocalDate dueDate = visitTaskDto.getDueDate();
		String dueTime = visitTaskDto.getDueTime();
		String description = visitTaskDto.getDescription();
		boolean remainderOn = visitTaskDto.isRemainderOn();
		String remainderVia = visitTaskDto.getRemainderVia();
		String remainderDueAt = visitTaskDto.getRemainderDueAt();
		LocalDate remainderDueOn = visitTaskDto.getRemainderDueOn();
		EmployeeDto assignTo = visitTaskDto.getAssinTo();
		visitTaskDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertNotNull(visitTaskId);
		assertEquals(1, visitTaskId);
		assertEquals("Subject", subject);
		assertEquals("Status", status);
		assertEquals("Priority", priority);
		assertNotNull(dueDate);
		assertEquals("10:00 AM", dueTime);
		assertEquals("Description", description);
		assertEquals(true, remainderOn);
		assertEquals("Email", remainderVia);
		assertEquals("09:00 AM", remainderDueAt);
		assertNotNull(remainderDueOn);
		assertNotNull(assignTo);
	}
}
