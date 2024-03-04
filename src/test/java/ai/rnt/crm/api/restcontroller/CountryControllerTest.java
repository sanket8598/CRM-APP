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
		ResponseEntity<EnumMap<ApiResponse, Object>> response = countryController.getAllCountry();
		verify(countryService).getAllCountry();
	}
}
