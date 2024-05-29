package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class UpdateProposalDtoTest {

	@Test
	void testSettersAndGetters() {
		UpdateProposalDto updateProposalDto = new UpdateProposalDto();
		updateProposalDto.setPropId(1);
		updateProposalDto.setEffectiveFrom(LocalDate.now());
		updateProposalDto.setEffectiveTo(LocalDate.now());
		updateProposalDto.setDiscount(5);
		updateProposalDto.setFinalAmount("5000");
		updateProposalDto.setSubTotal("4500");
		updateProposalDto.setPropDescription("Proposal description");
		ProposalServicesDto proposalServicesDto = new ProposalServicesDto();
		List<ProposalServicesDto> proposalServicesList = new ArrayList<>();
		proposalServicesList.add(proposalServicesDto);
		updateProposalDto.setProposalServices(proposalServicesList);
		assertEquals(1, updateProposalDto.getPropId());
		updateProposalDto.getEffectiveFrom();
		updateProposalDto.getEffectiveTo();
		updateProposalDto.getDiscount();
		updateProposalDto.getFinalAmount();
		updateProposalDto.getSubTotal();
		assertEquals("Proposal description", updateProposalDto.getPropDescription());
		assertNotNull(updateProposalDto.getProposalServices());
		assertEquals(1, updateProposalDto.getProposalServices().size());
	}
}
