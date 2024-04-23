package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.dto.opportunity.ProposalServicesDto;
import ai.rnt.crm.enums.ApiResponse;

public interface ProposalService {

	ResponseEntity<EnumMap<ApiResponse, Object>> generateProposalId();

	ResponseEntity<EnumMap<ApiResponse, Object>> addProposal(@Valid ProposalDto dto, Integer optyId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getProposalsByOptyId(Integer optyId);

	ResponseEntity<EnumMap<ApiResponse, Object>> addServicesToProposal(List<ProposalServicesDto> dto, Integer propId);

	ResponseEntity<EnumMap<ApiResponse, Object>> addNewService(String serviceName);

	ResponseEntity<EnumMap<ApiResponse, Object>> editProposal(Integer propId);

}
