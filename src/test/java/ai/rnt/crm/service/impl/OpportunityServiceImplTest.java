package ai.rnt.crm.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.DomainMasterDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.OpprtAttachmentDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.dto.EditCallDto;
import ai.rnt.crm.dto.EditEmailDto;
import ai.rnt.crm.dto.EditMeetingDto;
import ai.rnt.crm.dto.EditVisitDto;
import ai.rnt.crm.dto.TimeLineActivityDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.dto.opportunity.AnalysisOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseAsLostOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseOpportunityDto;
import ai.rnt.crm.dto.opportunity.OpprtAttachmentDto;
import ai.rnt.crm.dto.opportunity.ProposeOpportunityDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.OpprtAttachment;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;

class OpportunityServiceImplTest {

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@Autowired
	MockMvc mockMvc;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private ContactDaoService contactDaoService;

	@Mock
	private CityDaoService cityDaoService;

	@Mock
	private StateDaoService stateDaoService;

	@Mock
	private CountryDaoService countryDaoService;

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
	private CompanyMasterDaoService companyMasterDaoService;

	@Mock
	private OpportunityDaoService opportunityDaoService;

	@Mock
	private ServiceFallsDaoSevice serviceFallsDaoSevice;

	@Mock
	private LeadSourceDaoService leadSourceDaoService;

	@Mock
	private DomainMasterDaoService domainMasterDaoService;

	@Mock
	private Opportunity opportunity;

	@Mock
	private EmployeeMaster employeeMaster;

	@Mock
	private ServiceFallsMaster serviceFallsMaster;

	@Mock
	private Leads leads;

	@Mock
	private Contacts contacts;

	@Mock
	private OpprtAttachmentDto opprtAttachmentDto;

	@Mock
	private CloseOpportunityDto closeOpportunityDto;

	@Mock
	private UpdateLeadDto updateLeadDto;

	@Mock
	private TimeLineActivityDto timeLineActivityDto;

	@Mock
	private EditCallDto editCallDto;

	@Mock
	private EditEmailDto editEmailDto;

	@Mock
	private EditVisitDto editVisitDto;

	@Mock
	private EditMeetingDto editMeetingDto;

	@Mock
	private OpprtAttachmentDaoService opprtAttachmentDaoService;

