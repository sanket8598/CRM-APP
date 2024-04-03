package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class QualifyLeadDtoTest {

	@Test
	void testSetAndGetQualifyLeadData() {
		QualifyLeadDto qualifyLeadDto = new QualifyLeadDto();
		QualifyLeadDto dto = new QualifyLeadDto();
		QualifyLeadDto dto1 = new QualifyLeadDto();
		qualifyLeadDto.setLeadId(1);
		dto1.setLeadId(1);
		dto1.setBudgetAmount("123");
		dto1.setCurrentPhase("Qualify");
		dto1.setCustomerReadiness("test");
		dto1.setProgressStatus("Completed");
		dto1.setQualifyRemarks("testData");

		Integer leadId = qualifyLeadDto.getLeadId();

		qualifyLeadDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertNotNull(leadId);
		assertEquals(1, leadId);
	}
}
