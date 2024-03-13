package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class GetOpportunityTaskDtoTest {

	@Test
	void testGetOpportunityTaskDto() {
		GetOpportunityTaskDto opportunityTaskDto = new GetOpportunityTaskDto();
		opportunityTaskDto.setOptyTaskId(1);
		opportunityTaskDto.setSubject("Test Subject");
		opportunityTaskDto.setStatus("In Progress");
		opportunityTaskDto.setPriority("High");
		opportunityTaskDto.setUpdateDueDate(LocalDate.of(2024, 3, 15));
		opportunityTaskDto.setDueDate(LocalDate.of(2024, 3, 20));
		opportunityTaskDto.setDueTime("10:00 AM");
		opportunityTaskDto.setDueTime12Hours("10:00 AM");
		opportunityTaskDto.setDescription("Test Description");
		opportunityTaskDto.setRemainderOn(true);
		opportunityTaskDto.setRemainderVia("Email");
		opportunityTaskDto.setRemainderDueAt("Test Due At");
		opportunityTaskDto.setRemainderDueOn(LocalDate.of(2024, 3, 18));
		opportunityTaskDto.setUpdatedRemainderDueOn(LocalDate.of(2024, 3, 18));
		assertEquals(1, opportunityTaskDto.getOptyTaskId());
		assertEquals("Test Subject", opportunityTaskDto.getSubject());
		assertEquals("In Progress", opportunityTaskDto.getStatus());
		assertEquals("High", opportunityTaskDto.getPriority());
		assertEquals(LocalDate.of(2024, 3, 15), opportunityTaskDto.getUpdateDueDate());
		assertEquals(LocalDate.of(2024, 3, 20), opportunityTaskDto.getDueDate());
		assertEquals("10:00 AM", opportunityTaskDto.getDueTime());
		assertEquals("10:00 AM", opportunityTaskDto.getDueTime12Hours());
		assertEquals("Test Description", opportunityTaskDto.getDescription());
		assertTrue(opportunityTaskDto.isRemainderOn());
		assertEquals("Email", opportunityTaskDto.getRemainderVia());
		assertEquals("Test Due At", opportunityTaskDto.getRemainderDueAt());
		assertEquals(LocalDate.of(2024, 3, 18), opportunityTaskDto.getRemainderDueOn());
		assertEquals(LocalDate.of(2024, 3, 18), opportunityTaskDto.getUpdatedRemainderDueOn());
	}

	@Test
	void testGetOpportunityTaskDueDate() {
		GetOpportunityTaskDto dto = new GetOpportunityTaskDto();
		LocalDate dueDate = LocalDate.of(2024, 3, 13);
		String dueTime12Hours = "10:00 AM";
		dto.setDueDate(dueDate);
		dto.setDueTime12Hours(dueTime12Hours);
		String result = dto.getOpportunityTaskDueDate();
		String expectedResult = "13-Mar-2024 10:00 AM";
		assertEquals(expectedResult, result);
	}
}
