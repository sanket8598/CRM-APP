package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class EditProposalDtoTest {

	@Test
	void testGettersAndSetters() {
		EditProposalDto editProposalDto = new EditProposalDto();
		editProposalDto.setPropId(1);
		editProposalDto.setGenPropId("PROP123");
		editProposalDto.setEffectiveFrom(LocalDate.now());
		editProposalDto.setEffectiveTo(LocalDate.now());
		editProposalDto.setPropDescription("Proposal description");
		ProposalServicesDto proposalServicesDto = new ProposalServicesDto();
		List<ProposalServicesDto> proposalServicesList = new ArrayList<>();
		proposalServicesList.add(proposalServicesDto);
		editProposalDto.setProposalServices(proposalServicesList);
		OpportunityDto opportunityDto = new OpportunityDto();
		editProposalDto.setOpportunity(opportunityDto);
		assertEquals(1, editProposalDto.getPropId());
		assertEquals("PROP123", editProposalDto.getGenPropId());
		editProposalDto.getEffectiveFrom();
		editProposalDto.getEffectiveTo();
		assertEquals("Proposal description", editProposalDto.getPropDescription());
		assertNotNull(editProposalDto.getProposalServices());
		assertEquals(1, editProposalDto.getProposalServices().size());
		assertNotNull(editProposalDto.getOpportunity());
	}
}