package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_PROPOSAL;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.StringUtil.randomNumberGenerator;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.ProposalDaoService;
import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.Proposal;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.ProposalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 16-04-2024.
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProposalServiceImpl implements ProposalService {

	private final ProposalDaoService proposalDaoService;
	private final OpportunityDaoService opportunityDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> generateProposalId() {
		log.info("inside the generateProposalId method...");
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			result.put(DATA, randomNumberGenerator());
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.error("Got exception while generating proposal id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addProposal(@Valid ProposalDto dto, @Min(1) Integer optyId) {
		log.info("inside the addProposal method...{}", optyId);
		EnumMap<ApiResponse, Object> proposalData = new EnumMap<>(ApiResponse.class);
		try {
			Proposal proposal = TO_PROPOSAL.apply(dto)
					.orElseThrow(() -> new ResourceNotFoundException("Proposal", "propId", dto.getPropId()));
			Opportunity opportunity = opportunityDaoService.findOpportunity(optyId)
					.orElseThrow(() -> new ResourceNotFoundException("Opportunity", "optyId", optyId));
			proposal.setOpportunity(opportunity);

			if (nonNull(proposalDaoService.saveProposal(proposal))) {
				proposalData.put(MESSAGE, "Proposal Added Successfully");
				proposalData.put(SUCCESS, true);
			} else {
				proposalData.put(MESSAGE, "Proposal Not Added");
				proposalData.put(SUCCESS, false);
			}
			return new ResponseEntity<>(proposalData, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while adding the proposal to opportunity..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
