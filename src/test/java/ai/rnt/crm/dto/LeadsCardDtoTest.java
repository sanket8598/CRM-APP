package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LeadsCardDtoTest {

	@Test
	void testLeadsCardDto() {
		LeadsCardDto leadsCardDto = new LeadsCardDto();
		LeadsCardDto dto = new LeadsCardDto();
		LeadsCardDto dto1 = new LeadsCardDto();
		Integer leadId = 1;
		String shortName = "JD";
		String disqualifyAs = "Not applicable";
		String status = "Active";
		boolean important = true;
		String primaryField = "Topic";
		String secondaryField = "Company";
		leadsCardDto.setLeadId(leadId);
		leadsCardDto.setShortName(shortName);
		leadsCardDto.setDisqualifyAs(disqualifyAs);
		leadsCardDto.setStatus(status);
		leadsCardDto.setImportant(important);
		leadsCardDto.setPrimaryField(primaryField);
		leadsCardDto.setSecondaryField(secondaryField);
		dto1.setLeadId(leadId);
		dto1.setShortName(shortName);
		dto1.setDisqualifyAs(disqualifyAs);
		dto1.setStatus(status);
		dto1.setImportant(important);
		dto1.setPrimaryField(primaryField);
		dto1.setSecondaryField(secondaryField);
		leadsCardDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertEquals(leadId, leadsCardDto.getLeadId());
		assertEquals(shortName, leadsCardDto.getShortName());
		assertEquals(disqualifyAs, leadsCardDto.getDisqualifyAs());
		assertEquals(status, leadsCardDto.getStatus());
		assertEquals(important, leadsCardDto.isImportant());
		assertEquals(primaryField, leadsCardDto.getPrimaryField());
		assertEquals(secondaryField, leadsCardDto.getSecondaryField());
	}
}
