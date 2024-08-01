package ai.rnt.crm.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.dto.CountryDto;
import ai.rnt.crm.dto.StateDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.util.AuditAwareUtil;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

	@InjectMocks
	private CompanyServiceImpl companyServiceImpl;

	@Mock
	private CompanyMasterDaoService companyMasterDaoService;

	@Mock
	private CountryDaoService countryDaoService;

	@Mock
	private StateDaoService stateDaoService;

	@Mock
	private CityDaoService cityDaoService;

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@Mock
	private ContactDaoService contactDaoService;

	private final ZoneId systemDefault = ZoneId.systemDefault();
	private final ZoneId indiaZone = ZoneId.of("Asia/Kolkata");

	@Test
	void testCompanyListSuccess() {
		List<CompanyDto> mockCompanyList = Collections.singletonList(new CompanyDto());
		when(companyMasterDaoService.findAllCompanies()).thenReturn(mockCompanyList);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = companyServiceImpl.companyList();
		assertEquals(OK, responseEntity.getStatusCode());
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertTrue((Boolean) responseBody.get(ApiResponse.SUCCESS));
		assertEquals(mockCompanyList, responseBody.get(ApiResponse.DATA));
		verify(companyMasterDaoService, times(1)).findAllCompanies();
	}

	@Test
	void testCompanyListException() {
		when(companyMasterDaoService.findAllCompanies()).thenThrow(new RuntimeException("Test Exception"));
		assertThrows(CRMException.class, () -> companyServiceImpl.companyList());
	}

	@Test
	void testGetCompanySuccess() {
		Integer companyId = 1;
		CompanyMaster mockCompany = new CompanyMaster();
		when(companyMasterDaoService.findCompanyById(companyId)).thenReturn(Optional.of(mockCompany));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = companyServiceImpl.getCompany(companyId);
		assertEquals(OK, responseEntity.getStatusCode());
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertTrue((Boolean) responseBody.get(ApiResponse.SUCCESS));
		verify(companyMasterDaoService, times(1)).findCompanyById(companyId);
	}

	@Test
	void testGetCompanyException() {
		Integer companyId = 1;
		when(companyMasterDaoService.findCompanyById(companyId)).thenThrow(new RuntimeException("Test Exception"));
		assertThrows(CRMException.class, () -> companyServiceImpl.getCompany(companyId));
	}

	@Test
	void testAddCompanyAlreadyPresent() {
		CompanyDto dto = new CompanyDto();
		dto.setCompanyName("Test Company");
		CountryDto country = new CountryDto();
		country.setCountryId(1);
		dto.setCountry(country);
		StateDto state = new StateDto();
		state.setStateId(1);
		dto.setState(state);
		when(companyMasterDaoService.isCompanyPresent(anyString(), anyInt(), anyInt())).thenReturn(true);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = companyServiceImpl.addCompany(dto);
		assertEquals(OK, responseEntity.getStatusCode());
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertFalse((Boolean) responseBody.get(ApiResponse.SUCCESS));
		assertEquals("This Company Is Already Present !!", responseBody.get(ApiResponse.MESSAGE));
		verify(companyMasterDaoService, times(1)).isCompanyPresent(anyString(), anyInt(), anyInt());
	}

	@Test
	void testAddCompanySuccess() {
		CompanyDto dto = new CompanyDto();
		dto.setCompanyName("Test Company");
		CountryDto country = new CountryDto();
		country.setCountryId(1);
		dto.setCountry(country);
		StateDto state = new StateDto();
		state.setStateId(1);
		dto.setState(state);
		when(companyMasterDaoService.isCompanyPresent(anyString(), anyInt(), anyInt())).thenReturn(false);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = companyServiceImpl.addCompany(dto);
		assertEquals(CREATED, responseEntity.getStatusCode());
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertTrue((Boolean) responseBody.get(ApiResponse.SUCCESS));
		assertEquals("Company Added Successfully", responseBody.get(ApiResponse.MESSAGE));
		verify(companyMasterDaoService, times(1)).isCompanyPresent(anyString(), anyInt(), anyInt());
	}

	@Test
	void testAddCompanyException() {
		CompanyDto dto = new CompanyDto();
		dto.setCompanyName("Test Company");
		CountryDto country = new CountryDto();
		country.setCountryId(1);
		dto.setCountry(country);
		StateDto state = new StateDto();
		state.setStateId(1);
		dto.setState(state);
		when(companyMasterDaoService.isCompanyPresent(anyString(), anyInt(), anyInt()))
				.thenThrow(new RuntimeException("Database error"));
		CRMException exception = assertThrows(CRMException.class, () -> {
			companyServiceImpl.addCompany(dto);
		});
		assertEquals("Database error", exception.getCause().getMessage());
		verify(companyMasterDaoService, times(1)).isCompanyPresent(anyString(), anyInt(), anyInt());
	}

	@Test
	void testUpdateCompanySuccess() throws Exception {
		CompanyDto dto = new CompanyDto();
		CountryDto countryDto = new CountryDto();
		StateDto stateDto = new StateDto();
		stateDto.setStateId(1);
		CityDto cityDto = new CityDto();
		cityDto.setCityId(1);
		countryDto.setCountryId(1);
		dto.setCountry(countryDto);
		dto.setState(stateDto);
		dto.setCity(cityDto);
		int companyId = 1;
		CompanyMaster companyMaster = new CompanyMaster();

		when(companyMasterDaoService.findCompanyById(companyId)).thenReturn(Optional.of(companyMaster));
		when(countryDaoService.findCountryById(dto.getCountry().getCountryId()))
				.thenReturn(Optional.of(new CountryMaster()));
		when(stateDaoService.findStateById(dto.getState().getStateId())).thenReturn(Optional.of(new StateMaster()));
		when(cityDaoService.findCityById(dto.getCity().getCityId())).thenReturn(Optional.of(new CityMaster()));
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = companyServiceImpl.updateCompany(dto, companyId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertTrue((Boolean) responseBody.get(ApiResponse.SUCCESS));
		assertEquals("Company Updated Successfully", responseBody.get(ApiResponse.MESSAGE));
		verify(companyMasterDaoService, times(1)).findCompanyById(companyId);
		verify(countryDaoService, times(1)).findCountryById(dto.getCountry().getCountryId());
		verify(stateDaoService, times(1)).findStateById(dto.getState().getStateId());
		verify(cityDaoService, times(1)).findCityById(dto.getCity().getCityId());
		verify(companyMasterDaoService, times(1)).save(companyMaster);
	}

	@Test
	void testUpdateCompanyException() {
		Integer companyId = 1;
		CompanyDto dto = new CompanyDto();
		when(companyMasterDaoService.findCompanyById(companyId)).thenThrow(new RuntimeException("Test Exception"));
		assertThrows(CRMException.class, () -> companyServiceImpl.updateCompany(dto, companyId));
	}

	@Test
	void testDeleteCompanySuccess() throws Exception {
		int companyId = 1;
		int loggedInStaffId = 123;
		ZonedDateTime now = ZonedDateTime.now(systemDefault).withZoneSameInstant(indiaZone);
		LocalDateTime deletedDate = now.toLocalDateTime();
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		expectedResponse.put(ApiResponse.SUCCESS, true);
		expectedResponse.put(ApiResponse.MESSAGE, "Company Deleted Successfully");
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(loggedInStaffId);
		CompanyMaster companyMaster = new CompanyMaster();
		when(companyMasterDaoService.findCompanyById(companyId)).thenReturn(Optional.of(companyMaster));
		when(contactDaoService.findByCompanyId(companyId)).thenReturn(Collections.emptyList());
		companyMaster.setDeletedBy(loggedInStaffId);
		companyMaster.setDeletedDate(deletedDate);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = companyServiceImpl.deleteCompany(companyId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertTrue((Boolean) responseBody.get(ApiResponse.SUCCESS));
		assertEquals("Company Deleted Successfully", responseBody.get(ApiResponse.MESSAGE));
		verify(auditAwareUtil, times(1)).getLoggedInStaffId();
		verify(companyMasterDaoService, times(1)).findCompanyById(companyId);
		verify(contactDaoService, times(1)).findByCompanyId(companyId);
		verify(companyMasterDaoService, times(1)).save(companyMaster);
	}

	@Test
	void testDeleteCompanyCompanyInUse() {
		int companyId = 1;
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		expectedResponse.put(ApiResponse.SUCCESS, false);
		expectedResponse.put(ApiResponse.MESSAGE, "You Can't Delete This Company Is In Use!!");
		CompanyMaster companyMaster = new CompanyMaster();
		when(companyMasterDaoService.findCompanyById(companyId)).thenReturn(Optional.of(companyMaster));
		when(contactDaoService.findByCompanyId(companyId)).thenReturn(Collections.singletonList(new Contacts())); // Simulating
																													// company
																													// in
																													// use
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = companyServiceImpl.deleteCompany(companyId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertFalse((Boolean) responseBody.get(ApiResponse.SUCCESS));
		assertEquals("This company is in use, You can't delete.", responseBody.get(ApiResponse.MESSAGE));
		verify(companyMasterDaoService, times(1)).findCompanyById(companyId);
		verify(contactDaoService, times(1)).findByCompanyId(companyId);
	}

	@Test
	void testDeleteCompanyException() {
		Integer companyId = 1;
		when(companyMasterDaoService.findCompanyById(companyId)).thenThrow(new RuntimeException("Test Exception"));
		assertThrows(CRMException.class, () -> companyServiceImpl.deleteCompany(companyId));
	}
}
