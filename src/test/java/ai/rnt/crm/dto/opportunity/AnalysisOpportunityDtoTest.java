package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AnalysisOpportunityDtoTest {

	@Test
	void testAnalysisOpportunityDto() {
		AnalysisOpportunityDto analysisOpportunityDto = new AnalysisOpportunityDto();
		analysisOpportunityDto.setOpportunityId(1);
		analysisOpportunityDto.setCurrentPhase("Phase 1");
		analysisOpportunityDto.setProgressStatus("In Progress");
		analysisOpportunityDto.setProposedSolution("test data");
		analysisOpportunityDto.setCustomerNeed("test");
		analysisOpportunityDto.setTimeline("30/03/2024");
		analysisOpportunityDto.setAnalysisRemarks("analysis");

		assertEquals(1, analysisOpportunityDto.getOpportunityId());
		assertEquals("Phase 1", analysisOpportunityDto.getCurrentPhase());
		assertEquals("In Progress", analysisOpportunityDto.getProgressStatus());
		assertEquals("test data", analysisOpportunityDto.getProposedSolution());
		assertEquals("test", analysisOpportunityDto.getCustomerNeed());
		assertEquals("30/03/2024", analysisOpportunityDto.getTimeline());
		assertEquals("analysis", analysisOpportunityDto.getAnalysisRemarks());
	}
}
