package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ProposeOpportunityDtoTest {

	@Test
	void testProposeOpportunityDto() {
		ProposeOpportunityDto proposeOpportunityDto = new ProposeOpportunityDto();
		proposeOpportunityDto.setIdentifySme(true);
		proposeOpportunityDto.setDevelopProposal(true);
		proposeOpportunityDto.setComplInternalReview(true);
		proposeOpportunityDto.setPresentProposal(true);
		proposeOpportunityDto.setFinalCommAndTimeline(true);
		proposeOpportunityDto.setProgressStatus("In Progress");
		proposeOpportunityDto.setCurrentPhase("Phase 1");
		proposeOpportunityDto.setOpportunityId(1);
		proposeOpportunityDto.setProposeRemarks("Test");
		assertEquals(true, proposeOpportunityDto.getIdentifySme());
		assertEquals(true, proposeOpportunityDto.getDevelopProposal());
		assertEquals(true, proposeOpportunityDto.getComplInternalReview());
		assertEquals(true, proposeOpportunityDto.getPresentProposal());
		assertEquals(true, proposeOpportunityDto.getFinalCommAndTimeline());
		assertEquals("In Progress", proposeOpportunityDto.getProgressStatus());
		assertEquals("Phase 1", proposeOpportunityDto.getCurrentPhase());
		assertEquals(1, proposeOpportunityDto.getOpportunityId());
	}
}
