package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GetProposalsDtoTest {

	@Test
	void testGettersAndSetters() {
		GetProposalsDto getProposalsDto = new GetProposalsDto();
		getProposalsDto.setPropId(1);
		getProposalsDto.setGenPropId("GEN123");
		assertEquals(1, getProposalsDto.getPropId());
		assertEquals("GEN123", getProposalsDto.getGenPropId());
	}
}
