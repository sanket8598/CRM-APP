package ai.rnt.crm.service.impl;

import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDateTime;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.ProposalDaoService;
import ai.rnt.crm.dao.service.ProposalServicesDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dto.opportunity.GetProposalsDto;
import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.dto.opportunity.ProposalServicesDto;
import ai.rnt.crm.dto.opportunity.UpdateProposalDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.Proposal;
import ai.rnt.crm.entity.ProposalServices;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;
import ai.rnt.crm.util.StringUtil;

@ExtendWith(MockitoExtension.class)
class ProposalServiceImplTest {

	@InjectMocks
	private ProposalServiceImpl proposalServiceImpl;

	@Mock
	private StringUtil stringUtil;

	@Mock
	private ProposalDto dto;

	@Mock
	private ProposalServicesDto proposalServiceDto;

	@Mock
	private GetProposalsDto getProposalsDto;

	@Mock
	private Proposal proposal;

	@Mock
	private ProposalServices proposalServices;

	@Mock
	private Opportunity opportunity;

	@Mock
	private UpdateProposalDto updateProposalDto;

	@Mock
	private EmployeeDaoService employeeDaoService;

	@Mock
	private ProposalDaoService proposalDaoService;

	@Mock
	private ServiceFallsDaoSevice serviceFallsDaoSevice;

	@Mock
	private ProposalServicesDaoService proposalServicesDaoService;

	@Mock
	private OpportunityDaoService opportunityDaoService;

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@Mock
	private CallDaoService callDaoService;

	@Mock
	private EmployeeService employeeService;

