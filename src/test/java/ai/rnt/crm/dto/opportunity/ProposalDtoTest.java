package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ProposalDtoTest {

	@Test
	void testGettersAndSetters() {
		ProposalDto proposalDto = new ProposalDto();
		proposalDto.setPropId(1);
		proposalDto.setGenPropId("GEN123");
		proposalDto.setOwnerName("John Doe");
		proposalDto.setCurrency("USD");
		assertEquals(1, proposalDto.getPropId());
		assertEquals("GEN123", proposalDto.getGenPropId());
		assertEquals("John Doe", proposalDto.getOwnerName());
		assertEquals("USD", proposalDto.getCurrency());
	}
}
