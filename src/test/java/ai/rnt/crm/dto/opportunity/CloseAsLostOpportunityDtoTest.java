package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CloseAsLostOpportunityDtoTest {

	@Test
	void testCloseAsLostOpportunityDto() {
		CloseAsLostOpportunityDto closeOpportunityAsLostDto = new CloseAsLostOpportunityDto();
		closeOpportunityAsLostDto.setProgressStatus("Lost");
		closeOpportunityAsLostDto.setCurrentPhase("Phase 4");
		closeOpportunityAsLostDto.setLostReason("test");
		closeOpportunityAsLostDto.setThankMailSent(true);
		closeOpportunityAsLostDto.setDescription("testdate");

		assertEquals("Lost", closeOpportunityAsLostDto.getProgressStatus());
		assertEquals("Phase 4", closeOpportunityAsLostDto.getCurrentPhase());
	}
}
