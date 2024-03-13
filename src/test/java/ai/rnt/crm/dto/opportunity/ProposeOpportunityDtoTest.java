package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class ProposeOpportunityDtoTest {

	@Test
	void testProposeOpportunityDto() {
		ProposeOpportunityDto proposeOpportunityDto = new ProposeOpportunityDto();
		OpprtAttachmentDto attachment1 = new OpprtAttachmentDto();
		attachment1.setOptAttchId(1);
		OpprtAttachmentDto attachment2 = new OpprtAttachmentDto();
		attachment2.setOptAttchId(2);
		List<OpprtAttachmentDto> attachments = List.of(attachment1, attachment2);
		proposeOpportunityDto.setLicAndPricDetails("License and Pricing Details");
		proposeOpportunityDto.setDevPlan("Development Plan");
		proposeOpportunityDto.setPropAcceptCriteria("Proposal Acceptance Criteria");
		proposeOpportunityDto.setPresentation("Presentation");
		proposeOpportunityDto.setScopeOfWork("Scope of Work");
		proposeOpportunityDto.setTermsAndConditions("Terms and Conditions");
		proposeOpportunityDto.setProposition("Proposition");
		proposeOpportunityDto.setProgressStatus("In Progress");
		proposeOpportunityDto.setCurrentPhase("Phase 1");
		proposeOpportunityDto.setPropExpDate(LocalDate.of(2024, 3, 13));
		proposeOpportunityDto.setUpdatedPropExpDate(LocalDate.of(2024, 3, 15));
		proposeOpportunityDto.setAttachments(attachments);
		assertEquals("License and Pricing Details", proposeOpportunityDto.getLicAndPricDetails());
		assertEquals("Development Plan", proposeOpportunityDto.getDevPlan());
		assertEquals("Proposal Acceptance Criteria", proposeOpportunityDto.getPropAcceptCriteria());
		assertEquals("Presentation", proposeOpportunityDto.getPresentation());
		assertEquals("Scope of Work", proposeOpportunityDto.getScopeOfWork());
		assertEquals("Terms and Conditions", proposeOpportunityDto.getTermsAndConditions());
		assertEquals("Proposition", proposeOpportunityDto.getProposition());
		assertEquals("In Progress", proposeOpportunityDto.getProgressStatus());
		assertEquals("Phase 1", proposeOpportunityDto.getCurrentPhase());
		assertEquals(LocalDate.of(2024, 3, 13), proposeOpportunityDto.getPropExpDate());
		assertEquals(LocalDate.of(2024, 3, 15), proposeOpportunityDto.getUpdatedPropExpDate());
		assertEquals(attachments, proposeOpportunityDto.getAttachments());
	}
}
