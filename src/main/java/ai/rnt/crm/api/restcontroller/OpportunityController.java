package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.dto.opportunity.AnalysisOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseAsLostOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseOpportunityDto;
import ai.rnt.crm.dto.opportunity.ProposeOpportunityDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.OpportunityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/opportunity/")
@RequiredArgsConstructor
@Validated
public class OpportunityController {

	private final OpportunityService opportunityService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/dashboard/{status}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getOpportunityDataByStatus(@PathVariable String status) {
		return opportunityService.getOpportunityDataByStatus(status);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(value = { "/graph", "/graph/{staffId}" })
	public ResponseEntity<EnumMap<ApiResponse, Object>> getDashboardData(
			@PathVariable(name = "staffId", required = false) Integer staffId) {
		return opportunityService.getDashBoardData(staffId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/{optId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> editOpportunity(@PathVariable Integer optId) {
		return opportunityService.getOpportunityData(optId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/qualify/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getQualifyPopUpData(
			@PathVariable(name = "leadId") Integer leadId) {
		return opportunityService.getQualifyPopUpData(leadId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/analysis/{opportunityId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAnalysisPopUpData(
			@PathVariable(name = "opportunityId") Integer opportunityId) {
		return opportunityService.getAnalysisPopUpData(opportunityId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/analysis/{opportunityId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateAnalysisPopUpData(@RequestBody AnalysisOpportunityDto dto,
			@PathVariable(name = "opportunityId") Integer opportunityId) {
		return opportunityService.updateAnalysisPopUpData(dto, opportunityId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/propose/{opportunityId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getProposePopUpData(
			@PathVariable(name = "opportunityId") Integer opportunityId) {
		return opportunityService.getProposePopUpData(opportunityId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/propose/{opportunityId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateProposePopUpData(@RequestBody ProposeOpportunityDto dto,
			@PathVariable(name = "opportunityId") Integer opportunityId) {
		return opportunityService.updateProposePopUpData(dto, opportunityId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/close/{opportunityId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getClosePopUpData(
			@PathVariable(name = "opportunityId") Integer opportunityId) {
		return opportunityService.getClosePopUpData(opportunityId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/close/{opportunityId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateClosePopUpData(@RequestBody CloseOpportunityDto dto,
			@PathVariable(name = "opportunityId") Integer opportunityId) {
		return opportunityService.updateClosePopUpData(dto, opportunityId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/closeAsLost/{opportunityId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCloseAsLostData(
			@PathVariable(name = "opportunityId") Integer opportunityId) {
		return opportunityService.getCloseAsLostData(opportunityId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/closeAsLost/{opportunityId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCloseAsLostData(
			@RequestBody CloseAsLostOpportunityDto dto, @PathVariable(name = "opportunityId") Integer opportunityId) {
		return opportunityService.updateCloseAsLostData(dto, opportunityId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/{opportunityId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateOpportunity(@RequestBody UpdateLeadDto dto,
			@PathVariable(name = "opportunityId") Integer opportunityId) {
		return opportunityService.updateOpportunity(dto, opportunityId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/assign")
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignOpportunity(@RequestBody Map<String, Integer> map) {
		return opportunityService.assignOpportunity(map);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/reactive/{optyId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> reactiveOpty(@PathVariable Integer optyId) {
		return opportunityService.reactiveOpty(optyId);
	}
}
