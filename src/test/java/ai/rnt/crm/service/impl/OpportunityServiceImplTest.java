package ai.rnt.crm.service.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dto.opportunity.QualifyOpportunityDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;

class OpportunityServiceImplTest {

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private ContactDaoService contactDaoService;

	@Mock
	private OpportunityDaoService opportunityDaoService;

	@InjectMocks
	private OpportunityServiceImpl opportunityServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetDashBoardData_Success() {
		List<Opportunity> dashboardData = new ArrayList<>();
		when(opportunityDaoService.getOpportunityDashboardData()).thenReturn(dashboardData);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityServiceImpl.getDashBoardData(1477);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetDashBoardDataAdmin() {
		List<Opportunity> dashboardData = new ArrayList<>();
		when(auditAwareUtil.isAdmin()).thenReturn(true);
		when(opportunityDaoService.getOpportunityDashboardData()).thenReturn(dashboardData);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityServiceImpl.getDashBoardData(null);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetDashBoardDataUser() {
		List<Opportunity> dashboardData = new ArrayList<>();
		when(auditAwareUtil.isUser()).thenReturn(true);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1477);
		when(opportunityDaoService.getOpportunityDashboardData()).thenReturn(dashboardData);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityServiceImpl.getDashBoardData(1477);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	    void testGetDashBoardData_Exception() {
	        when(opportunityDaoService.getOpportunityDashboardData()).thenThrow();
	        assertThrows(CRMException.class, () -> opportunityServiceImpl.getDashBoardData(1477));
	    }

	@Test
	void getQualifyPopUpDataTest() {
		Opportunity opportunity = new Opportunity();
		Leads leads = new Leads();
		Contacts primaryContact = new Contacts();
		List<Contacts> contacts = new ArrayList<>();
		primaryContact.setPrimary(true);
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setStaffId(1477);
		opportunity.setEmployee(employeeMaster);
		LeadSourceMaster leadSourceMaster = new LeadSourceMaster();
		leadSourceMaster.setLeadSourceId(1);
		contacts.add(primaryContact);
		leads.setContacts(contacts);
		leads.setLeadSourceMaster(leadSourceMaster);
		opportunity.setLeads(leads);
		when(opportunityDaoService.findOpportunity(anyInt())).thenReturn(Optional.of(opportunity));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityServiceImpl.getQualifyPopUpData(1);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	     void getQualifyPopUpDataExceptionTest() {
	        when(opportunityDaoService.findOpportunity(anyInt())).thenReturn(Optional.empty());
	        assertThrows(CRMException.class, () -> opportunityServiceImpl.getQualifyPopUpData(1));
	    }
}
