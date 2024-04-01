package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CloseOpportunityDtoTest {

	@Test
	void testCloseOpportunityDto() {
		CloseOpportunityDto closeOpportunityDto = new CloseOpportunityDto();
		closeOpportunityDto.setProgressStatus("Won");
		closeOpportunityDto.setCurrentPhase("Phase 3");
		closeOpportunityDto.setProjectKickoff(true);
		closeOpportunityDto.setFinalisingTeam(true);
		closeOpportunityDto.setSlaSigned(true);
		closeOpportunityDto.setNdaSigned(true);
		closeOpportunityDto.setSowSigned(true);

		assertEquals("Won", closeOpportunityDto.getProgressStatus());
		assertEquals("Phase 3", closeOpportunityDto.getCurrentPhase());
	}
}