	@Test
	void testGenerateProposalId_Success() {
		mock(StringUtil.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = proposalServiceImpl.generateProposalId();
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.DATA));
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void addProposalTestSuccess() {
		dto.setPropId(1);
		Integer optyId = 1;
		proposal.setOpportunity(opportunity);
		Proposal mockedCall = mock(Proposal.class);
		when(opportunityDaoService.findOpportunity(optyId)).thenReturn(Optional.of(opportunity));
		when(proposalDaoService.saveProposal(any(Proposal.class))).thenReturn(mockedCall);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = proposalServiceImpl.addProposal(dto, optyId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Proposal Added Successfully", responseEntity.getBody().get(ApiResponse.MESSAGE));
		assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	void addProposalTestElseBlock() {
		dto.setPropId(1);
		Integer optyId = 1;
		proposal.setOpportunity(opportunity);
		when(opportunityDaoService.findOpportunity(optyId)).thenReturn(Optional.of(opportunity));
		when(proposalDaoService.saveProposal(any(Proposal.class))).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = proposalServiceImpl.addProposal(dto, optyId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Proposal Not Added", responseEntity.getBody().get(ApiResponse.MESSAGE));
		assertFalse((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	void addProposalTestWithException() {
		ProposalDto callDto = new ProposalDto();
		Integer optyId = 1;
		when(opportunityDaoService.findOpportunity(anyInt()))
				.thenThrow(new ResourceNotFoundException("Opportunity", "optyId", optyId));
		assertThrows(CRMException.class, () -> proposalServiceImpl.addProposal(callDto, optyId));
		verify(opportunityDaoService, times(1)).findOpportunity(anyInt());
	}

	@Test
	void getProposalsByOptyIdSuccess() {
		int optyId = 1;
		Proposal proposalDto = new Proposal();
		proposalDto.setCreatedBy(1);
		List<Proposal> proposals = Arrays.asList(proposalDto);
		Map<Integer, String> employeeMap = new HashMap<>();
		employeeMap.put(1, "John Doe");
		when(employeeDaoService.getEmployeeNameMap()).thenReturn(employeeMap);
		when(proposalDaoService.getProposalsByOptyId(optyId)).thenReturn(proposals);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = proposalServiceImpl.getProposalsByOptyId(optyId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
	}

	@Test
	void getProposalsByOptyIdException() {
		int optyId = 1;
		when(proposalDaoService.getProposalsByOptyId(anyInt())).thenThrow();
		assertThrows(CRMException.class, () -> proposalServiceImpl.getProposalsByOptyId(optyId));
	}

	@Test
	void testAddServicesToProposalSuccess() throws Exception {
		int proposalId = 1;
		when(proposalDaoService.findProposalById(proposalId)).thenReturn(Optional.of(proposal));
		when(proposalServicesDaoService.save(any(ProposalServices.class)))
				.thenReturn(Optional.of(new ProposalServicesDto()));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = proposalServiceImpl
				.addServicesToProposal(Collections.singletonList(proposalServiceDto), proposalId);
		assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Proposal Service Added Successfully !!", response.getBody().get(ApiResponse.MESSAGE));
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testAddServicesToProposalException() {
		int proposalId = 1;
		when(proposalDaoService.findProposalById(anyInt())).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> proposalServiceImpl
				.addServicesToProposal(Collections.singletonList(proposalServiceDto), proposalId));
	}

	@Test
	void testAddNewServiceSuccess() throws Exception {
		when(serviceFallsDaoSevice.findByServiceName(anyString())).thenReturn(false);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = proposalServiceImpl.addNewService("NewService");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("New Service Added Successfully !!", response.getBody().get(ApiResponse.MESSAGE));
		verify(serviceFallsDaoSevice, times(1)).findByServiceName("NewService");
		verify(serviceFallsDaoSevice, times(1)).save(any());
	}

	@Test
    void testAddNewServiceServiceExists() throws Exception {
        when(serviceFallsDaoSevice.findByServiceName(anyString())).thenReturn(true);
        ResponseEntity<EnumMap<ApiResponse, Object>> response = proposalServiceImpl.addNewService("ExistingService");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse((boolean) response.getBody().get(ApiResponse.SUCCESS));
        assertEquals("Service Is Present !!", response.getBody().get(ApiResponse.MESSAGE));
        verify(serviceFallsDaoSevice, times(1)).findByServiceName("ExistingService");
        verify(serviceFallsDaoSevice, never()).save(any());
    }

	@Test
    void testAddNewServiceException() {
        when(serviceFallsDaoSevice.findByServiceName(anyString())).thenThrow(new RuntimeException("Database connection failed"));
        assertThrows(CRMException.class, () -> proposalServiceImpl.addNewService("NewService"));
    }

	@Test
	void testEditProposalSuccess() {
		Proposal proposal = new Proposal();
		EmployeeMaster employeeMaster = mock(EmployeeMaster.class);
		Opportunity opportunity = new Opportunity();
		employeeMaster.setFirstName("test");
		employeeMaster.setLastName("data");
		proposal.setCreatedBy(1375);
		employeeMaster.setStaffId(1375);
		opportunity.setEmployee(employeeMaster);
		proposal.setOpportunity(opportunity);
		proposal.setPropId(1);
		proposal.setCreatedBy(1);
		when(employeeService.getById(proposal.getCreatedBy())).thenReturn(Optional.of(employeeMaster));
		when(proposalDaoService.findProposalById(anyInt())).thenReturn(Optional.of(proposal));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = proposalServiceImpl.editProposal(1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertNotNull(response.getBody().get(ApiResponse.DATA));
		verify(proposalDaoService, times(1)).findProposalById(1);
	}

	@Test
	    void testEditProposalException() {
	        when(proposalDaoService.findProposalById(anyInt())).thenThrow(new RuntimeException("Database connection failed"));
	        assertThrows(CRMException.class, () -> proposalServiceImpl.editProposal(1));
	    }

	@Test
	void testUpdateProposalSuccess() throws Exception {
		Proposal proposal = new Proposal();
		proposal.setPropId(1);
		proposal.setOwnerName("Old Owner");
		proposal.setCurrency("Old Currency");
		proposal.setPropDescription("Old Description");
		ProposalServices proposalServices = new ProposalServices();
		proposalServices.setPropServiceId(1);
		proposalServices.setServicePrice("Old Price");
		ProposalServicesDto proposalServicesDto = new ProposalServicesDto();
		when(proposalDaoService.findProposalById(anyInt())).thenReturn(Optional.of(proposal));
		when(proposalServicesDaoService.findById(any())).thenReturn(Optional.of(proposalServices));
		when(proposalServicesDaoService.save(any())).thenReturn(Optional.of(proposalServicesDto));
		UpdateProposalDto updateProposalDto = new UpdateProposalDto();
		proposalServicesDto.setPropServiceId(1);
		proposalServicesDto.setServicePrice("New Price");
		updateProposalDto.getProposalServices().add(proposalServicesDto);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = proposalServiceImpl.updateProposal(1,
				updateProposalDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Proposal Updated Successfully!!", response.getBody().get(ApiResponse.MESSAGE));
		assertEquals("New Price", proposalServices.getServicePrice());
		verify(proposalServicesDaoService, times(1)).findById(1);
	}

	@Test
	    void testUpdateProposalException() {
	        when(proposalDaoService.findProposalById(anyInt())).thenThrow(new RuntimeException("Database connection failed"));
	        assertThrows(CRMException.class, () -> proposalServiceImpl.updateProposal(1,updateProposalDto));
	    }

	@Test
	void deleteProposalTest() {
		Integer propId = 1;
		Proposal existingProposal = Mockito.mock(Proposal.class);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1477);
		when(proposalDaoService.findProposalById(Mockito.anyInt())).thenReturn(Optional.of(existingProposal));
		when(proposalDaoService.saveProposal(Mockito.any(Proposal.class))).thenReturn(existingProposal);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = proposalServiceImpl.deleteProposal(propId);
		assertNotNull(responseEntity);
		assertEquals(OK, responseEntity.getStatusCode());
		EnumMap<ApiResponse, Object> result = responseEntity.getBody();
		assertNotNull(result);
		assertTrue((Boolean) result.get(SUCCESS));
		assertEquals("Proposal Deleted Successfully.", result.get(MESSAGE));
		verify(auditAwareUtil, times(1)).getLoggedInStaffId();
		verify(proposalDaoService, times(1)).findProposalById(propId);
	}

	@Test
	void deleteProposalAndServicesTest() throws Exception {
		int propId = 1;
		int loggedInStaffId = 1;
		Proposal proposal = new Proposal();
		ProposalServices proposalServices1 = mock(ProposalServices.class);
		ProposalServices proposalServices2 = mock(ProposalServices.class);
		List<ProposalServices> services = Arrays.asList(proposalServices1, proposalServices2);
		proposal.setProposalServices(services);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(loggedInStaffId);
		when(proposalDaoService.findProposalById(propId)).thenReturn(Optional.of(proposal));
		proposalServiceImpl.deleteProposal(propId);
		verify(proposalServices1).setDeletedBy(loggedInStaffId);
		verify(proposalServices1).setDeletedDate(any(LocalDateTime.class));
		verify(proposalServices1).setDeletedBy(loggedInStaffId);
		verify(proposalServices1).setDeletedDate(any(LocalDateTime.class));
		verify(proposalServicesDaoService).save(proposalServices1);
		verify(proposalServicesDaoService).save(proposalServices2);
	}

	@Test
	void deleteProposalTestException() {
		Integer propId = 1;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(123);
		when(proposalDaoService.findProposalById(anyInt())).thenThrow(new RuntimeException("Simulated exception"));
		assertThrows(CRMException.class, () -> proposalServiceImpl.deleteProposal(propId));
		verify(auditAwareUtil, times(1)).getLoggedInStaffId();
		verify(proposalDaoService, times(1)).findProposalById(propId);
		verify(proposalDaoService, never()).saveProposal(any(Proposal.class));
	}
}
