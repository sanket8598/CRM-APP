package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ProposeOpportunityDtoTest {

	@Test
	void testObjectCreationAndGetSetData() {
		ProposeOpportunityDto dto = new ProposeOpportunityDto();
		dto.setLicAndPricDetails("Details");
		dto.setDevPlan("Plan");
		dto.setPropAcceptCriteria("Criteria");
		dto.setPresentation("Presentation");
		dto.setScopeOfWork("Scope");
		dto.setTermsAndConditions("Conditions");
		dto.setProposition("Proposition");
		dto.setProgressStatus("Status");
		dto.setCurrentPhase("Phase");
		dto.setPropExpDate(LocalDate.of(2024, 3, 7));
		dto.setUpdatedPropExpDate(LocalDate.of(2024, 3, 7));

		List<OpprtAttachmentDto> attachments = new ArrayList<>();
		attachments.add(new OpprtAttachmentDto());
		dto.setAttachments(attachments);
		assertEquals("Details", dto.getLicAndPricDetails());
		assertEquals("Plan", dto.getDevPlan());
		assertEquals("Criteria", dto.getPropAcceptCriteria());
		assertEquals("Presentation", dto.getPresentation());
		assertEquals("Scope", dto.getScopeOfWork());
		assertEquals("Conditions", dto.getTermsAndConditions());
		assertEquals("Proposition", dto.getProposition());
		assertEquals("Status", dto.getProgressStatus());
		assertEquals("Phase", dto.getCurrentPhase());
		assertEquals(LocalDate.of(2024, 3, 7), dto.getPropExpDate());
		assertEquals(LocalDate.of(2024, 3, 7), dto.getUpdatedPropExpDate());
		assertEquals(1, dto.getAttachments().size());
	}

	@Test
	void testToString() {
		ProposeOpportunityDto dto = new ProposeOpportunityDto();
		assertNotNull(dto.toString());
	}

	@Test
	void testHashCode() {
		ProposeOpportunityDto dto1 = new ProposeOpportunityDto();
		ProposeOpportunityDto dto2 = new ProposeOpportunityDto();
		assertEquals(dto1.hashCode(), dto2.hashCode());
	}

	@Test
	void testEquals() {
		ProposeOpportunityDto dto1 = new ProposeOpportunityDto();
		ProposeOpportunityDto dto2 = new ProposeOpportunityDto();
		assertTrue(dto1.equals(dto2));
	}

	@Test
	void testCanEquals() {
		ProposeOpportunityDto dto = new ProposeOpportunityDto();
		assertTrue(dto.canEqual(new ProposeOpportunityDto()));
	}
}
