package ai.rnt.crm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;

@ExtendWith(MockitoExtension.class)
class ServiceFallsServiceImplTest {

	@Mock
	private ServiceFallsDaoSevice serviceFallsDaoSevice;

	@InjectMocks
	private ServiceFallsServiceImpl serviceFallsServiceImpl;

	@Test
     void getAllServiceFallsTest() {
		List<ServiceFallsMaster> master = new ArrayList<>();
		ServiceFallsMaster serviceFallsMaster = new ServiceFallsMaster();
		serviceFallsMaster.setServiceName("It Service");
		master.add(serviceFallsMaster);
		when(serviceFallsDaoSevice.getAllSerciveFalls()).thenReturn(master);
        ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = serviceFallsServiceImpl.getAllSerciveFalls();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue((Boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
        assertNotNull(responseEntity.getBody().get(ApiResponse.DATA));
    }

	@Test
     void getAllServiceFallsTestException() {
        when(serviceFallsDaoSevice.getAllSerciveFalls()).thenThrow(new CRMException("NullPointer"));
        assertThrows(CRMException.class, () -> serviceFallsServiceImpl.getAllSerciveFalls());
    }
}
