package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.CREATE_LEAD;
import static ai.rnt.crm.constants.ApiConstants.LEAD;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ai.rnt.crm.dto.NewLeadDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.LeadService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(LEAD)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LeadsController {

	private final LeadService leadService;

	@PostMapping(CREATE_LEAD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> saveLead(@ModelAttribute NewLeadDto leadDto,
			@RequestParam(value = "businessCardImage", required = false) MultipartFile file) {
		return leadService.createLead(leadDto, file);
	}
}
