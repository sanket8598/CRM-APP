package ai.rnt.crm.api.restcontroller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.DashboardService;

class DashboardControllerTest {

	@Mock
	private DashboardService dashboardService;

	@InjectMocks
	private DashboardController dashboardController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void getDashboardDataTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(dashboardService.getDashboardData("Lead")).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = dashboardController.getDashboardData("Lead");
		verify(dashboardService).getDashboardData("Lead");
		assertEquals(expectedResponse, response);
	}

	@Test
	void getUpComingSectionDataTest() {
		String field = "someField";
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(dashboardService.getUpComingSectionData(field)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = dashboardController.getUpComingSectionData(field);
		verify(dashboardService).getUpComingSectionData(field);
		assertEquals(expectedResponse, response);
	}
}
