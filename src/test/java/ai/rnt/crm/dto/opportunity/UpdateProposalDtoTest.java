package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class UpdateProposalDtoTest {

	@Test
	void testSettersAndGetters() {
		UpdateProposalDto updateProposalDto = new UpdateProposalDto();
		updateProposalDto.setPropId(1);
		updateProposalDto.setOwnerName("John Doe");
		updateProposalDto.setCurrency("USD");
		updateProposalDto.setPropDescription("Proposal description");
		ProposalServicesDto proposalServicesDto = new ProposalServicesDto();
		List<ProposalServicesDto> proposalServicesList = new ArrayList<>();
		proposalServicesList.add(proposalServicesDto);
		updateProposalDto.setProposalServices(proposalServicesList);
		assertEquals(1, updateProposalDto.getPropId());
		assertEquals("John Doe", updateProposalDto.getOwnerName());
		assertEquals("USD", updateProposalDto.getCurrency());
		assertEquals("Proposal description", updateProposalDto.getPropDescription());
		assertNotNull(updateProposalDto.getProposalServices());
		assertEquals(1, updateProposalDto.getProposalServices().size());
	}
}
