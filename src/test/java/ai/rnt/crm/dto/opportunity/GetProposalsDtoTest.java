package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class GetProposalsDtoTest {

	@Test
	void testGettersAndSetters() {
		GetProposalsDto getProposalsDto = new GetProposalsDto();
		getProposalsDto.setPropId(1);
		getProposalsDto.setGenPropId("GEN123");
		getProposalsDto.setEffectiveFrom(LocalDate.now());
		getProposalsDto.setEffectiveTo(LocalDate.now());
		getProposalsDto.setOptyName("test");
		getProposalsDto.getEffectiveFrom();
		getProposalsDto.getEffectiveTo();
		getProposalsDto.getOptyName();
		assertEquals(1, getProposalsDto.getPropId());
		assertEquals("GEN123", getProposalsDto.getGenPropId());
	}
}
