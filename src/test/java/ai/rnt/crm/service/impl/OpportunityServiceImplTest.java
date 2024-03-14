package ai.rnt.crm.service.impl;

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

import java.util.ArrayList;
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

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.DomainMasterDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.OpprtAttachmentDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.dto.opportunity.CloseOpportunityDto;
import ai.rnt.crm.dto.opportunity.OpprtAttachmentDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.OpprtAttachment;
import ai.rnt.crm.entity.ServiceFallsMaster;
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
	private OpprtAttachmentDaoService opprtAttachmentDaoService;

	@InjectMocks
	private OpportunityServiceImpl opportunityServiceImpl;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(opportunityServiceImpl).build();
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
	void testUpdateOpportunity_Success() {
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
}
