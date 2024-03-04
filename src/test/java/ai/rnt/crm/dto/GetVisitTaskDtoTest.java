package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class GetVisitTaskDtoTest {

	GetVisitTaskDto dto = new GetVisitTaskDto();
	GetVisitTaskDto dto1 = new GetVisitTaskDto();

	@Test
	void testGetVisitTaskDto() {
		GetVisitTaskDto visitTaskDto = new GetVisitTaskDto();
		Integer visitTaskId = 1;
		String subject = "Follow-up call";
		String description = "Follow up on client's request";
		String status = "Pending";
		String priority = "High";
		LocalDate dueDate = LocalDate.of(2024, 3, 5);
		String dueTime = "09:00 AM";
		LocalDate updateDueDate = LocalDate.of(2024, 3, 4);
		String dueTime12Hours = "09:00 AM";
		boolean remainderOn = true;
		String remainderVia = "Email";
		String remainderDueAt = "08:30 AM";
		LocalDate remainderDueOn = LocalDate.of(2024, 3, 5);
		LocalDate updatedRemainderDueOn = LocalDate.of(2024, 3, 4);
		visitTaskDto.setVisitTaskId(visitTaskId);
		visitTaskDto.setSubject(subject);
		visitTaskDto.setDescription(description);
		visitTaskDto.setStatus(status);
		visitTaskDto.setPriority(priority);
		visitTaskDto.setDueDate(dueDate);
		visitTaskDto.setDueTime(dueTime);
		visitTaskDto.setUpdateDueDate(updateDueDate);
		visitTaskDto.setDueTime12Hours(dueTime12Hours);
		visitTaskDto.setRemainderOn(remainderOn);
		visitTaskDto.setRemainderVia(remainderVia);
		visitTaskDto.setRemainderDueAt(remainderDueAt);
		visitTaskDto.setRemainderDueOn(remainderDueOn);
		visitTaskDto.setUpdatedRemainderDueOn(updatedRemainderDueOn);
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(visitTaskId, visitTaskDto.getVisitTaskId());
		assertEquals(subject, visitTaskDto.getSubject());
		assertEquals(description, visitTaskDto.getDescription());
		assertEquals(status, visitTaskDto.getStatus());
		assertEquals(priority, visitTaskDto.getPriority());
		assertEquals(dueDate, visitTaskDto.getDueDate());
		assertEquals(dueTime, visitTaskDto.getDueTime());
		assertEquals(updateDueDate, visitTaskDto.getUpdateDueDate());
		assertEquals(dueTime12Hours, visitTaskDto.getDueTime12Hours());
		assertEquals(remainderOn, visitTaskDto.isRemainderOn());
		assertEquals(remainderVia, visitTaskDto.getRemainderVia());
		assertEquals(remainderDueAt, visitTaskDto.getRemainderDueAt());
		assertEquals(remainderDueOn, visitTaskDto.getRemainderDueOn());
		assertEquals(updatedRemainderDueOn, visitTaskDto.getUpdatedRemainderDueOn());
	}

	@Test
	void testGetVisitTaskDueDate() {
		GetVisitTaskDto visitTaskDto = new GetVisitTaskDto();
		LocalDate dueDate = LocalDate.of(2024, 3, 5);
		String dueTime12Hours = "09:00 AM";
		visitTaskDto.setDueDate(dueDate);
		visitTaskDto.setDueTime12Hours(dueTime12Hours);
		String visitTaskDueDate = visitTaskDto.getVisitTaskDueDate();
		assertEquals("05-Mar-2024 09:00 AM", visitTaskDueDate);
	}
}
