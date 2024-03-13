package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class CallTaskDtoTest {

	@Test
	void testCallTaskDto() {
		CallTaskDto callTaskDto = new CallTaskDto();
		EmployeeDto assignTo = new EmployeeDto();
		assignTo.setStaffId(1477);
		callTaskDto.setCallTaskId(1);
		callTaskDto.setAssignTo(assignTo);
		callTaskDto.setSubject("Test Subject");
		callTaskDto.setStatus("Pending");
		callTaskDto.setPriority("High");
		callTaskDto.setDueDate(LocalDate.now());
		callTaskDto.setDueTime("10:00 AM");
		callTaskDto.setDescription("Test Description");
		callTaskDto.setRemainderOn(true);
		callTaskDto.setRemainderVia("Email");
		callTaskDto.setRemainderDueAt("2024-03-15");
		callTaskDto.setRemainderDueOn(LocalDate.now().plusDays(1));

		assertEquals(1, callTaskDto.getCallTaskId());
		assertEquals("Test Subject", callTaskDto.getSubject());
		assertEquals("Pending", callTaskDto.getStatus());
		assertEquals("High", callTaskDto.getPriority());
		assertEquals(LocalDate.now(), callTaskDto.getDueDate());
		assertEquals("10:00 AM", callTaskDto.getDueTime());
		assertEquals("Test Description", callTaskDto.getDescription());
		assertTrue(callTaskDto.isRemainderOn());
		assertEquals("Email", callTaskDto.getRemainderVia());
		assertEquals("2024-03-15", callTaskDto.getRemainderDueAt());
		assertEquals(LocalDate.now().plusDays(1), callTaskDto.getRemainderDueOn());
		assertEquals(assignTo, callTaskDto.getAssignTo());
	}
}
