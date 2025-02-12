package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ADD_SORT_FILTER;
import static ai.rnt.crm.constants.ApiConstants.ASSIGN_LEAD;
import static ai.rnt.crm.constants.ApiConstants.CREATE_LEAD;
import static ai.rnt.crm.constants.ApiConstants.DASHBOARD_ALL_LEADS;
import static ai.rnt.crm.constants.ApiConstants.DASHBOARD_LEADS_BY_STATUS;
import static ai.rnt.crm.constants.ApiConstants.DIS_QUALIFY_LEAD;
import static ai.rnt.crm.constants.ApiConstants.EDIT_LEAD;
import static ai.rnt.crm.constants.ApiConstants.EDIT_QUALIFY_LEAD;
import static ai.rnt.crm.constants.ApiConstants.GET_ALL_LEADS;
import static ai.rnt.crm.constants.ApiConstants.GET_ALL_LEAD_SOURCE;
import static ai.rnt.crm.constants.ApiConstants.GET_ALL_SERVICE_FALLS;
import static ai.rnt.crm.constants.ApiConstants.GET_DROP_DOWN_DATA;
import static ai.rnt.crm.constants.ApiConstants.GET_LEADS_BY_STATUS;
import static ai.rnt.crm.constants.ApiConstants.IMPORTANT;
import static ai.rnt.crm.constants.ApiConstants.LEAD;
import static ai.rnt.crm.constants.ApiConstants.QUALIFY_LEAD;
import static ai.rnt.crm.constants.ApiConstants.REACTIVE;
import static ai.rnt.crm.constants.ApiConstants.UPDATE_LEAD_CONTACT;
import static ai.rnt.crm.constants.ApiConstants.UPLOAD_EXCEL;
import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ai.rnt.crm.dto.DescriptionDto;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.LeadSortFilterDto;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.marker.DisqualifiedLead;
import ai.rnt.crm.marker.LeadAdvanceInfo;
import ai.rnt.crm.marker.UpdateLead;
import ai.rnt.crm.service.LeadService;
import ai.rnt.crm.service.ServiceFallsService;
import ai.rnt.crm.validation.ValidFile;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * @author Sanket Wakankar
 * @since 19-08-2023.
 * @version 1.0
 *
 */
@RestController
@RequestMapping(LEAD)
@RequiredArgsConstructor
@Tag(name = "Lead", description = "This Section Gives Us The API Endpoint Related To The Lead")
@Validated
public class LeadsController {

	private final LeadService leadService;
	private final ServiceFallsService serviceFallsService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping(CREATE_LEAD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> saveLead(
			@Validated(LeadAdvanceInfo.class) @RequestBody LeadDto dto) {
		return leadService.createLead(dto);
	}

	// for open view data
	@PreAuthorize(CHECK_BOTH_ACCESS)
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

	// not in use
	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(DASHBOARD_ALL_LEADS)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadDashboardData() {
		return leadService.getLeadDashboardData();
	}

	// for dashboard leads by status.(All,Open,Qualified and Disqualified)
	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(DASHBOARD_LEADS_BY_STATUS)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadDashboardDataByStatus(@PathVariable String leadsStatus) {
		return leadService.getLeadDashboardDataByStatus(leadsStatus);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(EDIT_LEAD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> editLead(@PathVariable Integer leadId) {
		return leadService.editLead(leadId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping(QUALIFY_LEAD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> qualifyLead(@PathVariable Integer leadId,
			@RequestBody @Valid QualifyLeadDto dto) {
		return leadService.qualifyLead(leadId, dto);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping(ASSIGN_LEAD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignLead(@RequestBody Map<String, Integer> map) {
		return leadService.assignLead(map);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping(DIS_QUALIFY_LEAD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> disQualifyLead(@PathVariable Integer leadId,
			@Validated(DisqualifiedLead.class) @RequestBody LeadDto dto) {
		return leadService.disQualifyLead(leadId, dto);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping(UPDATE_LEAD_CONTACT)
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateLeadContact(
			@Validated(UpdateLead.class) @RequestBody UpdateLeadDto dto, @PathVariable Integer leadId) {
		return leadService.updateLeadContact(leadId, dto);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping(IMPORTANT)
	public ResponseEntity<EnumMap<ApiResponse, Object>> importantLead(@PathVariable Integer leadId,
			@PathVariable boolean status) {
		return leadService.importantLead(leadId, status);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping(REACTIVE)
	public ResponseEntity<EnumMap<ApiResponse, Object>> reactiveLead(@PathVariable Integer leadId) {
		return leadService.reactiveLead(leadId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping(ADD_SORT_FILTER)
	public ResponseEntity<EnumMap<ApiResponse, Object>> addSortFilterToLeads(
			@RequestBody LeadSortFilterDto sortFilter) {
		return leadService.addSortFilterForLeads(sortFilter);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping(UPLOAD_EXCEL)
	public ResponseEntity<EnumMap<ApiResponse, Object>> uploadExcel(
			@NotNull(message = "Please Select The File!!") @ValidFile(message = "Only Excel File is Allowed!!") @RequestParam("file") MultipartFile file) {
		return leadService.uploadExcel(file);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(EDIT_QUALIFY_LEAD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> editQualifyLead(@PathVariable Integer leadId) {
		return leadService.getForQualifyLead(leadId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping("/description/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addDescription(@Validated @RequestBody DescriptionDto dto,
			@Min(1) @PathVariable(name = "leadId") Integer leadId) {
		return leadService.addDescription(dto, leadId);
	}
	
	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/{leadId}/contact/info")
	public ResponseEntity<EnumMap<ApiResponse, Object>> contactInfo(@Min(1) @PathVariable(name = "leadId") Integer leadId) {
		return leadService.getContactInfo(leadId);
	}
}