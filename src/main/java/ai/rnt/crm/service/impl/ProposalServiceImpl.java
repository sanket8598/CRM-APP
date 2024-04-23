package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.EMPLOYEE;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_EDIT_PROPOSAL_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_PROPOSAL;
import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_PROPOSAL_DTOS;
import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_PROPOSAL_SERVICES;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.StringUtil.randomNumberGenerator;
import static ai.rnt.crm.util.XSSUtil.sanitize;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.ProposalDaoService;
import ai.rnt.crm.dao.service.ProposalServicesDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dto.opportunity.EditProposalDto;
import ai.rnt.crm.dto.opportunity.GetProposalsDto;
import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.dto.opportunity.ProposalServicesDto;
import ai.rnt.crm.dto.opportunity.UpdateProposalDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.Proposal;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.service.ProposalService;
import ai.rnt.crm.util.AuditAwareUtil;
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
	private final ServiceFallsDaoSevice serviceFallsDaoSevice;
	private final EmployeeService employeeService;
	private final AuditAwareUtil auditAwareUtil;

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

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addNewService(String serviceName) {
		log.info("inside the addNewService method...{}", serviceName);
		EnumMap<ApiResponse, Object> resultData = new EnumMap<>(ApiResponse.class);
		resultData.put(SUCCESS, false);
		resultData.put(MESSAGE, "New Service Not Added !!");
		try {
			if (serviceFallsDaoSevice.findByServiceName(serviceName)) {
				resultData.put(SUCCESS, false);
				resultData.put(MESSAGE, "Service Is Present !!");
				return new ResponseEntity<>(resultData, OK);
			} else {
				ServiceFallsMaster serviceFalls = new ServiceFallsMaster();
				serviceFalls.setServiceName(sanitize(serviceName));
				if (nonNull(serviceFallsDaoSevice.save(serviceFalls))) {
					resultData.put(SUCCESS, true);
					resultData.put(MESSAGE, "New Service Added Successfully !!");
				}
			}
			return new ResponseEntity<>(resultData, OK);
		} catch (Exception e) {
			log.error("Got exception while adding new service in the proposal...", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> editProposal(Integer propId) {
		log.info("inside the editProposal method...{}", propId);
		EnumMap<ApiResponse, Object> proposal = new EnumMap<>(ApiResponse.class);
		try {
			Map<String, Object> dataMap = new LinkedHashMap<>();

			Proposal proposalById = proposalDaoService.findProposalById(propId)
					.orElseThrow(() -> new ResourceNotFoundException("Proposal", "propId", propId));
			Optional<EditProposalDto> dto = TO_EDIT_PROPOSAL_DTO.apply(proposalById);
			dto.ifPresent(e -> {
				EmployeeMaster employeeMaster = employeeService.getById(proposalById.getCreatedBy()).orElseThrow(
						() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, proposalById.getCreatedBy()));
				e.setCreatedBy(employeeMaster.getFirstName() + " " + employeeMaster.getLastName());
			});
			dataMap.put("ProposalInfo", dto);
			proposal.put(SUCCESS, true);
			proposal.put(DATA, dataMap);
			return new ResponseEntity<>(proposal, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting data for edit the proposal data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateProposal(Integer propId, UpdateProposalDto dto) {
		log.info("inside the updateProposal method...{}", propId);
		EnumMap<ApiResponse, Object> updateProposal = new EnumMap<>(ApiResponse.class);
		updateProposal.put(SUCCESS, false);
		updateProposal.put(MESSAGE, "Proposal Not Update !!");
		try {
			Proposal proposalById = proposalDaoService.findProposalById(propId)
					.orElseThrow(() -> new ResourceNotFoundException("Proposal", "propId", propId));
			proposalById.setOwnerName(dto.getOwnerName());
			proposalById.setCurrency(dto.getCurrency());
			proposalById.setPropDescription(dto.getPropDescription());
			dto.getProposalServices().stream().forEach(e -> {
				proposalServicesDaoService.findById(e.getPropServiceId()).ifPresent(ps -> {
					ps.setServicePrice(e.getServicePrice());
					ps.setProposal(proposalById);
					try {
						proposalServicesDaoService.save(ps).ifPresent(save -> {
							updateProposal.put(SUCCESS, true);
							updateProposal.put(MESSAGE, "Proposal Updated Successfully!!");
						});
					} catch (Exception e1) {
						log.error("Got Exception while updating the proposal service data..{}", e1.getMessage());
					}
				});
			});
			return new ResponseEntity<>(updateProposal, OK);
		} catch (Exception e) {
			log.error("Got Exception while updating the proposal data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteProposal(@Min(1) Integer propId) {
		log.info("inside the delete proposal method...{}", propId);
		EnumMap<ApiResponse, Object> delPropMap = new EnumMap<>(ApiResponse.class);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			Proposal proposal = proposalDaoService.findProposalById(propId)
					.orElseThrow(() -> new ResourceNotFoundException("Proposal", "propId", propId));
			proposal.getProposalServices().stream().forEach(e -> {
				e.setDeletedBy(loggedInStaffId);
				e.setDeletedDate(now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
				try {
					proposalServicesDaoService.save(e);
				} catch (Exception e1) {
					log.error("Got Exception while deleting the proposal services..{}", e1.getMessage());
				}
			});
			proposal.setDeletedBy(loggedInStaffId);
			proposal.setDeletedDate(
					now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
			if (nonNull(proposalDaoService.saveProposal(proposal))) {
				delPropMap.put(MESSAGE, "Proposal Deleted Successfully.");
				delPropMap.put(SUCCESS, true);
			} else {
				delPropMap.put(MESSAGE, "Proposal Not Delete.");
				delPropMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(delPropMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while deleting the proposal..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
