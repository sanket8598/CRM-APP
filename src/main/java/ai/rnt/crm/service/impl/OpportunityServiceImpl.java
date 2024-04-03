package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.ACTIVITY;
import static ai.rnt.crm.constants.CRMConstants.COUNTDATA;
import static ai.rnt.crm.constants.CRMConstants.DOMAINS;
import static ai.rnt.crm.constants.CRMConstants.EMPLOYEE;
import static ai.rnt.crm.constants.CRMConstants.LEAD_ID;
import static ai.rnt.crm.constants.CRMConstants.LEAD_SOURCE;
import static ai.rnt.crm.constants.CRMConstants.SERVICE_FALL;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.CRMConstants.TASK;
import static ai.rnt.crm.constants.CRMConstants.TIMELINE;
import static ai.rnt.crm.constants.CRMConstants.UPNEXT_DATA;
import static ai.rnt.crm.constants.OppurtunityStatus.ANALYSIS;
import static ai.rnt.crm.constants.OppurtunityStatus.CLOSE;
import static ai.rnt.crm.constants.OppurtunityStatus.IN_PIPELINE;
import static ai.rnt.crm.constants.OppurtunityStatus.LOST;
import static ai.rnt.crm.constants.OppurtunityStatus.OPEN;
import static ai.rnt.crm.constants.OppurtunityStatus.PROPOSE;
import static ai.rnt.crm.constants.OppurtunityStatus.QUALIFY;
import static ai.rnt.crm.constants.OppurtunityStatus.WON;
import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.constants.StatusConstants.ALL;
import static ai.rnt.crm.constants.StatusConstants.LEAD;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_ANALYSIS_OPPORTUNITY_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_CLOSE_AS_LOST_OPPORTUNITY_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_CLOSE_OPPORTUNITY_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_DASHBOARD_OPPORTUNITY_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_DASHBOARD_OPPORTUNITY_DTOS;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_GRAPHICAL_DATA_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_OPPORTUNITY_ATTACHMENT;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_PROPOSE_OPPORTUNITY_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_QUALIFY_LEAD_DTO;
import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT_DTOS;
import static ai.rnt.crm.dto_mapper.DomainMasterDtoMapper.TO_DOMAIN_DTOS;
import static ai.rnt.crm.dto_mapper.LeadSourceDtoMapper.TO_LEAD_SOURCE_DTOS;
import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.ASSIGNED_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.IN_PIPELINE_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.LOSS_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.WON_OPPORTUNITIES;
import static ai.rnt.crm.util.CommonUtil.getActivityData;
import static ai.rnt.crm.util.CommonUtil.getTaskDataMap;
import static ai.rnt.crm.util.CommonUtil.getTimelineData;
import static ai.rnt.crm.util.CommonUtil.getUpnextData;
import static ai.rnt.crm.util.CommonUtil.setDomainToLead;
import static ai.rnt.crm.util.CommonUtil.setLeadSourceToLead;
import static ai.rnt.crm.util.CommonUtil.setServiceFallToLead;
import static ai.rnt.crm.util.CommonUtil.upNextActivities;
import static ai.rnt.crm.util.CompanyUtil.addUpdateCompanyDetails;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertLocalDate;
import static ai.rnt.crm.util.OpportunityUtil.amountInWords;
import static ai.rnt.crm.util.OpportunityUtil.calculateBubbleSize;
import static ai.rnt.crm.util.OpportunityUtil.checkPhase;
import static java.lang.Double.valueOf;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.constants.ApiResponseKeyConstant;
import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.DomainMasterDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.OpprtAttachmentDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.dto.opportunity.AnalysisOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseAsLostOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseOpportunityDto;
import ai.rnt.crm.dto.opportunity.GraphicalDataDto;
import ai.rnt.crm.dto.opportunity.OpportunityDto;
import ai.rnt.crm.dto.opportunity.OpprtAttachmentDto;
import ai.rnt.crm.dto.opportunity.ProposeOpportunityDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.OpprtAttachment;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.service.OpportunityService;
import ai.rnt.crm.util.AuditAwareUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sanket Wakankar
 * @since 06-02-2024
 * @version 2.0
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OpportunityServiceImpl implements OpportunityService {

	private static final String OPPORTUNITY2 = "Opportunity";
	private static final String OPPORTUNITY_INFO = "OpportunityInfo";
	private static final String ALL_OPPORTUNITY = "allOpportunity";
	private static final String IN_PIPELINE_OPPORTUNITY = "inPipelineOpportunity";
	private static final String WON_OPPORTUNITY = "wonOpportunity";
	private static final String LOSS_OPPORTUNITY = "lossOpportunity";

	private final AuditAwareUtil auditAwareUtil;
	private final OpportunityDaoService opportunityDaoService;
	private final CallDaoService callDaoService;
	private final EmailDaoService emailDaoService;
	private final VisitDaoService visitDaoService;
	private final MeetingDaoService meetingDaoService;
	private final EmployeeService employeeService;
	private final ServiceFallsDaoSevice serviceFallsDaoSevice;
	private final LeadSourceDaoService leadSourceDaoService;
	private final DomainMasterDaoService domainMasterDaoService;
	private final CompanyMasterDaoService companyMasterDaoService;
	private final CityDaoService cityDaoService;
	private final StateDaoService stateDaoService;
	private final CountryDaoService countryDaoService;
	private final ContactDaoService contactDaoService;
	private final LeadDaoService leadDaoService;
	private final OpprtAttachmentDaoService opprtAttachmentDaoService;

	private static final String OPPORTUNITY_ID = "opportunityId";

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getOpportunityDataByStatus(String status) {
		log.info("inside the getOpportunityDataByStatus method...{}", status);
		EnumMap<ApiResponse, Object> opportunityDataByStatus = new EnumMap<>(ApiResponse.class);
		opportunityDataByStatus.put(SUCCESS, false);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			List<Opportunity> opportunityDashboardData = opportunityDaoService.getOpportunityDashboardData();
			Map<String, Object> countMap = new HashMap<>();
			Map<String, Object> dataMap = new HashMap<>();
			if (auditAwareUtil.isAdmin()) {
				countMap.put(ALL_OPPORTUNITY, opportunityDashboardData.stream().count());
				countMap.put(IN_PIPELINE_OPPORTUNITY,
						opportunityDashboardData.stream().filter(IN_PIPELINE_OPPORTUNITIES).count());
				countMap.put(WON_OPPORTUNITY, opportunityDashboardData.stream().filter(WON_OPPORTUNITIES).count());
				countMap.put(LOSS_OPPORTUNITY, opportunityDashboardData.stream().filter(LOSS_OPPORTUNITIES).count());
				dataMap.put(COUNTDATA, countMap);
				if (nonNull(status) && status.equalsIgnoreCase(ALL)) {
					dataMap.put(ApiResponseKeyConstant.DATA,
							TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDashboardData));
					opportunityDataByStatus.put(DATA, dataMap);
				} else {
					if (IN_PIPELINE.equalsIgnoreCase(status))
						dataMap.put(ApiResponseKeyConstant.DATA,
								TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDaoService
										.getOpportunityByStatusIn(asList(ANALYSIS, PROPOSE, QUALIFY, CLOSE)).stream()
										.collect(toList())));
					else
						dataMap.put(ApiResponseKeyConstant.DATA, TO_DASHBOARD_OPPORTUNITY_DTOS.apply(
								opportunityDaoService.getOpportunityByStatus(status).stream().collect(toList())));
					opportunityDataByStatus.put(DATA, dataMap);
				}
			} else if (auditAwareUtil.isUser() && nonNull(loggedInStaffId)) {
				countMap.put(ALL_OPPORTUNITY, opportunityDashboardData.stream()
						.filter(d -> ASSIGNED_OPPORTUNITIES.test(d, loggedInStaffId)).count());
				countMap.put(IN_PIPELINE_OPPORTUNITY, opportunityDashboardData.stream().filter(
						l -> IN_PIPELINE_OPPORTUNITIES.test(l) && ASSIGNED_OPPORTUNITIES.test(l, loggedInStaffId))
						.count());
				countMap.put(WON_OPPORTUNITY,
						opportunityDashboardData.stream().filter(
								l -> WON_OPPORTUNITIES.test(l) && ASSIGNED_OPPORTUNITIES.test(l, loggedInStaffId))
								.count());
				countMap.put(LOSS_OPPORTUNITY,
						opportunityDashboardData.stream().filter(
								l -> LOSS_OPPORTUNITIES.test(l) && ASSIGNED_OPPORTUNITIES.test(l, loggedInStaffId))
								.count());
				dataMap.put(COUNTDATA, countMap);
				if (nonNull(status) && status.equalsIgnoreCase(ALL)) {
					dataMap.put(ApiResponseKeyConstant.DATA,
							TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDashboardData.stream()
									.filter(d -> ASSIGNED_OPPORTUNITIES.test(d, loggedInStaffId)).collect(toList())));
					opportunityDataByStatus.put(DATA, dataMap);
				} else {
					if (IN_PIPELINE.equalsIgnoreCase(status))
						dataMap.put(ApiResponseKeyConstant.DATA,
								TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDaoService
										.getOpportunityByStatusIn(asList(ANALYSIS, PROPOSE, QUALIFY, CLOSE)).stream()
										.filter(d -> ASSIGNED_OPPORTUNITIES.test(d, loggedInStaffId))
										.collect(toList())));
					else
						dataMap.put(ApiResponseKeyConstant.DATA,
								TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDaoService.getOpportunityByStatus(status)
										.stream().filter(d -> ASSIGNED_OPPORTUNITIES.test(d, loggedInStaffId))
										.collect(toList())));
					opportunityDataByStatus.put(DATA, dataMap);
				}
			} else
				opportunityDataByStatus.put(DATA, emptyList());

			opportunityDataByStatus.put(SUCCESS, true);
			return new ResponseEntity<>(opportunityDataByStatus, OK);
		} catch (Exception e) {
			log.error("Got exception while getting the opportunity data by status...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getDashBoardData(Integer staffId) {
		log.info("inside the Opportunity getDashBoardData method...{}");
		EnumMap<ApiResponse, Object> dashBoardData = new EnumMap<>(ApiResponse.class);
		dashBoardData.put(SUCCESS, false);
		try {
			Map<String, Object> dataMap = new HashMap<>();
			Map<String, Object> countMap = new HashMap<>();
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			List<Opportunity> opportunityDashboardData = opportunityDaoService.getOpportunityDashboardData().stream()
					.filter(e -> nonNull(e.getStatus()) && !OPEN.equalsIgnoreCase(e.getStatus())).collect(toList());
			if (nonNull(staffId))
				getGraphAndCanBanData(countMap, dataMap, opportunityDashboardData, dashBoardData, staffId);
			else {
				if (auditAwareUtil.isAdmin()) {
					countMap.put("inPipelineAmount",
							amountInWords(opportunityDashboardData.stream()
									.filter(opt -> asList(ANALYSIS, PROPOSE, QUALIFY, CLOSE).contains(opt.getStatus())
											&& nonNull(opt.getBudgetAmount()))
									.mapToDouble(e -> valueOf(e.getBudgetAmount().replace(",", ""))).sum()));
					countMap.put("inPipeline",
							opportunityDashboardData.stream()
									.filter(opt -> asList(ANALYSIS, PROPOSE, QUALIFY, CLOSE).contains(opt.getStatus()))
									.count());
					countMap.put("won", opportunityDashboardData.stream()
							.filter(opt -> opt.getStatus().equalsIgnoreCase(WON)).count());
					countMap.put("lost", opportunityDashboardData.stream()
							.filter(opt -> opt.getStatus().equalsIgnoreCase(LOST)).count());
					double totalBudgetAmount = opportunityDashboardData.stream()
							.filter(e -> nonNull(e.getBudgetAmount()))
							.mapToDouble(e -> valueOf(e.getBudgetAmount().replace(",", ""))).sum();
					List<GraphicalDataDto> graph = opportunityDashboardData.stream().map(opt -> {
						GraphicalDataDto graphicalData = TO_GRAPHICAL_DATA_DTO.apply(opt).get();
						opt.getLeads().getContacts().stream().filter(Contacts::getPrimary).findFirst()
								.ifPresent(e -> graphicalData.setCompanyName(e.getCompanyMaster().getCompanyName()));
						graphicalData.setBubbleSize(calculateBubbleSize(
								nonNull(opt.getBudgetAmount()) ? valueOf(opt.getBudgetAmount().replace(",", "")) : 0.0,
								totalBudgetAmount));
						graphicalData.setPhase(checkPhase(opt.getStatus()));
						graphicalData.setClosedDate(convertLocalDate(opt.getClosedOn()));
						return graphicalData;
					}).collect(toList());
					dataMap.put("canBanData", countMap);
					dataMap.put("graphData", graph);
					dashBoardData.put(DATA, dataMap);
				} else if (auditAwareUtil.isUser() && nonNull(loggedInStaffId)) {
					getGraphAndCanBanData(countMap, dataMap, opportunityDashboardData, dashBoardData, loggedInStaffId);
				} else
					dashBoardData.put(DATA, emptyList());
			}
			dashBoardData.put(SUCCESS, true);
			return new ResponseEntity<>(dashBoardData, OK);
		} catch (Exception e) {
			log.error("Got exception while getting the opportunity data by status...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getOpportunityData(Integer optId) {
		log.info("inside the getOpportunityData by optId method...{}", optId);
		EnumMap<ApiResponse, Object> opptDataMap = new EnumMap<>(ApiResponse.class);
		opptDataMap.put(SUCCESS, false);
		try {
			Map<String, Object> dataMap = new LinkedHashMap<>();
			Opportunity opportunity = opportunityDaoService.findOpportunity(optId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, "optId", optId));
			Integer leadId = opportunity.getLeads().getLeadId();
			List<Call> calls = callDaoService.getCallsByLeadId(leadId);
			List<Visit> visits = visitDaoService.getVisitsByLeadId(leadId);
			List<Email> emails = emailDaoService.getEmailByLeadId(leadId);
			List<Meetings> meetings = meetingDaoService.getMeetingByLeadId(leadId);

			OpportunityDto dto = TO_DASHBOARD_OPPORTUNITY_DTO.apply(opportunity)
					.orElseThrow(ResourceNotFoundException::new);
			dto.setContacts(TO_CONTACT_DTOS.apply(opportunity.getLeads().getContacts()));
			dto.setMessage("Assigned To " + opportunity.getEmployee().getFirstName() + " "
					+ opportunity.getEmployee().getLastName());
			EmployeeMaster employeeMaster = employeeService.getById(opportunity.getCreatedBy())
					.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, opportunity.getCreatedBy()));
			dto.setGeneratedBy(employeeMaster.getFirstName() + " " + employeeMaster.getLastName());
			dto.setDropDownAssignTo(employeeMaster.getStaffId());
			dataMap.put(OPPORTUNITY_INFO, dto);
			dataMap.put(SERVICE_FALL, TO_SERVICE_FALL_MASTER_DTOS.apply(serviceFallsDaoSevice.getAllSerciveFalls()));
			dataMap.put(LEAD_SOURCE, TO_LEAD_SOURCE_DTOS.apply(leadSourceDaoService.getAllLeadSource()));
			dataMap.put(DOMAINS, TO_DOMAIN_DTOS.apply(domainMasterDaoService.getAllDomains()));
			dataMap.put(TIMELINE, getTimelineData(calls, visits, emails, meetings, employeeService));
			dataMap.put(ACTIVITY, getActivityData(calls, visits, emails, meetings, employeeService));
			dataMap.put(UPNEXT_DATA, upNextActivities(getUpnextData(calls, visits, emails, meetings, employeeService)));
			dataMap.put(TASK, getTaskDataMap(calls, visits, meetings, opportunity.getLeads(), opportunity));
			opptDataMap.put(SUCCESS, true);
			opptDataMap.put(DATA, dataMap);
			return new ResponseEntity<>(opptDataMap, OK);
		} catch (Exception e) {
			log.error("Got exception while getting the opportunity data by id...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public void getGraphAndCanBanData(Map<String, Object> countMap, Map<String, Object> dataMap,
			List<Opportunity> opportunityDashboardData, EnumMap<ApiResponse, Object> dashBoardData,
			Integer loggedInStaffId) {
		countMap.put("inPipelineAmount",
				amountInWords(opportunityDashboardData.stream()
						.filter(opt -> ASSIGNED_OPPORTUNITIES.test(opt, loggedInStaffId)
								&& asList(ANALYSIS, PROPOSE, QUALIFY, CLOSE).contains(opt.getStatus())
								&& nonNull(opt.getBudgetAmount()))
						.mapToDouble(e -> valueOf(e.getBudgetAmount().replace(",", ""))).sum()));
		countMap.put("inPipeline",
				opportunityDashboardData.stream().filter(opt -> ASSIGNED_OPPORTUNITIES.test(opt, loggedInStaffId)
						&& asList(ANALYSIS, PROPOSE, QUALIFY, CLOSE).contains(opt.getStatus())).count());
		countMap.put("won", opportunityDashboardData.stream().filter(
				opt -> ASSIGNED_OPPORTUNITIES.test(opt, loggedInStaffId) && opt.getStatus().equalsIgnoreCase(WON))
				.count());
		countMap.put("lost", opportunityDashboardData.stream().filter(
				opt -> ASSIGNED_OPPORTUNITIES.test(opt, loggedInStaffId) && opt.getStatus().equalsIgnoreCase(LOST))
				.count());
		double totalBudgetAmount = opportunityDashboardData.stream()
				.filter(e -> ASSIGNED_OPPORTUNITIES.test(e, loggedInStaffId) && nonNull(e.getBudgetAmount()))
				.mapToDouble(e -> valueOf(e.getBudgetAmount().replace(",", ""))).sum();
		List<GraphicalDataDto> graph = opportunityDashboardData.stream()
				.filter(e -> ASSIGNED_OPPORTUNITIES.test(e, loggedInStaffId)).map(opt -> {
					GraphicalDataDto graphicalData = TO_GRAPHICAL_DATA_DTO.apply(opt).get();
					opt.getLeads().getContacts().stream().filter(Contacts::getPrimary).findFirst()
							.ifPresent(e -> graphicalData.setCompanyName(e.getCompanyMaster().getCompanyName()));
					graphicalData.setBubbleSize(calculateBubbleSize(
							nonNull(opt.getBudgetAmount()) ? valueOf(opt.getBudgetAmount().replace(",", "")) : 0.0,
							totalBudgetAmount));
					graphicalData.setPhase(checkPhase(opt.getStatus()));
					graphicalData.setClosedDate(convertLocalDate(opt.getClosedOn()));
					return graphicalData;
				}).collect(toList());
		dataMap.put("canBanData", countMap);
		dataMap.put("graphData", graph);
		dashBoardData.put(DATA, dataMap);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getQualifyPopUpData(Integer leadId) {
		log.info("inside the Opportunity getQualifyPopUpData method...{}", leadId);
		EnumMap<ApiResponse, Object> qualifyData = new EnumMap<>(ApiResponse.class);
		qualifyData.put(SUCCESS, false);
		try {
			Leads lead = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId));
			Optional<QualifyLeadDto> dto = TO_QUALIFY_LEAD_DTO.apply(lead);
			qualifyData.put(DATA, dto);
			qualifyData.put(SUCCESS, true);
			return new ResponseEntity<>(qualifyData, OK);
		} catch (Exception e) {
			log.error("Got Exception in Opportunity while getting qualify data...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAnalysisPopUpData(Integer opportunityId) {
		log.info("inside the Opportunity getAnalysisPopUpData method...{}", opportunityId);
		EnumMap<ApiResponse, Object> analysisData = new EnumMap<>(ApiResponse.class);
		analysisData.put(SUCCESS, false);
		try {
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			Optional<AnalysisOpportunityDto> dto = TO_ANALYSIS_OPPORTUNITY_DTO.apply(opportunityData);
			analysisData.put(DATA, dto);
			analysisData.put(SUCCESS, true);
			return new ResponseEntity<>(analysisData, OK);
		} catch (Exception e) {
			log.error("Got Exception in Opportunity while getting analysis data...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateAnalysisPopUpData(AnalysisOpportunityDto dto,
			Integer opportunityId) {
		log.info("inside the Opportunity updateAnalysisPopUpData method...{}", opportunityId);
		EnumMap<ApiResponse, Object> updateAnalysisData = new EnumMap<>(ApiResponse.class);
		try {
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			opportunityData.setCurrentPhase(dto.getCurrentPhase());
			opportunityData.setProgressStatus(dto.getProgressStatus());
			opportunityData.setCustomerNeed(dto.getCustomerNeed());
			opportunityData.setProposedSolution(dto.getProposedSolution());
			opportunityData.setTimeline(dto.getTimeline());
			opportunityData.setAnalysisRemarks(dto.getAnalysisRemarks());
			if (nonNull(opportunityDaoService.addOpportunity(opportunityData))) {
				updateAnalysisData.put(SUCCESS, true);
				updateAnalysisData.put(MESSAGE, "Analysis Successfully..!!");
			} else {
				updateAnalysisData.put(SUCCESS, false);
				updateAnalysisData.put(MESSAGE, "Not Analysis");
			}
			return new ResponseEntity<>(updateAnalysisData, CREATED);
		} catch (Exception e) {
			log.error("Got Exception in Opportunity while updating the analysis data...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getProposePopUpData(Integer opportunityId) {
		log.info("inside the Opportunity getProposePopUpData method...{}", opportunityId);
		EnumMap<ApiResponse, Object> proposeData = new EnumMap<>(ApiResponse.class);
		proposeData.put(SUCCESS, false);
		try {
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			Optional<ProposeOpportunityDto> dto = TO_PROPOSE_OPPORTUNITY_DTO.apply(opportunityData);
			proposeData.put(DATA, dto);
			proposeData.put(SUCCESS, true);
			return new ResponseEntity<>(proposeData, OK);
		} catch (Exception e) {
			log.error("Got Exception in Opportunity while getting propose data...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateProposePopUpData(ProposeOpportunityDto dto,
			Integer opportunityId) {
		log.info("inside the Opportunity updateProposePopUpData method...{}", opportunityId);
		EnumMap<ApiResponse, Object> updateProposeData = new EnumMap<>(ApiResponse.class);
		try {
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			opportunityData.setProgressStatus(dto.getProgressStatus());
			opportunityData.setCurrentPhase(dto.getCurrentPhase());
			opportunityData.setIdentifySme(dto.getIdentifySme());
			opportunityData.setDevelopProposal(dto.getDevelopProposal());
			opportunityData.setComplInternalReview(dto.getComplInternalReview());
			opportunityData.setPresentProposal(dto.getPresentProposal());
			opportunityData.setFinalCommAndTimeline(dto.getFinalCommAndTimeline());
			opportunityData.setProposeRemarks(dto.getProposeRemarks());
			if (nonNull(opportunityDaoService.addOpportunity(opportunityData))) {
				updateProposeData.put(SUCCESS, true);
				updateProposeData.put(MESSAGE, "Proposed Successfully..!!");
			} else {
				updateProposeData.put(SUCCESS, false);
				updateProposeData.put(MESSAGE, "Not Proposed.");
			}
			return new ResponseEntity<>(updateProposeData, CREATED);
		} catch (Exception e) {
			log.error("Got Exception in Opportunity while updating the propose data...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getClosePopUpData(Integer opportunityId) {
		log.info("inside the Opportunity getClosePopUpData method...{}", opportunityId);
		EnumMap<ApiResponse, Object> closeData = new EnumMap<>(ApiResponse.class);
		closeData.put(SUCCESS, false);
		try {
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			Optional<CloseOpportunityDto> dto = TO_CLOSE_OPPORTUNITY_DTO.apply(opportunityData);
			closeData.put(DATA, dto);
			closeData.put(SUCCESS, true);
			return new ResponseEntity<>(closeData, OK);
		} catch (Exception e) {
			log.error("Got Exception in Opportunity while getting close pop up data...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateClosePopUpData(CloseOpportunityDto dto,
			Integer opportunityId) {
		log.info("inside the Opportunity updateClosePopUpData method...{}", opportunityId);
		EnumMap<ApiResponse, Object> updateCloseData = new EnumMap<>(ApiResponse.class);
		try {
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			opportunityData.setProgressStatus(dto.getProgressStatus());
			opportunityData.setCurrentPhase(dto.getCurrentPhase());
			opportunityData.setProjectKickoff(dto.getProjectKickoff());
			opportunityData.setFinalisingTeam(dto.getFinalisingTeam());
			opportunityData.setSlaSigned(dto.getSlaSigned());
			opportunityData.setSowSigned(dto.getSowSigned());
			opportunityData.setNdaSigned(dto.getNdaSigned());
			opportunityData.setStatus(dto.getStatus());
			if (nonNull(opportunityDaoService.addOpportunity(opportunityData))) {
				updateCloseData.put(SUCCESS, true);
				updateCloseData.put(MESSAGE, "Close Successfully..!!");
			} else {
				updateCloseData.put(SUCCESS, false);
				updateCloseData.put(MESSAGE, "Not Close");
			}
			return new ResponseEntity<>(updateCloseData, CREATED);
		} catch (Exception e) {
			log.error("Got Exception in Opportunity while updating the close popup data...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCloseAsLostData(Integer opportunityId) {
		log.info("inside the Opportunity getCloseAsLostData method...{}", opportunityId);
		EnumMap<ApiResponse, Object> closeAsLostData = new EnumMap<>(ApiResponse.class);
		closeAsLostData.put(SUCCESS, false);
		try {
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			Optional<CloseAsLostOpportunityDto> dto = TO_CLOSE_AS_LOST_OPPORTUNITY_DTO.apply(opportunityData);
			closeAsLostData.put(DATA, dto);
			closeAsLostData.put(SUCCESS, true);
			return new ResponseEntity<>(closeAsLostData, OK);
		} catch (Exception e) {
			log.error("Got Exception in Opportunity while getting closeAsLost opty data...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCloseAsLostData(CloseAsLostOpportunityDto dto,
			Integer opportunityId) {
		log.info("inside the Opportunity updateCloseAsLostData method...{}", opportunityId);
		EnumMap<ApiResponse, Object> updateCloseAsLostData = new EnumMap<>(ApiResponse.class);
		try {
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			opportunityData.setProgressStatus(dto.getProgressStatus());
			opportunityData.setCurrentPhase(dto.getCurrentPhase());
			opportunityData.setLostReason(dto.getLostReason());
			opportunityData.setThankMailSent(dto.getThankMailSent());
			opportunityData.setDescription(dto.getDescription());
			opportunityData.setStatus(dto.getStatus());
			if (nonNull(opportunityDaoService.addOpportunity(opportunityData))) {
				updateCloseAsLostData.put(SUCCESS, true);
				updateCloseAsLostData.put(MESSAGE, "Opportunity Lost..!!");
			} else {
				updateCloseAsLostData.put(SUCCESS, false);
				updateCloseAsLostData.put(MESSAGE, "Not Lost");
			}
			return new ResponseEntity<>(updateCloseAsLostData, CREATED);
		} catch (Exception e) {
			log.error("Got Exception in Opportunity while updating the close as lost opty data...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateOpportunity(UpdateLeadDto dto, Integer opportunityId) {
		log.info("inside the updateOpportunity method...{}", opportunityId);
		EnumMap<ApiResponse, Object> updOpptMap = new EnumMap<>(ApiResponse.class);
		try {
			Opportunity opportunity = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			opportunity.setTopic(dto.getTopic());
			opportunity.setBudgetAmount(dto.getBudgetAmount());
			opportunity.setCustomerNeed(dto.getCustomerNeed());
			opportunity.setProposedSolution(dto.getProposedSolution());
			opportunity.setPseudoName(dto.getPseudoName());
			Leads lead = opportunity.getLeads();
			Contacts contact = lead.getContacts().stream().filter(Contacts::getPrimary).findFirst()
					.orElseThrow(() -> new ResourceNotFoundException("Primary Contact"));
			addUpdateCompanyDetails(cityDaoService, stateDaoService, countryDaoService, companyMasterDaoService, dto,
					contact);
			setServiceFallToLead(dto.getServiceFallsId(), lead, serviceFallsDaoSevice);
			setLeadSourceToLead(dto.getLeadSourceId(), lead, leadSourceDaoService);
			setDomainToLead(dto.getDomainId(), lead, domainMasterDaoService);
			contact.setLinkedinId(dto.getLinkedinId());
			opportunity.setLeads(lead);
			if (nonNull(contactDaoService.addContact(contact)) && nonNull(leadDaoService.addLead(lead))
					&& nonNull(opportunityDaoService.addOpportunity(opportunity)))
				updOpptMap.put(MESSAGE, "Opportunity Details Updated Successfully !!");
			else
				updOpptMap.put(MESSAGE, "Opportunity Details Not Updated !!");
			updOpptMap.put(SUCCESS, true);
			return new ResponseEntity<>(updOpptMap, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while updateOpportunity..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public boolean updateAttachmentsOfAllPhases(Opportunity opportunityData, String phase,
			List<OpprtAttachmentDto> isAttachments) {
		log.info("inside the updateAttachmentsOfAllPhases method...");
		try {
			Opportunity opportunity = null;
			boolean status = false;
			Integer staffId = auditAwareUtil.getLoggedInStaffId();
			List<Integer> newIds = isAttachments.stream().map(OpprtAttachmentDto::getOptAttchId)
					.filter(Objects::nonNull).collect(toList());
			List<OpprtAttachment> optData = opportunityData.getOprtAttachment().stream()
					.filter(e -> nonNull(e.getAttachmentOf()) && phase.equalsIgnoreCase(e.getAttachmentOf()))
					.filter(data -> newIds.isEmpty() || !newIds.contains(data.getOptAttchId())).filter(Objects::nonNull)
					.collect(toList());
			optData.stream().forEach(data -> {
				data.setDeletedBy(staffId);
				data.setDeletedDate(
						now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
				opprtAttachmentDaoService.addOpprtAttachment(data);
			});
			if (isAttachments.isEmpty()) {
				opportunity = opportunityDaoService.addOpportunity(opportunityData);
				status = nonNull(opportunity);
			} else {
				for (OpprtAttachmentDto attach : isAttachments) {
					OpprtAttachment attachment = TO_OPPORTUNITY_ATTACHMENT.apply(attach)
							.orElseThrow(ResourceNotFoundException::new);
					attachment.setOpportunity(opportunityData);
					OpprtAttachment addAttachment = opprtAttachmentDaoService.addOpprtAttachment(attachment);
					opportunity = addAttachment.getOpportunity();
					status = nonNull(opportunity);
				}
			}
			return status;
		} catch (Exception e) {
			log.error("Got Exception while updating the attachments of opportunity phase...{}", phase);
			throw new CRMException(e);
		}
	}
}