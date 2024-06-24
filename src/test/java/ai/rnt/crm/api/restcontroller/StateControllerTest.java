package ai.rnt.crm.api.restcontroller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.StateDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.StateService;

class StateControllerTest {

	@Mock
	private StateService stateService;

	@InjectMocks
	private StateController stateController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void getAllStateTest() {
		StateService stateService = mock(StateService.class);
		StateController controller = new StateController(stateService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(stateService.getAllState()).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.getAllState();
		verify(stateService).getAllState();
	}

	@Test
	void testAddState() {
		StateDto dto = new StateDto();
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(stateService.addState(dto)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = stateController.addState(dto);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertSame(expectedResponse, responseEntity);
		verify(stateService).addState(dto);
		verifyNoMoreInteractions(stateService);
	}

	@Test
	void testGetState() {
		Integer stateId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(stateService.getState(stateId)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = stateController.getState(stateId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertSame(expectedResponse, responseEntity);
		verify(stateService).getState(stateId);
		verifyNoMoreInteractions(stateService);
	}

	@Test
	void testUpdateState() {
		StateDto dto = new StateDto();
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(stateService.updateState(dto)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = stateController.updateState(dto);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertSame(expectedResponse, responseEntity);
		verify(stateService).updateState(dto);
		verifyNoMoreInteractions(stateService);
	}

	@Test
	void testDeleteState() {
		Integer stateId = 1;
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity
				.ok(new EnumMap<>(ApiResponse.class));
		when(stateService.deleteState(stateId)).thenReturn(expectedResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = stateController.deleteState(stateId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertSame(expectedResponse, responseEntity);
		verify(stateService).deleteState(stateId);
		verifyNoMoreInteractions(stateService);
	}
}
