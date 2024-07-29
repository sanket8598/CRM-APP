package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ALL_COUNTRIES;
import static ai.rnt.crm.constants.ApiConstants.COUNTRY;
import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;

import javax.validation.Valid;
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

import ai.rnt.crm.dto.CountryDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CountryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(COUNTRY)
@RequiredArgsConstructor
public class CountryController {

	private final CountryService countryService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(ALL_COUNTRIES)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllCountry() {
		return countryService.getAllCountry();
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCountry(@RequestBody @Valid CountryDto dto) {
		return countryService.addCountry(dto);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/{countryId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCountry(@Min(1) @PathVariable Integer countryId) {
		return countryService.getCountry(countryId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/{countryId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCountry(@RequestBody @Valid CountryDto dto,
			@Min(1) @PathVariable Integer countryId) {
		return countryService.updateCountry(dto, countryId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@DeleteMapping("/{countryId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCountry(@Min(1) @PathVariable Integer countryId) {
		return countryService.deleteCountry(countryId);
	}
}
