package ai.rnt.crm.api.restcontroller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.CityDto;
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
		cityController.getAllCity();
		verify(cityService).getAllCity();
	}

	@Test
	void testAddCity() {
		CityDto dto = new CityDto();
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(cityService.addCity(dto)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = cityController.addCity(dto);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertSame(expectedResponse, responseEntity);
		verify(cityService).addCity(dto);
		verifyNoMoreInteractions(cityService);
	}

	@Test
	void testGetCity() {
		Integer cityId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(cityService.getCity(cityId)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = cityController.getCity(cityId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertSame(expectedResponse, responseEntity);
		verify(cityService).getCity(cityId);
		verifyNoMoreInteractions(cityService);
	}

	@Test
	void testUpdateCity() {
		CityDto dto = new CityDto();
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(cityService.updateCity(dto)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = cityController.updateCity(dto);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertSame(expectedResponse, responseEntity);
		verify(cityService).updateCity(dto);
		verifyNoMoreInteractions(cityService);
	}

	@Test
	void testDeleteCity() {
		Integer cityId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(cityService.deleteCity(cityId)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = cityController.deleteCity(cityId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertSame(expectedResponse, responseEntity);
		verify(cityService).deleteCity(cityId);
		verifyNoMoreInteractions(cityService);
	}
}
