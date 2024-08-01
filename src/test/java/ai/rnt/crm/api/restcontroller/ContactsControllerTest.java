package ai.rnt.crm.api.restcontroller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.ContactService;

class ContactsControllerTest {

	@Mock
	private ContactService contactService;

	@InjectMocks
	private ContactsController contactsController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void createContactTest() {
		ContactDto contactDto = new ContactDto();
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(contactService.addContact(contactDto, 123)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = contactsController.createContact(contactDto, 123);
		verify(contactService).addContact(contactDto, 123);
	}

	@Test
	void findContactTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(contactService.getContact(1)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = contactsController.findContact(1);
		verify(contactService).getContact(1);
	}

	@Test
	void updateContactTest() {
		ContactDto contactDto = new ContactDto();
		contactDto.setName("John Doe");
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(contactService.updateContact(contactDto, 1)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = contactsController.updateContact(contactDto, 1);
		verify(contactService).updateContact(contactDto, 1);
	}
	
	 @Test
	    void testDeleteContact() {
	        Integer contactId = 1;
	        EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
	        expectedResponse.put(ApiResponse.SUCCESS, "Contact deleted successfully");
	        ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);

	        when(contactService.deleteContact(contactId)).thenReturn(responseEntity);

	        ResponseEntity<EnumMap<ApiResponse, Object>> actualResponse = contactsController.deleteContact(contactId);

	        assertEquals(responseEntity, actualResponse);
	        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
	        assertEquals(expectedResponse, actualResponse.getBody());
	        verify(contactService, times(1)).deleteContact(contactId);
	    }
}