	@InjectMocks
	private OpportunityServiceImpl opportunityServiceImpl;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(opportunityServiceImpl).build();
	}

	@Test
	void testGetDashBoardDataSuccess() {
		List<Opportunity> dashboardData = new ArrayList<>();
		when(opportunityDaoService.getOpportunityDashboardData()).thenReturn(dashboardData);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityServiceImpl.getDashBoardData(1477);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetDashBoardDataForNoUser() {
		when(auditAwareUtil.isAdmin()).thenReturn(false);
		when(auditAwareUtil.isUser()).thenReturn(false);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(null);
		Opportunity opt=new Opportunity();
		opt.setStatus("Qualify");
		Opportunity opt2=new Opportunity();
		opt.setStatus("Open");
		Opportunity opt3=new Opportunity();
		opt.setStatus(null);
		List<Opportunity> dashboardData = new ArrayList<>();
		dashboardData.add(opt);
		dashboardData.add(opt2);
		dashboardData.add(opt3);
		when(opportunityDaoService.getOpportunityDashboardData()).thenReturn(dashboardData);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityServiceImpl.getDashBoardData(null);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetDashBoardDataAdmin() {
		List<Contacts> contList = new ArrayList<>();
		Opportunity opp = new Opportunity();
		Leads lead = new Leads();
		Contacts cont = new Contacts();
		CompanyMaster comp = new CompanyMaster();
		comp.setCompanyName("Company Name");
		cont.setCompanyMaster(comp);
		cont.setPrimary(true);
		Contacts cont1 = new Contacts();
		CompanyMaster comp1 = new CompanyMaster();
		comp1.setCompanyName("Company Name");
		cont1.setCompanyMaster(comp1);
		cont1.setPrimary(false);
		contList.add(cont);
		contList.add(cont1);
		lead.setContacts(contList);
		opp.setStatus("Analysis");
		opp.setOpportunityId(1);
		opp.setLeads(lead);
		List<Opportunity> dashboardData = new ArrayList<>();
		dashboardData.add(opp);
		when(auditAwareUtil.isAdmin()).thenReturn(true);
		when(opportunityDaoService.getOpportunityDashboardData()).thenReturn(dashboardData);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityServiceImpl.getDashBoardData(null);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetDashBoardDataUser() {
		List<Contacts> contList = new ArrayList<>();
		Opportunity opt = new Opportunity();
		Opportunity opt2 = new Opportunity();
		opt.setStatus("Anaysis");
		EmployeeMaster emp = new EmployeeMaster();
		Leads lead = new Leads();
		emp.setStaffId(1477);
		opt.setEmployee(emp);
		Contacts cont = new Contacts();
		CompanyMaster comp = new CompanyMaster();
		comp.setCompanyName("Company Name");
		cont.setCompanyMaster(comp);
		cont.setPrimary(true);
		Contacts cont1 = new Contacts();
		CompanyMaster comp1 = new CompanyMaster();
		comp1.setCompanyName("Company Name");
		cont1.setCompanyMaster(comp1);
		cont1.setPrimary(false);
		contList.add(cont);
		contList.add(cont1);
		lead.setContacts(contList);
		opt.setLeads(lead);
		opt.setBudgetAmount("13,3232");
		List<Opportunity> dashboardData = new ArrayList<>();
		dashboardData.add(opt);
		opt2.setEmployee(emp);
		opt2.setLeads(lead);
		opt2.setStatus("Qualify");
		dashboardData.add(opt2);
		when(auditAwareUtil.isUser()).thenReturn(true);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1477);
		when(opportunityDaoService.getOpportunityDashboardData()).thenReturn(dashboardData);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityServiceImpl.getDashBoardData(null);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	    void testGetDashBoardDataException() {
	        when(opportunityDaoService.getOpportunityDashboardData()).thenThrow();
	        assertThrows(CRMException.class, () -> opportunityServiceImpl.getDashBoardData(1477));
	    }

	@Test
	void getQualifyPopUpDataTest() {
		when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(leads));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityServiceImpl.getQualifyPopUpData(anyInt());
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	     void getQualifyPopUpDataExceptionTest() {
	        when(opportunityDaoService.findOpportunity(anyInt())).thenReturn(Optional.empty());
	        assertThrows(CRMException.class, () -> opportunityServiceImpl.getQualifyPopUpData(1));
	    }

	@Test
	void testUpdateAttachmentsOfAllPhases() {
		Opportunity opportunityData = new Opportunity();
		opportunityData.setOpportunityId(1);
		List<OpprtAttachment> attachments = new ArrayList<>();
		OpprtAttachment attachment1 = new OpprtAttachment();
		attachment1.setOptAttchId(1);
		attachment1.setAttachmentOf("phase1");
		attachments.add(attachment1);
		opportunityData.setOprtAttachment(attachments);
		String phase = "phase1";
		List<OpprtAttachmentDto> isAttachments = new ArrayList<>();
		List<OpprtAttachmentDto> isAttachments1 = new ArrayList<>();
		OpprtAttachmentDto attachmentDto = new OpprtAttachmentDto();
		attachmentDto.setOptAttchId(2);
		isAttachments.add(attachmentDto);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(opprtAttachmentDaoService.addOpprtAttachment(any())).thenAnswer(invocation -> {
			OpprtAttachment attachedAttachment = invocation.getArgument(0);
			attachedAttachment.setOpportunity(opportunityData);
			return attachedAttachment;
		});
		when(opportunityDaoService.addOpportunity(opportunityData)).thenReturn(opportunityData);
		boolean status = opportunityServiceImpl.updateAttachmentsOfAllPhases(opportunityData, phase, isAttachments);
		boolean status1 = opportunityServiceImpl.updateAttachmentsOfAllPhases(opportunityData, phase, isAttachments1);
		assertTrue(status);
		assertTrue(status1);
		assertEquals(1, opportunityData.getOprtAttachment().size());
		OpprtAttachment updatedAttachment = opportunityData.getOprtAttachment().get(0);
		assertEquals(1, updatedAttachment.getDeletedBy());
		assertNotNull(updatedAttachment.getDeletedDate());
	}

	@Test
	void testUpdateAttachmentsOfAllPhasesException() {
		Opportunity opportunityData = new Opportunity();
		String phase = "Qualify";
		List<OpprtAttachmentDto> isAttachments = new ArrayList<>();
		isAttachments.add(new OpprtAttachmentDto());
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		when(opprtAttachmentDaoService.addOpprtAttachment(any())).thenThrow(new RuntimeException("Mock exception"));
		assertThrows(CRMException.class,
				() -> opportunityServiceImpl.updateAttachmentsOfAllPhases(opportunityData, phase, isAttachments));
		verify(opprtAttachmentDaoService, times(1)).addOpprtAttachment(any());
	}

	@Test
	void testUpdateOpportunitySuccess() {
		UpdateLeadDto dto = new UpdateLeadDto();
		Integer opportunityId = 123;
		Opportunity opportunity = new Opportunity();
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.of(opportunity));
		when(opportunityDaoService.addOpportunity(opportunity)).thenReturn(opportunity);
		Leads leads = new Leads();

		List<Contacts> contact = new ArrayList<>();
		Contacts contact1 = new Contacts();
		contact1.setPrimary(true);
		contact.add(contact1);
		leads.setContacts(contact);
		opportunity.setLeads(leads);
		dto.setCompanyName("RNT");
		dto.setCountry("India");
		dto.setState("Maharashtra");
		dto.setCity("Pune");
		dto.setServiceFallsId("1");
		when(cityDaoService.existCityByName(dto.getCity())).thenReturn(Optional.empty());
		when(stateDaoService.findBystate(dto.getState())).thenReturn(Optional.empty());
		when(countryDaoService.findByCountryName(dto.getCountry())).thenReturn(Optional.empty());
		when(companyMasterDaoService.findByCompanyName(dto.getCompanyName())).thenReturn(Optional.empty());
		when(serviceFallsDaoSevice.findByName(dto.getServiceFallsId())).thenReturn(Optional.empty());
		when(companyMasterDaoService.save(any())).thenReturn(Optional.of(mock(CompanyDto.class)));
		when(contactDaoService.addContact(contact1)).thenReturn(new Contacts());
		when(leadDaoService.addLead(leads)).thenReturn(new Leads());
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = opportunityServiceImpl.updateOpportunity(dto,
				opportunityId);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.MESSAGE));
		assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Opportunity Details Updated Successfully !!", responseEntity.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testUpdateOpportunityException() {
		UpdateLeadDto dto = new UpdateLeadDto();
		Integer opportunityId = 123;
		when(opportunityDaoService.findOpportunity(opportunityId)).thenThrow(new RuntimeException("Mock exception"));
		assertThrows(CRMException.class, () -> opportunityServiceImpl.updateOpportunity(dto, opportunityId));
	}

	@Test
	void testUpdateClosePopUpDataException() {
		CloseOpportunityDto dto = new CloseOpportunityDto();
		Integer opportunityId = 1;
		when(opportunityDaoService.findOpportunity(opportunityId)).thenThrow(new RuntimeException("Mock exception"));
		assertThrows(CRMException.class, () -> opportunityServiceImpl.updateClosePopUpData(dto, opportunityId));
	}

	@Test
	void testUpdateCloseAsLOstDataException() {
		CloseAsLostOpportunityDto dto = new CloseAsLostOpportunityDto();
		Integer opportunityId = 1;
		when(opportunityDaoService.findOpportunity(opportunityId)).thenThrow(new RuntimeException("Mock exception"));
		assertThrows(CRMException.class, () -> opportunityServiceImpl.updateCloseAsLostData(dto, opportunityId));
	}

	@Test
	void testGetClosePopUpDataSuccess() {
		Integer opportunityId = 1;
		Opportunity opportunityData = new Opportunity();
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.of(opportunityData));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = opportunityServiceImpl
				.getClosePopUpData(opportunityId);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.DATA));
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetClosePopUpDataOpportunityNotFound() {
		Integer opportunityId = 1;
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> opportunityServiceImpl.getClosePopUpData(opportunityId));
	}

	@Test
	void testGetProposePopUpDataSuccess() {
		Integer opportunityId = 1;
		Opportunity opportunityData = new Opportunity();
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.of(opportunityData));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = opportunityServiceImpl
				.getProposePopUpData(opportunityId);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.DATA));
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetProposePopUpDataOpportunityNotFound() {
		Integer opportunityId = 1;
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> opportunityServiceImpl.getProposePopUpData(opportunityId));
	}

	@Test
	void testGetCloseAsLostDataSuccess() {
		Integer opportunityId = 1;
		Opportunity opportunityData = new Opportunity();
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.of(opportunityData));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = opportunityServiceImpl
				.getCloseAsLostData(opportunityId);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.DATA));
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetCloseAsLostDataOpportunityNotFound() {
		Integer opportunityId = 1;
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> opportunityServiceImpl.getCloseAsLostData(opportunityId));
	}

	@Test
	void testGetAnalysisPopUpDataSuccess() {
		Integer opportunityId = 1;
		Opportunity opportunityData = new Opportunity();
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.of(opportunityData));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = opportunityServiceImpl
				.getAnalysisPopUpData(opportunityId);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.DATA));
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testGetAnalysisPopUpDataOpportunityNotFound() {
		Integer opportunityId = 1;
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> opportunityServiceImpl.getAnalysisPopUpData(opportunityId));
	}

	@Test
	void testUpdateProposePopUpDataOpportunityNotFound() {
		Integer opportunityId = 1;
		ProposeOpportunityDto dto = new ProposeOpportunityDto();
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> opportunityServiceImpl.updateProposePopUpData(dto, opportunityId));
	}

	@Test
	void testUpdateAnalysisPopUpDataOpportunityNotFound() {
		Integer opportunityId = 1;
		AnalysisOpportunityDto dto = new AnalysisOpportunityDto();
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.empty());
		assertThrows(CRMException.class, () -> opportunityServiceImpl.updateAnalysisPopUpData(dto, opportunityId));
	}

	@Test
	void testUpdateClosePopUpDataSuccess1() {
		Integer opportunityId = 1;
		CloseOpportunityDto dto = new CloseOpportunityDto();
		Leads leads = new Leads();
		Opportunity opportunity = new Opportunity();
		opportunity.setLeads(leads);
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.of(opportunity));
		when(opportunityDaoService.addOpportunity(any())).thenReturn(new Opportunity());
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity1 = opportunityServiceImpl.updateClosePopUpData(dto,
				opportunityId);
		assertNotNull(responseEntity1);
		assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());
		assertTrue(responseEntity1.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity1.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity1.getBody().get(ApiResponse.MESSAGE));
		when(opportunityDaoService.addOpportunity(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity2 = opportunityServiceImpl.updateClosePopUpData(dto,
				opportunityId);
		assertNotNull(responseEntity2);
		assertEquals(HttpStatus.CREATED, responseEntity2.getStatusCode());
		assertTrue(responseEntity2.getBody().containsKey(ApiResponse.SUCCESS));
		assertFalse((boolean) responseEntity2.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity2.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testUpdateCloseAsLostDataSuccess1() {
		Integer opportunityId = 1;
		CloseAsLostOpportunityDto dto = new CloseAsLostOpportunityDto();
		Leads leads = new Leads();
		Opportunity opportunity = new Opportunity();
		opportunity.setLeads(leads);
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.of(opportunity));
		when(opportunityDaoService.addOpportunity(any())).thenReturn(new Opportunity());
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity1 = opportunityServiceImpl.updateCloseAsLostData(dto,
				opportunityId);
		assertNotNull(responseEntity1);
		assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());
		assertTrue(responseEntity1.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity1.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity1.getBody().get(ApiResponse.MESSAGE));
		when(opportunityDaoService.addOpportunity(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity2 = opportunityServiceImpl.updateCloseAsLostData(dto,
				opportunityId);
		assertNotNull(responseEntity2);
		assertEquals(HttpStatus.CREATED, responseEntity2.getStatusCode());
		assertTrue(responseEntity2.getBody().containsKey(ApiResponse.SUCCESS));
		assertFalse((boolean) responseEntity2.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity2.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testUpdateProposePopUpDataSuccess1() {
		Integer opportunityId = 1;
		ProposeOpportunityDto dto = new ProposeOpportunityDto();
		Leads leads = new Leads();
		Opportunity opportunity = new Opportunity();
		opportunity.setLeads(leads);
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.of(opportunity));
		when(opportunityDaoService.addOpportunity(any())).thenReturn(new Opportunity());
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity1 = opportunityServiceImpl
				.updateProposePopUpData(dto, opportunityId);
		assertNotNull(responseEntity1);
		assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());
		assertTrue(responseEntity1.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity1.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity1.getBody().get(ApiResponse.MESSAGE));
		when(opportunityDaoService.addOpportunity(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity2 = opportunityServiceImpl
				.updateProposePopUpData(dto, opportunityId);
		assertNotNull(responseEntity2);
		assertEquals(HttpStatus.CREATED, responseEntity2.getStatusCode());
		assertTrue(responseEntity2.getBody().containsKey(ApiResponse.SUCCESS));
		assertFalse((boolean) responseEntity2.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity2.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testUpdateAnalysisPopUpDataSuccess1() {
		Integer opportunityId = 1;
		AnalysisOpportunityDto dto = new AnalysisOpportunityDto();
		Leads leads = new Leads();
		Opportunity opportunity = new Opportunity();
		opportunity.setLeads(leads);
		when(opportunityDaoService.findOpportunity(opportunityId)).thenReturn(Optional.of(opportunity));
		when(opportunityDaoService.addOpportunity(any())).thenReturn(new Opportunity());
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity1 = opportunityServiceImpl
				.updateAnalysisPopUpData(dto, opportunityId);
		assertNotNull(responseEntity1);
		assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());
		assertTrue(responseEntity1.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity1.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity1.getBody().get(ApiResponse.MESSAGE));
		when(opportunityDaoService.addOpportunity(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity2 = opportunityServiceImpl
				.updateAnalysisPopUpData(dto, opportunityId);
		assertNotNull(responseEntity2);
		assertEquals(HttpStatus.CREATED, responseEntity2.getStatusCode());
		assertTrue(responseEntity2.getBody().containsKey(ApiResponse.SUCCESS));
		assertFalse((boolean) responseEntity2.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity2.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
    void testGetOpportunityDataByStatusAdminAllStatus() {
        when(auditAwareUtil.isAdmin()).thenReturn(true);
        List<Opportunity> opportunityData = Arrays.asList();
        when(opportunityDaoService.getOpportunityDashboardData()).thenReturn(opportunityData);
        ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = opportunityServiceImpl.getOpportunityDataByStatus("All");
        ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity1 = opportunityServiceImpl.getOpportunityDataByStatus("in-pipeline");
        ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity2 = opportunityServiceImpl.getOpportunityDataByStatus(null);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity1);
        assertNotNull(responseEntity2);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().containsKey(ApiResponse.SUCCESS));
        assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
        assertNotNull(responseEntity.getBody().get(ApiResponse.DATA));
    }

	@Test
	void testGetOpportunityDataWithNoUser() {
		when(auditAwareUtil.isAdmin()).thenReturn(false);
		when(auditAwareUtil.isUser()).thenReturn(false);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(null);
		List<Opportunity> opportunityData = Arrays.asList();
		when(opportunityDaoService.getOpportunityDashboardData()).thenReturn(opportunityData);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = opportunityServiceImpl.getOpportunityDataByStatus("All");
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	
	}

	@Test
	void testGetOpportunityDataByStatusUserAllStatus() {
		when(auditAwareUtil.isUser()).thenReturn(true);
		List<Opportunity> opportunityData = Arrays.asList();
		when(opportunityDaoService.getOpportunityDashboardData()).thenReturn(opportunityData);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = opportunityServiceImpl.getOpportunityDataByStatus("All");
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity1 = opportunityServiceImpl.getOpportunityDataByStatus("in-pipeline");
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity2 = opportunityServiceImpl.getOpportunityDataByStatus("other");
		assertNotNull(responseEntity);
		assertNotNull(responseEntity1);
		assertNotNull(responseEntity2);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity.getBody().get(ApiResponse.DATA));
	}

	@Test
    void testGetOpportunityDataByStatusException() {
        when(auditAwareUtil.isAdmin()).thenReturn(true);
        when(opportunityDaoService.getOpportunityDashboardData()).thenThrow(new RuntimeException("Simulated exception"));
        assertThrows(CRMException.class, () -> opportunityServiceImpl.getOpportunityDataByStatus("ALL"));
    }

	@Test
	void testGetOpportunityData_Success() {
		Opportunity opportunity = new Opportunity();
		Leads leads = new Leads();
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setFirstName("test");
		employeeMaster.setLastName("data");
		employeeMaster.setStaffId(1477);
		leads.setLeadId(1);
		opportunity.setLeads(leads);
		opportunity.setCreatedBy(1477);
		opportunity.setEmployee(employeeMaster);
		List<Call> calls = new ArrayList<>();
		List<Email> emails = new ArrayList<>();
		List<Visit> visits = new ArrayList<>();
		List<Meetings> mettings = new ArrayList<>();
		Call call = new Call();
		call.setCallId(1);
		call.setCallTo("testcall");
		call.setCallFrom(employeeMaster);
		call.setCreatedDate(LocalDateTime.now());
		calls.add(call);
		Email email = new Email();
		email.setMailId(1);
		email.setMailFrom("s.wakankar@rnt.ai");
		email.setCreatedDate(LocalDateTime.now());
		emails.add(email);
		Visit visit = new Visit();
		visit.setVisitId(1);
		visit.setVisitBy(employeeMaster);
		visit.setCreatedDate(LocalDateTime.now());
		visits.add(visit);
		Meetings meetings = new Meetings();
		meetings.setMeetingId(1);
		meetings.setAssignTo(employeeMaster);
		meetings.setCreatedDate(LocalDateTime.now());
		mettings.add(meetings);
		when(opportunityDaoService.findOpportunity(anyInt())).thenReturn(Optional.of(opportunity));
		when(callDaoService.getCallsByLeadId(opportunity.getLeads().getLeadId())).thenReturn(calls);
		when(visitDaoService.getVisitsByLeadId(opportunity.getLeads().getLeadId())).thenReturn(visits);
		when(emailDaoService.getEmailByLeadId(opportunity.getLeads().getLeadId())).thenReturn(emails);
		when(meetingDaoService.getMeetingByLeadId(opportunity.getLeads().getLeadId())).thenReturn(mettings);
		when(employeeService.getById(opportunity.getCreatedBy())).thenReturn(Optional.of(employeeMaster));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = opportunityServiceImpl.getOpportunityData(1);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity.getBody().get(ApiResponse.DATA));
	}

	@Test
	void testTimelinegetOpportunitySuccess() {
		Opportunity opportunity = new Opportunity();
		Leads leads = new Leads();
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setFirstName("test");
		employeeMaster.setLastName("data");
		employeeMaster.setStaffId(1477);
		leads.setLeadId(1);
		opportunity.setLeads(leads);
		opportunity.setCreatedBy(1477);
		opportunity.setEmployee(employeeMaster);
		List<Call> calls = new ArrayList<>();
		List<Email> emails = new ArrayList<>();
		List<Visit> visits = new ArrayList<>();
		List<Meetings> mettings = new ArrayList<>();
		Call call = new Call();
		call.setCallId(1);
		call.setStatus("Complete");
		call.setCallTo("testcall");
		call.setCallFrom(employeeMaster);
		call.setCreatedDate(LocalDateTime.now());
		call.setUpdatedDate(LocalDateTime.now());
		calls.add(call);
		Email email = new Email();
		email.setMailId(1);
		email.setStatus("Send");
		email.setMailFrom("s.wakankar@rnt.ai");
		email.setCreatedDate(LocalDateTime.now());
		email.setUpdatedDate(LocalDateTime.now());
		emails.add(email);
		Visit visit = new Visit();
		visit.setVisitId(1);
		visit.setStatus("Complete");
		visit.setVisitBy(employeeMaster);
		visit.setCreatedDate(LocalDateTime.now());
		visit.setUpdatedDate(LocalDateTime.now());
		visits.add(visit);
		Meetings meetings = new Meetings();
		meetings.setMeetingId(1);
		meetings.setMeetingStatus("Complete");
		meetings.setAssignTo(employeeMaster);
		meetings.setCreatedDate(LocalDateTime.now());
		meetings.setUpdatedDate(LocalDateTime.now());
		mettings.add(meetings);
		when(opportunityDaoService.findOpportunity(anyInt())).thenReturn(Optional.of(opportunity));
		when(callDaoService.getCallsByLeadId(opportunity.getLeads().getLeadId())).thenReturn(calls);
		when(visitDaoService.getVisitsByLeadId(opportunity.getLeads().getLeadId())).thenReturn(visits);
		when(emailDaoService.getEmailByLeadId(opportunity.getLeads().getLeadId())).thenReturn(emails);
		when(meetingDaoService.getMeetingByLeadId(opportunity.getLeads().getLeadId())).thenReturn(mettings);
		when(employeeService.getById(opportunity.getCreatedBy())).thenReturn(Optional.of(employeeMaster));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = opportunityServiceImpl.getOpportunityData(1);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(responseEntity.getBody().get(ApiResponse.DATA));
	}

	@Test
    void testGetOpportunityDataResourceNotFoundException() {
        when(opportunityDaoService.findOpportunity(anyInt())).thenReturn(Optional.empty());
        assertThrows(CRMException.class, () -> opportunityServiceImpl.getOpportunityData(1));
    }

	@Test
    void testGetOpportunityDataException() {
        when(opportunityDaoService.findOpportunity(anyInt())).thenThrow(new RuntimeException("Simulated exception"));
        assertThrows(CRMException.class, () -> opportunityServiceImpl.getOpportunityData(1));
    }
}
