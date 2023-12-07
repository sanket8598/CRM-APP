package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.TASK;

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

import ai.rnt.crm.dto.LeadTaskDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.LeadTaskService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(TASK)
@CrossOrigin("*")
@RequiredArgsConstructor
public class LeadTaskController {

	private final LeadTaskService leadTaskService;

	@PostMapping("/add/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addTask(@RequestBody @Valid LeadTaskDto dto,
			@PathVariable(name = "leadId") Integer leadsId) {
		return leadTaskService.addTask(dto, leadsId);
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadTask(@PathVariable Integer taskId) {
		return leadTaskService.getLeadTask(taskId);
	}

	@PutMapping("/update/{taskId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateTask(@RequestBody LeadTaskDto dto,
			@PathVariable(name = "taskId") Integer taskId) {
		return leadTaskService.updateTask(dto, taskId);
	}

	@PutMapping("/assign")
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignTask(@RequestBody Map<String, Integer> map) {
		return leadTaskService.assignTask(map);
	}

	@DeleteMapping("/delete/{taskId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteTask(@PathVariable Integer taskId) {
		return leadTaskService.deleteTask(taskId);
	}

}
