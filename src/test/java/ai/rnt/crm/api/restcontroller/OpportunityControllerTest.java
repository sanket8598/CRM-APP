package ai.rnt.crm.api.restcontroller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.dto.opportunity.AnalysisOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseOpportunityDto;
import ai.rnt.crm.dto.opportunity.ProposeOpportunityDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.OpportunityService;

class OpportunityControllerTest {

	@Mock
	private OpportunityService opportunityService;

	@InjectMocks
	private OpportunityController opportunityController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void getOpportunityDataByStatusTest() {
		String status = "Open";
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		OpportunityService opportunityService = Mockito.mock(OpportunityService.class);
		when(opportunityService.getOpportunityDataByStatus(status)).thenReturn(expectedResponse);
		OpportunityController opportunityController = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityController
				.getOpportunityDataByStatus(status);
		verify(opportunityService).getOpportunityDataByStatus(status);
	}

	@Test
	void getDashboardDataTest() {
		Integer staffId = 1477;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		OpportunityService opportunityService = Mockito.mock(OpportunityService.class);
		when(opportunityService.getDashBoardData(staffId)).thenReturn(expectedResponse);
		OpportunityController opportunityController = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityController.getDashboardData(staffId);
		verify(opportunityService).getDashBoardData(staffId);
	}

	@Test
	void editOpportunityTest() {
		Integer optId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		OpportunityService opportunityService = Mockito.mock(OpportunityService.class);
		when(opportunityService.getOpportunityData(optId)).thenReturn(expectedResponse);
		OpportunityController opportunityController = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = opportunityController.editOpportunity(optId);
		verify(opportunityService).getOpportunityData(optId);
	}

	@Test
	void getQualifyPopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer leadId = 1;
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.getQualifyPopUpData(leadId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.getQualifyPopUpData(leadId);
		verify(opportunityService).getQualifyPopUpData(leadId);
	}

	@Test
	void getAnalysisPopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.getAnalysisPopUpData(opportunityId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.getAnalysisPopUpData(opportunityId);
		verify(opportunityService).getAnalysisPopUpData(opportunityId);
	}

	@Test
	void updateAnalysisPopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		AnalysisOpportunityDto dto = new AnalysisOpportunityDto();
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.updateAnalysisPopUpData(dto, opportunityId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.updateAnalysisPopUpData(dto, opportunityId);
		verify(opportunityService).updateAnalysisPopUpData(dto, opportunityId);
	}

	@Test
	void getProposePopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.getProposePopUpData(opportunityId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.getProposePopUpData(opportunityId);
		verify(opportunityService).getProposePopUpData(opportunityId);
	}

	@Test
	void updateProposePopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		ProposeOpportunityDto dto = new ProposeOpportunityDto();
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.updateProposePopUpData(dto, opportunityId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.updateProposePopUpData(dto, opportunityId);
		verify(opportunityService).updateProposePopUpData(dto, opportunityId);
	}

	@Test
	void getClosePopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.getClosePopUpData(opportunityId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.getClosePopUpData(opportunityId);
		verify(opportunityService).getClosePopUpData(opportunityId);
	}

	@Test
	void updateClosePopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		CloseOpportunityDto dto = new CloseOpportunityDto();
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.updateClosePopUpData(dto, opportunityId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.updateClosePopUpData(dto, opportunityId);
		verify(opportunityService).updateClosePopUpData(dto, opportunityId);
	}

	@Test
	void updateOpportunityTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		UpdateLeadDto dto = new UpdateLeadDto();
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.updateOpportunity(dto, opportunityId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.updateOpportunity(dto, opportunityId);
		verify(opportunityService).updateOpportunity(dto, opportunityId);
	}
}
