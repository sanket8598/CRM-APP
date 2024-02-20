package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ADD_LEAD_TASK;
import static ai.rnt.crm.constants.ApiConstants.ASSIGN_LEAD_TASK;
import static ai.rnt.crm.constants.ApiConstants.DELETE_LEAD_TASK;
import static ai.rnt.crm.constants.ApiConstants.GET_LEAD_TASK;
import static ai.rnt.crm.constants.ApiConstants.LEAD_TASK;
import static ai.rnt.crm.constants.ApiConstants.UPDATE_LEAD_TASK;
import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;
import java.util.Map;

import javax.validation.Valid;

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

import ai.rnt.crm.dto.GetLeadTaskDto;
import ai.rnt.crm.dto.LeadTaskDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.LeadTaskService;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 07/12/2023
 * @version 1.0
 *
 */
@RestController
@RequestMapping(LEAD_TASK)
@RequiredArgsConstructor
public class LeadTaskController {

	private final LeadTaskService leadTaskService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping(ADD_LEAD_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> addLeadTask(@RequestBody @Valid LeadTaskDto dto,
			@PathVariable(name = "leadId") Integer leadsId) {
		return leadTaskService.addLeadTask(dto, leadsId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(GET_LEAD_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadTask(@PathVariable Integer taskId) {
		return leadTaskService.getLeadTask(taskId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping(UPDATE_LEAD_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateLeadTask(@RequestBody GetLeadTaskDto dto,
			@PathVariable(name = "taskId") Integer taskId) {
		return leadTaskService.updateLeadTask(dto, taskId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping(ASSIGN_LEAD_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignLeadTask(@RequestBody Map<String, Integer> map) {
		return leadTaskService.assignLeadTask(map);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@DeleteMapping(DELETE_LEAD_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteLeadTask(@PathVariable Integer taskId) {
		return leadTaskService.deleteLeadTask(taskId);
	}
}
