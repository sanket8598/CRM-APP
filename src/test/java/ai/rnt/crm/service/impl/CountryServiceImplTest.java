package ai.rnt.crm.service.impl;

import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

	@InjectMocks
	private CountryServiceImpl countryServiceImpl;

	@Mock
	private CountryDaoService countryDaoService;

	@Test
	    void getAllCountry_Success() {
		CountryMaster city1=new CountryMaster();
		CountryMaster city2=new CountryMaster();
		List<CountryMaster> cities=new ArrayList<>();
		cities.add(city2);
		cities.add(city1);
	        when(countryDaoService.getAllCountry()).thenReturn(cities);
	        ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = countryServiceImpl.getAllCountry();
	        assertEquals(OK, responseEntity.getStatusCode());
	        assertEquals(true, responseEntity.getBody().get(SUCCESS));
	    }

	@Test
	    void getAllCountry_Exception() {
	        when(countryDaoService.getAllCountry()).thenThrow(new RuntimeException("Test Exception"));
	        assertThrows(CRMException.class, () -> countryServiceImpl.getAllCountry());
	    }
}
