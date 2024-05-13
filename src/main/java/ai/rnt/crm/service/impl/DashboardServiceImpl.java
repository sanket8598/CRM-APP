package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.COUNTDATA;
import static ai.rnt.crm.dto_mapper.DashboardDtoMapper.TO_DASHBOARD_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ASSIGNED_TO_FILTER;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.ASSIGNED_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.LOSS_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.WON_OPPORTUNITIES;
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

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.DashboardService;
import ai.rnt.crm.util.AuditAwareUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 11/05/2024
 * @version 1.0
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

	private final LeadDaoService leadDaoService;
	private final OpportunityDaoService opportunityDaoService;
	private final AuditAwareUtil auditAwareUtil;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getDashboardData() {
		log.info("inside the getDashboardData method...");
		EnumMap<ApiResponse, Object> dashboardData = new EnumMap<>(ApiResponse.class);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			Map<String, Object> countMap = new HashMap<>();
			Map<String, Object> dataMap = new HashMap<>();
			List<Leads> leads = leadDaoService.getAllLeads();
			List<Opportunity> findAllOpty = opportunityDaoService.findAllOpty();
			if (auditAwareUtil.isAdmin()) {
				countMap.put("totalOpty", findAllOpty.stream().count());
				countMap.put("wonOpty", findAllOpty.stream().filter(WON_OPPORTUNITIES).count());
				countMap.put("lossOpty", findAllOpty.stream().filter(LOSS_OPPORTUNITIES).count());
				dataMap.put(COUNTDATA, countMap);
				dataMap.put("workItem", TO_DASHBOARD_DTOS.apply(leads.stream()
						.sorted((l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate())).collect(toList())));
				List<Map<String, Integer>> leadSourceData = leadDaoService.getLeadSourceCount();
				dataMap.put("leadsBySource", leadSourceData);
				dashboardData.put(DATA, dataMap);
			} else if (auditAwareUtil.isUser() && nonNull(loggedInStaffId)) {
				countMap.put("totalOpty",
						findAllOpty.stream().filter(d -> ASSIGNED_OPPORTUNITIES.test(d, loggedInStaffId)).count());
				countMap.put("wonOpty",
						findAllOpty.stream().filter(
								l -> WON_OPPORTUNITIES.test(l) && ASSIGNED_OPPORTUNITIES.test(l, loggedInStaffId))
								.count());
				countMap.put("lossOpty",
						findAllOpty.stream().filter(
								l -> LOSS_OPPORTUNITIES.test(l) && ASSIGNED_OPPORTUNITIES.test(l, loggedInStaffId))
								.count());
				dataMap.put("workItem",
						TO_DASHBOARD_DTOS.apply(leads.stream().filter(l -> ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
								.sorted((l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate()))
								.collect(toList())));
				List<Map<String, Integer>> leadSourceData = leadDaoService.getLeadSourceCount(loggedInStaffId);
				dataMap.put("leadsBySource", leadSourceData);
				dataMap.put(COUNTDATA, countMap);
				dashboardData.put(DATA, dataMap);
			} else
				dashboardData.put(DATA, emptyList());

			dashboardData.put(SUCCESS, true);
			return new ResponseEntity<>(dashboardData, OK);
		} catch (Exception e) {
			e.printStackTrace();
			dashboardData.put(SUCCESS, true);
			log.error("Got exception while fetching the main dashboard data...");
			throw new CRMException(e);
		}
	}
}
