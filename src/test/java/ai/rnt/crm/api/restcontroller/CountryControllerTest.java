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

import ai.rnt.crm.dto.CountryDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CountryService;

class CountryControllerTest {

	@Mock
	private CountryService countryService;

	@InjectMocks
	private CountryController countryController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void getAllCountryTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(countryService.getAllCountry()).thenReturn(expectedResponse);
		countryController.getAllCountry();
		verify(countryService).getAllCountry();
	}

	@Test
	void addCountryTest() {
		CountryDto dto = new CountryDto();
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(countryController.addCountry(dto)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = countryController.addCountry(dto);
		assertSame(expectedResponse, responseEntity);
		verify(countryService).addCountry(dto);
		verifyNoMoreInteractions(countryService);
	}

	@Test
	void testGetCountry() {
		Integer countryId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(countryService.getCountry(countryId)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = countryController.getCountry(countryId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertSame(expectedResponse, responseEntity);
		verify(countryService).getCountry(countryId);
		verifyNoMoreInteractions(countryService);
	}

	@Test
	void testUpdateCountry() {
		Integer countryId = 1;
		CountryDto dto = new CountryDto();
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(countryService.updateCountry(dto, countryId)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = countryController.updateCountry(dto, countryId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertSame(expectedResponse, responseEntity);
		verify(countryService).updateCountry(dto, countryId);
		verifyNoMoreInteractions(countryService);
	}

	@Test
	void testDeleteCountry() {
		Integer countryId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(countryService.deleteCountry(countryId)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = countryController.deleteCountry(countryId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertSame(expectedResponse, responseEntity);
		verify(countryService).deleteCountry(countryId);
		verifyNoMoreInteractions(countryService);
	}
}
