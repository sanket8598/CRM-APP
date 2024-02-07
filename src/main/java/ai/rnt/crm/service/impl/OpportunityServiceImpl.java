package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.COUNTDATA;
import static ai.rnt.crm.constants.StatusConstants.ALL;
import static ai.rnt.crm.dto_mapper.OpportunityDtoMapper.TO_DASHBOARD_OPPORTUNITY_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.IN_PIPELINE_OPPORTUNITY_FILTER;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.LOSS_OPPORTUNITY_FILTER;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.OPPORTUNITY_ASSIGNED_TO_FILTER;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.WON_OPPORTUNITY_FILTER;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.constants.ApiResponseKeyConstant;
import ai.rnt.crm.dao.service.OpportunityDaoService;
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

	private final AuditAwareUtil auditAwareUtil;
	private final OpportunityDaoService opportunityDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getOpportunityDataByStatus(String status) {
		log.info("inside the getOpportunityDataByStatus method...{}", status);
		EnumMap<ApiResponse, Object> opportunityDataByStatus = new EnumMap<>(ApiResponse.class);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			List<Opportunity> opportunityDashboardData = opportunityDaoService.getOpportunityDashboardData();
			Map<String, Object> countMap = new HashMap<>();
			Map<String, Object> dataMap = new HashMap<>();
			if (auditAwareUtil.isAdmin()) {
				countMap.put("allOpportunity", opportunityDashboardData.stream().count());
				countMap.put("inPipelineOpportunity",
						opportunityDashboardData.stream().filter(IN_PIPELINE_OPPORTUNITY_FILTER).count());
				countMap.put("wonOpportunity",
						opportunityDashboardData.stream().filter(WON_OPPORTUNITY_FILTER).count());
				countMap.put("lossOpportunity",
						opportunityDashboardData.stream().filter(LOSS_OPPORTUNITY_FILTER).count());
				dataMap.put(COUNTDATA, countMap);
				if (nonNull(status) && status.equalsIgnoreCase(ALL)) {
					dataMap.put(ApiResponseKeyConstant.DATA,
							TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDashboardData));
					opportunityDataByStatus.put(DATA, dataMap);
				} else {
					dataMap.put(ApiResponseKeyConstant.DATA, TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDaoService
							.getOpportunityByStatus(status).stream().collect(Collectors.toList())));
					opportunityDataByStatus.put(DATA, dataMap);
				}
			} else if (auditAwareUtil.isUser() && nonNull(loggedInStaffId)) {
				countMap.put("allOpportunity", opportunityDashboardData.stream()
						.filter(d -> OPPORTUNITY_ASSIGNED_TO_FILTER.test(d, loggedInStaffId)).count());
				countMap.put("inPipelineOpportunity",
						opportunityDashboardData.stream().filter(l -> IN_PIPELINE_OPPORTUNITY_FILTER.test(l)
								&& OPPORTUNITY_ASSIGNED_TO_FILTER.test(l, loggedInStaffId)).count());
				countMap.put("wonOpportunity", opportunityDashboardData.stream().filter(
						l -> WON_OPPORTUNITY_FILTER.test(l) && OPPORTUNITY_ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
						.count());
				countMap.put("lossOpportunity", opportunityDashboardData.stream().filter(
						l -> LOSS_OPPORTUNITY_FILTER.test(l) && OPPORTUNITY_ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
						.count());
				dataMap.put(COUNTDATA, countMap);
				if (nonNull(status) && status.equalsIgnoreCase(ALL)) {
					dataMap.put(ApiResponseKeyConstant.DATA,
							TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDashboardData.stream()
									.filter(d -> OPPORTUNITY_ASSIGNED_TO_FILTER.test(d, loggedInStaffId))
									.collect(toList())));
					opportunityDataByStatus.put(DATA, dataMap);
				} else {
					dataMap.put(ApiResponseKeyConstant.DATA,
							TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDaoService.getOpportunityByStatus(status)
									.stream().filter(d -> OPPORTUNITY_ASSIGNED_TO_FILTER.test(d, loggedInStaffId))
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
}
