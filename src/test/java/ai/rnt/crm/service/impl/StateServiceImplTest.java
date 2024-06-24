package ai.rnt.crm.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dto.CountryDto;
import ai.rnt.crm.dto.StateDto;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.util.AuditAwareUtil;

class StateServiceImplTest {

	@Mock
	private StateDaoService stateDaoService;

	@Mock
	private CountryDaoService countryDaoService;

	@Mock
	private CompanyMasterDaoService companyMasterDaoService;

	@Mock
	private CityDaoService cityDaoService;

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@InjectMocks
	private StateServiceImpl stateServiceImpl;

	@Mock
	private CountryMaster countryMaster;

	@Mock
	private StateMaster stateMaster;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllState_Success() {
		ResponseEntity<EnumMap<ApiResponse, Object>> response = stateServiceImpl.getAllState();
		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		EnumMap<ApiResponse, Object> responseBody = response.getBody();
		assertEquals(true, responseBody.get(ApiResponse.SUCCESS));
	}

	@Test
    void getAllStateExceptionTest() {
        when(stateServiceImpl.getAllState()).thenThrow(new RuntimeException("Test Exception"));
        assertThrows(CRMException.class, () -> stateServiceImpl.getAllState());
    }

	@Test
	void testAddStateStateAlreadyPresent() {
		StateDto stateDto = new StateDto();
		CountryDto countryDto = new CountryDto();
		stateDto.setState("NewYork");
		countryDto.setCountry("India");
		countryDto.setCountryId(1);
		stateDto.setCountry(countryDto);
		when(stateDaoService.isStatePresent(stateDto.getState(), stateDto.getCountry().getCountryId()))
				.thenReturn(true);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = stateServiceImpl.addState(stateDto);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("This State Is Already Present !!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testAddStateAddedSuccessfully() {
		StateDto stateDto = new StateDto();
		stateDto.setState("Maharashtra");
		CountryDto countryDto = new CountryDto();
		countryDto.setCountry("India");
		countryDto.setCountryId(1);
		stateDto.setCountry(countryDto);
		StateMaster stateMaster = mock(StateMaster.class);
		when(stateDaoService.addState(any(StateMaster.class))).thenReturn(stateMaster);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = stateServiceImpl.addState(stateDto);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("State Added Successfully", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testAddStateNotAdded() {
		StateMaster stateMaster = mock(StateMaster.class);
		StateDto stateDto = new StateDto();
		stateDto.setState("Maharashtra");
		CountryDto countryDto = new CountryDto();
		countryDto.setCountry("India");
		countryDto.setCountryId(1);
		stateDto.setCountry(countryDto);
		when(stateDaoService.isStatePresent("Maharashtra", 1)).thenReturn(false);
		when(stateDaoService.addState(stateMaster)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = stateServiceImpl.addState(stateDto);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("State Not Added", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testAddStateException() {
		StateDto stateDto = new StateDto();
		stateDto.setState("Maharashtra");
		CountryDto countryDto = new CountryDto();
		countryDto.setCountry("India");
		countryDto.setCountryId(1);
		stateDto.setCountry(countryDto);
		when(stateDaoService.isStatePresent("Maharashtra", 1)).thenThrow(new RuntimeException("Database error"));
		CRMException exception = assertThrows(CRMException.class, () -> {
			stateServiceImpl.addState(stateDto);
		});
		assertEquals("Database error", exception.getCause().getMessage());
	}

	@Test
    void testGetStateSuccess() {
	 when(stateDaoService.findStateById(1)).thenReturn(Optional.of(new StateMaster()));
       ResponseEntity<EnumMap<ApiResponse, Object>> response = stateServiceImpl.getState(1);
       assertNotNull(response);
       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
   }

	@Test
	void testGetStateException() {
		int stateId = 1;
		when(stateDaoService.findStateById(stateId)).thenThrow(new RuntimeException("Database error"));
		CRMException exception = assertThrows(CRMException.class, () -> stateServiceImpl.getState(stateId));
		assertEquals("Database error", exception.getCause().getMessage());
	}

	@Test
	void testUpdateStateSuccess() {
		StateDto stateDto = new StateDto();
		CountryDto countryDto = new CountryDto();
		countryDto.setCountry("India");
		countryDto.setCountryId(1);
		stateDto.setState("Maharashtra");
		stateDto.setStateId(1);
		stateDto.setCountry(countryDto);
		when(stateDaoService.findStateById(1)).thenReturn(Optional.of(stateMaster));
		when(countryDaoService.findCountryById(1)).thenReturn(Optional.of(countryMaster));
		when(stateDaoService.addState(stateMaster)).thenReturn(stateMaster);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = stateServiceImpl.updateState(stateDto);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("State Updated Successfully", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testUpdateStateException() {
		StateDto stateDto = new StateDto();
		assertThrows(CRMException.class, () -> stateServiceImpl.updateState(stateDto));
	}

	@Test
	void testDeleteStateSuccess() {
		int stateId = 1;
		int loggedInStaffId = 100;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(loggedInStaffId);
		when(stateDaoService.findStateById(stateId)).thenReturn(Optional.of(stateMaster));
		when(companyMasterDaoService.findByCountryId(stateId)).thenReturn(Collections.emptyList());
		when(cityDaoService.findByStateId(stateId)).thenReturn(Collections.emptyList());
		when(stateDaoService.addState(any(StateMaster.class))).thenReturn(stateMaster);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = stateServiceImpl.deleteState(stateId);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("State Deleted Successfully", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testDeleteStateCannotDelete() {
		int stateId = 1;
		when(stateDaoService.findStateById(stateId)).thenReturn(Optional.of(stateMaster));
		when(companyMasterDaoService.findByStateId(stateId)).thenReturn(Collections.singletonList(new CompanyMaster()));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = stateServiceImpl.deleteState(stateId);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("You Can't Delete This State Is In Use!!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testDeleteStateException() {
		int stateId = 1;
		when(stateDaoService.findStateById(stateId)).thenThrow(new RuntimeException("Database error"));
		CRMException exception = assertThrows(CRMException.class, () -> {
			stateServiceImpl.deleteState(stateId);
		});
		assertEquals("Database error", exception.getCause().getMessage());
	}
}
