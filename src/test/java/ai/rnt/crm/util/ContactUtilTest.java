package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Leads;

class ContactUtilTest {

	@InjectMocks
	private ContactUtil contactUtil;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testIsDuplicateContact_NotDuplicate() {
		List<Contacts> existingContacts = new ArrayList<>();
		Contacts contact = new Contacts();
		contact.setFirstName("Jane");
		contact.setLastName("Smith");
		contact.setDesignation("Engineer");
		contact.setCompanyMaster(new CompanyMaster());
		contact.setLead(new Leads());
		assertFalse(ContactUtil.isDuplicateContact(existingContacts, contact));
	}

	@Test
	void testIsDuplicateContact_Duplicate() {
		List<Contacts> existingContacts = new ArrayList<>();
		Contacts existingContact = new Contacts();
		existingContact.setFirstName("John");
		existingContact.setLastName("Doe");
		existingContact.setDesignation("Manager");
		existingContact.setCompanyMaster(new CompanyMaster());
		existingContact.setLead(new Leads());
		existingContacts.add(existingContact);
		Contacts contact = new Contacts();
		contact.setFirstName("John");
		contact.setLastName("Doe");
		contact.setDesignation("Manager");
		contact.setCompanyMaster(new CompanyMaster());
		contact.setLead(new Leads());
		assertTrue(ContactUtil.isDuplicateContact(existingContacts, contact));
	}

	@Test
	void testIsDuplicateContact_NullParameters() {
		List<Contacts> existingContacts = new ArrayList<>();
		Contacts contact = new Contacts();
		contact.setFirstName("Jane");
		contact.setLastName("Smith");
		contact.setDesignation("Engineer");
		contact.setCompanyMaster(new CompanyMaster());
		contact.setLead(new Leads());
		assertFalse(ContactUtil.isDuplicateContact(existingContacts, null));
	}

	@Test
	void testIsDuplicateContact_DifferentAttributes() {
		List<Contacts> existingContacts = new ArrayList<>();
		Contacts existingContact = new Contacts();
		existingContact.setFirstName("John");
		existingContact.setLastName("Doe");
		existingContact.setDesignation("Manager");
		existingContact.setCompanyMaster(new CompanyMaster());
		existingContact.setLead(new Leads());
		existingContacts.add(existingContact);
		Contacts contact1 = new Contacts();
		contact1.setFirstName("John");
		contact1.setLastName("Doe");
		contact1.setDesignation("Manager");
		contact1.setCompanyMaster(new CompanyMaster());
		contact1.setLead(new Leads());
		Contacts contact2 = new Contacts();
		contact2.setFirstName("John");
		contact2.setLastName("Doe");
		contact2.setDesignation("Manager");
		contact2.setCompanyMaster(new CompanyMaster());
		contact2.setLead(new Leads());
		Contacts contact3 = new Contacts();
		contact3.setFirstName("John");
		contact3.setLastName("Doe");
		contact3.setDesignation("Engineer");
		contact3.setCompanyMaster(new CompanyMaster());
		contact3.setLead(new Leads());
		assertTrue(ContactUtil.isDuplicateContact(existingContacts, contact1));
	}
}
