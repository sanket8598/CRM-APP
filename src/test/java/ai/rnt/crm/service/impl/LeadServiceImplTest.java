package ai.rnt.crm.service.impl;

import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.constants.OppurtunityStatus;
import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.DomainMasterDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.ExcelHeaderDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadSortFilterDaoService;
import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.RoleMasterDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.dto.DescriptionDto;
import ai.rnt.crm.dto.EditCallDto;
import ai.rnt.crm.dto.EditEmailDto;
import ai.rnt.crm.dto.EditLeadDto;
import ai.rnt.crm.dto.EditVisitDto;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.LeadSortFilterDto;
import ai.rnt.crm.dto.LeadSourceDto;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.dto.ServiceFallsDto;
import ai.rnt.crm.dto.TimeLineActivityDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.Description;
import ai.rnt.crm.entity.DomainMaster;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.ExcelHeaderMaster;
import ai.rnt.crm.entity.LeadImportant;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;
import ai.rnt.crm.util.EmailUtil;
import ai.rnt.crm.util.ReadExcelUtil;
import ai.rnt.crm.util.SignatureUtil;
import ai.rnt.crm.util.TaskNotificationsUtil;

class LeadServiceImplTest {

	@Mock
	private LeadDaoService leadDaoService;

	@Mock
	private ServiceFallsDaoSevice serviceFallsDaoSevice;

	@Mock
	private CountryDaoService countryDaoService;

	@Mock
	private CallDaoService callDaoService;

	@Mock
	private VisitDaoService visitDaoService;

	@Mock
	private EmailDaoService emailDaoService;

	@Mock
	private MeetingDaoService meetingDaoService;

	@Mock
	private LeadSortFilterDaoService leadSortFilterDaoService;

	@Mock
	private TaskNotificationsUtil taskNotificationsUtil;

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
	private ExcelHeaderDaoService excelHeaderDaoService;

	@Mock
	private CityDaoService cityDaoService;

	@Mock
	private Logger log;

	@Mock
	private RoleMasterDaoService roleMasterDaoService;

	@Mock
	private ContactDaoService contactDaoService;

	@Mock
	private StateDaoService stateDaoService;

	@Mock
	private EmailUtil emailUtil;

	@Mock
	private DomainMasterDaoService domainMasterDaoService;

	@Mock
	private LeadDto leadDto;

	@Mock
	private DescriptionDto descriptionDto;

	@Mock
	private QualifyLeadDto qualifyLeadDto;

	@Mock
	private EditLeadDto editLeadDto;

	@Mock
	private EditVisitDto editVisitDto;

	@Mock
	private EditEmailDto editEmailDto;

	@Mock
	private EditCallDto editCallDto;

	@Mock
	private LeadImportant leadImportant;

	@Mock
	private EmployeeMaster employee;

	@Mock
	private Leads leads;

	@Mock
	Description description;

	@Mock
	private Call call;

	@Mock
	private Visit visit;

	@Mock
	private Email email;

	@Mock
	private Meetings meetings;

	@Mock
	private TimeLineActivityDto timeLineActivityDto;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	private LeadServiceImpl leadService;

