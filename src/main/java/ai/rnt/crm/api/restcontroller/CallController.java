package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ADD_CALL;
import static ai.rnt.crm.constants.ApiConstants.ADD_CALL_TASK;
import static ai.rnt.crm.constants.ApiConstants.ASSIGN_CALL;
import static ai.rnt.crm.constants.ApiConstants.ASSIGN_CALL_TASK;
import static ai.rnt.crm.constants.ApiConstants.CALL;
import static ai.rnt.crm.constants.ApiConstants.DELETE_CALL;
import static ai.rnt.crm.constants.ApiConstants.DELETE_CALL_TASK;
import static ai.rnt.crm.constants.ApiConstants.EDIT_CALL;
import static ai.rnt.crm.constants.ApiConstants.GET_CALL_TASK;
import static ai.rnt.crm.constants.ApiConstants.UPDATE_CALL;
import static ai.rnt.crm.constants.ApiConstants.UPDATE_CALL_TASK;

import java.util.EnumMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.CallDto;
import ai.rnt.crm.dto.CallTaskDto;
import ai.rnt.crm.dto.GetCallTaskDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CallService;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 11/09/2023
 * @version 1.0
 *
 */
@RestController
@RequestMapping(CALL)
@RequiredArgsConstructor
@Validated
public class CallController {

	private final CallService callService;

	@PostMapping(ADD_CALL)
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCall(@RequestBody @Valid CallDto dto,
			@Min(1) @PathVariable(name = "leadId") Integer leadsId) {
		return callService.addCall(dto, leadsId);
	}

	@GetMapping(EDIT_CALL)
	public ResponseEntity<EnumMap<ApiResponse, Object>> editCall(@Min(1) @PathVariable Integer callId) {
		return callService.editCall(callId);
	}

	@PutMapping(UPDATE_CALL)
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCall(@RequestBody CallDto dto,
			@Min(1) @PathVariable(name = "callId") Integer callId,
			@Min(1) @NotBlank(message = "Status should not be null!!") @PathVariable(name = "status") String status) {
		return callService.updateCall(dto, callId, status);
	}

	@PutMapping(ASSIGN_CALL)
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignCall(
			@RequestBody Map<@NotBlank String, @NotNull @Min(1) Integer> map) {
		return callService.assignCall(map);
	}

	@PutMapping("/updateCall/{callId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> markAsCompleted(@Min(1) @PathVariable Integer callId) {
		return callService.markAsCompleted(callId);
	}

	@DeleteMapping(DELETE_CALL)
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCall(@Min(1) @PathVariable Integer callId) {
		return callService.deleteCall(callId);
	}

	@PostMapping(ADD_CALL_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCallTask(@RequestBody @Valid CallTaskDto dto,
			@Min(1) @PathVariable(name = "leadId") Integer leadsId,
			@Min(1) @PathVariable(name = "callId") Integer callId) {
		return callService.addCallTask(dto, leadsId, callId);
	}

	@GetMapping(GET_CALL_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCallTask(@Min(1) @PathVariable Integer taskId) {
		return callService.getCallTask(taskId);
	}

	@PutMapping(UPDATE_CALL_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCallTask(@RequestBody GetCallTaskDto dto,
			@Min(1) @PathVariable Integer taskId) {
		return callService.updateCallTask(dto, taskId);
	}

	@PutMapping(ASSIGN_CALL_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignCallTask(
			@RequestBody Map<@NotBlank String, @NotNull @Min(1) Integer> map) {
		return callService.assignCallTask(map);
	}

	@DeleteMapping(DELETE_CALL_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCallTask(@Min(1) @PathVariable Integer taskId) {
		return callService.deleteCallTask(taskId);
	}
}
