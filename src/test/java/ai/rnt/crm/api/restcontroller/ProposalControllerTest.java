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
}