	@BeforeEach
	void setUp() {
		leadDaoService = mock(LeadDaoService.class);
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(leadService).build();
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
		assertEquals("Lead Disqualified Successfully", response.getBody().get(ApiResponse.MESSAGE));
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
		assertEquals("Lead Reactivate Successfully", response.getBody().get(ApiResponse.MESSAGE));
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
	void testAddToOpportunitySuccess() {
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
		leads.setCustomerNeed("Test Customer Need");
		leads.setProposedSolution("Test Proposed Solution");
		opportunity.setTopic("Test Topic");
		opportunity.setPseudoName("Test Pseudo Name");
		opportunity.setEmployee(employee);
		opportunity.setLeads(leads);
		when(opportunityDaoService.addOpportunity(any())).thenReturn(opportunity);
		opportunity = leadService.addToOpputunity(leads);
		assertNotNull(opportunity);
		assertEquals(OppurtunityStatus.OPEN, opportunity.getStatus());
		assertEquals(leads.getBudgetAmount(), opportunity.getBudgetAmount());
		assertEquals(leads.getTopic(), opportunity.getTopic());
		assertEquals(leads.getPseudoName(), opportunity.getPseudoName());
		assertEquals(employee, opportunity.getEmployee());
	}

	@Test
	void testAddToOpportunityFailure() {
		Leads leads = new Leads();
		when(opportunityDaoService.addOpportunity(any())).thenReturn(null);
		Opportunity opportunity = leadService.addToOpputunity(leads);
		assertNull(opportunity);
	}

	@Test
	void createLeadUnSuccess() {
		LeadDto dto = mock(LeadDto.class);
		dto.setAssignTo(1);
		dto.setFirstName("sanket");
		Integer staffId = 1;
		when(auditAwareUtil.getLoggedInUserName()).thenReturn("username");
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(staffId);
		when(employeeService.getById(staffId)).thenReturn(Optional.of(employee));
		CountryMaster country = new CountryMaster();
		country.setCountry("india");
		when(countryDaoService.findByCountryName("india")).thenReturn(Optional.of(country));
		CompanyMaster company = mock(CompanyMaster.class);
		CompanyDto companyDto = mock(CompanyDto.class);
		companyDto.setCompanyName("rnt.ai");
		companyDto.setCompanyWebsite("wwwwwwwww");
		when(companyMasterDaoService.save(company)).thenReturn(Optional.of(companyDto));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.createLead(dto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Lead Not Added !!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void assignLeadSuccess() {
		Map<String, Integer> map = new HashMap<>();
		map.put("leadId", 1);
		map.put("staffId", 1);
		Leads lead = new Leads();
		EmployeeMaster employee = new EmployeeMaster();
		when(leadDaoService.getLeadById(1)).thenReturn(Optional.of(lead));
		when(employeeService.getById(1)).thenReturn(Optional.of(employee));
		when(leadDaoService.addLead(any(Leads.class))).thenReturn(lead);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.assignLead(map);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Lead Assigned but problem while sending email !!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void assignLead_UnSuccess() {
		Map<String, Integer> map = new HashMap<>();
		map.put("leadId", 1);
		map.put("staffId", 1);
		Leads lead = new Leads();
		EmployeeMaster employee = new EmployeeMaster();
		when(leadDaoService.getLeadById(1)).thenReturn(Optional.of(lead));
		when(employeeService.getById(1)).thenReturn(Optional.of(employee));
		when(leadDaoService.addLead(any(Leads.class))).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.assignLead(map);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void assignLead_Exception() {
		Map<String, Integer> map = new HashMap<>();
		map.put("leadId", 1);
		map.put("staffId", 1);
		when(leadDaoService.getLeadById(1)).thenThrow(new RuntimeException("Database error"));
		assertThrows(CRMException.class, () -> leadService.assignLead(map));
	}

	@Test
	void testUpdateLeadContact_Success() {
		Integer leadId = 1;
		UpdateLeadDto dto = mock(UpdateLeadDto.class);
		Leads lead = mock(Leads.class);
		Contacts contact = mock(Contacts.class);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		result.put(MESSAGE, "Lead Details Updated Successfully !!");
		result.put(SUCCESS, true);
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.of(lead));
		when(lead.getContacts()).thenReturn(Arrays.asList(contact));
		when(contact.getPrimary()).thenReturn(true);
		when(contact.getPrimary()).thenReturn(true);
		when(cityDaoService.existCityByName("dhjsd")).thenReturn(Optional.of(mock(CityMaster.class)));
		when(stateDaoService.findBystate("dfs")).thenReturn(Optional.of(mock(StateMaster.class)));
		when(countryDaoService.findByCountryName("sfgdfsd")).thenReturn(Optional.of(mock(CountryMaster.class)));
		when(companyMasterDaoService.findByCompanyName("fsdfsdds")).thenReturn(Optional.of(mock(CompanyDto.class)));
		when(countryDaoService.addCountry(any(CountryMaster.class))).thenReturn(mock(CountryMaster.class));
		when(stateDaoService.addState(any(StateMaster.class))).thenReturn(mock(StateMaster.class));
		when(cityDaoService.addCity(any(CityMaster.class))).thenReturn(mock(CityMaster.class));
		when(companyMasterDaoService.save(any(CompanyMaster.class))).thenReturn(Optional.of(mock(CompanyDto.class)));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = leadService.updateLeadContact(leadId, dto);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	@Test
	void updateLeadTestException() {
		Integer leadId = 1;
		UpdateLeadDto dto = mock(UpdateLeadDto.class);
		when(leadDaoService.getLeadById(leadId)).thenThrow(new RuntimeException("Database error"));
		assertThrows(CRMException.class, () -> leadService.updateLeadContact(leadId, dto));
	}

	// @Test
	void testUploadExcelSuccessful() throws Exception {
		InputStream stream = mock(InputStream.class);
		MockMultipartFile file = new MockMultipartFile("file", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[1]);
		when(file.getInputStream()).thenReturn(stream);
		Map<String, Object> mockExcelData = new HashMap<>();
		mockExcelData.put("FLAG", true);
		mockExcelData.put("LEAD_DATA", Arrays.asList(leadDto));
		when(readExcelUtil.readExcelFile(any(), any())).thenReturn(mockExcelData);
		when(contactDaoService.addContact(any())).thenReturn(new Contacts());
		when(leadDaoService.getAllLeads()).thenReturn(Arrays.asList());
		when(auditAwareUtil.getLoggedInUserName()).thenReturn("Test User");
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.uploadExcel(file);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("1 Leads Added And 0 Duplicate Found!!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testGetLeadsByStatusAdminNullStatus() {
		String leadsStatus = null;
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		expectedResponse.put(ApiResponse.SUCCESS, true);
		when(auditAwareUtil.isAdmin()).thenReturn(true);
		when(leadDaoService.getAllLeads()).thenReturn(Collections.emptyList());
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.getLeadsByStatus(leadsStatus);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(leadDaoService).getAllLeads();
	}

	@Test
	void testGetLeadsByStatusUserNullStatus() {
		String leadsStatus = null;
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		expectedResponse.put(ApiResponse.SUCCESS, true);
		when(auditAwareUtil.isUser()).thenReturn(true);
		when(leadDaoService.getAllLeads()).thenReturn(Collections.emptyList());
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.getLeadsByStatus(leadsStatus);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(leadDaoService).getAllLeads();
	}

	@Test
	void testGetLeadsByStatusWithStatus() {
		String leadsStatus = "Open";
		List<Leads> leads = Collections.singletonList(new Leads());
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		expectedResponse.put(ApiResponse.SUCCESS, true);
		when(auditAwareUtil.isAdmin()).thenReturn(false);
		when(auditAwareUtil.isUser()).thenReturn(true);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(leadDaoService.getLeadsByStatus(leadsStatus)).thenReturn(leads);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.getLeadsByStatus(leadsStatus);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(leadDaoService).getLeadsByStatus(leadsStatus);
	}

	@Test
	void testGetLeadsByStatusWithStatusException() {
	    when(leadDaoService.getLeadsByStatus("All")).thenThrow(new RuntimeException("Database error"));
	    assertThrows(CRMException.class, () -> leadService.getLeadsByStatus("All"));
	}

	@Test
	void testEditLeadSuccess() throws ParseException {
		Integer leadId = 1;
		Integer contactId = 1;
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		expectedResponse.put(ApiResponse.SUCCESS, true);
		Leads lead = new Leads();
		EditLeadDto dto = new EditLeadDto();
		ContactDto contactDto = new ContactDto();
		contactDto.setPrimary(true);
		dto.setPrimaryContact(contactDto);
		List<Contacts> contact = new ArrayList<>();
		Contacts contact1 = new Contacts();
		contact1.setPrimary(true);
		contact1.setContactId(contactId);
		contact.add(contact1);
		lead.setContacts(contact);
		EmployeeMaster employeeMaster = mock(EmployeeMaster.class);
		employeeMaster.setFirstName("test");
		employeeMaster.setLastName("data");
		lead.setCreatedBy(1375);
		employeeMaster.setStaffId(1375);
		lead.setEmployee(employeeMaster);
		lead.setAssignBy(employeeMaster);
		List<Call> calls = new ArrayList<>();
		List<Email> emails = new ArrayList<>();
		List<Visit> visits = new ArrayList<>();
		List<Meetings> meetings = new ArrayList<>();
		Call call = mock(Call.class);
		Email email = mock(Email.class);
		Visit visit = mock(Visit.class);
		call.setCallId(1);
		visit.setVisitBy(employeeMaster);
		visit.setVisitId(1);
		visits.add(visit);
		calls.add(call);
		emails.add(email);
		Meetings m = mock(Meetings.class);
		m.setMeetingStatus("asa");
		meetings.add(m);
		when(call.getCallTo()).thenReturn("Sanket wakankar");
		when(email.getMailFrom()).thenReturn("s.waknakr@rnt.ai");
		when(call.getCallFrom()).thenReturn(employeeMaster);
		when(visit.getVisitBy()).thenReturn(employeeMaster);
		when(m.getAssignTo()).thenReturn(employeeMaster);
		when(m.getCreatedDate()).thenReturn(LocalDateTime.now());
		when(call.getCreatedDate()).thenReturn(LocalDateTime.now());
		when(visit.getCreatedDate()).thenReturn(LocalDateTime.now());
		when(email.getCreatedDate()).thenReturn(LocalDateTime.now());
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.of(lead));
		when(employeeService.getById(lead.getCreatedBy())).thenReturn(Optional.of(employeeMaster));
		when(callDaoService.getCallsByLeadIdAndIsOpportunity(anyInt())).thenReturn(calls);
		when(visitDaoService.getVisitsByLeadIdAndIsOpportunity(anyInt())).thenReturn(visits);
		when(emailDaoService.getEmailByLeadIdAndIsOpportunity(anyInt())).thenReturn(emails);
		when(meetingDaoService.getMeetingByLeadIdAndIsOpportunity(anyInt())).thenReturn(meetings);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.editLead(leadId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		when(visit.getStatus()).thenReturn("Complete");
		when(call.getStatus()).thenReturn("Complete");
		when(email.getStatus()).thenReturn("Send");
		when(m.getMeetingStatus()).thenReturn("Complete");
		when(m.getUpdatedDate()).thenReturn(LocalDateTime.now());
		when(visit.getUpdatedDate()).thenReturn(LocalDateTime.now());
		when(call.getUpdatedDate()).thenReturn(LocalDateTime.now());
		when(email.getUpdatedDate()).thenReturn(LocalDateTime.now());
		ResponseEntity<EnumMap<ApiResponse, Object>> response1 = leadService.editLead(leadId);
	}

	@Test
	void testEditLeadLeadNotFound() {
		Integer leadId = 1;
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> leadService.editLead(leadId));
	}

	@Test
	void testQualifyLeadUnSuccess() throws Exception {
		Integer leadId = 1;
		QualifyLeadDto dto = new QualifyLeadDto();
		dto.setBudgetAmount("100");
		dto.setSignature("uy1KrsQ4MsRvK1AxhwDwZpxkLTehZGrgHzabis5KAbY=");
		dto.setQualify(true);
		Leads lead = new Leads();
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.of(lead));
		when(leadDaoService.addLead(any())).thenReturn(lead);
		when(serviceFallsDaoSevice.findByName("Other")).thenReturn(Optional.of(new ServiceFallsMaster()));
		when(serviceFallsDaoSevice.getServiceFallById(1)).thenReturn(Optional.of(new ServiceFallsMaster()));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.qualifyLead(leadId, dto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse((boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Lead Not Qualify", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testQualifyLeadLeadNotFound() {
		Integer leadId = 1;
		QualifyLeadDto dto = new QualifyLeadDto();
		when(leadDaoService.getLeadById(leadId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> leadService.qualifyLead(leadId, dto));
	}

	@Test
	void testSetLocationToCompanyLocationNotNull() {
		String location = "Some Location";
		CompanyMaster company = new CompanyMaster();
		when(countryDaoService.findByCountryName(location)).thenReturn(Optional.empty());
		leadService.setLocationToCompany(location, company);
		assertNull(company.getCountry());
		verify(countryDaoService, times(1)).findByCountryName(location);
	}

	@Test
	void testSetLocationToCompanyLocationExists() {
		String location = "Some Location";
		CompanyMaster company = new CompanyMaster();
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountry(location);
		Optional<CountryMaster> countryOptional = Optional.of(countryMaster);
		when(countryDaoService.findByCountryName(location)).thenReturn(countryOptional);
		leadService.setLocationToCompany(location, company);
		assertEquals(countryMaster, company.getCountry());
		verify(countryDaoService, times(1)).findByCountryName(location);
		verify(countryDaoService, never()).addCountry(any());
	}

	@Test
	void testSetLocationToCompanyLocationDoesNotExist() {
		String location = "New Location";
		CompanyMaster company = new CompanyMaster();
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountry(location);
		when(countryDaoService.findByCountryName(location)).thenReturn(Optional.empty());
		when(countryDaoService.addCountry(any())).thenReturn(countryMaster);
		leadService.setLocationToCompany(location, company);
		assertEquals(countryMaster, company.getCountry());
		verify(countryDaoService, times(1)).findByCountryName(location);
		verify(countryDaoService, times(1)).addCountry(any());
	}

	@Test
	void testIsValidExcelAllHeadersExistInExcelAndDB() throws IOException {
		Sheet sheet = mock(Sheet.class);
		List<ExcelHeaderMaster> dbHeaderNames = new ArrayList<>();
		ExcelHeaderMaster excelHeaderMaster = new ExcelHeaderMaster();
		excelHeaderMaster.setHeaderName("Fname");
		dbHeaderNames.add(excelHeaderMaster);
		List<String> excelHeader = Arrays.asList("Name", "Age", "Address");
		when(excelHeaderDaoService.getExcelHeadersFromDB()).thenReturn(dbHeaderNames);
		when(readExcelUtil.readExcelHeaders(sheet)).thenReturn(excelHeader);
		boolean isValid = leadService.isValidExcel(sheet);
		assertFalse(isValid);
	}

	@Test
	void testBuildLeadObjIfSuccess() {
		LeadDto leadDto = new LeadDto();
		leadDto.setCompanyName("Company1");
		leadDto.setServiceFallsId("ServiceFall1");
		leadDto.setLeadSourceId("LeadSource1");
		leadDto.setDomainId("Domain1");
		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(1);
		Leads leads = new Leads();
		Contacts contact = new Contacts();
		when(companyMasterDaoService.findByCompanyName(leadDto.getCompanyName()))
				.thenReturn(Optional.of(new CompanyDto()));
		when(companyMasterDaoService.save(any(CompanyMaster.class))).thenReturn(Optional.of(mock(CompanyDto.class)));
		when(serviceFallsDaoSevice.findByName(leadDto.getServiceFallsId()))
				.thenReturn(Optional.of(new ServiceFallsMaster()));
		when(leadSourceDaoService.getByName(leadDto.getLeadSourceId())).thenReturn(Optional.of(new LeadSourceMaster()));
		when(domainMasterDaoService.findByName(leadDto.getDomainId())).thenReturn(Optional.of(new DomainMaster()));
		Contacts result = leadService.buildLeadObj(leadDto);
		assertNotNull(result);
	}

	@Test
	void testBuildLeadObjElseSuccess1() {
		LeadDto leadDto = new LeadDto();
		leadDto.setCompanyName("Company1");
		leadDto.setServiceFallsId(null);
		leadDto.setLeadSourceId(null);
		leadDto.setDomainId(null);
		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(1);
		when(companyMasterDaoService.findByCompanyName(leadDto.getCompanyName()))
				.thenReturn(Optional.of(new CompanyDto()));
		when(companyMasterDaoService.save(any(CompanyMaster.class))).thenReturn(Optional.of(mock(CompanyDto.class)));
		when(serviceFallsDaoSevice.findByName(leadDto.getServiceFallsId()))
				.thenReturn(Optional.of(new ServiceFallsMaster()));
		when(leadSourceDaoService.getByName(leadDto.getLeadSourceId())).thenReturn(Optional.of(new LeadSourceMaster()));
		when(domainMasterDaoService.findByName(leadDto.getDomainId())).thenReturn(Optional.of(new DomainMaster()));
		Contacts result = leadService.buildLeadObj(leadDto);
		assertNotNull(result);
	}

	@Test
	void testBuildLeadObjElseSuccess() throws Exception {
		LeadDto leadDto = new LeadDto();
		leadDto.setCompanyName("Company1");
		leadDto.setServiceFallsId("test");
		leadDto.setLeadSourceId("Company1");
		leadDto.setDomainId("test");
		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(1);
		LeadSourceMaster leadSources = new LeadSourceMaster();
		leadSources.setSourceName(leadDto.getLeadSourceId());
		ServiceFallsMaster serviceFall = new ServiceFallsMaster();
		serviceFall.setServiceName(leadDto.getServiceFallsId());
		DomainMaster newDomain = new DomainMaster();
		newDomain.setDomainName(leadDto.getDomainId());
		when(serviceFallsDaoSevice.save(any(ServiceFallsMaster.class)))
				.thenReturn(Optional.of(mock(ServiceFallsDto.class)));
		when(leadSourceDaoService.save(any(LeadSourceMaster.class))).thenReturn(Optional.of(mock(LeadSourceDto.class)));
		when(domainMasterDaoService.addDomain(any(DomainMaster.class)))
				.thenReturn(Optional.of(mock(DomainMaster.class)));
		Contacts result = leadService.buildLeadObj(leadDto);
		assertNotNull(result);
	}

	@Test
	void testBuildLeadObjException() {
		LeadDto leadDto = new LeadDto();
		leadDto.setCompanyName("Company1");
		when(companyMasterDaoService.findByCompanyName(leadDto.getCompanyName()))
				.thenThrow(new RuntimeException("Simulated Exception"));
		Contacts result = leadService.buildLeadObj(leadDto);
		assertNull(result);
	}

	@Test
	void testAssignLeadNotificationException() {
		when(leadDaoService.getLeadById(1)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> leadService.assignLeadNotification(123));
	}

	@Test
    void testAddDescriptionSuccess() {
        when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(leads));
        when(leadDaoService.addDesc(any(Description.class))).thenReturn(description);
        ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.addDescription(descriptionDto, 1);
        assertEquals(CREATED, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get(SUCCESS));
        assertEquals("Description Added Successfully", response.getBody().get(MESSAGE));
    }

	@Test
    void testAddDescriptionFails() {
        when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(leads));
        when(leadDaoService.addDesc(any(Description.class))).thenReturn(null);
        ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.addDescription(descriptionDto, 1);
        assertEquals(CREATED, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get(SUCCESS));
        assertEquals("Description Not Added", response.getBody().get(MESSAGE));
    }

	@Test
	void testAddDescriptionException() {
		when(leadDaoService.getLeadById(1)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> leadService.addDescription(descriptionDto,1));
	}
}
