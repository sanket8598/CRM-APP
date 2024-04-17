package ai.rnt.crm.service;

import java.util.EnumMap;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.enums.ApiResponse;

public interface ProposalService {

	ResponseEntity<EnumMap<ApiResponse, Object>> generateProposalId();

	ResponseEntity<EnumMap<ApiResponse, Object>> addProposal(@Valid ProposalDto dto, @Min(1) Integer optyId);

}
