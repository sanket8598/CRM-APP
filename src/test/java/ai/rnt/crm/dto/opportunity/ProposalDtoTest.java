package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ProposalDtoTest {

	@Test
	void testGettersAndSetters() {
		ProposalDto proposalDto = new ProposalDto();
		proposalDto.setPropId(1);
		proposalDto.setGenPropId("GEN123");
		proposalDto.setEffectiveFrom(LocalDate.now());
		proposalDto.setEffectiveTo(LocalDate.now());
		assertEquals(1, proposalDto.getPropId());
		proposalDto.getEffectiveFrom();
		proposalDto.getEffectiveTo();
	}
}
