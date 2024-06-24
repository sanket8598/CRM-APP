package ai.rnt.crm.api.restcontroller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CustomerService;

class CustomerControllerTest {

	@Mock
	private CustomerService customerService;

	@InjectMocks
	private CustomerController customerController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCustomerDashboardData() {
		EnumMap<ApiResponse, Object> expectedResponse = new EnumMap<>(ApiResponse.class);
		expectedResponse.put(ApiResponse.SUCCESS, "Dashboard Data");
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = new ResponseEntity<>(expectedResponse,
				HttpStatus.OK);
		when(customerService.customerDashBoardData()).thenReturn(responseEntity);
		ResponseEntity<EnumMap<ApiResponse, Object>> actualResponse = customerController.customerDashboardData();
		assertEquals(responseEntity, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(expectedResponse, actualResponse.getBody());
		verify(customerService, times(1)).customerDashBoardData();
	}
}
