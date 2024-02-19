package ai.rnt.crm.api.restcontroller;

import java.util.EnumMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/add/{optyId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addOpportunityTask(@RequestBody @Valid OpportunityTaskDto dto,
			@PathVariable(name = "optyId") Integer optyId) {
		return opportunityTaskService.addOpportunityTask(dto, optyId);
	}
}
