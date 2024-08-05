package ai.rnt.crm.service.impl;

import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.CurrencyDaoService;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dto.CountryDto;
import ai.rnt.crm.dto.CurrencyDto;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.util.AuditAwareUtil;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

	@InjectMocks
	private CountryServiceImpl countryServiceImpl;

	@Mock
	private CountryDaoService countryDaoService;

	@Mock
	private CurrencyDaoService currencyDaoService;;

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@Mock
	private CompanyMasterDaoService companyMasterDaoService;

	@Mock
	private StateDaoService stateDaoService;

	@Mock
	CountryMaster countryMaster;

	@Test
	void testGetAllCountrySuccess() {
		CountryMaster city1 = new CountryMaster();
		CountryMaster city2 = new CountryMaster();
		List<CountryMaster> cities = new ArrayList<>();
		cities.add(city2);
		cities.add(city1);
		when(countryDaoService.getAllCountry()).thenReturn(cities);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = countryServiceImpl.getAllCountry();
		assertEquals(OK, responseEntity.getStatusCode());
		assertEquals(true, responseEntity.getBody().get(SUCCESS));
	}

	@Test
	    void getAllCountryException() {
	        when(countryDaoService.getAllCountry()).thenThrow(new RuntimeException("Test Exception"));
	        assertThrows(CRMException.class, () -> countryServiceImpl.getAllCountry());
	    }

	@Test
	void testAddCountryCountryAlreadyPresent() {
		CountryDto countryDto = new CountryDto();
		countryDto.setCountry("India");
		when(countryDaoService.isCountryPresent(countryDto.getCountry())).thenReturn(true);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = countryServiceImpl.addCountry(countryDto);
		assertNotNull(response);
		assertEquals(OK, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(SUCCESS));
		assertEquals("This Country Is Already Present !!", response.getBody().get(MESSAGE));
	}

	@Test
	void testAddCountryCountryAddedSuccessfully() {
		CountryDto countryDto = new CountryDto();
		CurrencyDto currencyDto = new CurrencyDto();
		currencyDto.setCurrencyId(1);
		currencyDto.setCurrencyName("USD");
		currencyDto.setCurrencySymbol("$");
		countryDto.setCurrency(currencyDto);
		countryDto.setCountry("India");
		CountryMaster countryMaster = mock(CountryMaster.class);
		when(countryDaoService.addCountry(any(CountryMaster.class))).thenReturn(countryMaster);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = countryServiceImpl.addCountry(countryDto);
		assertNotNull(response);
		assertEquals(CREATED, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(SUCCESS));
		assertEquals("Country Added Successfully !!", response.getBody().get(MESSAGE));
	}

	@Test
	void testAddCountryCountryNotAdded() {
		CountryDto countryDto = new CountryDto();
		countryDto.setCountry("India");
		when(countryDaoService.isCountryPresent("India")).thenReturn(false);
		when(countryDaoService.addCountry(any(CountryMaster.class))).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = countryServiceImpl.addCountry(countryDto);
		assertNotNull(response);
		assertEquals(CREATED, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(SUCCESS));
		assertEquals("Country Not Added !!", response.getBody().get(MESSAGE));
	}

	@Test
	void testAddCountryException() {
		CountryDto countryDto = new CountryDto();
		assertThrows(CRMException.class, () -> countryServiceImpl.addCountry(countryDto));
	}

	@Test
	     void testGetCountrySuccess() {
		 when(countryDaoService.findCountryById(1)).thenReturn(Optional.of(new CountryMaster()));
	        ResponseEntity<EnumMap<ApiResponse, Object>> response = countryServiceImpl.getCountry(1);
	        assertNotNull(response);
	        assertEquals(OK, response.getStatusCode());
	        assertTrue((Boolean) response.getBody().get(SUCCESS));
	    }

	@Test
	void testGetCountryException() {
		int countryId = 1;
		when(countryDaoService.findCountryById(countryId)).thenThrow(new RuntimeException("Database error"));
		CRMException exception = assertThrows(CRMException.class, () -> countryServiceImpl.getCountry(countryId));
		assertEquals("Database error", exception.getCause().getMessage());
	}

	@Test
	void testUpdateCountrySuccess() {
		CountryDto countryDto = new CountryDto();
		CurrencyDto currencyDto = new CurrencyDto();
		currencyDto.setCurrencyId(1);
		currencyDto.setCurrencyName("USD");
		currencyDto.setCurrencySymbol("$");
		countryDto.setCurrency(currencyDto);
		countryDto.setCountry("India");
		when(countryDaoService.findCountryById(1)).thenReturn(Optional.of(countryMaster));
		when(countryDaoService.addCountry(any(CountryMaster.class))).thenReturn(countryMaster);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = countryServiceImpl.updateCountry(countryDto, 1);
		assertNotNull(response);
		assertEquals(CREATED, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(SUCCESS));
		assertEquals("Country Updated Successfully", response.getBody().get(MESSAGE));
	}

	@Test
	void testUpdateCountryElse() {
		CountryDto countryDto = mock(CountryDto.class);
		CurrencyDto currencyDto = new CurrencyDto();
		currencyDto.setCurrencyId(1);
		countryDto.setCountryId(1);
		currencyDto.setCurrencyName("USD");
		currencyDto.setCurrencySymbol("$");
		countryDto.setCurrency(currencyDto);
		countryDto.setCountry("India");
		when(countryDaoService.findCountryById(1)).thenReturn(Optional.of(countryMaster));
		when(countryDaoService.addCountry(any(CountryMaster.class))).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = countryServiceImpl.updateCountry(countryDto, 1);
		assertNotNull(response);
		assertEquals("Country Not Update.", response.getBody().get(MESSAGE));
	}

	@Test
	void testUpdateCountryIfBlock() {
		CountryDto countryDto = new CountryDto();
		CurrencyDto currencyDto = new CurrencyDto();
		currencyDto.setCurrencyId(1);
		currencyDto.setCurrencyName("USD");
		currencyDto.setCurrencySymbol("$");
		countryDto.setCurrency(currencyDto);
		countryDto.setCountry("India");
		when(countryDaoService.isCountryPresent(countryDto.getCountry(), 1)).thenReturn(true);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = countryServiceImpl.updateCountry(countryDto, 1);
		assertNotNull(response);
		assertEquals(OK, response.getStatusCode());
	}

	@Test
	void testUpdateCountryException() {
		CountryDto countryDto = new CountryDto();
		countryDto.setCountry("India");
		when(countryDaoService.findCountryById(1)).thenThrow(new RuntimeException("Database error"));
		CRMException exception = assertThrows(CRMException.class, () -> {
			countryServiceImpl.updateCountry(countryDto, 1);
		});
		assertEquals("Database error", exception.getCause().getMessage());
	}

	@Test
	void testDeleteCountrySuccess() {
		int countryId = 1;
		int loggedInStaffId = 100;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(loggedInStaffId);
		when(countryDaoService.findCountryById(countryId)).thenReturn(Optional.of(countryMaster));
		when(companyMasterDaoService.findByCountryId(countryId)).thenReturn(Collections.emptyList());
		when(stateDaoService.findByCountryId(countryId)).thenReturn(Collections.emptyList());
		when(countryDaoService.addCountry(any(CountryMaster.class))).thenReturn(countryMaster);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = countryServiceImpl.deleteCountry(countryId);
		assertNotNull(response);
		assertEquals(OK, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(SUCCESS));
		assertEquals("Country Deleted Successfully", response.getBody().get(MESSAGE));
	}
	
	@Test
	void testDeleteCountryElse() {
		int countryId = 1;
		int loggedInStaffId = 100;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(loggedInStaffId);
		when(countryDaoService.findCountryById(countryId)).thenReturn(Optional.of(countryMaster));
		when(companyMasterDaoService.findByCountryId(countryId)).thenReturn(Collections.emptyList());
		when(stateDaoService.findByCountryId(countryId)).thenReturn(Collections.emptyList());
		when(countryDaoService.addCountry(any(CountryMaster.class))).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = countryServiceImpl.deleteCountry(countryId);
		assertNotNull(response);
		assertEquals(OK, response.getStatusCode());
		assertEquals("Country Not Delete.", response.getBody().get(MESSAGE));
	}

	@Test
	void testDeleteCountryCannotDelete() {
		int countryId = 1;
		when(countryDaoService.findCountryById(countryId)).thenReturn(Optional.of(countryMaster));
		when(companyMasterDaoService.findByCountryId(countryId))
				.thenReturn(Collections.singletonList(new CompanyMaster()));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = countryServiceImpl.deleteCountry(countryId);
		assertNotNull(response);
		assertEquals(OK, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(SUCCESS));
		assertEquals("This country is in use, You can't delete.", response.getBody().get(MESSAGE));
	}

	@Test
	void testDeleteCountryException() {
		int countryId = 1;
		when(countryDaoService.findCountryById(countryId)).thenThrow(new RuntimeException("Database error"));
		CRMException exception = assertThrows(CRMException.class, () -> {
			countryServiceImpl.deleteCountry(countryId);
		});
		assertEquals("Database error", exception.getCause().getMessage());
	}
}
