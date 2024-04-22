package ai.rnt.crm.api.restcontroller;

import java.util.EnumMap;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.dto.opportunity.ProposalServicesDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.ProposalService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/proposal/")
@RequiredArgsConstructor
@Validated
public class ProposalController {

	private final ProposalService proposalService;

	@GetMapping("/generateId")
	public ResponseEntity<EnumMap<ApiResponse, Object>> generateProposalId() {
		return proposalService.generateProposalId();
	}

	@PostMapping("/add/{optyId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addProposal(@RequestBody @Valid ProposalDto dto,
			@Min(1) @PathVariable(name = "optyId") Integer optyId) {
		return proposalService.addProposal(dto, optyId);
	}

	@GetMapping("/get/{optyId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getProposals(
			@Min(1) @PathVariable(name = "optyId") Integer optyId) {
		return proposalService.getProposalsByOptyId(optyId);
	}

	@PostMapping("/addServices/{propId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addServicesToProposal(
			@RequestBody @Valid List<ProposalServicesDto> dto, @Min(1) @PathVariable(name = "propId") Integer propId) {
		return proposalService.addServicesToProposal(dto, propId);
	}

	@PostMapping("/add/newService")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addNewService(@RequestParam String serviceName) {
		return proposalService.addNewService(serviceName.trim());
	}
}
