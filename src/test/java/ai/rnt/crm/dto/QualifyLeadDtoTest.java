package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class QualifyLeadDtoTest {

	QualifyLeadDto dto = new QualifyLeadDto();
	QualifyLeadDto dto1 = new QualifyLeadDto();

	@Test
	void testSetAndGetQualifyLeadData() {
		// Create a QualifyLeadDto object
		QualifyLeadDto qualifyLeadDto = new QualifyLeadDto();

		// Set data
		qualifyLeadDto.setLeadId(1);
		qualifyLeadDto.setCustomerNeed("Software solution for inventory management");
		qualifyLeadDto.setProposedSolution("Customized software with inventory tracking features");
		qualifyLeadDto.setServiceFallsMaster(new ServiceFallsDto());
		qualifyLeadDto.setIsFollowUpRemainder(true);
		qualifyLeadDto.setRemainderVia("Email");
		qualifyLeadDto.setRemainderDueAt("08:30 AM");
		qualifyLeadDto.setRemainderDueOn(LocalDate.of(2024, 3, 9));

		// Get data
		Integer leadId = qualifyLeadDto.getLeadId();
		String customerNeed = qualifyLeadDto.getCustomerNeed();
		String proposedSolution = qualifyLeadDto.getProposedSolution();
		ServiceFallsDto serviceFallsMaster = qualifyLeadDto.getServiceFallsMaster();
		Boolean isFollowUpRemainder = qualifyLeadDto.getIsFollowUpRemainder();
		String remainderVia = qualifyLeadDto.getRemainderVia();
		String remainderDueAt = qualifyLeadDto.getRemainderDueAt();
		LocalDate remainderDueOn = qualifyLeadDto.getRemainderDueOn();

		// Assertions
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertNotNull(leadId);
		assertEquals(1, leadId);
		assertEquals("Software solution for inventory management", customerNeed);
		assertEquals("Customized software with inventory tracking features", proposedSolution);
		assertNotNull(serviceFallsMaster);
		assertEquals(true, isFollowUpRemainder);
		assertEquals("Email", remainderVia);
		assertEquals("08:30 AM", remainderDueAt);
		assertEquals(LocalDate.of(2024, 3, 9), remainderDueOn);
	}
}
