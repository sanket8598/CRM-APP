package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_PROPOSAL;
import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_PROPOSAL_DTOS;
import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_PROPOSAL_SERVICES;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.StringUtil.randomNumberGenerator;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.ProposalDaoService;
import ai.rnt.crm.dao.service.ProposalServicesDaoService;
import ai.rnt.crm.dto.opportunity.GetProposalsDto;
import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.dto.opportunity.ProposalServicesDto;
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
	private final ProposalServicesDaoService proposalServicesDaoService;
	private final OpportunityDaoService opportunityDaoService;
	private final EmployeeDaoService employeeDaoService;

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
	public ResponseEntity<EnumMap<ApiResponse, Object>> addProposal(@Valid ProposalDto dto, Integer optyId) {
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

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getProposalsByOptyId(Integer optyId) {
		log.info("inside the getProposalsByOptyId method...");
		EnumMap<ApiResponse, Object> proposalData = new EnumMap<>(ApiResponse.class);
		try {
			Map<Integer, String> employeeMap = employeeDaoService.getEmployeeNameMap();
			List<Proposal> proposals = proposalDaoService.getProposalsByOptyId(optyId);
			List<GetProposalsDto> dto = TO_PROPOSAL_DTOS.apply(proposals.stream()
					.sorted((b, a) -> a.getCreatedDate().compareTo(b.getCreatedDate())).collect(Collectors.toList()));
			dto.forEach(e -> e.setCreatedBy(employeeMap.get(Integer.parseInt(e.getCreatedBy()))));
			proposalData.put(DATA, dto);
			proposalData.put(SUCCESS, true);
			return new ResponseEntity<>(proposalData, OK);
		} catch (Exception e) {
			log.error("Got exception while getting the proposals by optyId..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addServicesToProposal(List<ProposalServicesDto> dto,
			Integer propId) {
		log.info("inside the addServicesToProposal method...{}", propId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		result.put(SUCCESS, false);
		result.put(MESSAGE, "Proposal Service Not Added !!");
		try {
			Proposal proposal = proposalDaoService.findProposalById(propId)
					.orElseThrow(() -> new ResourceNotFoundException("Proposal", "propId", propId));
			TO_PROPOSAL_SERVICES.apply(dto).stream().forEach(e -> {
				e.setProposal(proposal);
				try {
					proposalServicesDaoService.save(e).ifPresent(res -> {
						result.put(SUCCESS, true);
						result.put(MESSAGE, "Proposal Service Added Successfully !!");
					});
				} catch (Exception e1) {
					log.error("Got exception while saving proposal services by propId..{}", e1.getMessage());
				}
			});
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.error("Got exception while adding services to the proposals by propId..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
