package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LeadDtoTest {

	LeadDto dto = new LeadDto();
	LeadDto dto1 = new LeadDto();

	@Test
	void testLeadDto() {
		// Arrange
		LeadDto leadDto = new LeadDto();
		Integer leadId = 1;
		String firstName = "John";
		String lastName = "Doe";
		String phoneNumber = "1234567890";
		String topic = "Potential customer";
		String email = "john.doe@example.com";
		String companyWebsite = "www.example.com";
		String leadSourceId = "123";
		String serviceFallsId = "456";
		String domainId = "789";
		String companyName = "Example Company";
		String budgetAmount = "$5000";
		Integer assignTo = 1;
		String status = "Active";
		String disqualifyAs = "Not applicable";
		String disqualifyReason = "None";
		String designation = "Manager";
		String pseudoName = "John";
		String location = "New York";
		String linkedinId = "john_doe";
		String businessCard = "rdtfyghjklfghb";
		String businessCardName = "myData";
		String businessCardType = "pdf";

		// Act
		leadDto.setLeadId(leadId);
		leadDto.setFirstName(firstName);
		leadDto.setLastName(lastName);
		leadDto.setPhoneNumber(phoneNumber);
		leadDto.setTopic(topic);
		leadDto.setEmail(email);
		leadDto.setCompanyWebsite(companyWebsite);
		leadDto.setLeadSourceId(leadSourceId);
		leadDto.setServiceFallsId(serviceFallsId);
		leadDto.setDomainId(domainId);
		leadDto.setCompanyName(companyName);
		leadDto.setBudgetAmount(budgetAmount);
		leadDto.setAssignTo(assignTo);
		leadDto.setStatus(status);
		leadDto.setDisqualifyAs(disqualifyAs);
		leadDto.setDisqualifyReason(disqualifyReason);
		leadDto.setDesignation(designation);
		leadDto.setPseudoName(pseudoName);
		leadDto.setLocation(location);
		leadDto.setLinkedinId(linkedinId);
		leadDto.setBusinessCard(businessCard);
		leadDto.setBusinessCardName(businessCardName);
		leadDto.setBusinessCardType(businessCardType);

		// Assert
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(leadId, leadDto.getLeadId());
		assertEquals(firstName, leadDto.getFirstName());
		assertEquals(lastName, leadDto.getLastName());
		assertEquals(phoneNumber, leadDto.getPhoneNumber());
		assertEquals(topic, leadDto.getTopic());
		assertEquals(email, leadDto.getEmail());
		assertEquals(companyWebsite, leadDto.getCompanyWebsite());
		assertEquals(leadSourceId, leadDto.getLeadSourceId());
		assertEquals(serviceFallsId, leadDto.getServiceFallsId());
		assertEquals(domainId, leadDto.getDomainId());
		assertEquals(companyName, leadDto.getCompanyName());
		assertEquals(budgetAmount, leadDto.getBudgetAmount());
		assertEquals(assignTo, leadDto.getAssignTo());
		assertEquals(status, leadDto.getStatus());
		assertEquals(disqualifyAs, leadDto.getDisqualifyAs());
		assertEquals(disqualifyReason, leadDto.getDisqualifyReason());
		assertEquals(designation, leadDto.getDesignation());
		assertEquals(pseudoName, leadDto.getPseudoName());
		assertEquals(location, leadDto.getLocation());
		assertEquals(linkedinId, leadDto.getLinkedinId());
		assertEquals(businessCard, leadDto.getBusinessCard());
		assertEquals(businessCardName, leadDto.getBusinessCardName());
		assertEquals(businessCardType, leadDto.getBusinessCardType());
	}
}
