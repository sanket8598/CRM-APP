package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.COMPANY;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CompanyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(COMPANY)
@CrossOrigin("*")
@RequiredArgsConstructor
public class CompanyController {

	private final CompanyService companyService;

	@GetMapping("/companies")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllCompany() {
		return companyService.getAllCompany();
	}

}
