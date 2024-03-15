package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.ServiceFallsMaster;

class LeadsCardUtilTest {

	@Test
	void testShortName() {
		assertEquals("JD", LeadsCardUtil.shortName("John", "Doe"));
	}

	@Test
	void testShortNameNullFirstName() {
		assertNull(LeadsCardUtil.shortName(null, "Doe"));
	}

	@Test
	void testShortNameNullLastName() {
		assertNull(LeadsCardUtil.shortName("John", null));
	}

	@Test
	void testShortNameBothNull() {
		assertNull(LeadsCardUtil.shortName(null, null));
	}

	@Test
	void testShortNameWithFullName() {
		assertEquals("JD", LeadsCardUtil.shortName("John Doe"));
	}

	@Test
	void testShortNameWithSingleName() {
		assertEquals("J", LeadsCardUtil.shortName("John"));
	}

	@Test
	void testCheckDuplicateLead_WithDuplicateLeads() {
		Leads existingLead = new Leads();
		Leads newLead = new Leads();
		newLead.setBudgetAmount("sagfsd");
		existingLead.setBudgetAmount("sagfsd");
		newLead.setProposedSolution("abc");
		existingLead.setProposedSolution("abc");
		newLead.setCustomerNeed("abc");
		existingLead.setCustomerNeed("abc");
		newLead.setTopic("getTopic");
		existingLead.setTopic("getTopic");
		ServiceFallsMaster serviceFallsMaster = new ServiceFallsMaster();
		serviceFallsMaster.setServiceFallsId(1);
		LeadSourceMaster leadsource = new LeadSourceMaster();
		leadsource.setLeadSourceId(1);
		CompanyMaster cmp = new CompanyMaster();
		cmp.setCompanyId(12);
		Contacts contact = new Contacts();
		contact.setPrimary(true);
		contact.setFirstName("John");
		contact.setLastName("Doe");
		contact.setBusinessCard("abc");
		contact.setCompanyMaster(cmp);
		Contacts contact2 = new Contacts();
		contact2.setPrimary(false);
		contact2.setFirstName("John");
		contact2.setLastName("Doe");
		contact2.setBusinessCard("abc");
		contact2.setCompanyMaster(cmp);
		List<Contacts> list = new ArrayList<>();
		list.add(contact);
		list.add(contact2);
		newLead.setContacts(list);
		newLead.setServiceFallsMaster(serviceFallsMaster);
		existingLead.setServiceFallsMaster(serviceFallsMaster);
		newLead.setLeadSourceMaster(leadsource);
		existingLead.setLeadSourceMaster(leadsource);
		existingLead.setContacts(list);
		List<Leads> allLeads = new ArrayList<>();
		allLeads.add(existingLead);
		boolean result = LeadsCardUtil.checkDuplicateLead(allLeads, newLead);
		assertTrue(result);
	}

	@Test
	void testCheckDuplicateLead_WithNoDuplicateLeads() {
		Leads existingLead = new Leads();

		Leads newLead = new Leads();
		Contacts contact = new Contacts();
		contact.setPrimary(true);
		contact.setFirstName("John");
		LeadSourceMaster leadsource = new LeadSourceMaster();
		leadsource.setLeadSourceId(1);
		ServiceFallsMaster serviceFallsMaster = new ServiceFallsMaster();
		serviceFallsMaster.setServiceFallsId(1);
		newLead.setLeadSourceMaster(leadsource);
		existingLead.setLeadSourceMaster(null);
		existingLead.setServiceFallsMaster(null);
		newLead.setServiceFallsMaster(serviceFallsMaster);
		List<Contacts> list = new ArrayList<>();
		list.add(contact);
		existingLead.setContacts(list);
		newLead.setContacts(list);
		List<Leads> allLeads = new ArrayList<>();
		allLeads.add(existingLead);
		boolean result = LeadsCardUtil.checkDuplicateLead(allLeads, newLead);
		assertFalse(result);
	}

}
