package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ALL_COUNTRIES;
import static ai.rnt.crm.constants.ApiConstants.COUNTRY;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CountryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(COUNTRY)
@CrossOrigin("*")
@RequiredArgsConstructor
public class CountryController {

	private final CountryService countryService;

	@GetMapping(ALL_COUNTRIES)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllCountry() {
		return countryService.getAllCountry();
	}
}
