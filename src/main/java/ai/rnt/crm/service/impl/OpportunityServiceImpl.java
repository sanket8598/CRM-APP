package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.COUNTDATA;
import static ai.rnt.crm.constants.OppurtunityStatus.ANALYSIS;
import static ai.rnt.crm.constants.OppurtunityStatus.CLOSE;
import static ai.rnt.crm.constants.OppurtunityStatus.IN_PIPELINE;
import static ai.rnt.crm.constants.OppurtunityStatus.LOST;
import static ai.rnt.crm.constants.OppurtunityStatus.PROPOSE;
import static ai.rnt.crm.constants.OppurtunityStatus.QUALIFY;
import static ai.rnt.crm.constants.OppurtunityStatus.WON;
import static ai.rnt.crm.constants.StatusConstants.ALL;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_DASHBOARD_OPPORTUNITY_DTOS;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_GRAPHICAL_DATA_DTO;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.ASSIGNED_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.IN_PIPELINE_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.LOSS_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.WON_OPPORTUNITIES;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertLocalDate;
import static ai.rnt.crm.util.OpportunityUtil.amountInWords;
import static ai.rnt.crm.util.OpportunityUtil.calculateBubbleSize;
import static ai.rnt.crm.util.OpportunityUtil.checkPhase;
import static java.lang.Double.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.constants.ApiResponseKeyConstant;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dto.opportunity.GraphicalDataDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.OpportunityService;
import ai.rnt.crm.util.AuditAwareUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 06/02/2024
 * @version 2.0
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OpportunityServiceImpl implements OpportunityService {

	private static final String ALL_OPPORTUNITY = "allOpportunity";
	private static final String IN_PIPELINE_OPPORTUNITY = "inPipelineOpportunity";
	private static final String WON_OPPORTUNITY = "wonOpportunity";
	private static final String LOSS_OPPORTUNITY = "lossOpportunity";

	private final AuditAwareUtil auditAwareUtil;
	private final OpportunityDaoService opportunityDaoService;

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
			List<Opportunity> opportunityDashboardData = opportunityDaoService.getOpportunityDashboardData();
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
}
