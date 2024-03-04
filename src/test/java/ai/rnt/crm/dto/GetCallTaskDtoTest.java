package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class GetCallTaskDtoTest {

	GetCallTaskDto dto = new GetCallTaskDto();
	GetCallTaskDto dto1 = new GetCallTaskDto();

	@Test
	void testGetCallTaskDto() {
		GetCallTaskDto getCallTaskDto = new GetCallTaskDto();
		Integer callTaskId = 1;
		String subject = "Follow-up";
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
		getCallTaskDto.setCallTaskId(callTaskId);
		getCallTaskDto.setSubject(subject);
		getCallTaskDto.setDescription(description);
		getCallTaskDto.setStatus(status);
		getCallTaskDto.setPriority(priority);
		getCallTaskDto.setDueDate(dueDate);
		getCallTaskDto.setDueTime(dueTime);
		getCallTaskDto.setUpdateDueDate(updateDueDate);
		getCallTaskDto.setDueTime12Hours(dueTime12Hours);
		getCallTaskDto.setRemainderOn(remainderOn);
		getCallTaskDto.setRemainderVia(remainderVia);
		getCallTaskDto.setRemainderDueAt(remainderDueAt);
		getCallTaskDto.setRemainderDueOn(remainderDueOn);
		getCallTaskDto.setUpdatedRemainderDueOn(updatedRemainderDueOn);
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(callTaskId, getCallTaskDto.getCallTaskId());
		assertEquals(subject, getCallTaskDto.getSubject());
		assertEquals(description, getCallTaskDto.getDescription());
		assertEquals(status, getCallTaskDto.getStatus());
		assertEquals(priority, getCallTaskDto.getPriority());
		assertEquals(dueDate, getCallTaskDto.getDueDate());
		assertEquals(dueTime, getCallTaskDto.getDueTime());
		assertEquals(updateDueDate, getCallTaskDto.getUpdateDueDate());
		assertEquals(dueTime12Hours, getCallTaskDto.getDueTime12Hours());
		assertEquals(remainderOn, getCallTaskDto.isRemainderOn());
		assertEquals(remainderVia, getCallTaskDto.getRemainderVia());
		assertEquals(remainderDueAt, getCallTaskDto.getRemainderDueAt());
		assertEquals(remainderDueOn, getCallTaskDto.getRemainderDueOn());
		assertEquals(updatedRemainderDueOn, getCallTaskDto.getUpdatedRemainderDueOn());
	}

	@Test
	void testGetCallTaskDueDate() {
		GetCallTaskDto getCallTaskDto = new GetCallTaskDto();
		LocalDate dueDate = LocalDate.of(2024, 3, 5);
		String dueTime12Hours = "09:00 AM";
		getCallTaskDto.setDueDate(dueDate);
		getCallTaskDto.setDueTime12Hours(dueTime12Hours);
		String callTaskDueDate = getCallTaskDto.getCallTaskDueDate();
		assertEquals("05-Mar-2024 09:00 AM", callTaskDueDate);
	}
}
