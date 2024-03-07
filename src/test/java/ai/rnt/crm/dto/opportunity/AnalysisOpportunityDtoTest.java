package ai.rnt.crm.dto.opportunity;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class AnalysisOpportunityDtoTest {

	@Test
	void testEqualsAndHashCode() {
		AnalysisOpportunityDto dto1 = createSampleDto();
		AnalysisOpportunityDto dto2 = createSampleDto();

		assertEquals(dto1, dto2);
		assertEquals(dto1.hashCode(), dto2.hashCode());

		dto2.setOpportunityId(2);
		assertNotEquals(dto1, dto2);
	}

	@Test
	void testToString() {
		AnalysisOpportunityDto dto = createSampleDto();
		assertNotNull(dto.toString());
	}

	@Test
	void testCanEqual() {
		AnalysisOpportunityDto dto = createSampleDto();
		assertTrue(dto.canEqual(new AnalysisOpportunityDto()));
	}

	private AnalysisOpportunityDto createSampleDto() {
		AnalysisOpportunityDto dto = new AnalysisOpportunityDto();
		dto.setOpportunityId(1);
		dto.setTechnicalNeed("Technical need");
		dto.setIntegrationPoint("Integration point");
		dto.setSecAndComp("Security and Compliance");
		dto.setRiskMinigation("Risk mitigation");
		dto.setInitialTimeline(LocalDate.of(2024, 3, 7));
		dto.setUpdatedInitialTimeline(LocalDate.of(2024, 3, 8));
		dto.setCurrentPhase("Current phase");
		dto.setProgressStatus("Progress status");
		dto.setAttachments(new ArrayList<>());
		return dto;
	}
}
