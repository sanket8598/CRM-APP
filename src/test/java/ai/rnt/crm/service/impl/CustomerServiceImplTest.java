package ai.rnt.crm.service.impl;

import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.EnumMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

	@Mock
	private ContactDaoService contactDaoService;

	@InjectMocks
	private CustomerServiceImpl customerServiceImpl;

	@Test
	void testCustomerDashBoardDataSuccess() {
		Contacts contacts1 = mock(Contacts.class);
		Contacts contacts2 = mock(Contacts.class);
		when(contactDaoService.findAllPrimaryContacts()).thenReturn(asList(contacts1, contacts2));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = customerServiceImpl.customerDashBoardData();
		assertNotNull(response);
		assertEquals(CREATED, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(SUCCESS));
	}

	@Test
	void testCustomerDashBoardDataException() {
			when(customerServiceImpl.customerDashBoardData()).thenThrow(new RuntimeException("Test Exception"));
			assertThrows(CRMException.class, () -> customerServiceImpl.customerDashBoardData());
		}
}
