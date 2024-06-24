package ai.rnt.crm.service.impl;

import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.dto.StateDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.util.AuditAwareUtil;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

	@Mock
	private CityDaoService cityDaoService;

	@Mock
	private AuditAwareUtil auditAwareUtil;

	@Mock
	private CompanyMasterDaoService companyMasterDaoService;

	@Mock
	private StateDaoService stateDaoService;

	@InjectMocks
	private CityServiceImpl cityServiceImpl;

	@Mock
	private StateMaster stateMaster;

	@Mock
	private CountryMaster countryMaster;

	@Mock
	private CityMaster cityMaster;

	@Test
	void getAllCityTest() {
		CityMaster city1 = new CityMaster();
		CityMaster city2 = new CityMaster();
		List<CityMaster> cities = new ArrayList<>();
		cities.add(city2);
		cities.add(city1);
		when(cityDaoService.getAllCity()).thenReturn(cities);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = cityServiceImpl.getAllCity();
		assertEquals(OK, responseEntity.getStatusCode());
		EnumMap<ApiResponse, Object> responseBody = responseEntity.getBody();
		assertEquals(true, responseBody.get(SUCCESS));
	}

	@Test
    void getAllCityTestException() {
        when(cityDaoService.getAllCity()).thenThrow(new RuntimeException("Test Exception"));
       assertThrows(CRMException.class, () -> cityServiceImpl.getAllCity());
    }

	@Test
	void testAddCityCityAlreadyPresent() {
		CityDto cityDto = new CityDto();
		StateDto stateDto = new StateDto();
		cityDto.setCity("Mumbai");
		cityDto.setState(stateDto);
		when(cityDaoService.isCityPresent(cityDto.getCity(), cityDto.getState().getStateId())).thenReturn(true);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = cityServiceImpl.addCity(cityDto);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("This City Is Already Present !!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testAddCitySuccess() {
		CityDto cityDto = new CityDto();
		StateDto stateDto = new StateDto();
		StateMaster stateMaster = new StateMaster();
		stateMaster.setState("MAharashtra");
		cityDto.setCity("Mumbai");
		cityDto.setState(stateDto);

		CityMaster cityMaster = new CityMaster();
		cityMaster.setCityId(1);
		cityMaster.setCity("Mumbai");
		cityMaster.setState(stateMaster);

		when(cityDaoService.isCityPresent(cityDto.getCity(), cityDto.getState().getStateId())).thenReturn(false);
		when(cityDaoService.addCity(any())).thenReturn(cityMaster);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = cityServiceImpl.addCity(cityDto);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("City Added Successfully", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testAddCityCityNotAdded() {
		CityDto cityDto = new CityDto();
		StateDto stateDto = new StateDto();
		cityDto.setCity("Mumbai");
		cityDto.setState(stateDto);
		when(cityDaoService.isCityPresent(cityDto.getCity(), cityDto.getState().getStateId())).thenReturn(false);
		when(cityDaoService.addCity(any())).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = cityServiceImpl.addCity(cityDto);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("City Not Added", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testAddCityException() {
		CityDto cityDto = new CityDto();
		assertThrows(CRMException.class, () -> cityServiceImpl.addCity(cityDto));
	}

	@Test
    void testGetCitySuccess() {
	 when(cityDaoService.findCityById(1)).thenReturn(Optional.of(new CityMaster()));
       ResponseEntity<EnumMap<ApiResponse, Object>> response = cityServiceImpl.getCity(1);
       assertNotNull(response);
       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
   }

	@Test
	void testGetCityException() {
		int cityId = 1;
		when(cityDaoService.findCityById(cityId)).thenThrow(new RuntimeException("Database error"));
		CRMException exception = assertThrows(CRMException.class, () -> cityServiceImpl.getCity(cityId));
		assertEquals("Database error", exception.getCause().getMessage());
	}

	@Test
	void testUpdateCitySuccess() {
		CityDto cityDto = new CityDto();
		StateDto stateDto = new StateDto();
		stateDto.setState("Maharashtra");
		stateDto.setStateId(1);
		cityDto.setCity("Pune");
		cityDto.setCityId(1);
		cityDto.setState(stateDto);
		when(cityDaoService.findCityById(1)).thenReturn(Optional.of(cityMaster));
		when(stateDaoService.findStateById(1)).thenReturn(Optional.of(stateMaster));
		when(cityDaoService.addCity(cityMaster)).thenReturn(cityMaster);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = cityServiceImpl.updateCity(cityDto);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("City Updated Successfully", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testUpdateCityException() {
		CityDto cityDto = new CityDto();
		assertThrows(CRMException.class, () -> cityServiceImpl.updateCity(cityDto));
	}

	@Test
	void testDeleteCitySuccess() {
		int cityId = 1;
		int loggedInStaffId = 100;
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(loggedInStaffId);
		when(cityDaoService.findCityById(cityId)).thenReturn(Optional.of(cityMaster));
		when(cityDaoService.addCity(any(CityMaster.class))).thenReturn(cityMaster);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = cityServiceImpl.deleteCity(cityId);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("City Deleted Successfully", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testDeleteCityCannotDelete() {
		int cityId = 1;
		when(cityDaoService.findCityById(cityId)).thenReturn(Optional.of(cityMaster));
		when(companyMasterDaoService.findByCityId(cityId)).thenReturn(Collections.singletonList(new CompanyMaster()));
		ResponseEntity<EnumMap<ApiResponse, Object>> response = cityServiceImpl.deleteCity(cityId);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("You Can't Delete This City Is In Use!!", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void testDeleteCityException() {
		int cityId = 1;
		when(cityDaoService.findCityById(cityId)).thenThrow(new RuntimeException("Database error"));
		CRMException exception = assertThrows(CRMException.class, () -> {
			cityServiceImpl.deleteCity(cityId);
		});
		assertEquals("Database error", exception.getCause().getMessage());
	}
}