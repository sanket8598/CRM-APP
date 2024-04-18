package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.dto.LeadDashboardDto;

class OpportunityDtoTest {

	@Test
	void testOpportunityDto() {
		OpportunityDto opportunityDto = new OpportunityDto();
		OpportunityDto opportunityDto1 = new OpportunityDto();
		LeadDashboardDto leadDashboardDto = new LeadDashboardDto();
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setStaffId(1);
		List<ContactDto> contacts = Arrays.asList(new ContactDto(), new ContactDto());
		opportunityDto.setOpportunityId(1);
		opportunityDto.setStatus("Open");
		opportunityDto.setTopic("Test Topic");
		opportunityDto.setPseudoName("Test PseudoName");
		opportunityDto.setBudgetAmount("10000");
		opportunityDto.setCustomerNeed("Test Customer Need");
		opportunityDto.setProposedSolution("Test Proposed Solution");
		opportunityDto.setMessage("Test Message");
		opportunityDto.setGeneratedBy("John Doe");
		opportunityDto.setDropDownAssignTo(2);
		opportunityDto.setCurrentPhase("Phase 1");
		opportunityDto.setProgressStatus("In Progress");
		opportunityDto.setClosedOn(LocalDate.now());
		opportunityDto.setLeadDashboardDto(leadDashboardDto);
		opportunityDto.setEmployee(employeeDto);
		opportunityDto.setContacts(contacts);
		opportunityDto.setCreatedOn("2024-03-13");
		opportunityDto.setCreatedDate(LocalDateTime.now());
		opportunityDto.setAssignBy("Me");
		opportunityDto.setAssignDate(LocalDate.now());
		opportunityDto1.setOpportunityId(1);
		opportunityDto1.setStatus("Open");
		opportunityDto1.setTopic("Test Topic");
		opportunityDto1.setPseudoName("Test PseudoName");
		opportunityDto1.setBudgetAmount("10000");
		opportunityDto1.setCustomerNeed("Test Customer Need");
		opportunityDto1.setProposedSolution("Test Proposed Solution");
		opportunityDto1.setMessage("Test Message");
		opportunityDto1.setGeneratedBy("John Doe");
		opportunityDto1.setDropDownAssignTo(2);
		opportunityDto1.setCurrentPhase("Phase 1");
		opportunityDto1.setProgressStatus("In Progress");
		opportunityDto1.setClosedOn(LocalDate.now());
		opportunityDto1.setLeadDashboardDto(leadDashboardDto);
		opportunityDto1.setEmployee(employeeDto);
		opportunityDto1.setContacts(contacts);
		opportunityDto1.setCreatedOn("2024-03-13");

		opportunityDto.equals(opportunityDto1);
		opportunityDto.toString();
		opportunityDto.hashCode();
		opportunityDto1.hashCode();

		assertEquals(1, opportunityDto.getOpportunityId());
		assertEquals("Open", opportunityDto.getStatus());
		assertEquals("Test Topic", opportunityDto.getTopic());
		assertEquals("Test PseudoName", opportunityDto.getPseudoName());
		assertEquals("10000", opportunityDto.getBudgetAmount());
		assertEquals("Test Customer Need", opportunityDto.getCustomerNeed());
		assertEquals("Test Proposed Solution", opportunityDto.getProposedSolution());
		assertEquals("Test Message", opportunityDto.getMessage());
		assertEquals("John Doe", opportunityDto.getGeneratedBy());
		assertEquals(2, opportunityDto.getDropDownAssignTo());
		assertEquals("Phase 1", opportunityDto.getCurrentPhase());
		assertEquals("In Progress", opportunityDto.getProgressStatus());
		assertEquals(LocalDate.now(), opportunityDto.getClosedOn());
		assertEquals(leadDashboardDto, opportunityDto.getLeadDashboardDto());
		assertEquals(employeeDto, opportunityDto.getEmployee());
		assertEquals(contacts, opportunityDto.getContacts());
		assertEquals("2024-03-13", opportunityDto.getCreatedOn());
		assertEquals("Me", opportunityDto.getAssignBy());
		opportunityDto.getCreatedDate();
		opportunityDto.getAssignDate();
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

	@Test
	void testGetFullNameWithNonNullLeadDashboardDto() {
		OpportunityDto opportunityDto = new OpportunityDto();
		LeadDashboardDto leadDashboardDto = new LeadDashboardDto();
		ContactDto primaryContact = new ContactDto();
		primaryContact.setFirstName("John");
		primaryContact.setLastName("Doe");
		leadDashboardDto.setPrimaryContact(primaryContact);
		opportunityDto.setLeadDashboardDto(leadDashboardDto);
		String fullName = opportunityDto.getFullName();
		assertEquals("John Doe", fullName);
	}

	@Test
	void testGetFullNameWithNullLeadDashboardDto() {
		OpportunityDto opportunityDto = new OpportunityDto();
		String fullName = opportunityDto.getFullName();
		assertNull(fullName);
	}

	@Test
	void testGetFullNameWithNullPrimaryContact() {
		OpportunityDto opportunityDto = new OpportunityDto();
		LeadDashboardDto leadDashboardDto = new LeadDashboardDto();
		leadDashboardDto.setPrimaryContact(null);
		opportunityDto.setLeadDashboardDto(leadDashboardDto);
		String fullName = opportunityDto.getFullName();
		assertNull(fullName);
	}

	@Test
	void testGetShortNameWithNonNullLeadDashboardDto() {
		OpportunityDto opportunityDto = new OpportunityDto();
		LeadDashboardDto leadDashboardDto = new LeadDashboardDto();
		ContactDto primaryContact = new ContactDto();
		primaryContact.setFirstName("John");
		primaryContact.setLastName("Doe");
		leadDashboardDto.setPrimaryContact(primaryContact);
		opportunityDto.setLeadDashboardDto(leadDashboardDto);
		String shortName = opportunityDto.getShortName();
		assertEquals("JD", shortName);
	}

	@Test
	void testGetShortNameWithNullLeadDashboardDto() {
		OpportunityDto opportunityDto = new OpportunityDto();
		String shortName = opportunityDto.getShortName();
		assertNull(shortName);
	}

	@Test
	void testGetShortNameWithNullPrimaryContact() {
		OpportunityDto opportunityDto = new OpportunityDto();
		LeadDashboardDto leadDashboardDto = new LeadDashboardDto();
		leadDashboardDto.setPrimaryContact(null);
		opportunityDto.setLeadDashboardDto(leadDashboardDto);
		String shortName = opportunityDto.getShortName();
		assertNull(shortName);
	}
}
