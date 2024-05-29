package ai.rnt.crm.api.restcontroller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.dto.opportunity.ProposalServicesDto;
import ai.rnt.crm.dto.opportunity.UpdateProposalDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.ProposalService;

class ProposalControllerTest {

	@Mock
	private ProposalService proposalService;

	@InjectMocks
	private ProposalController proposalController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void generateProposalIdTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		ProposalService proposalService = mock(ProposalService.class);
		when(proposalService.generateProposalId()).thenReturn(expectedResponse);
		ProposalController proposalController = new ProposalController(proposalService);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = proposalController.generateProposalId();
		verify(proposalService).generateProposalId();
		assertEquals(expectedResponse, response);
	}

	@Test
	void addProposalTest() {
		ProposalDto proposalDto = new ProposalDto();
		proposalDto.setGenPropId("RNT-1AB6CD");
		Integer optyId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		ProposalService proposalService = mock(ProposalService.class);
		when(proposalService.addProposal(any(ProposalDto.class), anyInt())).thenReturn(expectedResponse);
		ProposalController proposalController = new ProposalController(proposalService);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = proposalController.addProposal(proposalDto, optyId);
		verify(proposalService).addProposal(proposalDto, optyId);
		assertEquals(expectedResponse, response);
	}

	@Test
	void getProposalsTest() {
		Integer optyId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		ProposalService proposalService = mock(ProposalService.class);
		when(proposalService.getProposalsByOptyId(anyInt())).thenReturn(expectedResponse);
		ProposalController proposalController = new ProposalController(proposalService);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = proposalController.getProposals(optyId);
		verify(proposalService).getProposalsByOptyId(optyId);
		assertEquals(expectedResponse, response);
	}

	@Test
	void addServicesToProposalTest() {
		List<ProposalServicesDto> dtoList = new ArrayList<>();
		ProposalServicesDto proposalServicesDto = new ProposalServicesDto();
		dtoList.add(proposalServicesDto);
		Integer propId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		ProposalService proposalService = mock(ProposalService.class);
		when(proposalService.addServicesToProposal(any(List.class), anyInt())).thenReturn(expectedResponse);
		ProposalController proposalController = new ProposalController(proposalService);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = proposalController.addServicesToProposal(dtoList,
				propId);
		verify(proposalService).addServicesToProposal(dtoList, propId);
		assertEquals(expectedResponse, response);
	}

	@Test
	void addNewServiceTest() {
		String serviceName = "New Service";
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponseEntity = ResponseEntity.ok(expectedResponse);
		when(proposalService.addNewService(serviceName.trim())).thenReturn(expectedResponseEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = proposalController.addNewService(serviceName);
		verify(proposalService).addNewService(serviceName.trim());
		assertEquals(expectedResponseEntity, responseEntity);
	}

	@Test
	void editProposalTest() {
		int propId = 1;
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponseEntity = ResponseEntity.ok(expectedResponse);
		when(proposalService.editProposal(propId)).thenReturn(expectedResponseEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = proposalController.editProposal(propId);
		verify(proposalService).editProposal(propId);
		assertEquals(expectedResponseEntity, responseEntity);
	}

	@Test
	void updateProposalTest() {
		int propId = 1;
		UpdateProposalDto dto = new UpdateProposalDto(); // Assuming UpdateProposalDto has a no-arg constructor
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponseEntity = ResponseEntity.ok(expectedResponse);
		when(proposalService.updateProposal(propId, dto)).thenReturn(expectedResponseEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = proposalController.updateProposal(propId, dto);
		verify(proposalService).updateProposal(propId, dto);
		assertEquals(expectedResponseEntity, responseEntity);
	}

	@Test
	void deleteProposalTest() {
		int propId = 1;
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponseEntity = ResponseEntity.ok(expectedResponse);
		when(proposalService.deleteProposal(propId)).thenReturn(expectedResponseEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = proposalController.deleteProposal(propId);
		verify(proposalService).deleteProposal(propId);
		assertEquals(expectedResponseEntity, responseEntity);
	}

	@Test
	void deleteServiceTest() {
		int propServiceId = 1;
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponseEntity = ResponseEntity.ok(expectedResponse);
		when(proposalService.deleteService(propServiceId)).thenReturn(expectedResponseEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = proposalController.deleteService(propServiceId);
		verify(proposalService).deleteService(propServiceId);
		assertEquals(expectedResponseEntity, responseEntity);
	}
}
