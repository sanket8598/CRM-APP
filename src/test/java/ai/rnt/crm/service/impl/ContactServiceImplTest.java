package ai.rnt.crm.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
	
	@Mock
	ContactDto contactDto;
	
	@Mock
	Contacts contactDto1;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(contactServiceImpl).build();
	}

	@Test
	void testAddContact() {
		contactDto.setName("John Doe12");
		contactDto.setPrimary(false);

		Integer leadId = 1;

		List<Contacts> existingContacts = new ArrayList<>();
		Contacts existingContact = new Contacts();
		existingContact.setPrimary(false);
		existingContacts.add(existingContact);

		CompanyMaster companyMaster = new CompanyMaster();
		existingContact.setCompanyMaster(companyMaster);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = contactServiceImpl.addContact(contactDto, leadId);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));

		ContactDto contactDto1 = new ContactDto();
		contactDto1.setPrimary(true);

		Integer leadId1 = 1;

		List<Contacts> existingContacts1 = new ArrayList<>();
		Contacts existingContact1 = new Contacts();
		existingContact1.setPrimary(true);
		existingContacts1.add(existingContact1);
		Contacts contactDto2 = new Contacts();
		contactDto2.setPrimary(true);
		existingContacts1.add(contactDto2);

		CompanyMaster companyMaster1 = new CompanyMaster();
		existingContact.setCompanyMaster(companyMaster1);
		ResponseEntity<EnumMap<ApiResponse, Object>> response1 = contactServiceImpl.addContact(contactDto1, leadId1);

		assertEquals(HttpStatus.CREATED, response1.getStatusCode());
		assertFalse((Boolean) response1.getBody().get(ApiResponse.SUCCESS));
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

        // Mock behavior of findById to return empty optional
        when(contactDaoService.findById(contactId)).thenReturn(Optional.empty());

        // Call the method and expect a ResourceNotFoundException to be thrown
        assertThrows(CRMException.class, () -> {
            contactServiceImpl.updateContact(contactDto, contactId);
        });
    }

    @Test
    void testUpdateContactUnmarkPrimary() {
        // Mock data
        ContactDto contactDto = new ContactDto();
        contactDto.setPrimary(false);
        Integer contactId = 1;

        // Mock existing contact
        Contacts existingContact = new Contacts();
        existingContact.setContactId(contactId);
        existingContact.setPrimary(true);
        existingContact.setLead(new Leads());

        List<Contacts> existingContacts = new ArrayList<>();
        existingContacts.add(existingContact);

        // Mock behavior of findById
        when(contactDaoService.findById(contactId)).thenReturn(Optional.of(existingContact));
        // Mock behavior of contactsOfLead
        when(contactDaoService.contactsOfLead(anyInt())).thenReturn(existingContacts);

        ResponseEntity<EnumMap<ApiResponse, Object>> response = contactServiceImpl.updateContact(contactDto, contactId);

        // Assertions
        assertEquals("Contact Not Updated !!", response.getBody().get(ApiResponse.MESSAGE));
    }

}
