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

import ai.rnt.crm.dto.opportunity.QualifyOpportunityDto;
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
		Integer opportunityId = 1;
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.getQualifyPopUpData(opportunityId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.getQualifyPopUpData(opportunityId);
		verify(opportunityService).getQualifyPopUpData(opportunityId);
	}

	@Test
	void updateQualifyPopUpDataTest() {
		OpportunityService opportunityService = mock(OpportunityService.class);
		Integer opportunityId = 1;
		QualifyOpportunityDto dto = new QualifyOpportunityDto();
		OpportunityController controller = new OpportunityController(opportunityService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(opportunityService.updateQualifyPopUpData(dto, opportunityId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.updateQualifyPopUpData(dto, opportunityId);
		verify(opportunityService).updateQualifyPopUpData(dto, opportunityId);
	}
}
