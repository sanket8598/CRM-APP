package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.CUSTOMER;
import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping
	public ResponseEntity<EnumMap<ApiResponse, Object>> customerDashboardData() {
		return customerService.customerDashBoardData();
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/{customerId}/{field}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> editCustomer(@PathVariable Integer customerId,
			@PathVariable(name = "field", required = true) String field) {
		return customerService.editCustomer(field, customerId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/{customerId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCustomer(@RequestBody @Valid ContactDto contactDto,
			@PathVariable Integer customerId) {
		return customerService.updateCustomer(contactDto, customerId);
	}
}
