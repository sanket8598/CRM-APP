package ai.rnt.crm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {

	@Mock
	private ContactDaoService contactDaoService;

	@Mock
	private LeadDaoService leadDaoService;

	@InjectMocks
	private ContactServiceImpl contactServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// @Test
	void testAddContact_Success() {
		// Arrange
		Contacts contacts = new Contacts();
		ContactDto contactDto = new ContactDto();
		contactDto.setPrimary(true);
		contactDto.setFirstName("FirstName");
		contactDto.setLastName(" LastName");
		Integer leadId = 1;
		when(contactDaoService.contactsOfLead(leadId)).thenReturn(Collections.emptyList());
		when(contactDaoService.addContact(any(Contacts.class))).thenReturn(contacts);
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.empty());
		ResponseEntity<EnumMap<ApiResponse, Object>> response = contactServiceImpl.addContact(contactDto, leadId);
		assertNotNull(response);
		assertEquals(201, response.getStatusCodeValue());
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Added Successfully !!", response.getBody().get(ApiResponse.MESSAGE));
	}

	//@Test
	void testGetContact_WhenContactExists_ReturnsResponseEntityWithContactData() {
		Contacts contact = new Contacts();
		int contactId = 5;
		when(contactDaoService.findById(contactId)).thenReturn(Optional.of(contact));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = contactServiceImpl.getContact(contactId);
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	//@Test
	void testGetContact_WhenContactDoesNotExist_ReturnsResourceNotFoundException() {
		int contactId = 1;
		when(contactDaoService.findById(contactId)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> contactServiceImpl.getContact(contactId));
	}
}
