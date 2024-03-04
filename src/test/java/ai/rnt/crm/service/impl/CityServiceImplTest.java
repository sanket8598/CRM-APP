package ai.rnt.crm.service.impl;

import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

	@Mock
	private CityDaoService cityDaoService;

	@InjectMocks
	private CityServiceImpl cityServiceImpl;

	@Test
    void getAllCityTest() {
        when(cityDaoService.getAllCity()).thenReturn(of());
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
}
