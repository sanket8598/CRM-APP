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
import ai.rnt.crm.service.CompanyService;

class CompanyControllerTest {

	@Mock
	private CompanyService companyService;

	@InjectMocks
	private CompanyController companyController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void getAllCompanyTest() {
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(companyService.getAllCompany()).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = companyController.getAllCompany();
		verify(companyService).getAllCompany();
	}
}
