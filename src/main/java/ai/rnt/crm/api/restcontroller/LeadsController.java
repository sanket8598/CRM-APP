package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.CREATE_LEAD;
import static ai.rnt.crm.constants.ApiConstants.GET_ALL_LEADS;
import static ai.rnt.crm.constants.ApiConstants.GET_LEADS_BY_STATUS;
import static ai.rnt.crm.constants.ApiConstants.LEAD;
import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.LeadService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(LEAD)
@RequiredArgsConstructor
public class LeadsController {

	private final LeadService leadService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping(CREATE_LEAD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> saveLead(@RequestBody LeadDto dto) {
		return leadService.createLead(dto);
	}

	@GetMapping(value = { GET_LEADS_BY_STATUS, GET_ALL_LEADS })
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadsByStatus(
			@PathVariable(name = "leadsStatus", required = false) String leadsStatus) {
		return leadService.getLeadsByStatus(leadsStatus);
	}
}
