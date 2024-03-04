package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EditLeadDtoTest {

	EditLeadDto dto = new EditLeadDto();
	EditLeadDto dto1 = new EditLeadDto();

	@Test
	void testEditLeadDto() {
		EditLeadDto editLeadDto = new EditLeadDto();
		Integer leadId = 1;
		String topic = "New Project";
		String budgetAmount = "10000";
		Integer assignTo = 2;
		String status = "Active";
		String customerNeed = "Increase sales";
		String proposedSolution = "Implement new marketing strategies";
		ServiceFallsDto serviceFallsMaster = new ServiceFallsDto();
		LeadSourceDto leadSourceMaster = new LeadSourceDto();
		DomainMasterDto domainMaster = new DomainMasterDto();
		String message = "Please follow up";
		String generatedBy = "User1";
		String leadRequirements = "Detailed requirements";
		String pseudoName = "JD";
		Integer dropDownAssignTo = 3;
		ContactDto primaryContact = new ContactDto();
		primaryContact.setFirstName("John");
		primaryContact.setLastName("Doe");
		primaryContact.setContactNumberPrimary("1234567890");
		editLeadDto.setLeadId(leadId);
		editLeadDto.setTopic(topic);
		editLeadDto.setBudgetAmount(budgetAmount);
		editLeadDto.setAssignTo(assignTo);
		editLeadDto.setStatus(status);
		editLeadDto.setCustomerNeed(customerNeed);
		editLeadDto.setProposedSolution(proposedSolution);
		editLeadDto.setServiceFallsMaster(serviceFallsMaster);
		editLeadDto.setLeadSourceMaster(leadSourceMaster);
		editLeadDto.setDomainMaster(domainMaster);
		editLeadDto.setMessage(message);
		editLeadDto.setGeneratedBy(generatedBy);
		editLeadDto.setLeadRequirements(leadRequirements);
		editLeadDto.setPseudoName(pseudoName);
		editLeadDto.setDropDownAssignTo(dropDownAssignTo);
		editLeadDto.setPrimaryContact(primaryContact);
		editLeadDto.getContacts().add(primaryContact);
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(leadId, editLeadDto.getLeadId());
		assertEquals(topic, editLeadDto.getTopic());
		assertEquals(budgetAmount, editLeadDto.getBudgetAmount());
		assertEquals(assignTo, editLeadDto.getAssignTo());
		assertEquals(status, editLeadDto.getStatus());
		assertEquals(customerNeed, editLeadDto.getCustomerNeed());
		assertEquals(proposedSolution, editLeadDto.getProposedSolution());
		assertEquals(serviceFallsMaster, editLeadDto.getServiceFallsMaster());
		assertEquals(leadSourceMaster, editLeadDto.getLeadSourceMaster());
		assertEquals(domainMaster, editLeadDto.getDomainMaster());
		assertEquals(message, editLeadDto.getMessage());
		assertEquals(generatedBy, editLeadDto.getGeneratedBy());
		assertEquals(leadRequirements, editLeadDto.getLeadRequirements());
		assertEquals(pseudoName, editLeadDto.getPseudoName());
		assertEquals(dropDownAssignTo, editLeadDto.getDropDownAssignTo());
		assertEquals(primaryContact, editLeadDto.getPrimaryContact());
		assertEquals(primaryContact, editLeadDto.getContacts().get(0));
	}

	@Test
	void testGetShortName() {
		EditLeadDto editLeadDto = new EditLeadDto();
		ContactDto primaryContact = new ContactDto();
		primaryContact.setFirstName("John");
		primaryContact.setLastName("Doe");
		editLeadDto.setPrimaryContact(primaryContact);
		String shortName = editLeadDto.getShortName();
		assertEquals("JD", shortName);
	}

	@Test
	void testGetFullName() {
		EditLeadDto editLeadDto = new EditLeadDto();
		ContactDto primaryContact = new ContactDto();
		primaryContact.setFirstName("John");
		primaryContact.setLastName("Doe");
		editLeadDto.setPrimaryContact(primaryContact);
		String fullName = editLeadDto.getFullName();
		assertEquals("John Doe", fullName);
	}

	@Test
	void testGetShortNameWithNullPrimaryContact() {
		EditLeadDto editLeadDto = new EditLeadDto();
		String shortName = editLeadDto.getShortName();
		assertEquals(null, shortName);
	}

	@Test
	void testGetFullNameWithNullPrimaryContact() {
		EditLeadDto editLeadDto = new EditLeadDto();
		String fullName = editLeadDto.getFullName();
		assertEquals(null, fullName);
	}
}
