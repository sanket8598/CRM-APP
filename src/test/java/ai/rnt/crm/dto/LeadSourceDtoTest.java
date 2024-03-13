package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LeadSourceDtoTest {

	@Test
	void testLeadSourceDto() {
		LeadSourceDto leadSourceDto = new LeadSourceDto();
		LeadSourceDto dto = new LeadSourceDto();
		LeadSourceDto dto1 = new LeadSourceDto();
		Integer leadSourceId = 1;
		String sourceName = "Advertisement";
		leadSourceDto.setLeadSourceId(leadSourceId);
		leadSourceDto.setSourceName(sourceName);
		dto1.setLeadSourceId(leadSourceId);
		dto1.setSourceName(sourceName);
		leadSourceDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertEquals(leadSourceId, leadSourceDto.getLeadSourceId());
		assertEquals(sourceName, leadSourceDto.getSourceName());
	}
}
