package ai.rnt.crm.entity;

import org.junit.jupiter.api.Test;

class ContactsTest {

	Contacts contacts = new Contacts();

	@Test
	void getterTest() {
		contacts.getContactId();
		contacts.getFirstName();
		contacts.getLastName();
		contacts.getDesignation();
		contacts.getWorkEmail();
		contacts.getPersonalEmail();
		contacts.getContactNumberPrimary();
		contacts.getContactNumberSecondary();
		contacts.getLinkedinId();
		contacts.getBusinessCard();
		contacts.getBusinessCardName();
		contacts.getBusinessCardType();
		contacts.getLead();
		contacts.getCompanyMaster();
		contacts.getPrimary();
		contacts.getClient();

	}

	Leads leads = new Leads();
	CompanyMaster CompanyMaster = new CompanyMaster();

	@Test
	void setterTest() {
		contacts.setContactId(1);
		contacts.setFirstName("Jakson");
		contacts.setLastName("Roy");
		contacts.setDesignation("Developer");
		contacts.setWorkEmail("j.roy@gmail.com");
		contacts.setPersonalEmail("j11roy@gmail.com");
		contacts.setContactNumberPrimary("+917894653246");
		contacts.setContactNumberSecondary("+917345098723");
		contacts.setLinkedinId("jRoy2112");
		contacts.setBusinessCard("ertyuikjvdyjm");
		contacts.setBusinessCardName("myData");
		contacts.setBusinessCardType("img");
		contacts.setLead(leads);
		contacts.setCompanyMaster(CompanyMaster);
		contacts.setPrimary(true);
		contacts.setClient(true);

	}
}
