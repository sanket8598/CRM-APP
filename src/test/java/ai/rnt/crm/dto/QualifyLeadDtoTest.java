package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class QualifyLeadDtoTest {

	@Test
	void testSetAndGetQualifyLeadData() {
		QualifyLeadDto qualifyLeadDto = new QualifyLeadDto();
		QualifyLeadDto dto = new QualifyLeadDto();
		QualifyLeadDto dto1 = new QualifyLeadDto();
		qualifyLeadDto.setLeadId(1);
		qualifyLeadDto.setCustomerNeed("Software solution for inventory management");
		qualifyLeadDto.setProposedSolution("Customized software with inventory tracking features");
		qualifyLeadDto.setServiceFallsMaster(new ServiceFallsDto());
		qualifyLeadDto.setIsFollowUpRemainder(true);
		qualifyLeadDto.setRemainderVia("Email");
		qualifyLeadDto.setRemainderDueAt("08:30 AM");
		qualifyLeadDto.setRemainderDueOn(LocalDate.of(2024, 3, 9));
		dto1.setLeadId(1);
		dto1.setCustomerNeed("Software solution for inventory management");
		dto1.setProposedSolution("Customized software with inventory tracking features");
		dto1.setServiceFallsMaster(new ServiceFallsDto());
		dto1.setIsFollowUpRemainder(true);
		dto1.setRemainderVia("Email");
		dto1.setRemainderDueAt("08:30 AM");
		dto1.setRemainderDueOn(LocalDate.of(2024, 3, 9));

		Integer leadId = qualifyLeadDto.getLeadId();
		String customerNeed = qualifyLeadDto.getCustomerNeed();
		String proposedSolution = qualifyLeadDto.getProposedSolution();
		ServiceFallsDto serviceFallsMaster = qualifyLeadDto.getServiceFallsMaster();
		Boolean isFollowUpRemainder = qualifyLeadDto.getIsFollowUpRemainder();
		String remainderVia = qualifyLeadDto.getRemainderVia();
		String remainderDueAt = qualifyLeadDto.getRemainderDueAt();
		LocalDate remainderDueOn = qualifyLeadDto.getRemainderDueOn();

		qualifyLeadDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
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
