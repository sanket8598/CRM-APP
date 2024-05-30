package ai.rnt.crm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

	@InjectMocks
	private DashboardServiceImpl dashboardServiceImpl;

	@Mock
	private LeadDaoService leadDaoService;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@Mock
	private OpportunityDaoService opportunityDaoService;
	
	@Mock
	private  CallDaoService callDaoService;
	
	@Mock
	private  EmailDaoService emailDaoService;
	
	@Mock
	private  VisitDaoService visitDaoService;
	@Mock
	private  MeetingDaoService meetingDaoService;

	@Test
    void testGetDashboardDataForAdmin() {
        when(auditAwareUtil.isAdmin()).thenReturn(true);
        when(opportunityDaoService.findAllOpty()).thenReturn(mockOpportunityList());
        when(leadDaoService.getAllLeads()).thenReturn(mockLeadList());
        when(leadDaoService.getLeadSourceCount()).thenReturn(mockLeadSourceCount());
        ResponseEntity<EnumMap<ApiResponse, Object>> response = dashboardServiceImpl.getDashboardData();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
        assertNotNull(response.getBody().get(ApiResponse.DATA));
    }

	@Test
	void testGetDashboardDataForUser() {
		Integer mockStaffId = 1;
		when(auditAwareUtil.isUser()).thenReturn(true);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(mockStaffId);
		when(opportunityDaoService.findAllOpty()).thenReturn(mockOpportunityList());
		when(leadDaoService.getAllLeads()).thenReturn(mockLeadList());
		when(leadDaoService.getLeadSourceCount(mockStaffId)).thenReturn(mockLeadSourceCount());
		ResponseEntity<EnumMap<ApiResponse, Object>> response = dashboardServiceImpl.getDashboardData();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
    void testGetDashboardDataException() {
        when(auditAwareUtil.isAdmin()).thenThrow(new RuntimeException("Exception occurred"));
        assertThrows(CRMException.class, () -> {
        	dashboardServiceImpl.getDashboardData();
        });
    }

	private List<Opportunity> mockOpportunityList() {
		Opportunity opty1 = new Opportunity();
		opty1.setCreatedDate(LocalDateTime.now());
		Leads leads = new Leads();
		opty1.setStatus("WON");
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setStaffId(1);
		opty1.setEmployee(employeeMaster);
		List<Contacts> contacts = new ArrayList<>();
		Contacts con = new Contacts();
		con.setFirstName("test");
		con.setLastName("data");
		con.setPrimary(true);
		contacts.add(con);
		leads.setContacts(contacts);
		opty1.setLeads(leads);
		Opportunity opty2 = new Opportunity();
		EmployeeMaster employeeMaster1 = new EmployeeMaster();
		employeeMaster1.setStaffId(1);
		opty2.setEmployee(employeeMaster1);
		opty2.setStatus("LOSS");
		opty2.setCreatedDate(LocalDateTime.now());
		opty2.setLeads(leads);
		return Arrays.asList(opty1, opty2);
	}

	private List<Leads> mockLeadList() {
		Leads lead1 = new Leads();
		lead1.setCreatedDate(LocalDateTime.now());
		List<Contacts> contacts = new ArrayList<>();
		Contacts con = new Contacts();
		con.setFirstName("test");
		con.setLastName("data");
		con.setPrimary(true);
		contacts.add(con);
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setStaffId(1);
		lead1.setEmployee(employeeMaster);
		lead1.setContacts(contacts);
		Leads lead2 = new Leads();
		List<Contacts> contacts1 = new ArrayList<>();
		Contacts con1 = new Contacts();
		con1.setFirstName("test");
		con1.setLastName("data");
		con1.setPrimary(true);
		contacts1.add(con1);
		EmployeeMaster employeeMaster1 = new EmployeeMaster();
		employeeMaster1.setStaffId(1);
		lead2.setContacts(contacts1);
		lead2.setEmployee(employeeMaster1);
		lead2.setCreatedDate(LocalDateTime.now());
		return Arrays.asList(lead1, lead2);
	}

	private List<Map<String, Integer>> mockLeadSourceCount() {
		Map<String, Integer> leadSource = new HashMap<>();
		leadSource.put("Source1", 10);
		leadSource.put("Source2", 5);
		return Collections.singletonList(leadSource);
	}
	
	@Test
	void testGetUpComingSectionData() {
		String field = "LEAD";
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(auditAwareUtil.isAdmin()).thenReturn(true);
		List<Call> calls = new ArrayList<>();
		List<Email> emails = new ArrayList<>();
		List<Visit> visits = new ArrayList<>();
		List<Meetings> mettings = new ArrayList<>();
		when(callDaoService.getAllLeadCalls(anyBoolean())).thenReturn(calls);
		when(visitDaoService.getAllLeadVisits(anyBoolean())).thenReturn(visits);
		when(emailDaoService.getAllLeadEmails(anyBoolean())).thenReturn(emails);
		when(meetingDaoService.getAllLeasMeetings(anyBoolean())).thenReturn(mettings);

		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = dashboardServiceImpl
				.getUpComingSectionData(field);
		assertEquals(200, responseEntity.getStatusCodeValue()); // Check if status code is OK
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(true, responseBody.get(ApiResponse.SUCCESS)); // Check if success flag is true
	}
	@Test
	void testGetUpComingSectionDataForUser() {
		String field = "Opportunity";
		String emailId="abc@email.com";
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(auditAwareUtil.isUser()).thenReturn(true);
		Call call=new Call();
		Visit visit=new Visit();
		EmployeeMaster emp=new EmployeeMaster();
		emp.setStaffId(1);
		emp.setEmailId(emailId);
		call.setCallFrom(emp);
		visit.setVisitBy(emp);
		List<Call> calls = new ArrayList<>();
		calls.add(call);
		Email email=new Email();
		email.setMailFrom(emailId);
		List<Email> emails = new ArrayList<>();
		emails.add(email);
		List<Visit> visits = new ArrayList<>();
		visits.add(visit);
		Meetings meet=new Meetings();
		meet.setAssignTo(emp);
		List<Meetings> mettings = new ArrayList<>();
		mettings.add(meet);
		when(callDaoService.getAllLeadCalls(anyBoolean())).thenReturn(calls);
		when(visitDaoService.getAllLeadVisits(anyBoolean())).thenReturn(visits);
		when(emailDaoService.getAllLeadEmails(anyBoolean())).thenReturn(emails);
		when(meetingDaoService.getAllLeasMeetings(anyBoolean())).thenReturn(mettings);
		when(employeeService.getById(anyInt())).thenReturn(Optional.of(emp));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = dashboardServiceImpl
				.getUpComingSectionData(field);
		assertEquals(200, responseEntity.getStatusCodeValue()); // Check if status code is OK
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(true, responseBody.get(ApiResponse.SUCCESS)); // Check if success flag is true
	}
	@Test
	void testGetUpComingSectionDataForNullField() {
		String field =null;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = dashboardServiceImpl
				.getUpComingSectionData(field);
		assertEquals(200, responseEntity.getStatusCodeValue()); 
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(false, responseBody.get(ApiResponse.SUCCESS)); 
	}
	@Test
	void testGetUpComingSectionDataForEmptyField() {
		String field ="";
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = dashboardServiceImpl
				.getUpComingSectionData(field);
		assertEquals(200, responseEntity.getStatusCodeValue()); 
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(false, responseBody.get(ApiResponse.SUCCESS)); 
	}
	@Test
	void testGetUpComingSectionDataForInvalidField() {
		String field ="abc";
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(null);
		when(auditAwareUtil.isUser()).thenReturn(true);
		when(auditAwareUtil.isAdmin()).thenReturn(false);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = dashboardServiceImpl
				.getUpComingSectionData(field);
		assertEquals(200, responseEntity.getStatusCodeValue()); 
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(true, responseBody.get(ApiResponse.SUCCESS)); 
	}
	@Test
	void testGetUpComingSectionDataForException() {
		String field ="notEmpty";
		when(auditAwareUtil.isUser()).thenReturn(true);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(employeeService.getById(anyInt())).thenThrow(new ResourceNotFoundException("Employee", "staffId", 1));
		assertThrows(CRMException.class,()->dashboardServiceImpl.getUpComingSectionData(field));
	}
	
	
	
}
