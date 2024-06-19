package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.CUSTOMER;
import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;
	
	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping()
	public ResponseEntity<EnumMap<ApiResponse, Object>> customerDashboardData(){
		return customerService.customerDashBoardData();
	}

}
