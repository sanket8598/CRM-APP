package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class AnalysisOpportunityDtoTest {

	@Test
	void testAnalysisOpportunityDto() {
		AnalysisOpportunityDto analysisOpportunityDto = new AnalysisOpportunityDto();
		OpprtAttachmentDto attachment1 = new OpprtAttachmentDto();
		attachment1.setAttachmentOf("Analysis");
		OpprtAttachmentDto attachment2 = new OpprtAttachmentDto();
		attachment2.setAttachmentOf("Analysis");
		List<OpprtAttachmentDto> attachments=new ArrayList<>();
		attachments.add(attachment1);
		attachments.add(attachment2);
		analysisOpportunityDto.setOpportunityId(1);
		analysisOpportunityDto.setTechnicalNeed("Test Technical Need");
		analysisOpportunityDto.setIntegrationPoint("Test Integration Point");
		analysisOpportunityDto.setSecAndComp("Test Security and Compliance");
		analysisOpportunityDto.setRiskMinigation("Test Risk Minigation");
		analysisOpportunityDto.setInitialTimeline(LocalDate.of(2024, 3, 13));
		analysisOpportunityDto.setUpdatedInitialTimeline(LocalDate.of(2024, 3, 15));
		analysisOpportunityDto.setCurrentPhase("Phase 1");
		analysisOpportunityDto.setProgressStatus("In Progress");
		analysisOpportunityDto.setAttachments(attachments);

		assertEquals(1, analysisOpportunityDto.getOpportunityId());
		assertEquals("Test Technical Need", analysisOpportunityDto.getTechnicalNeed());
		assertEquals("Test Integration Point", analysisOpportunityDto.getIntegrationPoint());
		assertEquals("Test Security and Compliance", analysisOpportunityDto.getSecAndComp());
		assertEquals("Test Risk Minigation", analysisOpportunityDto.getRiskMinigation());
		assertEquals(LocalDate.of(2024, 3, 13), analysisOpportunityDto.getInitialTimeline());
		assertEquals(LocalDate.of(2024, 3, 15), analysisOpportunityDto.getUpdatedInitialTimeline());
		assertEquals("Phase 1", analysisOpportunityDto.getCurrentPhase());
		assertEquals("In Progress", analysisOpportunityDto.getProgressStatus());
		assertEquals(attachments, analysisOpportunityDto.getAttachments());
	}
}
