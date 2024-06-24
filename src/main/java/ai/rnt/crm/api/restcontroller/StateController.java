package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ALL_STATE;
import static ai.rnt.crm.constants.ApiConstants.STATE;
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

import ai.rnt.crm.dto.StateDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.StateService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(STATE)
@RequiredArgsConstructor
public class StateController {

	private final StateService stateService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(ALL_STATE)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllState() {
		return stateService.getAllState();
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping
	public ResponseEntity<EnumMap<ApiResponse, Object>> addState(@RequestBody StateDto dto) {
		return stateService.addState(dto);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/{stateId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getState(@Min(1) @PathVariable Integer stateId) {
		return stateService.getState(stateId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateState(@RequestBody StateDto dto) {
		return stateService.updateState(dto);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@DeleteMapping("/{stateId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteState(@Min(1) @PathVariable Integer stateId) {
		return stateService.deleteState(stateId);
	}
}
