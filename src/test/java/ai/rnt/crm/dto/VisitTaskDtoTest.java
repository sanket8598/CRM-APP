package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class VisitTaskDtoTest {

	VisitTaskDto dto = new VisitTaskDto();
	VisitTaskDto dto1 = new VisitTaskDto();

	@Test
	void testSetAndGetVisitTaskData() {
		VisitTaskDto visitTaskDto = new VisitTaskDto();
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
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
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
