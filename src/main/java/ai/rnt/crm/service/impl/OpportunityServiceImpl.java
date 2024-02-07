package ai.rnt.crm.service.impl;

import org.springframework.stereotype.Service;

import ai.rnt.crm.service.OpportunityService;
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

	/*
	 * private static final String ALL_OPPORTUNITY = "allOpportunity"; private
	 * static final String IN_PIPELINE_OPPORTUNITY = "inPipelineOpportunity";
	 * private static final String WON_OPPORTUNITY = "wonOpportunity"; private
	 * static final String LOSS_OPPORTUNITY = "lossOpportunity"; private final
	 * AuditAwareUtil auditAwareUtil; private final OpportunityDaoService
	 * opportunityDaoService;
	 * 
	 * @Override public ResponseEntity<EnumMap<ApiResponse, Object>>
	 * getOpportunityDataByStatus(String status) {
	 * log.info("inside the getOpportunityDataByStatus method...{}", status);
	 * EnumMap<ApiResponse, Object> opportunityDataByStatus = new
	 * EnumMap<>(ApiResponse.class); try { Integer loggedInStaffId =
	 * auditAwareUtil.getLoggedInStaffId(); List<Opportunity>
	 * opportunityDashboardData =
	 * opportunityDaoService.getOpportunityDashboardData(); Map<String, Object>
	 * countMap = new HashMap<>(); Map<String, Object> dataMap = new HashMap<>(); if
	 * (auditAwareUtil.isAdmin()) { countMap.put(ALL_OPPORTUNITY,
	 * opportunityDashboardData.stream().count());
	 * countMap.put(IN_PIPELINE_OPPORTUNITY,
	 * opportunityDashboardData.stream().filter(IN_PIPELINE_OPPORTUNITIES).count());
	 * countMap.put(WON_OPPORTUNITY,
	 * opportunityDashboardData.stream().filter(WON_OPPORTUNITIES).count());
	 * countMap.put(LOSS_OPPORTUNITY,
	 * opportunityDashboardData.stream().filter(LOSS_OPPORTUNITIES).count());
	 * dataMap.put(COUNTDATA, countMap); if (nonNull(status) &&
	 * status.equalsIgnoreCase(ALL)) { dataMap.put(ApiResponseKeyConstant.DATA,
	 * TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDashboardData));
	 * opportunityDataByStatus.put(DATA, dataMap); } else {
	 * dataMap.put(ApiResponseKeyConstant.DATA,
	 * TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDaoService
	 * .getOpportunityByStatus(status).stream().collect(Collectors.toList())));
	 * opportunityDataByStatus.put(DATA, dataMap); } } else if
	 * (auditAwareUtil.isUser() && nonNull(loggedInStaffId)) {
	 * countMap.put(ALL_OPPORTUNITY, opportunityDashboardData.stream() .filter(d ->
	 * ASSIGNED_OPPORTUNITIES.test(d, loggedInStaffId)).count());
	 * countMap.put(IN_PIPELINE_OPPORTUNITY,
	 * opportunityDashboardData.stream().filter( l ->
	 * IN_PIPELINE_OPPORTUNITIES.test(l) && ASSIGNED_OPPORTUNITIES.test(l,
	 * loggedInStaffId)) .count()); countMap.put(WON_OPPORTUNITY,
	 * opportunityDashboardData.stream().filter( l -> WON_OPPORTUNITIES.test(l) &&
	 * ASSIGNED_OPPORTUNITIES.test(l, loggedInStaffId)) .count());
	 * countMap.put(LOSS_OPPORTUNITY, opportunityDashboardData.stream().filter( l ->
	 * LOSS_OPPORTUNITIES.test(l) && ASSIGNED_OPPORTUNITIES.test(l,
	 * loggedInStaffId)) .count()); dataMap.put(COUNTDATA, countMap); if
	 * (nonNull(status) && status.equalsIgnoreCase(ALL)) {
	 * dataMap.put(ApiResponseKeyConstant.DATA,
	 * TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDashboardData.stream()
	 * .filter(d -> ASSIGNED_OPPORTUNITIES.test(d,
	 * loggedInStaffId)).collect(toList()))); opportunityDataByStatus.put(DATA,
	 * dataMap); } else { dataMap.put(ApiResponseKeyConstant.DATA,
	 * TO_DASHBOARD_OPPORTUNITY_DTOS.apply(opportunityDaoService.
	 * getOpportunityByStatus(status) .stream().filter(d ->
	 * ASSIGNED_OPPORTUNITIES.test(d, loggedInStaffId)) .collect(toList())));
	 * opportunityDataByStatus.put(DATA, dataMap); } } else
	 * opportunityDataByStatus.put(DATA, emptyList());
	 * 
	 * opportunityDataByStatus.put(SUCCESS, true); return new
	 * ResponseEntity<>(opportunityDataByStatus, OK); } catch (Exception e) {
	 * log.error("Got exception while getting the opportunity data by status...{}",
	 * e.getMessage()); throw new CRMException(e); } }
	 */
}
