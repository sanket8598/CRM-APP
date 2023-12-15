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

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin("*")
@RequiredArgsConstructor
public class CallController {

	private final CallService callService;

	@PostMapping(ADD_CALL)
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCall(@RequestBody @Valid CallDto dto,
			@PathVariable(name = "leadId") Integer leadsId) {
		return callService.addCall(dto, leadsId);
	}

	@GetMapping(EDIT_CALL)
	public ResponseEntity<EnumMap<ApiResponse, Object>> editCall(@PathVariable Integer callId) {
		return callService.editCall(callId);
	}

	@PutMapping(UPDATE_CALL)
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCall(@RequestBody CallDto dto,
			@PathVariable(name = "callId") Integer callId, @PathVariable(name = "status") String status) {
		return callService.updateCall(dto, callId, status);
	}

	@PutMapping(ASSIGN_CALL)
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignCall(@RequestBody Map<String, Integer> map) {
		return callService.assignCall(map);
	}

	@PutMapping("/updateCall/{callId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> markAsCompleted(@PathVariable Integer callId) {
		return callService.markAsCompleted(callId);
	}

	@DeleteMapping(DELETE_CALL)
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCall(@PathVariable Integer callId) {
		return callService.deleteCall(callId);
	}

	@PostMapping(ADD_CALL_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCallTask(@RequestBody @Valid CallTaskDto dto,
			@PathVariable(name = "leadId") Integer leadsId, @PathVariable(name = "callId") Integer callId) {
		return callService.addCallTask(dto, leadsId, callId);
	}

	@GetMapping(GET_CALL_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCallTask(@PathVariable Integer taskId) {
		return callService.getCallTask(taskId);
	}

	@PutMapping(UPDATE_CALL_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCallTask(@RequestBody GetCallTaskDto dto,
			@PathVariable Integer taskId) {
		return callService.updateCallTask(dto, taskId);
	}

	@PutMapping(ASSIGN_CALL_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignCallTask(@RequestBody Map<String, Integer> map) {
		return callService.assignCallTask(map);
	}

	@DeleteMapping(DELETE_CALL_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCallTask(@PathVariable Integer taskId) {
		return callService.deleteCallTask(taskId);
	}
}
