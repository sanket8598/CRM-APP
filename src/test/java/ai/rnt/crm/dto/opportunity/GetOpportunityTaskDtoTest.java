package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class GetOpportunityTaskDtoTest {

	@Test
	void testEqualsAndHashCode() {
		GetOpportunityTaskDto dto1 = createSampleDto();
		GetOpportunityTaskDto dto2 = createSampleDto();

		assertEquals(dto1, dto2);
		assertEquals(dto1.hashCode(), dto2.hashCode());

		dto2.setOptyTaskId(2);
		assertNotEquals(dto1, dto2);
	}

	@Test
	void testToString() {
		GetOpportunityTaskDto dto = createSampleDto();
		assertNotNull(dto.toString());
	}

	@Test
	void testCanEqual() {
		GetOpportunityTaskDto dto = createSampleDto();
		assertTrue(dto.canEqual(new GetOpportunityTaskDto()));
	}

	private GetOpportunityTaskDto createSampleDto() {
		GetOpportunityTaskDto dto = new GetOpportunityTaskDto();
		dto.setOptyTaskId(1);
		dto.setSubject("Sample subject");
		dto.setStatus("Open");
		dto.setPriority("High");
		dto.setUpdateDueDate(LocalDate.now());
		dto.setDueDate(LocalDate.now());
		dto.setDueTime("12:00");
		dto.setDueTime12Hours("12:00 PM");
		dto.setDescription("Sample description");
		dto.setRemainderOn(true);
		dto.setRemainderVia("Email");
		dto.setRemainderDueAt("10:00");
		dto.setRemainderDueOn(LocalDate.now());
		dto.setUpdatedRemainderDueOn(LocalDate.now());
		return dto;
	}

	@Test
	void testGetOpportunityTaskDueDate() {
		GetOpportunityTaskDto dto = new GetOpportunityTaskDto();
		dto.setDueDate(LocalDate.of(2024, 3, 7));
		dto.setDueTime12Hours("12:00 PM");
		assertEquals("07-Mar-2024 12:00 PM", dto.getOpportunityTaskDueDate());
	}
}
