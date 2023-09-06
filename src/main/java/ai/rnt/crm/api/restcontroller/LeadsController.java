package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.CREATE_LEAD;
import static ai.rnt.crm.constants.ApiConstants.LEAD;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.payloads.JwtAuthRequest;
import ai.rnt.crm.service.LeadService;
import ai.rnt.crm.util.LeadUtil;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(LEAD)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LeadsController {

	private final LeadService leadService;

	@PostMapping(CREATE_LEAD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> saveLead(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("phoneNumber") Integer phoneNumber,
			@RequestParam("assignTo") Integer assignTo, @RequestParam("budgetAmount") Float budgetAmount,
			@RequestParam("email") String email, @RequestParam("topic") String topic,
			@RequestParam("companyId") Integer companyId, @RequestParam("serviceFallsId") Integer serviceFallsId,
			@RequestParam("leadSourceId") Integer leadSourceId,
			@RequestParam(value = "businessCardImage", required = false) MultipartFile file) {
		return leadService.createLead(LeadUtil.createLeadDto(firstName, lastName, phoneNumber, assignTo, budgetAmount,
				email, topic, companyId, serviceFallsId, leadSourceId), file);
	}
	
	@PostMapping("/saveLead")
	public String addLead(@RequestBody JwtAuthRequest dto) {
		return dto.toString();
	}
}
