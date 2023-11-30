package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.CALL;

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

	@PostMapping("/add/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCall(@RequestBody @Valid CallDto dto,
			@PathVariable(name = "leadId") Integer leadsId) {
		return callService.addCall(dto, leadsId);
	}

	@PutMapping("/assignCall")
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignCall(@RequestBody Map<String, Integer> map) {
		return callService.assignCall(map);
	}

	@PutMapping("/updateCall/{callId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> markAsCompleted(@PathVariable Integer callId) {
		return callService.markAsCompleted(callId);
	}

	@DeleteMapping("/deleteCall/{callId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCall(@PathVariable Integer callId) {
		return callService.deleteCall(callId);
	}

	@GetMapping("/edit/{callId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> editCall(@PathVariable Integer callId) {
		return callService.editCall(callId);
	}

	@PutMapping("/update/{callId}/{status}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCall(@RequestBody CallDto dto,
			@PathVariable(name = "callId") Integer callId, @PathVariable(name = "status") String status) {
		return callService.updateCall(dto, callId, status);
	}

	@PostMapping("/addTask/{leadId}/{callId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCallTask(@RequestBody @Valid CallTaskDto dto,
			@PathVariable(name = "leadId") Integer leadsId, @PathVariable(name = "callId") Integer callId) {
		return callService.addCallTask(dto, leadsId, callId);
	}

	@GetMapping("/task/{taskId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCallTask(@PathVariable Integer taskId) {
		return callService.getCallTask(taskId);
	}
}
