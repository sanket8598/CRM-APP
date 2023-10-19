package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.CREATE_LEAD;
import static ai.rnt.crm.constants.ApiConstants.DASHBOARD_ALL_LEADS;
import static ai.rnt.crm.constants.ApiConstants.DASHBOARD_LEADS_BY_STATUS;
import static ai.rnt.crm.constants.ApiConstants.GET_ALL_LEADS;
import static ai.rnt.crm.constants.ApiConstants.GET_ALL_LEAD_SOURCE;
import static ai.rnt.crm.constants.ApiConstants.GET_ALL_SERVICE_FALLS;
import static ai.rnt.crm.constants.ApiConstants.GET_DROP_DOWN_DATA;
import static ai.rnt.crm.constants.ApiConstants.GET_LEADS_BY_STATUS;
import static ai.rnt.crm.constants.ApiConstants.LEAD;
import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.LeadService;
import ai.rnt.crm.service.ServiceFallsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(LEAD)
@CrossOrigin("*")
@RequiredArgsConstructor
public class LeadsController {

	private final LeadService leadService;
	private final ServiceFallsService serviceFallsService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping(CREATE_LEAD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> saveLead(@RequestBody @Valid LeadDto dto) {
		return leadService.createLead(dto);
	}

	@GetMapping(value = { GET_LEADS_BY_STATUS, GET_ALL_LEADS })
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadsByStatus(
			@PathVariable(name = "leadsStatus", required = false) String leadsStatus) {
		return leadService.getLeadsByStatus(leadsStatus);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(GET_ALL_SERVICE_FALLS)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllSerciveFalls() {
		return serviceFallsService.getAllSerciveFalls();
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(GET_ALL_LEAD_SOURCE)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllLeadSource() {
		return leadService.getAllLeadSource();
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(GET_DROP_DOWN_DATA)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllDropDownData() {
		return leadService.getAllDropDownData();
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(DASHBOARD_ALL_LEADS)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadDashboardData() {
		return leadService.getLeadDashboardData();
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(DASHBOARD_LEADS_BY_STATUS)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadDashboardDataByStatus(@PathVariable String leadsStatus) {
		return leadService.getLeadDashboardDataByStatus(leadsStatus);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/edit/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> editLead(@PathVariable Integer leadId) {
		return leadService.editLead(leadId);
	}

	@PutMapping("/qualify/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> qualifyLead(@PathVariable Integer leadId,
			@RequestBody QualifyLeadDto dto) {
		return leadService.qualifyLead(leadId, dto);
	}

	@PutMapping("/assignLead")
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignLead(@RequestBody Map<String, Integer> map) {
		return leadService.assignLead(map);
	}

	@PutMapping("/disQualify/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> disQualifyLead(@PathVariable Integer leadId,
			@RequestBody LeadDto dto) {
		return leadService.disQualifyLead(leadId, dto);
	}

	@PutMapping("/updateLeadContact/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateLeadContact(@PathVariable Integer leadId,
			@RequestBody UpdateLeadDto dto) {
		return leadService.updateLeadContact(leadId, dto);
	}

	@PutMapping("/important/{leadId}/{status}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> importantLead(@PathVariable Integer leadId,
			@PathVariable boolean status) {
		return leadService.importantLead(leadId, status);
	}
}