package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class CloseOpportunityDtoTest {

	@Test
	void testCloseOpportunityDto() {
		CloseOpportunityDto closeOpportunityDto = new CloseOpportunityDto();
		OpprtAttachmentDto attachment1 = new OpprtAttachmentDto();
		attachment1.setAttachmentOf("Close");
		OpprtAttachmentDto attachment2 = new OpprtAttachmentDto();
		attachment2.setAttachmentOf("Close");
		List<OpprtAttachmentDto> attachments = Arrays.asList(attachment1, attachment2);
		closeOpportunityDto.setWinLoseReason("Test Win/Lose Reason");
		closeOpportunityDto.setPaymentTerms("Test Payment Terms");
		closeOpportunityDto.setContract("Test Contract");
		closeOpportunityDto.setSupportPlan("Test Support Plan");
		closeOpportunityDto.setFinalBudget("Test Final Budget");
		closeOpportunityDto.setProgressStatus("Closed Won");
		closeOpportunityDto.setCurrentPhase("Phase 3");
		closeOpportunityDto.setAttachments(attachments);

		assertEquals("Test Win/Lose Reason", closeOpportunityDto.getWinLoseReason());
		assertEquals("Test Payment Terms", closeOpportunityDto.getPaymentTerms());
		assertEquals("Test Contract", closeOpportunityDto.getContract());
		assertEquals("Test Support Plan", closeOpportunityDto.getSupportPlan());
		assertEquals("Test Final Budget", closeOpportunityDto.getFinalBudget());
		assertEquals("Closed Won", closeOpportunityDto.getProgressStatus());
		assertEquals("Phase 3", closeOpportunityDto.getCurrentPhase());
		assertEquals(attachments, closeOpportunityDto.getAttachments());
	}
}
