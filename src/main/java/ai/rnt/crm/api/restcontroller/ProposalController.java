package ai.rnt.crm.api.restcontroller;

import java.util.EnumMap;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.opportunity.ProposalDto;
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
}
