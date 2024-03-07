package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class CloseOpportunityDtoTest {

	@Test
	void testEqualsAndHashCode() {
		CloseOpportunityDto dto1 = createSampleDto();
		CloseOpportunityDto dto2 = createSampleDto();

		assertEquals(dto1, dto2);
		assertEquals(dto1.hashCode(), dto2.hashCode());

		dto2.setWinLoseReason("Different reason");
		assertNotEquals(dto1, dto2);
	}

	@Test
	void testToString() {
		CloseOpportunityDto dto = createSampleDto();
		assertNotNull(dto.toString());
	}

	@Test
	void testCanEqual() {
		CloseOpportunityDto dto = createSampleDto();
		assertTrue(dto.canEqual(new CloseOpportunityDto()));
	}

	private CloseOpportunityDto createSampleDto() {
		CloseOpportunityDto dto = new CloseOpportunityDto();
		dto.setWinLoseReason("Win reason");
		dto.setPaymentTerms("Payment terms");
		dto.setContract("Contract");
		dto.setSupportPlan("Support plan");
		dto.setFinalBudget("Final budget");
		dto.setProgressStatus("Progress status");
		dto.setCurrentPhase("Current phase");
		dto.setAttachments(new ArrayList<>());
		return dto;
	}

}
