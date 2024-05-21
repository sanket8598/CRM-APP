package ai.rnt.crm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
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
		opty1.setStatus("WON");
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setStaffId(1);
		opty1.setEmployee(employeeMaster);
		Opportunity opty2 = new Opportunity();
		EmployeeMaster employeeMaster1 = new EmployeeMaster();
		employeeMaster1.setStaffId(1);
		opty2.setEmployee(employeeMaster1);
		opty2.setStatus("LOSS");
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
}
