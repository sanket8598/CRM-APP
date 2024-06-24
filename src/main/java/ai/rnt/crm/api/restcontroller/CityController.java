package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ALL_CITY;
import static ai.rnt.crm.constants.ApiConstants.CITY;
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

import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(CITY)
@RequiredArgsConstructor
public class CityController {

	private final CityService cityService;

	/**
	 * @author Nikhil Gaikwad
	 * @version 1.0
	 * @since 11/09/2023.
	 * @return city
	 */

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(ALL_CITY)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllCity() {
		return cityService.getAllCity();
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCity(@RequestBody CityDto dto) {
		return cityService.addCity(dto);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/{cityId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCity(@Min(1) @PathVariable Integer cityId) {
		return cityService.getCity(cityId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCity(@RequestBody CityDto dto) {
		return cityService.updateCity(dto);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@DeleteMapping("/{cityId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCity(@Min(1) @PathVariable Integer cityId) {
		return cityService.deleteCity(cityId);
	}
}
