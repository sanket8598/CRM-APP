package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GraphicalDataDtoTest {

	@Test
	void testGraphicalDataDto() {
		GraphicalDataDto graphicalDataDto = new GraphicalDataDto();
		graphicalDataDto.setTopic("Test Topic");
		graphicalDataDto.setStatus("In Progress");
		graphicalDataDto.setCompanyName("Test Company");
		graphicalDataDto.setPhase("Phase 1");
		graphicalDataDto.setBudgetAmount("10000");
		graphicalDataDto.setBubbleSize(50.0);
		graphicalDataDto.setClosedDate("2024-03-13");
		assertEquals("Test Topic", graphicalDataDto.getTopic());
		assertEquals("In Progress", graphicalDataDto.getStatus());
		assertEquals("Test Company", graphicalDataDto.getCompanyName());
		assertEquals("Phase 1", graphicalDataDto.getPhase());
		assertEquals("10000", graphicalDataDto.getBudgetAmount());
		assertEquals(50.0, graphicalDataDto.getBubbleSize());
		assertEquals("2024-03-13", graphicalDataDto.getClosedDate());
	}
}
