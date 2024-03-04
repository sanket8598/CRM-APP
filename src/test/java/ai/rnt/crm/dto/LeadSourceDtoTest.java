package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LeadSourceDtoTest {

	LeadSourceDto dto = new LeadSourceDto();
	LeadSourceDto dto1 = new LeadSourceDto();

	@Test
	void testLeadSourceDto() {
		LeadSourceDto leadSourceDto = new LeadSourceDto();
		Integer leadSourceId = 1;
		String sourceName = "Advertisement";
		leadSourceDto.setLeadSourceId(leadSourceId);
		leadSourceDto.setSourceName(sourceName);
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(leadSourceId, leadSourceDto.getLeadSourceId());
		assertEquals(sourceName, leadSourceDto.getSourceName());
	}
}
