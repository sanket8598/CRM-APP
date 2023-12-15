package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ADD;
import static ai.rnt.crm.constants.ApiConstants.ADD_VISIT_TASK;
import static ai.rnt.crm.constants.ApiConstants.ASSIGN_TASK;
import static ai.rnt.crm.constants.ApiConstants.ASSIGN_VISIT;
import static ai.rnt.crm.constants.ApiConstants.DELETE_TASK;
import static ai.rnt.crm.constants.ApiConstants.DELETE_VISIT;
import static ai.rnt.crm.constants.ApiConstants.EDIT_VISIT;
import static ai.rnt.crm.constants.ApiConstants.GET_VISIT_TASK;
import static ai.rnt.crm.constants.ApiConstants.UPDATE_VISIT;
import static ai.rnt.crm.constants.ApiConstants.UPDATE_VISIT_TASK;
import static ai.rnt.crm.constants.ApiConstants.VISIT;

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

import ai.rnt.crm.dto.GetVisitTaskDto;
import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.dto.VisitTaskDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.VisitService;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 14-09-2023.
 *
 */
@RestController
@RequestMapping(VISIT)
@CrossOrigin("*")
@RequiredArgsConstructor
public class VisitController {

	private final VisitService visitService;

	@PostMapping(ADD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> saveVisit(@RequestBody @Valid VisitDto dto) {
		return visitService.saveVisit(dto);
	}

	@GetMapping(EDIT_VISIT)
	public ResponseEntity<EnumMap<ApiResponse, Object>> editVisit(@PathVariable Integer visitId) {
		return visitService.editVisit(visitId);
	}

	@PutMapping(UPDATE_VISIT)
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateVisit(@RequestBody VisitDto dto,
			@PathVariable(name = "visitId") Integer visitId, @PathVariable(name = "status") String status) {
		return visitService.updateVisit(dto, visitId, status);
	}

	@PutMapping(ASSIGN_VISIT)
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignVisit(@RequestBody Map<String, Integer> map) {
		return visitService.assignVisit(map);
	}

	@PutMapping("/updateVisit/{visitId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> visitMarkAsCompleted(@PathVariable Integer visitId) {
		return visitService.visitMarkAsCompleted(visitId);
	}

	@DeleteMapping(DELETE_VISIT)
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteVisit(@PathVariable Integer visitId) {
		return visitService.deleteVisit(visitId);
	}

	@PostMapping(ADD_VISIT_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> addVisitTask(@RequestBody @Valid VisitTaskDto dto,
			@PathVariable(name = "leadId") Integer leadsId, @PathVariable(name = "visitId") Integer visitId) {
		return visitService.addVisitTask(dto, leadsId, visitId);
	}

	@GetMapping(GET_VISIT_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getVisitTask(@PathVariable Integer taskId) {
		return visitService.getVisitTask(taskId);
	}

	@PutMapping(UPDATE_VISIT_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateVisitTask(@RequestBody GetVisitTaskDto dto,
			@PathVariable Integer taskId) {
		return visitService.updateVisitTask(dto, taskId);
	}

	@PutMapping(ASSIGN_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignVisitTask(@RequestBody Map<String, Integer> map) {
		return visitService.assignVisitTask(map);
	}

	@DeleteMapping(DELETE_TASK)
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteVisitTask(@PathVariable Integer taskId) {
		return visitService.deleteVisitTask(taskId);
	}
}
