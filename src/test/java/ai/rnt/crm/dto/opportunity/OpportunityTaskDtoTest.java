package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class OpportunityTaskDtoTest {

	@Test
	void testEqualsAndHashCode() {
		OpportunityTaskDto dto1 = createSampleDto();
		OpportunityTaskDto dto2 = createSampleDto();

		assertEquals(dto1, dto2);
		assertEquals(dto1.hashCode(), dto2.hashCode());

		dto2.setOptyTaskId(1);
	}

	@Test
	void testToString() {
		OpportunityTaskDto dto = createSampleDto();
		assertNotNull(dto.toString());
	}

	@Test
	void testCanEqual() {
		OpportunityTaskDto dto = createSampleDto();
		assertTrue(dto.canEqual(new OpportunityTaskDto()));
	}

	private OpportunityTaskDto createSampleDto() {
		OpportunityTaskDto dto = new OpportunityTaskDto();
		dto.setOptyTaskId(1);
		dto.setSubject("Sample subject");
		dto.setStatus("Open");
		dto.setPriority("High");
		dto.setDueDate(LocalDate.now());
		dto.setDueTime("12:00");
		dto.setDescription("Sample description");
		dto.setRemainderOn(true);
		dto.setRemainderVia("Email");
		dto.setRemainderDueAt("10:00");
		dto.setRemainderDueOn(LocalDate.now());
		return dto;
	}
}
