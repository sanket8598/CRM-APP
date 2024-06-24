package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.COMPANY;
import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CompanyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(COMPANY)
@RequiredArgsConstructor
public class CompanyController {

	private final CompanyService companyService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping
	public ResponseEntity<EnumMap<ApiResponse, Object>> companyList() {
		return companyService.companyList();
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/{companyId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCompany(@Min(1) @PathVariable Integer companyId) {
		return companyService.getCompany(companyId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCompany(@RequestBody CompanyDto dto) {
		return companyService.addCompany(dto);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/{companyId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCompany(@RequestBody CompanyDto dto,
			@Min(1) @PathVariable Integer companyId) {
		return companyService.updateCompany(dto, companyId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@DeleteMapping("/{companyId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCompany(@Min(1) @PathVariable Integer companyId) {
		return companyService.deleteCompany(companyId);
	}
}
