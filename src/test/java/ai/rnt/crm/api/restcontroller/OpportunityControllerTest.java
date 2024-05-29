package ai.rnt.crm.api.restcontroller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.dto.opportunity.AnalysisOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseAsLostOpportunityDto;
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
		opportunityController.getOpportunityDataByStatus(status);
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
		opportunityController.getDashboardData(staffId);
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
		opportunityController.editOpportunity(optId);
		verify(opportunityService).getOpportunityData(optId);
	}

	@Test
	void getQualifyPopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer leadId = 1;
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.getQualifyPopUpData(leadId)).thenReturn(mockResponse);
		controller.getQualifyPopUpData(leadId);
		verify(opportunityService).getQualifyPopUpData(leadId);
	}

	@Test
	void getAnalysisPopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.getAnalysisPopUpData(opportunityId)).thenReturn(mockResponse);
		controller.getAnalysisPopUpData(opportunityId);
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
		controller.updateAnalysisPopUpData(dto, opportunityId);
		verify(opportunityService).updateAnalysisPopUpData(dto, opportunityId);
	}

	@Test
	void getProposePopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.getProposePopUpData(opportunityId)).thenReturn(mockResponse);
		controller.getProposePopUpData(opportunityId);
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
		controller.updateProposePopUpData(dto, opportunityId);
		verify(opportunityService).updateProposePopUpData(dto, opportunityId);
	}

	@Test
	void getClosePopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.getClosePopUpData(opportunityId)).thenReturn(mockResponse);
		controller.getClosePopUpData(opportunityId);
		verify(opportunityService).getClosePopUpData(opportunityId);
	}

	@Test
	void getCloseAsLostPopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.getCloseAsLostData(opportunityId)).thenReturn(mockResponse);
		controller.getCloseAsLostData(opportunityId);
		verify(opportunityService).getCloseAsLostData(opportunityId);
	}

	@Test
	void updateClosePopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		CloseOpportunityDto dto = new CloseOpportunityDto();
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.updateClosePopUpData(dto, opportunityId)).thenReturn(mockResponse);
		controller.updateClosePopUpData(dto, opportunityId);
		verify(opportunityService).updateClosePopUpData(dto, opportunityId);
	}

	@Test
	void updateCloseAsLostDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		CloseAsLostOpportunityDto dto = new CloseAsLostOpportunityDto();
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.updateCloseAsLostData(dto, opportunityId)).thenReturn(mockResponse);
		controller.updateCloseAsLostData(dto, opportunityId);
		verify(opportunityService).updateCloseAsLostData(dto, opportunityId);
	}

	@Test
	void updateOpportunityTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		UpdateLeadDto dto = new UpdateLeadDto();
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.updateOpportunity(dto, opportunityId)).thenReturn(mockResponse);
		controller.updateOpportunity(dto, opportunityId);
		verify(opportunityService).updateOpportunity(dto, opportunityId);
	}

	@Test
	void assignOpportunityTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("OptyId", 1);
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(opportunityService.assignOpportunity(map)).thenReturn(expectedResponse);
		opportunityController.assignOpportunity(map);
		verify(opportunityService).assignOpportunity(map);
	}

	@Test
	void reactiveOptyTest() {
		Integer optyId = 123;
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponseEntity = ResponseEntity.ok(expectedResponse);
		when(opportunityService.reactiveOpty(optyId)).thenReturn(expectedResponseEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = opportunityController.reactiveOpty(optyId);
		verify(opportunityService).reactiveOpty(optyId);
		assertEquals(expectedResponseEntity, responseEntity);
	}
}
