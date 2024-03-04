package ai.rnt.crm.api.restcontroller;

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
import ai.rnt.crm.service.CityService;

class CityControllerTest {

	@Mock
	private CityService cityService;

	@InjectMocks
	private CityController cityController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void getAllCityTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(cityService.getAllCity()).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = cityController.getAllCity();
		verify(cityService).getAllCity();
	}
}
