package ai.rnt.crm.service.impl;

import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.dto.EditContactDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

	@Mock
	private ContactDaoService contactDaoService;

	@Mock
	private LeadDaoService leadDaoService;

	@Mock
	private CallDaoService callDaoService;

	@Mock
	private VisitDaoService visitDaoService;

	@Mock
	private EmailDaoService emailDaoService;

	@Mock
	private MeetingDaoService meetingDaoService;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private OpportunityDaoService opportunityDaoService;

	@InjectMocks
	private CustomerServiceImpl customerServiceImpl;

	@Test
	void testCustomerDashBoardDataSuccess() {
		Contacts contacts1 = mock(Contacts.class);
		Contacts contacts2 = mock(Contacts.class);
		when(contactDaoService.findAllPrimaryContacts()).thenReturn(asList(contacts1, contacts2));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = customerServiceImpl.customerDashBoardData();
		assertNotNull(response);
		assertEquals(OK, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(SUCCESS));
	}

	@Test
	void testCustomerDashBoardDataException() {
			when(customerServiceImpl.customerDashBoardData()).thenThrow(new RuntimeException("Test Exception"));
			assertThrows(CRMException.class, () -> customerServiceImpl.customerDashBoardData());
		}

	@Test
	void testEditCustomerWithValidFieldLead() {
		Integer customerId = 1;
		Integer optyId = 1;
		Integer leadId = 1;
		String field = "lead";
		String opty = "Opportunity";
		Opportunity opportunity = new Opportunity();
		opportunity.setOpportunityId(optyId);
		Contacts contact = new Contacts();
		contact.setPrimary(true);
		Leads lead = new Leads();
		lead.setLeadId(leadId);
		lead.setOpportunity(opportunity);
		opportunity.setLeads(lead);
		EditContactDto dto = new EditContactDto();

		ContactDto contactDto = new ContactDto();
		contact.setLead(new Leads());
		contactDto.setPrimary(true);
		dto.setPrimaryContact(contactDto);
		List<Contacts> contacts = new ArrayList<>();
		Contacts contact1 = new Contacts();
		contact1.setPrimary(true);
		contact1.setContactId(customerId);
		contacts.add(contact1);
		lead.setContacts(contacts);
		when(contactDaoService.findById(customerId)).thenReturn(Optional.of(contact));
		when(leadDaoService.getLeadById(any())).thenReturn(Optional.of(lead));
		when(opportunityDaoService.findOpportunity(any())).thenReturn(Optional.of(opportunity));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = customerServiceImpl.editCustomer(field, customerId);
		ResponseEntity<EnumMap<ApiResponse, Object>> response1 = customerServiceImpl.editCustomer(opty, customerId);
		assertNotNull(response);
		assertEquals(OK, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response1);
		assertEquals(OK, response1.getStatusCode());
		assertTrue((Boolean) response1.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	void testEditCustomerWithNullField() {
		Integer customerId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> response = customerServiceImpl.editCustomer(null, customerId);
		assertNotNull(response);
		assertEquals(OK, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	void testEditCustomerThrowsException() {
		Integer customerId = 1;
		String field = "lead";
		when(contactDaoService.findById(customerId)).thenThrow(new RuntimeException("Error"));
		assertThrows(CRMException.class, () -> customerServiceImpl.editCustomer(field, customerId));
	}
}
