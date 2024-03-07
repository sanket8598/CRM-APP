package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GraphicalDataDtoTest {

	@Test
	void testEqualsAndHashCode() {
		GraphicalDataDto dto1 = createSampleDto();
		GraphicalDataDto dto2 = createSampleDto();

		assertEquals(dto1, dto2);
		assertEquals(dto1.hashCode(), dto2.hashCode());

		dto2.setTopic("Different topic");
		assertNotEquals(dto1, dto2);
	}

	@Test
	void testToString() {
		GraphicalDataDto dto = createSampleDto();
		assertNotNull(dto.toString());
	}

	@Test
	void testCanEqual() {
		GraphicalDataDto dto = createSampleDto();
		assertTrue(dto.canEqual(new GraphicalDataDto()));
	}

	private GraphicalDataDto createSampleDto() {
		GraphicalDataDto dto = new GraphicalDataDto();
		dto.setTopic("Sample topic");
		dto.setStatus("Status");
		dto.setCompanyName("Company");
		dto.setPhase("Phase");
		dto.setBudgetAmount("Budget");
		dto.setBubbleSize(5.0);
		dto.setClosedDate("2024-03-07");
		return dto;
	}

}
