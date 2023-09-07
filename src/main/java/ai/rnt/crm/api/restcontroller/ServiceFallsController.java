package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.SERVICE_FALLS;
import static ai.rnt.crm.constants.ApiConstants.GET_ALL_SERVICE_FALLS;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.ServiceFallsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(SERVICE_FALLS)
@Slf4j
@RequiredArgsConstructor
public class ServiceFallsController {

	private final ServiceFallsService serviceFallsService;

	@GetMapping(GET_ALL_SERVICE_FALLS)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllSerciveFalls() {
		return serviceFallsService.getAllSerciveFalls();
	}
}
