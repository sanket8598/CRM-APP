package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.TASK;

import java.util.EnumMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
}
