package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_EDIT_PROPOSAL_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_PROPOSAL;
import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_PROPOSAL_DTOS;
import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_PROPOSAL_SERVICES;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.SignatureUtil.generateSignature;
import static ai.rnt.crm.util.SignatureUtil.verifySignature;
import static ai.rnt.crm.util.StringUtil.randomNumberGenerator;
import static ai.rnt.crm.util.XSSUtil.sanitize;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.ProposalDaoService;
import ai.rnt.crm.dao.service.ProposalServicesDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dto.opportunity.EditProposalDto;
import ai.rnt.crm.dto.opportunity.GetProposalsDto;
import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.dto.opportunity.ProposalServicesDto;
import ai.rnt.crm.dto.opportunity.UpdateProposalDto;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.Proposal;
import ai.rnt.crm.entity.ProposalServices;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.ProposalService;
import ai.rnt.crm.util.AuditAwareUtil;
import ai.rnt.crm.util.SignatureUtil;
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
@PropertySource("classpath:confidential.properties")
public class ProposalServiceImpl implements ProposalService {

	private final ProposalDaoService proposalDaoService;
	private final ProposalServicesDaoService proposalServicesDaoService;
	private final OpportunityDaoService opportunityDaoService;
	private final ServiceFallsDaoSevice serviceFallsDaoSevice;
	private final AuditAwareUtil auditAwareUtil;
	private final SignatureUtil signatureUtil;

	@Value("${secretkey}")
	private String secretKey;

	@Value("${cbcalgo}")
	private String cbcAlgo;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> generateProposalId() {
		log.info("inside the generateProposalId method...");
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			String proposalId = randomNumberGenerator();
			String signature = generateSignature(proposalId);
			result.put(DATA, proposalId + ":" + signature);
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.error("Got exception while generating proposal id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addProposal(ProposalDto dto, Integer optyId) {
		log.info("inside the addProposal method...{}", optyId);
		EnumMap<ApiResponse, Object> proposalData = new EnumMap<>(ApiResponse.class);
		try {
			if (!verifySignature(dto.getGenPropId(), dto.getSignature())) {
				proposalData.put(MESSAGE, "Invalid Proposal Id");
				proposalData.put(SUCCESS, false);
				return new ResponseEntity<>(proposalData, UNAUTHORIZED);
			}
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
			// Map<Integer, String> employeeMap = employeeDaoService.getEmployeeNameMap();
			List<Proposal> proposals = proposalDaoService.getProposalsByOptyId(optyId);
			List<GetProposalsDto> dto = TO_PROPOSAL_DTOS.apply(proposals.stream()
					.sorted((b, a) -> a.getCreatedDate().compareTo(b.getCreatedDate())).collect(Collectors.toList()));
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
				resultData.put(MESSAGE, "Service Is Already Present !!");
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
			Map<String, Object> editProposalMap = new LinkedHashMap<>();

			Proposal proposalById = proposalDaoService.findProposalById(propId)
					.orElseThrow(() -> new ResourceNotFoundException("Proposal", "propId", propId));
			Optional<EditProposalDto> dto = TO_EDIT_PROPOSAL_DTO.apply(proposalById);
			dto.ifPresent(propsl -> propsl.setCreatedOn(proposalById.getCreatedDate()));
			editProposalMap.put("ProposalInfo", dto);
			proposal.put(SUCCESS, true);
			proposal.put(DATA, editProposalMap);
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
			proposalById.setEffectiveFrom(dto.getEffectiveFrom());
			proposalById.setEffectiveTo(dto.getEffectiveTo());
			proposalById.setPropDescription(dto.getPropDescription());
			List<ProposalServicesDto> proposalServices = dto.getProposalServices();
			if (!proposalServices.isEmpty()) {
				dto.getProposalServices().stream().forEach(e -> {
					proposalServicesDaoService.findById(e.getPropServiceId()).ifPresent(ps -> {
						ps.setServicePrice(e.getServicePrice());
						if (nonNull(dto.getSubTotal()) && !dto.getSubTotal().isEmpty()) {
							try {
								proposalById.setSubTotal(
										signatureUtil.decryptAmount(dto.getSubTotal(), secretKey, cbcAlgo));
								proposalById.setFinalAmount(
										signatureUtil.decryptAmount(dto.getFinalAmount(), secretKey, cbcAlgo));
							} catch (Exception e1) {
								log.error("Got Exception while set decrypted amount in updateProposal method..{}",
										e1.getMessage());
							}
						} else {
							proposalById.setSubTotal(dto.getSubTotal());
							proposalById.setFinalAmount(dto.getFinalAmount());
						}
						proposalById.setDiscount(dto.getDiscount());
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
			} else {
				proposalDaoService.saveProposal(proposalById);
				updateProposal.put(SUCCESS, true);
				updateProposal.put(MESSAGE, "Proposal Updated Successfully!!");
			}
			return new ResponseEntity<>(updateProposal, OK);
		} catch (Exception e) {
			log.error("Got Exception while updating the proposal data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteProposal(Integer propId) {
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

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteService(Integer propServiceId) {
		log.info("inside the deleteService method...{}", propServiceId);
		EnumMap<ApiResponse, Object> deleteSerive = new EnumMap<>(ApiResponse.class);
		try {
			ProposalServices proposalServices = proposalServicesDaoService.findById(propServiceId).orElseThrow(
					() -> new ResourceNotFoundException("ProposalServices", "propServiceId", propServiceId));
			proposalServices.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
			proposalServices.setDeletedDate(
					now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
			if (nonNull(proposalServicesDaoService.save(proposalServices))) {
				deleteSerive.put(MESSAGE, "Service Deleted Successfully.");
				deleteSerive.put(SUCCESS, true);
			} else {
				deleteSerive.put(MESSAGE, "Service Not Delete.");
				deleteSerive.put(SUCCESS, false);
			}
			return new ResponseEntity<>(deleteSerive, OK);
		} catch (Exception e) {
			log.error("error occured while deleting the proposal service by id..{}", +propServiceId, e.getMessage());
			throw new CRMException(e);
		}
	}
}
