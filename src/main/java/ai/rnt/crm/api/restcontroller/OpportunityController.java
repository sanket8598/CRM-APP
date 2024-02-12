package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.OpportunityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/opportunity/")
@RequiredArgsConstructor
public class OpportunityController {

	private final OpportunityService opportunityService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/dashboard/{status}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getOpportunityDataByStatus(@PathVariable String status) {
		return opportunityService.getOpportunityDataByStatus(status);
	}
	
	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(value={"/graph","/graph/{staffId}"})
	public ResponseEntity<EnumMap<ApiResponse, Object>> getDashboardData(@PathVariable(name = "staffId", required = false) Integer staffId) {
		return opportunityService.getDashBoardData(staffId);
	}
	
	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/{optId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> editOpportunity(@PathVariable Integer optId) {
		return opportunityService.getOpportunityData(optId);
	}

}
