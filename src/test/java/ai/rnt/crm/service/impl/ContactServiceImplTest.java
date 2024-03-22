package ai.rnt.crm.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.util.ContactUtil;

class ContactServiceImplTest {

	@Mock
	private ContactDaoService contactDaoService;

	@Mock
	private ContactUtil contactUtil;

	@Mock
	private LeadDaoService leadDaoService;

	@InjectMocks
	private ContactServiceImpl contactServiceImpl;

	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	ContactDto contactDto;
	@InjectMocks
	ContactDto contact2;
	
	@InjectMocks
	Contacts contactDto1;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(contactServiceImpl).build();
	}

	@Test
	void testAddContact() {
		contactDto.setName("John Doe12");
		contactDto.setPrimary(true);

		Integer leadId = 1;
       
		List<Contacts> existingContacts = new ArrayList<>();
		Contacts existingContact = new Contacts();
		existingContact.setPrimary(true);
		existingContacts.add(existingContact);
		when(contactDaoService.contactsOfLead(leadId)).thenReturn(existingContacts);
		CompanyMaster companyMaster = new CompanyMaster();
		existingContact.setCompanyMaster(companyMaster);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = contactServiceImpl.addContact(contactDto, leadId);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));

	}
	@Test
	void addContactTest1() {
		ContactDto contactDto1 = new ContactDto();
		contactDto1.setName("Hon");
		contactDto1.setFirstName("Hon");
		contactDto1.setLastName("Hon");
		contactDto1.setLastName("Hon");
		contactDto1.setDesignation("Hon");
		contactDto1.setDesignation("Hon");
		contactDto1.setDesignation("Hon");
		CompanyMaster companyMaster1 = new CompanyMaster();
		companyMaster1.setCompanyId(1);
		Leads lead=new Leads();
		lead.setLeadId(1);
		when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(lead));
		contactDto1.setPrimary(true);

		Integer leadId1 = 1;

		List<Contacts> existingContacts1 = new ArrayList<>();
		Contacts existingContact1 = new Contacts();
		existingContact1.setPrimary(false);
		existingContacts1.add(existingContact1);
		Contacts contactDto2 = new Contacts();
		contactDto2.setPrimary(true);
		contactDto2.setFirstName("Hon");
		contactDto2.setLastName("Hon");
		contactDto2.setLastName("Hon");
		contactDto2.setDesignation("Hon");
		contactDto2.setDesignation("Hon");
		contactDto2.setDesignation("Hon");
		companyMaster1.setCompanyId(1);
		contactDto2.setLead(lead);
		existingContacts1.add(contactDto2);

		existingContact1.setCompanyMaster(companyMaster1);
		ResponseEntity<EnumMap<ApiResponse, Object>> response1 = contactServiceImpl.addContact(contactDto1, leadId1);
		assertEquals(HttpStatus.CREATED, response1.getStatusCode());
		assertFalse((Boolean) response1.getBody().get(ApiResponse.SUCCESS));
	}
	@Test
	void addContactTest3() {
		contact2.setName("hon");
		contact2.setPrimary(true);

		Integer leadId2 = 1;

		List<Contacts> existingContacts2 = new ArrayList<>();
		Contacts existingContact2 = new Contacts();
		existingContact2.setPrimary(false);
		existingContacts2.add(existingContact2);

		CompanyMaster companyMaster2 = new CompanyMaster();
		existingContact2.setCompanyMaster(companyMaster2);
		when(contactDaoService.contactsOfLead(leadId2)).thenReturn(existingContacts2);
		ResponseEntity<EnumMap<ApiResponse, Object>> response2 = contactServiceImpl.addContact(contact2, leadId2);
		assertEquals(HttpStatus.CREATED, response2.getStatusCode());
		assertFalse((Boolean) response2.getBody().get(ApiResponse.SUCCESS));
	}
	
	@Test
	void testaddContactException() {
		Integer contactId = 1;
		contactDto1.setPrimary(true);
		when(contactDaoService.contactsOfLead(contactId)).thenThrow(CRMException.class);
		assertThrows(CRMException.class, () -> {
			contactServiceImpl.addContact(contactDto,contactId);
		});
	}
    @Test
    void testGetContactNotFound() {
        Integer contactId = 1;
        assertThrows(CRMException.class, () -> {
        	contactServiceImpl.getContact(contactId);
        });
    }
    
    @Test
    void testUpdateContactSuccess() {
        // Mock data
        ContactDto contactDto = new ContactDto();
        contactDto.setName("John Doe");
        Integer contactId = 1;

        // Mock existing contact
        Contacts existingContact = new Contacts();
        existingContact.setContactId(contactId);
        existingContact.setLead(new Leads());
        when(contactDaoService.findById(contactId)).thenReturn(Optional.of(existingContact));
        ResponseEntity<EnumMap<ApiResponse, Object>> response = contactServiceImpl.updateContact(contactDto, contactId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateContactNotFound() {
        // Mock data
        ContactDto contactDto = new ContactDto();
        Integer contactId = 1;
        when(contactDaoService.findById(contactId)).thenReturn(Optional.empty());
        assertThrows(CRMException.class, () -> {
            contactServiceImpl.updateContact(contactDto, contactId);
        });
    }

    @Test
    void testUpdateContactSuceess() {
    	ContactDto contactDto = new ContactDto();
    	contactDto.setName("Hon");
    	contactDto.setFirstName("Hon");
    	contactDto.setLastName("Hon");
    	contactDto.setLastName("Hon");
    	contactDto.setDesignation("Hon");
    	contactDto.setDesignation("Hon");
    	contactDto.setDesignation("Hon");
    	CompanyMaster companyMaster1 = new CompanyMaster();
    	companyMaster1.setCompanyId(1);
    	Leads lead=new Leads();
    	lead.setLeadId(1);
    	when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(lead));
    	contactDto.setPrimary(true);
    	Integer contactId = 1;
    	Contacts existingContact = new Contacts();
    	existingContact.setContactId(contactId);
    	existingContact.setPrimary(true);
    	existingContact.setLead(lead);
    	
    	List<Contacts> existingContacts = new ArrayList<>();
    	existingContacts.add(existingContact);
    	
    	when(contactDaoService.findById(contactId)).thenReturn(Optional.of(existingContact));
    	when(contactDaoService.contactsOfLead(anyInt())).thenReturn(existingContacts);
    	when(contactDaoService.addContact(any())).thenReturn(existingContact);
    	ResponseEntity<EnumMap<ApiResponse, Object>> response = contactServiceImpl.updateContact(contactDto, contactId);
    	assertEquals("Contact Updated Successfully !!", response.getBody().get(ApiResponse.MESSAGE));
    }
    @Test
    void testUpdateContactUnmarkPrimary() {
        ContactDto contactDto = new ContactDto();
		contactDto.setName("Hon");
		contactDto.setFirstName("Hon");
		contactDto.setLastName("Hon");
		contactDto.setLastName("Hon");
		contactDto.setDesignation("Hon");
		contactDto.setDesignation("Hon");
		contactDto.setDesignation("Hon");
		CompanyMaster companyMaster1 = new CompanyMaster();
		companyMaster1.setCompanyId(1);
		Leads lead=new Leads();
		lead.setLeadId(1);
		when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(lead));
		contactDto.setPrimary(true);
        Integer contactId = 1;
        Contacts existingContact = new Contacts();
        existingContact.setContactId(contactId);
        existingContact.setPrimary(true);
        existingContact.setLead(lead);

        List<Contacts> existingContacts = new ArrayList<>();
        existingContacts.add(existingContact);

        when(contactDaoService.findById(contactId)).thenReturn(Optional.of(existingContact));
        when(contactDaoService.contactsOfLead(anyInt())).thenReturn(existingContacts);
        ResponseEntity<EnumMap<ApiResponse, Object>> response = contactServiceImpl.updateContact(contactDto, contactId);
        assertEquals("Contact Not Updated !!", response.getBody().get(ApiResponse.MESSAGE));
    }
    
    @Test
    void testUpdateContactUnmarkPrimary1() {
        ContactDto contactDto = new ContactDto();
		contactDto.setName("Hon");
		contactDto.setFirstName("Hon");
		contactDto.setLastName("Hon");
		contactDto.setLastName("Hon");
		contactDto.setDesignation("Hon");
		contactDto.setDesignation("Hon");
		contactDto.setDesignation("Hon");
		CompanyMaster companyMaster1 = new CompanyMaster();
		companyMaster1.setCompanyId(1);
		Leads lead=new Leads();
		lead.setLeadId(1);
		when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(lead));
		contactDto.setPrimary(false);
        Integer contactId = 1;
        Contacts existingContact = new Contacts();
        existingContact.setContactId(contactId);
        existingContact.setPrimary(true);
        existingContact.setLead(lead);

        List<Contacts> existingContacts = new ArrayList<>();
        existingContacts.add(existingContact);

        when(contactDaoService.findById(contactId)).thenReturn(Optional.of(existingContact));
        when(contactDaoService.contactsOfLead(anyInt())).thenReturn(existingContacts);

        ResponseEntity<EnumMap<ApiResponse, Object>> response = contactServiceImpl.updateContact(contactDto, contactId);
        assertEquals("Cannot unmark the only contact as primary !!", response.getBody().get(ApiResponse.MESSAGE));
    }
    @Test
    void testUpdateContactUnmarkPrimaryScen2() {
    	ContactDto contactDto = new ContactDto();
    	contactDto.setName("Hon");
    	contactDto.setFirstName("Hon");
    	contactDto.setLastName("Hon");
    	contactDto.setLastName("Hon");
    	contactDto.setDesignation("Hon");
    	contactDto.setDesignation("Hon");
    	contactDto.setDesignation("Hon");
    	CompanyMaster companyMaster1 = new CompanyMaster();
    	companyMaster1.setCompanyId(1);
    	Leads lead=new Leads();
    	lead.setLeadId(1);
    	when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(lead));
    	contactDto.setPrimary(false);
    	Integer contactId = 1;
    	Contacts existingContact = new Contacts();
    	existingContact.setContactId(contactId);
    	existingContact.setPrimary(true);
    	existingContact.setLead(lead);
    	Contacts existingContact1 = new Contacts();
    	existingContact1.setContactId(contactId);
    	existingContact1.setPrimary(true);
    	existingContact1.setLead(lead);
    	
    	List<Contacts> existingContacts = new ArrayList<>();
    	existingContacts.add(existingContact);
    	existingContacts.add(existingContact1);
    	
    	when(contactDaoService.findById(contactId)).thenReturn(Optional.of(existingContact));
    	when(contactDaoService.contactsOfLead(anyInt())).thenReturn(existingContacts);
    	ResponseEntity<EnumMap<ApiResponse, Object>> response = contactServiceImpl.updateContact(contactDto, contactId);
    	assertEquals("Cannot unmark the only contact as primary !!", response.getBody().get(ApiResponse.MESSAGE));
    }

}
