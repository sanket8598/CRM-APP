package ai.rnt.crm.service.impl;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;

@ExtendWith(MockitoExtension.class)
class StateServiceImplTest {

	@Mock
	private StateDaoService stateDaoService;

	@InjectMocks
	private StateServiceImpl stateServiceImpl;

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

}
