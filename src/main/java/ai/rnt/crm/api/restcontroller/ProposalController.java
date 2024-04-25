package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.dto.opportunity.ProposalServicesDto;
import ai.rnt.crm.dto.opportunity.UpdateProposalDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.ProposalService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/proposal/")
@RequiredArgsConstructor
@Validated
public class ProposalController {

	private final ProposalService proposalService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/generateId")
	public ResponseEntity<EnumMap<ApiResponse, Object>> generateProposalId() {
		return proposalService.generateProposalId();
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping("/add/{optyId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addProposal(@RequestBody @Valid ProposalDto dto,
			@Min(1) @PathVariable(name = "optyId") Integer optyId) {
		return proposalService.addProposal(dto, optyId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/get/{optyId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getProposals(
			@Min(1) @PathVariable(name = "optyId") Integer optyId) {
		return proposalService.getProposalsByOptyId(optyId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping("/addServices/{propId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addServicesToProposal(
			@RequestBody @Valid List<ProposalServicesDto> dto, @Min(1) @PathVariable(name = "propId") Integer propId) {
		return proposalService.addServicesToProposal(dto, propId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PostMapping("/add/newService")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addNewService(@RequestBody String serviceName) {
		return proposalService.addNewService(serviceName.trim());
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/edit/{propId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> editProposal(@Valid @Min(1) @PathVariable Integer propId) {
		return proposalService.editProposal(propId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/{propId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateProposal(@PathVariable Integer propId,
			@RequestBody UpdateProposalDto dto) {
		return proposalService.updateProposal(propId, dto);
	}

	@DeleteMapping("/{propId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteProposal(@Min(1) @PathVariable Integer propId) {
		return proposalService.deleteProposal(propId);
	}

	@DeleteMapping("/service/{propServiceId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteService(@Min(1) @PathVariable Integer propServiceId) {
		return proposalService.deleteService(propServiceId);
	}
}
