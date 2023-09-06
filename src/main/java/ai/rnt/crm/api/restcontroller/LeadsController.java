package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.CREATE_LEAD;
import static ai.rnt.crm.constants.ApiConstants.LEAD;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
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

	@PostMapping(CREATE_LEAD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> saveLead(@RequestBody LeadDto dto) {
		return leadService.createLead(dto);
	}
}
