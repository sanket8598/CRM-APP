package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ContactDtoTest {

	ContactDto dto = new ContactDto();
	ContactDto dto1 = new ContactDto();

	@Test
	void getterData() {
		dto.getContactId();
		dto.getFirstName();
		dto.getLastName();
		dto.getDesignation();
		dto.getWorkEmail();
		dto.getPersonalEmail();
		dto.getContactNumberPrimary();
		dto.getContactNumberSecondary();
		dto.getCompanyMaster();
		dto.getLinkedinId();
		dto.getPrimary();
		dto.getBusinessCard();
		dto.getBusinessCardName();
		dto.getBusinessCardType();
		dto.getClient();
		dto.getName();
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
	}

	CompanyDto companyDto = new CompanyDto();

	@Test
	void setterData() {
		dto.setContactId(1);
		dto.setFirstName("John");
		dto.setLastName("Doe");
		dto.setDesignation("Software Engineer");
		dto.setWorkEmail("john.doe@example.com");
		dto.setPersonalEmail("john.doe.personal@example.com");
		dto.setContactNumberPrimary("+911234567890");
		dto.setContactNumberSecondary("+9198765432144");
		dto.setCompanyMaster(companyDto);
		dto.setLinkedinId("fsdja6567");
		dto.setPrimary(true);
		dto.setBusinessCard("jgkhiuyrte567iyukhjghf");
		dto.setBusinessCardName("myData");
		dto.setBusinessCardType("pdf");
		dto.setClient(true);
		dto.setName("gdsfytukhmvb");
	}

	@Test
	void testGetNameWithNonNullName() {
		ContactDto contactDto = new ContactDto();
		contactDto.setFirstName("John");
		contactDto.setLastName("Doe");
		String name = contactDto.getName();
		assertEquals("John Doe", name);
	}

	@Test
	void testGetNameWithNullName() {
		ContactDto contactDto = new ContactDto();
		contactDto.setFirstName("John");
		contactDto.setLastName(null);
		String name = contactDto.getName();
		assertEquals("John null", name);
	}
}
