package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ADD_LEAD_TASK;
import static ai.rnt.crm.constants.ApiConstants.ASSIGN_LEAD_TASK;
import static ai.rnt.crm.constants.ApiConstants.DELETE_LEAD_TASK;
import static ai.rnt.crm.constants.ApiConstants.GET_LEAD_TASK;
import static ai.rnt.crm.constants.ApiConstants.LEAD_TASK;
import static ai.rnt.crm.constants.ApiConstants.UPDATE_LEAD_TASK;

import java.util.EnumMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping(LEAD_TASK)
@RequiredArgsConstructor
public class LeadTaskController {

	private final LeadTaskService leadTaskService;

	@PostMapping(ADD_LEAD_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> addLeadTask(@RequestBody @Valid LeadTaskDto dto,
			@PathVariable(name = "leadId") Integer leadsId) {
		return leadTaskService.addLeadTask(dto, leadsId);
	}

	@GetMapping(GET_LEAD_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadTask(@PathVariable Integer taskId) {
		return leadTaskService.getLeadTask(taskId);
	}

	@PutMapping(UPDATE_LEAD_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateLeadTask(@RequestBody GetLeadTaskDto dto,
			@PathVariable(name = "taskId") Integer taskId) {
		return leadTaskService.updateLeadTask(dto, taskId);
	}

	@PutMapping(ASSIGN_LEAD_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignLeadTask(@RequestBody Map<String, Integer> map) {
		return leadTaskService.assignLeadTask(map);
	}

	@DeleteMapping(DELETE_LEAD_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteLeadTask(@PathVariable Integer taskId) {
		return leadTaskService.deleteLeadTask(taskId);
	}
}
