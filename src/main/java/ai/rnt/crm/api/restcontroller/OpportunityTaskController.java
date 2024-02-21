package ai.rnt.crm.api.restcontroller;

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

import ai.rnt.crm.dto.opportunity.GetOpportunityTaskDto;
import ai.rnt.crm.dto.opportunity.OpportunityTaskDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.OpportunityTaskService;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 19-02-2024
 * @version 1.2
 *
 */
@RestController
@RequestMapping("/api/v1/opportunity/task/")
@RequiredArgsConstructor
public class OpportunityTaskController {

	private final OpportunityTaskService opportunityTaskService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping("/{optyId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addOpportunityTask(@RequestBody @Valid OpportunityTaskDto dto,
			@PathVariable(name = "optyId") Integer optyId) {
		return opportunityTaskService.addOpportunityTask(dto, optyId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/{taskId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getOpportunityTask(@PathVariable Integer taskId) {
		return opportunityTaskService.getOpportunityTask(taskId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/{taskId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateOpportunityTask(@RequestBody GetOpportunityTaskDto dto,
			@PathVariable(name = "taskId") Integer taskId) {
		return opportunityTaskService.updateOpportunityTask(dto, taskId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/assign")
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignOpportunityTask(@RequestBody Map<String, Integer> map) {
		return opportunityTaskService.assignOpportunityTask(map);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@DeleteMapping("/{taskId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteOpportunityTask(@PathVariable Integer taskId) {
		return opportunityTaskService.deleteOpportunityTask(taskId);
	}
}
