package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class GetLeadTaskDtoTest {

	@Test
	void testGetLeadTaskDto() {
		GetLeadTaskDto getLeadTaskDto = new GetLeadTaskDto();
		GetLeadTaskDto dto = new GetLeadTaskDto();
		GetLeadTaskDto dto1 = new GetLeadTaskDto();
		Integer leadTaskId = 1;
		String subject = "Follow-up";
		String status = "Pending";
		String priority = "High";
		LocalDate updateDueDate = LocalDate.of(2024, 3, 4);
		LocalDate dueDate = LocalDate.of(2024, 3, 5);
		String dueTime = "09:00";
		String dueTime12Hours = "09:00 AM";
		String description = "Follow up on lead's requirement";
		boolean remainderOn = true;
		String remainderVia = "Email";
		String remainderDueAt = "08:30 AM";
		LocalDate remainderDueOn = LocalDate.of(2024, 3, 5);
		LocalDate updatedRemainderDueOn = LocalDate.of(2024, 3, 4);
		getLeadTaskDto.setLeadTaskId(leadTaskId);
		getLeadTaskDto.setSubject(subject);
		getLeadTaskDto.setStatus(status);
		getLeadTaskDto.setPriority(priority);
		getLeadTaskDto.setUpdateDueDate(updateDueDate);
		getLeadTaskDto.setDueDate(dueDate);
		getLeadTaskDto.setDueTime(dueTime);
		getLeadTaskDto.setDueTime12Hours(dueTime12Hours);
		getLeadTaskDto.setDescription(description);
		getLeadTaskDto.setRemainderOn(remainderOn);
		getLeadTaskDto.setRemainderVia(remainderVia);
		getLeadTaskDto.setRemainderDueAt(remainderDueAt);
		getLeadTaskDto.setRemainderDueOn(remainderDueOn);
		getLeadTaskDto.setUpdatedRemainderDueOn(updatedRemainderDueOn);
		dto1.setLeadTaskId(leadTaskId);
		dto1.setSubject(subject);
		dto1.setStatus(status);
		dto1.setPriority(priority);
		dto1.setUpdateDueDate(updateDueDate);
		dto1.setDueDate(dueDate);
		dto1.setDueTime(dueTime);
		dto1.setDueTime12Hours(dueTime12Hours);
		dto1.setDescription(description);
		dto1.setRemainderOn(remainderOn);
		dto1.setRemainderVia(remainderVia);
		dto1.setRemainderDueAt(remainderDueAt);
		dto1.setRemainderDueOn(remainderDueOn);
		dto1.setUpdatedRemainderDueOn(updatedRemainderDueOn);
		getLeadTaskDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertEquals(leadTaskId, getLeadTaskDto.getLeadTaskId());
		assertEquals(subject, getLeadTaskDto.getSubject());
		assertEquals(status, getLeadTaskDto.getStatus());
		assertEquals(priority, getLeadTaskDto.getPriority());
		assertEquals(updateDueDate, getLeadTaskDto.getUpdateDueDate());
		assertEquals(dueDate, getLeadTaskDto.getDueDate());
		assertEquals(dueTime, getLeadTaskDto.getDueTime());
		assertEquals(dueTime12Hours, getLeadTaskDto.getDueTime12Hours());
		assertEquals(description, getLeadTaskDto.getDescription());
		assertEquals(remainderOn, getLeadTaskDto.isRemainderOn());
		assertEquals(remainderVia, getLeadTaskDto.getRemainderVia());
		assertEquals(remainderDueAt, getLeadTaskDto.getRemainderDueAt());
		assertEquals(remainderDueOn, getLeadTaskDto.getRemainderDueOn());
		assertEquals(updatedRemainderDueOn, getLeadTaskDto.getUpdatedRemainderDueOn());
	}

	@Test
	void testGetLeadTaskDueDate() {
		GetLeadTaskDto getLeadTaskDto = new GetLeadTaskDto();
		LocalDate dueDate = LocalDate.of(2024, 3, 5);
		String dueTime12Hours = "09:00 AM";
		getLeadTaskDto.setDueDate(dueDate);
		getLeadTaskDto.setDueTime12Hours(dueTime12Hours);
		String leadTaskDueDate = getLeadTaskDto.getLeadTaskDueDate();
		assertEquals("05-Mar-2024 09:00 AM", leadTaskDueDate);
	}
}
