package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GetProposalsDtoTest {

	@Test
	void testGettersAndSetters() {
		GetProposalsDto getProposalsDto = new GetProposalsDto();
		getProposalsDto.setPropId(1);
		getProposalsDto.setGenPropId("GEN123");
		getProposalsDto.setOwnerName("John Doe");
		getProposalsDto.setCurrency("USD");
		getProposalsDto.setCreatedBy("Creator");
		assertEquals(1, getProposalsDto.getPropId());
		assertEquals("GEN123", getProposalsDto.getGenPropId());
		assertEquals("John Doe", getProposalsDto.getOwnerName());
		assertEquals("USD", getProposalsDto.getCurrency());
		assertEquals("Creator", getProposalsDto.getCreatedBy());
	}
}
