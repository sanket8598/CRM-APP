package ai.rnt.crm.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.constants.OppurtunityStatus;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.DomainMasterDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadSortFilterDaoService;
import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.RoleMasterDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.LeadSortFilterDto;
import ai.rnt.crm.entity.DomainMaster;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.LeadImportant;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;
import ai.rnt.crm.util.ReadExcelUtil;

class LeadServiceImplTest {

	@Mock
	private LeadDaoService leadDaoService;

	@Mock
	private ServiceFallsDaoSevice serviceFallsDaoSevice;

	@Mock
	private LeadSortFilterDaoService leadSortFilterDaoService;

	@Mock
	private LeadSourceDaoService leadSourceDaoService;

	@Mock
	private CompanyMasterDaoService companyMasterDaoService;

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@Mock
	private OpportunityDaoService opportunityDaoService;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private ReadExcelUtil readExcelUtil;

	@Mock
	private RoleMasterDaoService roleMasterDaoService;

	@Mock
	private ContactDaoService contactDaoService;

	@Mock
	private DomainMasterDaoService domainMasterDaoService;

	// Mock other dependencies

	@InjectMocks
	private LeadServiceImpl leadService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllLeadSourceSuccess() {
		List<LeadSourceMaster> leadSources = new ArrayList<>();
		when(leadSourceDaoService.getAllLeadSource()).thenReturn(leadSources);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.getAllLeadSource();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	    void testGetAllLeadSourceException() {
	        when(leadSourceDaoService.getAllLeadSource()).thenThrow();
	        assertThrows(CRMException.class, () -> leadService.getAllLeadSource());
	    }

	@Test
	void testGetAllDropDownData_Success() {
		List<ServiceFallsMaster> serviceFalls = new ArrayList<>();
		List<LeadSourceMaster> leadSources = new ArrayList<>();
		List<DomainMaster> domains = new ArrayList<>();
		List<EmployeeMaster> employees = new ArrayList<>();
		when(serviceFallsDaoSevice.getAllSerciveFalls()).thenReturn(serviceFalls);
		when(leadSourceDaoService.getAllLeadSource()).thenReturn(leadSources);
		when(domainMasterDaoService.getAllDomains()).thenReturn(domains);
		when(roleMasterDaoService.getAdminAndUser()).thenReturn(employees);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.getAllDropDownData();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	    void testGetAllDropDownData_Exception() {
	        when(serviceFallsDaoSevice.getAllSerciveFalls()).thenThrow(/* specify the exception */);
	        assertThrows(CRMException.class, () -> leadService.getAllDropDownData());
	    }

	@Test
	void testGetLeadDashboardData_Success() {
		List<Leads> dashboardData = new ArrayList<>();
		when(leadDaoService.getLeadDashboardData()).thenReturn(dashboardData);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.getLeadDashboardData();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	    void testGetLeadDashboardData_Exception() {
	        when(leadDaoService.getLeadDashboardData()).thenThrow();
	        assertThrows(CRMException.class, () -> leadService.getLeadDashboardData());
	    }

	@Test
	void testGetLeadDashboardDataByStatus_Admin_All() {
		String leadsStatus = "ALL";
		when(auditAwareUtil.isAdmin()).thenReturn(true);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.getLeadDashboardDataByStatus(leadsStatus);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetLeadDashboardDataByStatus_User_All() {
		String leadsStatus = "ALL";
		when(auditAwareUtil.isUser()).thenReturn(true);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.getLeadDashboardDataByStatus(leadsStatus);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetLeadDashboardDataByStatus_Admin_Open() {
		String leadsStatus = "Open";
		when(auditAwareUtil.isAdmin()).thenReturn(true);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.getLeadDashboardDataByStatus(leadsStatus);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetLeadDashboardDataByStatus_User_Open() {
		String leadsStatus = "Open";
		when(auditAwareUtil.isUser()).thenReturn(true);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.getLeadDashboardDataByStatus(leadsStatus);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetLeadDashboardDataByStatusUserOpenElse() {
		String leadsStatus = "Open";
		when(auditAwareUtil.isUser()).thenReturn(false);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.getLeadDashboardDataByStatus(leadsStatus);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetLeadDashboardDataByStatus_Exception() {
		String leadsStatus = "ALL";
		when(auditAwareUtil.isAdmin()).thenReturn(true);
		when(leadDaoService.getLeadDashboardData()).thenThrow();
		assertThrows(CRMException.class, () -> leadService.getLeadDashboardDataByStatus(leadsStatus));
	}

	@Test
	void testDisQualifyLead_Success() {
		Integer leadId = 1;
		LeadDto dto = new LeadDto();
		dto.setDisqualifyAs("Reason");
		dto.setDisqualifyReason("Reason");
		Leads lead = new Leads();
		lead.setLeadId(leadId);
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.of(lead));
		when(leadDaoService.addLead(lead)).thenReturn(new Leads());
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.disQualifyLead(leadId, dto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertTrue(response.getBody().containsKey(ApiResponse.MESSAGE));
		assertEquals("Lead Disqualified SuccessFully", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testDisQualifyLead_Failure() {
		Integer leadId = 1;
		LeadDto dto = new LeadDto();
		dto.setDisqualifyAs("Reason");
		dto.setDisqualifyReason("Reason");
		Leads lead = new Leads();
		lead.setLeadId(leadId);
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.of(lead));
		when(leadDaoService.addLead(lead)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.disQualifyLead(leadId, dto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue(response.getBody().containsKey(ApiResponse.MESSAGE));
		assertEquals("Lead Not Disqualify", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testDisQualifyLead_LeadNotFound() {
		Integer leadId = 1;
		LeadDto dto = new LeadDto();
		dto.setDisqualifyAs("Reason");
		dto.setDisqualifyReason("Reason");
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> leadService.disQualifyLead(leadId, dto));
	}

	@Test
	void testImportantLead_MarkAsImportant() {
		Integer leadId = 1;
		boolean status = true;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.of(new Leads()));
		when(employeeService.getById(anyInt())).thenReturn(Optional.of(new EmployeeMaster()));
		when(leadDaoService.addImportantLead(any())).thenReturn(Optional.of(new LeadImportant()));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.importantLead(leadId, status);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertTrue(response.getBody().containsKey(ApiResponse.MESSAGE));
		assertEquals("Lead marked as Important !!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testImportantLead_MarkAsUnimportant() {
		Integer leadId = 1;
		boolean status = false;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(leadDaoService.deleteImportantLead(leadId, 1)).thenReturn(true);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.importantLead(leadId, status);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertTrue(response.getBody().containsKey(ApiResponse.MESSAGE));
		assertEquals("Lead marked as Unimportant !!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testImportantLeadException() {
		Integer leadId = 1;
		boolean status = true;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.of(new Leads()));
		when(employeeService.getById(anyInt())).thenReturn(Optional.of(new EmployeeMaster()));
		when(leadDaoService.addImportantLead(any())).thenThrow(new DataIntegrityViolationException("Violation"));
		assertNotNull(leadService.importantLead(leadId, status));
	}

	@Test
	void testReactiveLeadSuccessfulReactivation() {
		Integer leadId = 1;
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.of(new Leads()));
		when(leadDaoService.addLead(any(Leads.class))).thenReturn(new Leads());
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.reactiveLead(leadId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertTrue(response.getBody().containsKey(ApiResponse.MESSAGE));
		assertEquals("Lead Reactivate SuccessFully", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testReactiveLead_LeadNotFound() {
		Integer leadId = 1;
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> leadService.reactiveLead(leadId));
	}

	@Test
	void testReactiveLeadException() {
		Integer leadId = 1;
		when(leadDaoService.getLeadById(leadId)).thenThrow(new RuntimeException("Some error occurred"));
		assertThrows(CRMException.class, () -> leadService.reactiveLead(leadId));
	}

	@Test
	void testReactiveLeadUnsuccessful() {
		Integer leadId = 1;
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.of(new Leads()));
		when(leadDaoService.addLead(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.reactiveLead(leadId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertTrue(response.getBody().containsKey(ApiResponse.MESSAGE));
		assertEquals("Lead Not Reactivate", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testAddSortFilterForLeads_Successful() throws Exception {
		LeadSortFilterDto sortFilter = new LeadSortFilterDto();
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(employeeService.getById(anyInt())).thenReturn(Optional.of(new EmployeeMaster()));
		Optional<LeadSortFilterDto> optionalSortFilter = Optional.of(new LeadSortFilterDto());
		when(leadSortFilterDaoService.save(any())).thenReturn(optionalSortFilter);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.addSortFilterForLeads(sortFilter);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertTrue(response.getBody().containsKey(ApiResponse.MESSAGE));
		assertEquals("Lead Sort Filter Added Successfully!!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testAddSortFilterForLeads_SaveNull() throws Exception {
		LeadSortFilterDto sortFilter = new LeadSortFilterDto();
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(employeeService.getById(anyInt())).thenReturn(Optional.of(new EmployeeMaster()));
		when(leadSortFilterDaoService.save(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.addSortFilterForLeads(sortFilter);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertTrue(response.getBody().containsKey(ApiResponse.MESSAGE));
		assertEquals("Lead Sort Filter Not Added!!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testAddSortFilterForLeads_Exception() throws Exception {
		LeadSortFilterDto sortFilter = new LeadSortFilterDto();
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(employeeService.getById(1)).thenReturn(Optional.of(new EmployeeMaster()));
		when(leadSortFilterDaoService.save(any())).thenThrow(new RuntimeException("Database connection failed"));
		Exception exception = assertThrows(CRMException.class, () -> leadService.addSortFilterForLeads(sortFilter));
		assertEquals("java.lang.RuntimeException: Database connection failed", exception.getMessage());
	}

	@Test
	void testGetForQualifyLead_LeadFound() {
		Integer leadId = 1;
		Leads lead = new Leads();
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.of(lead));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.getForQualifyLead(leadId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertTrue(response.getBody().containsKey(ApiResponse.DATA));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetForQualifyLead_LeadNotFound() {
		Integer leadId = 1;
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> leadService.getForQualifyLead(leadId));
	}

	@Test
	void testGetForQualifyLead_Exception() {
		Integer leadId = 1;
		when(leadDaoService.getLeadById(leadId)).thenThrow(new RuntimeException("Database error"));
		assertThrows(CRMException.class, () -> leadService.getForQualifyLead(leadId));
	}

	@Test
	void testSetAssignToNameForTheLead_WithLastName() {
		Leads leads = new Leads();
		String[] split = { "John", "Doe" };
		EmployeeMaster employeeMaster = new EmployeeMaster();
		when(employeeService.findByName("John", "Doe")).thenReturn(Optional.of(employeeMaster));
		leadService.setAssignToNameForTheLead(leads, split);
		assertEquals(employeeMaster, leads.getEmployee());
	}

	@Test
	void testSetAssignToNameForTheLead_WithoutLastName() {
		Leads leads = new Leads();
		String[] split = { "John" };
		EmployeeMaster employeeMaster = new EmployeeMaster();
		when(employeeService.findByName("John", null)).thenReturn(Optional.of(employeeMaster));
		leadService.setAssignToNameForTheLead(leads, split);
		assertEquals(employeeMaster, leads.getEmployee());
	}

	@Test
	void testSetAssignToNameForTheLead_EmployeeNotFound() {
		Leads leads = new Leads();
		String[] split = { "John" };
		when(employeeService.findByName("John", null)).thenReturn(Optional.empty());
		leadService.setAssignToNameForTheLead(leads, split);
		assertNull(leads.getEmployee());
	}

	@Test
	void testUpdateLeadsStatus_LeadExists() {
		Integer leadId = 1;
		Leads lead = new Leads();
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.of(lead));
		assertDoesNotThrow(() -> leadService.updateLeadsStatus(leadId));
		assertEquals("Qualified", lead.getDisqualifyAs());
		verify(leadDaoService, times(1)).addLead(lead);
	}

	@Test
	void testUpdateLeadsStatus_LeadNotFound() {
		Integer leadId = 1;
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> leadService.updateLeadsStatus(leadId));
	}

	@Test
	void testAddToOpportunity_Success() {
		Leads leads = new Leads();
		leads.setBudgetAmount("1000.0");
		leads.setCustomerNeed("Test Customer Need");
		leads.setProposedSolution("Test Proposed Solution");
		leads.setTopic("Test Topic");
		leads.setPseudoName("Test Pseudo Name");
		EmployeeMaster employee = new EmployeeMaster();
		leads.setEmployee(employee);
		Opportunity opportunity = new Opportunity();
		opportunity.setStatus("Open");
		opportunity.setBudgetAmount("1000.0");
		opportunity.setCustomerNeed("Test Customer Need");
		opportunity.setProposedSolution("Test Proposed Solution");
		opportunity.setTopic("Test Topic");
		opportunity.setPseudoName("Test Pseudo Name");
		opportunity.setEmployee(employee);
		when(opportunityDaoService.addOpportunity(any())).thenReturn(opportunity);
		boolean result = leadService.addToOpputunity(leads);
		assertTrue(result);
		assertEquals(OppurtunityStatus.OPEN, opportunity.getStatus());
		assertEquals(leads.getBudgetAmount(), opportunity.getBudgetAmount());
		assertEquals(leads.getCustomerNeed(), opportunity.getCustomerNeed());
		assertEquals(leads.getProposedSolution(), opportunity.getProposedSolution());
		assertEquals(leads.getTopic(), opportunity.getTopic());
		assertEquals(leads.getPseudoName(), opportunity.getPseudoName());
		assertEquals(employee, opportunity.getEmployee());
	}

	@Test
	void testAddToOpportunity_Failure() {
		Leads leads = new Leads();
		when(opportunityDaoService.addOpportunity(any())).thenReturn(null);
		boolean result = leadService.addToOpputunity(leads);
		assertFalse(result);
	}
}