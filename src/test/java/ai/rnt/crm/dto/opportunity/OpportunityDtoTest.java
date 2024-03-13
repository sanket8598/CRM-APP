package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.dto.LeadDashboardDto;

class OpportunityDtoTest {

	//@Test
	void testEqualsAndHashCode() {
		OpportunityDto dto1 = createSampleDto();
		OpportunityDto dto2 = createSampleDto();

		assertEquals(dto1, dto2);
		assertEquals(dto1.hashCode(), dto2.hashCode());

		dto2.setOpportunityId(2);
		assertNotEquals(dto1, dto2);
	}

	@Test
	void testToString() {
		OpportunityDto dto = createSampleDto();
		assertNotNull(dto.toString());
	}

	@Test
	void testCanEqual() {
		OpportunityDto dto = createSampleDto();
		assertTrue(dto.canEqual(new OpportunityDto()));
	}

	@Test
	void testGetShortNameNotNull() {
		OpportunityDto dto = createSampleDto();
		dto.setLeadDashboardDto(new LeadDashboardDto());
		ContactDto primaryContact = new ContactDto();
		primaryContact.setFirstName("John");
		primaryContact.setLastName("Doe");
		dto.getLeadDashboardDto().setPrimaryContact(primaryContact);
		assertEquals("JD", dto.getShortName());
	}

	@Test
	void testGetFullNameNotNull() {
		OpportunityDto dto = createSampleDto();
		dto.setLeadDashboardDto(new LeadDashboardDto());
		ContactDto primaryContact = new ContactDto();
		primaryContact.setFirstName("John");
		primaryContact.setLastName("Doe");
		dto.getLeadDashboardDto().setPrimaryContact(primaryContact);
		assertEquals("John Doe", dto.getFullName());
	}

	@Test
	void testGetShortNameNull() {
		OpportunityDto dto = new OpportunityDto();
		assertNull(dto.getShortName());
	}

	@Test
	void testGetFullNameNull() {
		OpportunityDto dto = new OpportunityDto();
		assertNull(dto.getFullName());
	}

	private OpportunityDto createSampleDto() {
		OpportunityDto dto = new OpportunityDto();
		dto.setOpportunityId(1);
		dto.setStatus("Status");
		dto.setTopic("Topic");
		dto.setPseudoName("Pseudo");
		dto.setBudgetAmount("Budget");
		dto.setCustomerNeed("Need");
		dto.setProposedSolution("Solution");
		dto.setMessage("Message");
		dto.setGeneratedBy("GeneratedBy");
		dto.setDropDownAssignTo(1);
		dto.setCurrentPhase("Phase");
		dto.setProgressStatus("Progress");
		dto.setClosedOn(LocalDate.now());
		dto.setLeadDashboardDto(new LeadDashboardDto());
		dto.setEmployee(new EmployeeDto());
		dto.setContacts(new ArrayList<>());
		dto.setCreatedOn("2024-03-07");
		return dto;
	}
}
