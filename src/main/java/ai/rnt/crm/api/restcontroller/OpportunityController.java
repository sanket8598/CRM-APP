package ai.rnt.crm.api.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.service.OpportunityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/opportunity/")
@RequiredArgsConstructor
public class OpportunityController {

	private final OpportunityService opportunityService;

	/*
	 * @GetMapping("/dashboard/{status}") public ResponseEntity<EnumMap<ApiResponse,
	 * Object>> getOpportunityDataByStatus(@PathVariable String status) { return
	 * opportunityService.getOpportunityDataByStatus(status); }
	 */
}
