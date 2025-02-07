package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class UpdateLeadDtoTest {

	@Test
	void testSetAndGetUpdateLeadData() {
		UpdateLeadDto updateLeadDto = new UpdateLeadDto();
		UpdateLeadDto dto = new UpdateLeadDto();
		UpdateLeadDto dto1 = new UpdateLeadDto();
		updateLeadDto.setFirstName("John");
		updateLeadDto.setLastName("Doe");
		updateLeadDto.setPhoneNumber("1234567890");
		updateLeadDto.setEmail("john.doe@example.com");
		updateLeadDto.setBudgetAmount("10000");
		updateLeadDto.setServiceFallsId("serviceFallsId");
		updateLeadDto.setLeadSourceId("leadSourceId");
		updateLeadDto.setDomainId("domainId");
		updateLeadDto.setCompanyWebsite("example.com");
		updateLeadDto.setCompanyName("Example Company");
		updateLeadDto.setCountry("USA");
		updateLeadDto.setState("California");
		updateLeadDto.setCity("San Francisco");
		updateLeadDto.setZipCode("12345");
		updateLeadDto.setAddressLineOne("123 Main St");
		updateLeadDto.setFullName("John Doe");
		updateLeadDto.setCustomerNeed("Customer need");
		updateLeadDto.setProposedSolution("Proposed solution");
		updateLeadDto.setPseudoName("pseudoName");
		updateLeadDto.setLinkedinId("john_doe");
		updateLeadDto.setTopic("test");
		dto1.setFirstName("John");
		dto1.setLastName("Doe");
		dto1.setPhoneNumber("1234567890");
		dto1.setEmail("john.doe@example.com");
		dto1.setBudgetAmount("10000");
		dto1.setServiceFallsId("serviceFallsId");
		dto1.setLeadSourceId("leadSourceId");
		dto1.setDomainId("domainId");
		dto1.setCompanyWebsite("example.com");
		dto1.setCompanyName("Example Company");
		dto1.setCountry("USA");
		dto1.setState("California");
		dto1.setCity("San Francisco");
		dto1.setZipCode("12345");
		dto1.setAddressLineOne("123 Main St");
		dto1.setFullName("John Doe");
		dto1.setCustomerNeed("Customer need");
		dto1.setProposedSolution("Proposed solution");
		dto1.setPseudoName("pseudoName");
		dto1.setLinkedinId("john_doe");
		dto1.setTopic("test");
		String firstName = updateLeadDto.getFirstName();
		String lastName = updateLeadDto.getLastName();
		String phoneNumber = updateLeadDto.getPhoneNumber();
		String email = updateLeadDto.getEmail();
		String budgetAmount = updateLeadDto.getBudgetAmount();
		String serviceFallsId = updateLeadDto.getServiceFallsId();
		String leadSourceId = updateLeadDto.getLeadSourceId();
		String domainId = updateLeadDto.getDomainId();
		String companyWebsite = updateLeadDto.getCompanyWebsite();
		String companyName = updateLeadDto.getCompanyName();
		String country = updateLeadDto.getCountry();
		String state = updateLeadDto.getState();
		String city = updateLeadDto.getCity();
		String zipCode = updateLeadDto.getZipCode();
		String addressLineOne = updateLeadDto.getAddressLineOne();
		String fullName = updateLeadDto.getFullName();
		String customerNeed = updateLeadDto.getCustomerNeed();
		String proposedSolution = updateLeadDto.getProposedSolution();
		String pseudoName = updateLeadDto.getPseudoName();
		String linkedinId = updateLeadDto.getLinkedinId();
		String topic = updateLeadDto.getTopic();
		updateLeadDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertNotNull(firstName);
		assertEquals("John", firstName);
		assertEquals("Doe", lastName);
		assertEquals("1234567890", phoneNumber);
		assertEquals("john.doe@example.com", email);
		assertEquals("10000", budgetAmount);
		assertEquals("serviceFallsId", serviceFallsId);
		assertEquals("leadSourceId", leadSourceId);
		assertEquals("domainId", domainId);
		assertEquals("example.com", companyWebsite);
		assertEquals("Example Company", companyName);
		assertEquals("USA", country);
		assertEquals("California", state);
		assertEquals("San Francisco", city);
		assertEquals("12345", zipCode);
		assertEquals("123 Main St", addressLineOne);
		assertEquals("John Doe", fullName);
		assertEquals("Customer need", customerNeed);
		assertEquals("Proposed solution", proposedSolution);
		assertEquals("pseudoName", pseudoName);
		assertEquals("john_doe", linkedinId);
		assertEquals("test", topic);
	}

}
