package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ALL_STATE;
import static ai.rnt.crm.constants.ApiConstants.STATE;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.StateService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(STATE)
@CrossOrigin("*")
@RequiredArgsConstructor
public class StateController {

	private final StateService stateService;

	@GetMapping(ALL_STATE)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllState() {
		return stateService.getAllState();
	}
}
