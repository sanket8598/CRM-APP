package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ALL_CITY;
import static ai.rnt.crm.constants.ApiConstants.CITY;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(CITY)
@RequiredArgsConstructor
public class CityController {

	private final CityService cityService;

	/**@author Nikhil Gaikwad 
	 * @version 1.0
	 * @since 11/09/2023.
	 * @return city
	 */
	@GetMapping(ALL_CITY)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllCity() {
		return cityService.getAllCity();
	}
}
