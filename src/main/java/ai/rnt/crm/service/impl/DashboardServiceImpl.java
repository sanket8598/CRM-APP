package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.COUNTDATA;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.StatusConstants.LEAD;
import static ai.rnt.crm.constants.StatusConstants.OPPORTUNITY;
import static ai.rnt.crm.dto_mapper.DashboardDtoMapper.TO_DASHBOARD_DTOS;
import static ai.rnt.crm.dto_mapper.DashboardDtoMapper.TO_OPTY_MAIN_DASHBOARD_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ASSIGNED_TO_FILTER;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.ASSIGNED_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.LOSS_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.OPEN_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.WON_OPPORTUNITIES;
import static ai.rnt.crm.util.CommonUtil.getUpnextActivities;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
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

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.DashboardService;
import ai.rnt.crm.service.EmployeeService;
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
	private final CallDaoService callDaoService;
	private final AuditAwareUtil auditAwareUtil;
	private final EmailDaoService emailDaoService;
	private final VisitDaoService visitDaoService;
	private final MeetingDaoService meetingDaoService;
	private final EmployeeService employeeService;

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
				countMap.put("openOpty", findAllOpty.stream().filter(OPEN_OPPORTUNITIES).count());
				dataMap.put(COUNTDATA, countMap);
				dataMap.put("workItem", TO_DASHBOARD_DTOS.apply(leads.stream()
						.sorted((l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate())).collect(toList())));
				dataMap.put("OptyWorkItem", TO_OPTY_MAIN_DASHBOARD_DTOS.apply(findAllOpty.stream()
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
				countMap.put("openOpty",
						findAllOpty.stream().filter(
								l -> OPEN_OPPORTUNITIES.test(l) && ASSIGNED_OPPORTUNITIES.test(l, loggedInStaffId))
								.count());
				dataMap.put("workItem",
						TO_DASHBOARD_DTOS.apply(leads.stream().filter(l -> ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
								.sorted((l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate()))
								.collect(toList())));
				dataMap.put("optyWorkItem", TO_OPTY_MAIN_DASHBOARD_DTOS.apply(findAllOpty.stream()
						.filter(l -> ASSIGNED_OPPORTUNITIES.test(l, loggedInStaffId))
						.sorted((l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate())).collect(toList())));
				List<Map<String, Integer>> leadSourceData = leadDaoService.getLeadSourceCount(loggedInStaffId);
				dataMap.put("leadsBySource", leadSourceData);
				dataMap.put(COUNTDATA, countMap);
				dashboardData.put(DATA, dataMap);
			} else
				dashboardData.put(DATA, emptyList());

			dashboardData.put(SUCCESS, true);
			return new ResponseEntity<>(dashboardData, OK);
		} catch (Exception e) {
			dashboardData.put(SUCCESS, false);
			log.error("Got exception while fetching the main dashboard data...");
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getUpComingSectionData(String field) {
		log.info("inside the getUpComingSectionData method... {}", field);
		EnumMap<ApiResponse, Object> upComingData = new EnumMap<>(ApiResponse.class);
		try {
			upComingData.put(SUCCESS, false);
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			boolean isOpportunity = false;
			if (isNull(field) || field.isEmpty())
				return new ResponseEntity<>(upComingData, OK);
			else if (LEAD.equalsIgnoreCase(field))
				isOpportunity = false;
			else if (OPPORTUNITY.equalsIgnoreCase(field))
				isOpportunity = true;
			upComingData.put(SUCCESS, true);
			if (auditAwareUtil.isAdmin())
				upComingData.put(DATA,
						getUpnextActivities(callDaoService.getAllLeadCalls(isOpportunity),
								visitDaoService.getAllLeadVisits(isOpportunity),
								emailDaoService.getAllLeadEmails(isOpportunity),
								meetingDaoService.getAllLeasMeetings(isOpportunity), employeeService));
			else if (auditAwareUtil.isUser() && nonNull(loggedInStaffId)) {
				String emailId = employeeService.getById(loggedInStaffId)
						.orElseThrow(() -> new ResourceNotFoundException("Employee", STAFF_ID, loggedInStaffId))
						.getEmailId();
				upComingData.put(DATA,
						getUpnextActivities(
								callDaoService.getAllLeadCalls(isOpportunity).stream()
										.filter(call -> call.getCallFrom().getStaffId().equals(loggedInStaffId))
										.collect(Collectors.toList()),
								visitDaoService.getAllLeadVisits(isOpportunity).stream()
										.filter(visit -> visit.getVisitBy().getStaffId().equals(loggedInStaffId))
										.collect(Collectors.toList()),
								emailDaoService.getAllLeadEmails(isOpportunity).stream()
										.filter(email -> email.getMailFrom().equalsIgnoreCase(emailId))
										.collect(Collectors.toList()),
								meetingDaoService.getAllLeasMeetings(isOpportunity).stream()
										.filter(meet -> meet.getAssignTo().getStaffId().equals(loggedInStaffId))
										.collect(Collectors.toList()),
								employeeService));
			} else
				upComingData.put(DATA, emptyList());
			return new ResponseEntity<>(upComingData, OK);
		} catch (Exception e) {
			log.error("Got exception while fetching the UpComingSection Data on dashboard...");
			throw new CRMException(e);
		}
	}
}
