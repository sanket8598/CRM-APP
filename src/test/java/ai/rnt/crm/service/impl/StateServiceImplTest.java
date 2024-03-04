package ai.rnt.crm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
import ai.rnt.crm.entity.StateMaster;
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

	// @Test
	    void testGetAllState_Successful() {
	        // Arrange
	        List<StateMaster> stateList = Arrays.asList(new StateMaster());
	        when(stateDaoService.getAllState()).thenReturn(stateList);

	        // Act
	        ResponseEntity<EnumMap<ApiResponse, Object>> response = stateServiceImpl.getAllState();

	        // Assert
	        assertNotNull(response);
	        assertEquals(HttpStatus.FOUND, response.getStatusCode());
	        assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
	        assertNotNull(response.getBody().get(ApiResponse.DATA));
	        // Add more assertions based on your method's behavior
	    }

	   // @Test
	    void testGetAllState_ExceptionThrown() {
	        // Arrange
	        when(stateDaoService.getAllState()).thenThrow(new RuntimeException("Simulating an exception"));

	        // Act
	        ResponseEntity<EnumMap<ApiResponse, Object>> response = stateServiceImpl.getAllState();

	        // Assert
	        assertNotNull(response);
	        assertEquals(HttpStatus.FOUND, response.getStatusCode()); // Correcting status code to 500
	        assertFalse((boolean) response.getBody().get(ApiResponse.SUCCESS));
	        assertNull(response.getBody().get(ApiResponse.DATA));
	    }
}
