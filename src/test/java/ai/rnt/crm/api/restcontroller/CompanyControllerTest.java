package ai.rnt.crm.api.restcontroller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.CompanyDto;
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
	void testCompanyList() {
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		expectedResponse.put(ApiResponse.SUCCESS, "Sample Data");
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = new ResponseEntity<>(expectedResponse,
				HttpStatus.OK);
		when(companyService.companyList()).thenReturn(responseEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> actualResponse = companyController.companyList();
		assertEquals(responseEntity, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(expectedResponse, actualResponse.getBody());
		verify(companyService, times(1)).companyList();
	}

	@Test
	void testGetCompany() {
		Integer companyId = 1;
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		expectedResponse.put(ApiResponse.SUCCESS, "Sample Company Data");
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = new ResponseEntity<>(expectedResponse,
				HttpStatus.OK);
		when(companyService.getCompany(companyId)).thenReturn(responseEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> actualResponse = companyController.getCompany(companyId);
		assertEquals(responseEntity, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(expectedResponse, actualResponse.getBody());
		verify(companyService, times(1)).getCompany(companyId);
	}

	@Test
	void testAddCompany() {
		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyName("Sample Company");
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		expectedResponse.put(ApiResponse.SUCCESS, "Company added successfully");
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = new ResponseEntity<>(expectedResponse,
				HttpStatus.CREATED);
		when(companyService.addCompany(companyDto)).thenReturn(responseEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> actualResponse = companyController.addCompany(companyDto);
		assertEquals(responseEntity, actualResponse);
		assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
		assertEquals(expectedResponse, actualResponse.getBody());
		verify(companyService, times(1)).addCompany(companyDto);
	}

	@Test
	void testUpdateCompany() {
		Integer companyId = 1;
		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyName("Updated Company");
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		expectedResponse.put(ApiResponse.SUCCESS, "Company updated successfully");
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = new ResponseEntity<>(expectedResponse,
				HttpStatus.OK);
		when(companyService.updateCompany(companyDto, companyId)).thenReturn(responseEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> actualResponse = companyController.updateCompany(companyDto,
				companyId);
		assertEquals(responseEntity, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(expectedResponse, actualResponse.getBody());
		verify(companyService, times(1)).updateCompany(companyDto, companyId);
	}

	@Test
	void testDeleteCompany() {
		Integer companyId = 1;
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		expectedResponse.put(ApiResponse.SUCCESS, "Company deleted successfully");
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = new ResponseEntity<>(expectedResponse,
				HttpStatus.OK);
		when(companyService.deleteCompany(companyId)).thenReturn(responseEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> actualResponse = companyController.deleteCompany(companyId);
		assertEquals(responseEntity, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(expectedResponse, actualResponse.getBody());
		verify(companyService, times(1)).deleteCompany(companyId);
	}
}
