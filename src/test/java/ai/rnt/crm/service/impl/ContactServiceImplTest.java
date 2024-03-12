package ai.rnt.crm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.util.ContactUtil;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {

	@Mock
	private ContactDaoService contactDaoService;
	
	@Mock
	private ContactUtil contactUtil;

	@Mock
	private LeadDaoService leadDaoService;

	@InjectMocks
	private ContactServiceImpl contactServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	//@Test
	void addContact_Successful() {
	    LeadDaoService leadDaoService = mock(LeadDaoService.class);
	    ContactDaoService contactDaoService = mock(ContactDaoService.class);
	    Leads lead = new Leads();
	    CompanyMaster master = new CompanyMaster();
	    ContactDto contactDto = new ContactDto();
	    List<Contacts> existingContacts = new ArrayList<>();
	    Contacts existingPrimaryContact = new Contacts();
	    contactDto.setName("John Doe");
	    Integer leadId = 1;
	    contactDto.setPrimary(true);
	    master.setCompanyId(1);
	    existingPrimaryContact.setCompanyMaster(master);
	    lead.setLeadId(1);
	    existingPrimaryContact.setLead(lead);
	    existingPrimaryContact.setPrimary(true);
	    existingContacts.add(existingPrimaryContact);
	    when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.of(new Leads()));
	    when(contactUtil.isDuplicateContact(existingContacts,existingPrimaryContact)).thenReturn(false);
	    when(contactDaoService.contactsOfLead(leadId)).thenReturn(existingContacts);
	    ResponseEntity<EnumMap<ApiResponse, Object>> response = contactServiceImpl.addContact(contactDto, leadId);
	    assertNotNull(response);
	    assertEquals(HttpStatus.CREATED, response.getStatusCode());
	    assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
	    assertEquals("Contact Added Successfully !!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void getContactExceptionTest() {
		assertThrows(CRMException.class, () -> contactServiceImpl.getContact(1));
	}
}
