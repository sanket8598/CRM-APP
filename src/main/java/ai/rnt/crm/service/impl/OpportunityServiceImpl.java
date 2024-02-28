package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.ACTIVITY;
import static ai.rnt.crm.constants.CRMConstants.COUNTDATA;
import static ai.rnt.crm.constants.CRMConstants.DOMAINS;
import static ai.rnt.crm.constants.CRMConstants.EMPLOYEE;
import static ai.rnt.crm.constants.CRMConstants.LEAD_SOURCE;
import static ai.rnt.crm.constants.CRMConstants.SERVICE_FALL;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.CRMConstants.TASK;
import static ai.rnt.crm.constants.CRMConstants.TIMELINE;
import static ai.rnt.crm.constants.CRMConstants.UPNEXT_DATA;
import static ai.rnt.crm.constants.DateFormatterConstant.DATE_TIME_WITH_AM_OR_PM;
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
import static ai.rnt.crm.constants.StatusConstants.CALL;
import static ai.rnt.crm.constants.StatusConstants.EMAIL;
import static ai.rnt.crm.constants.StatusConstants.MEETING;
import static ai.rnt.crm.constants.StatusConstants.VISIT;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_ANALYSIS_OPPORTUNITY_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_CLOSE_OPPORTUNITY_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_DASHBOARD_OPPORTUNITY_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_DASHBOARD_OPPORTUNITY_DTOS;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_GRAPHICAL_DATA_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_OPPORTUNITY_ATTACHMENT;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_OPPORTUNITY_ATTACHMENT_DTOS;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_PROPOSE_OPPORTUNITY_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_QUALIFY_OPPORTUNITY_DTO;
import static ai.rnt.crm.dto_mapper.AttachmentDtoMapper.TO_ATTACHMENT_DTOS;
import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT_DTO;
import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT_DTOS;
import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_OPTY_ATTACHMENT_DTOS;
import static ai.rnt.crm.dto_mapper.DomainMasterDtoMapper.TO_DOMAIN_DTOS;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_EMPLOYEE;
import static ai.rnt.crm.dto_mapper.LeadSourceDtoMapper.TO_LEAD_SOURCE_DTOS;
import static ai.rnt.crm.dto_mapper.MeetingAttachmentDtoMapper.TO_METTING_ATTACHMENT_DTOS;
import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ACTIVITY_CALL;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ACTIVITY_EMAIL;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ACTIVITY_MEETING;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ACTIVITY_VISIT;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.TIMELINE_CALL;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.TIMELINE_EMAIL;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.TIMELINE_MEETING;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.TIMELINE_VISIT;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.UPNEXT_CALL;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.UPNEXT_MEETING;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.UPNEXT_VISIT;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.ASSIGNED_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.IN_PIPELINE_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.LOSS_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OpportunityPredicates.WON_OPPORTUNITIES;
import static ai.rnt.crm.functional.predicates.OverDueActivity.OVER_DUE;
import static ai.rnt.crm.util.CommonUtil.getTaskDataMap;
import static ai.rnt.crm.util.CommonUtil.setDomainToLead;
import static ai.rnt.crm.util.CommonUtil.setLeadSourceToLead;
import static ai.rnt.crm.util.CommonUtil.setServiceFallToLead;
import static ai.rnt.crm.util.CommonUtil.upNextActivities;
import static ai.rnt.crm.util.CompanyUtil.addUpdateCompanyDetails;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDate;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertLocalDate;
import static ai.rnt.crm.util.LeadsCardUtil.shortName;
import static ai.rnt.crm.util.OpportunityUtil.amountInWords;
import static ai.rnt.crm.util.OpportunityUtil.calculateBubbleSize;
import static ai.rnt.crm.util.OpportunityUtil.checkPhase;
import static java.lang.Boolean.TRUE;
import static java.lang.Double.valueOf;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.parse;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Map.Entry.comparingByKey;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.Comparator;
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
import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.dto.EditCallDto;
import ai.rnt.crm.dto.EditEmailDto;
import ai.rnt.crm.dto.EditMeetingDto;
import ai.rnt.crm.dto.EditVisitDto;
import ai.rnt.crm.dto.TimeLineActivityDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.dto.opportunity.AnalysisOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseOpportunityDto;
import ai.rnt.crm.dto.opportunity.GraphicalDataDto;
import ai.rnt.crm.dto.opportunity.OpportunityDto;
import ai.rnt.crm.dto.opportunity.OpprtAttachmentDto;
import ai.rnt.crm.dto.opportunity.ProposeOpportunityDto;
import ai.rnt.crm.dto.opportunity.QualifyOpportunityDto;
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
		EnumMap<ApiResponse, Object> dashBoardData = new EnumMap<>(ApiResponse.class);
		dashBoardData.put(SUCCESS, false);
		try {
			Map<String, Object> dataMap = new LinkedHashMap<>();
			Opportunity opportunity = opportunityDaoService.findOpportunity(optId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, "optId", optId));
			Integer leadId = opportunity.getLeads().getLeadId();
			List<Call> calls = callDaoService.getCallsByLeadId(leadId);
			List<Visit> visits = visitDaoService.getVisitsByLeadId(leadId);
			List<Email> emails = emailDaoService.getEmailByLeadId(leadId);
			List<Meetings> meetings = meetingDaoService.getMeetingByLeadId(leadId);
			List<TimeLineActivityDto> timeLine = calls.stream().filter(TIMELINE_CALL).map(call -> {
				EditCallDto callDto = new EditCallDto();
				callDto.setId(call.getCallId());
				callDto.setSubject(call.getSubject());
				callDto.setType(CALL);
				callDto.setBody(call.getComment());
				callDto.setStatus(call.getStatus());
				callDto.setCreatedOn(convertDate(call.getUpdatedDate()));
				callDto.setShortName(shortName(call.getCallTo()));
				TO_EMPLOYEE.apply(call.getCallFrom())
						.ifPresent(e -> callDto.setCallFrom(e.getFirstName() + " " + e.getLastName()));
				return callDto;
			}).collect(toList());
			timeLine.addAll(emails.stream().filter(TIMELINE_EMAIL).map(email -> {
				EditEmailDto editEmailDto = new EditEmailDto();
				editEmailDto.setId(email.getMailId());
				editEmailDto.setType(EMAIL);
				editEmailDto.setSubject(email.getSubject());
				editEmailDto.setBody(email.getContent());
				editEmailDto.setAttachments(TO_ATTACHMENT_DTOS.apply(email.getAttachment()));
				editEmailDto.setCreatedOn(convertDate(email.getCreatedDate()));
				editEmailDto.setShortName(shortName(email.getMailFrom()));
				editEmailDto.setStatus(email.getStatus());
				return editEmailDto;
			}).collect(toList()));
			timeLine.addAll(visits.stream().filter(TIMELINE_VISIT).map(visit -> {
				EditVisitDto visitDto = new EditVisitDto();
				visitDto.setId(visit.getVisitId());
				visitDto.setLocation(visit.getLocation());
				visitDto.setSubject(visit.getSubject());
				visitDto.setType(VISIT);
				visitDto.setBody(visit.getContent());
				visitDto.setStatus(visit.getStatus());
				employeeService.getById(visit.getCreatedBy()).ifPresent(
						byId -> visitDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
				visitDto.setCreatedOn(convertDate(visit.getUpdatedDate()));
				return visitDto;
			}).collect(toList()));
			timeLine.addAll(meetings.stream().filter(TIMELINE_MEETING).map(meet -> {
				EditMeetingDto meetDto = new EditMeetingDto();
				meetDto.setId(meet.getMeetingId());
				meetDto.setType(MEETING);
				employeeService.getById(meet.getCreatedBy()).ifPresent(
						byId -> meetDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
				meetDto.setSubject(meet.getMeetingTitle());
				meetDto.setBody(meet.getDescription());
				meetDto.setStatus(meet.getMeetingStatus());
				meetDto.setAttachments(TO_METTING_ATTACHMENT_DTOS.apply(meet.getMeetingAttachments()));
				meetDto.setCreatedOn(convertDate(meet.getUpdatedDate()));
				return meetDto;
			}).collect(toList()));
			timeLine.sort((t1, t2) -> parse(t2.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)
					.compareTo(parse(t1.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)));
			List<TimeLineActivityDto> activity = calls.stream().filter(ACTIVITY_CALL).map(call -> {
				EditCallDto callDto = new EditCallDto();
				callDto.setId(call.getCallId());
				callDto.setSubject(call.getSubject());
				callDto.setType(CALL);
				callDto.setBody(call.getComment());
				callDto.setShortName(shortName(call.getCallTo()));
				callDto.setDueDate(convertDateDateWithTime(call.getStartDate(), call.getStartTime12Hours()));
				callDto.setCreatedOn(convertDate(call.getCreatedDate()));
				TO_EMPLOYEE.apply(call.getCallFrom()).ifPresent(e -> {
					callDto.setCallFrom(e.getFirstName() + " " + e.getLastName());
					callDto.setAssignTo(e.getStaffId());
				});
				callDto.setOverDue(OVER_DUE.test(callDto.getDueDate()));
				callDto.setStatus(call.getStatus());
				return callDto;
			}).collect(toList());
			activity.addAll(emails.stream().filter(ACTIVITY_EMAIL).map(email -> {
				EditEmailDto editEmailDto = new EditEmailDto();
				editEmailDto.setId(email.getMailId());
				editEmailDto.setType(EMAIL);
				editEmailDto.setSubject(email.getSubject());
				editEmailDto.setBody(email.getContent());
				editEmailDto.setAttachments(TO_ATTACHMENT_DTOS.apply(email.getAttachment()));
				editEmailDto.setCreatedOn(convertDate(email.getCreatedDate()));
				editEmailDto.setShortName(shortName(email.getMailFrom()));
				editEmailDto.setOverDue(false);
				editEmailDto.setStatus(email.getStatus());
				editEmailDto.setAssignTo(employeeService.findByEmailId(email.getMailFrom()));
				editEmailDto.setScheduledDate(
						convertDateDateWithTime(email.getScheduledOn(), email.getScheduledAtTime12Hours()));
				return editEmailDto;
			}).collect(toList()));
			activity.addAll(visits.stream().filter(ACTIVITY_VISIT).map(visit -> {
				EditVisitDto editVisitDto = new EditVisitDto();
				editVisitDto.setId(visit.getVisitId());
				editVisitDto.setLocation(visit.getLocation());
				editVisitDto.setSubject(visit.getSubject());
				editVisitDto.setType(VISIT);
				editVisitDto.setBody(visit.getContent());
				editVisitDto.setDueDate(convertDateDateWithTime(visit.getStartDate(), visit.getStartTime12Hours()));
				employeeService.getById(visit.getCreatedBy()).ifPresent(
						byId -> editVisitDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
				editVisitDto.setAssignTo(visit.getVisitBy().getStaffId());
				editVisitDto.setCreatedOn(convertDate(visit.getCreatedDate()));
				editVisitDto.setOverDue(OVER_DUE.test(editVisitDto.getDueDate()));
				editVisitDto.setStatus(visit.getStatus());
				return editVisitDto;
			}).collect(toList()));
			activity.addAll(meetings.stream().filter(ACTIVITY_MEETING).map(meet -> {
				EditMeetingDto meetDto = new EditMeetingDto();
				meetDto.setId(meet.getMeetingId());
				meetDto.setType(MEETING);
				employeeService.getById(meet.getCreatedBy()).ifPresent(
						byId -> meetDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
				meetDto.setSubject(meet.getMeetingTitle());
				meetDto.setBody(meet.getDescription());
				meetDto.setDueDate(convertDateDateWithTime(meet.getStartDate(), meet.getStartTime12Hours()));
				meetDto.setAttachments(TO_METTING_ATTACHMENT_DTOS.apply(meet.getMeetingAttachments()));
				meetDto.setCreatedOn(convertDate(meet.getCreatedDate()));
				meetDto.setOverDue(OVER_DUE.test(meetDto.getDueDate()));
				meetDto.setStatus(meet.getMeetingStatus());
				meetDto.setAssignTo(meet.getAssignTo().getStaffId());
				return meetDto;
			}).collect(toList()));
			Comparator<TimeLineActivityDto> overDueActivity = (a1, a2) -> a2.getOverDue().compareTo(a1.getOverDue());
			activity.sort(overDueActivity.thenComparing((t1, t2) -> parse(t2.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)
					.compareTo(parse(t1.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM))));
			List<TimeLineActivityDto> upNext = calls.stream().filter(UPNEXT_CALL).map(call -> {
				EditCallDto callDto = new EditCallDto();
				callDto.setId(call.getCallId());
				callDto.setSubject(call.getSubject());
				callDto.setType(CALL);
				callDto.setBody(call.getComment());
				callDto.setCreatedOn(convertDateDateWithTime(call.getStartDate(), call.getStartTime12Hours()));
				TO_EMPLOYEE.apply(call.getCallFrom()).ifPresent(e -> {
					callDto.setCallFrom(e.getFirstName() + " " + e.getLastName());
					callDto.setAssignTo(e.getStaffId());
				});
				callDto.setStatus(call.getStatus());
				return callDto;
			}).collect(toList());

			upNext.addAll(visits.stream().filter(UPNEXT_VISIT).map(visit -> {
				EditVisitDto editVisitDto = new EditVisitDto();
				editVisitDto.setId(visit.getVisitId());
				editVisitDto.setLocation(visit.getLocation());
				editVisitDto.setSubject(visit.getSubject());
				editVisitDto.setType(VISIT);
				editVisitDto.setBody(visit.getContent());
				employeeService.getById(visit.getCreatedBy()).ifPresent(
						byId -> editVisitDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
				editVisitDto.setCreatedOn(convertDateDateWithTime(visit.getStartDate(), visit.getStartTime12Hours()));
				editVisitDto.setStatus(visit.getStatus());
				editVisitDto.setAssignTo(visit.getVisitBy().getStaffId());
				return editVisitDto;
			}).collect(toList()));
			upNext.addAll(meetings.stream().filter(UPNEXT_MEETING).map(meet -> {
				EditMeetingDto meetDto = new EditMeetingDto();
				meetDto.setId(meet.getMeetingId());
				meetDto.setType(MEETING);
				employeeService.getById(meet.getCreatedBy()).ifPresent(
						byId -> meetDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
				meetDto.setSubject(meet.getMeetingTitle());
				meetDto.setBody(meet.getDescription());
				meetDto.setAttachments(TO_METTING_ATTACHMENT_DTOS.apply(meet.getMeetingAttachments()));
				meetDto.setCreatedOn(convertDateDateWithTime(meet.getStartDate(), meet.getStartTime12Hours()));
				meetDto.setStatus(meet.getMeetingStatus());
				meetDto.setAssignTo(meet.getAssignTo().getStaffId());
				return meetDto;
			}).collect(toList()));
			upNext.sort((t1, t2) -> parse(t1.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)
					.compareTo(parse(t2.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)));
			LinkedHashMap<Long, List<TimeLineActivityDto>> upNextActivities = upNext.stream()
					.collect(groupingBy(e -> DAYS.between(
							now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime(),
							parse(e.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM))))
					.entrySet().stream().sorted(comparingByKey()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
							(oldValue, newValue) -> oldValue, LinkedHashMap::new));
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
			dataMap.put(TIMELINE, timeLine);
			dataMap.put(ACTIVITY, activity);
			dataMap.put(UPNEXT_DATA, upNextActivities(upNextActivities));
			dataMap.put(TASK, getTaskDataMap(calls, visits, meetings, opportunity.getLeads()));
			dashBoardData.put(SUCCESS, true);
			dashBoardData.put(DATA, dataMap);
			return new ResponseEntity<>(dashBoardData, OK);
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
	public ResponseEntity<EnumMap<ApiResponse, Object>> getQualifyPopUpData(Integer opportunityId) {
		log.info("inside the Opportunity getQualifyPopUpData method...{}", opportunityId);
		EnumMap<ApiResponse, Object> qualifyData = new EnumMap<>(ApiResponse.class);
		qualifyData.put(SUCCESS, false);
		try {
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			List<Contacts> contacts = opportunityData.getLeads().getContacts();
			List<OpprtAttachment> attachments = opportunityData.getOprtAttachment().stream()
					.filter(e -> nonNull(e.getAttachmentOf()) && QUALIFY.equalsIgnoreCase(e.getAttachmentOf()))
					.collect(toList());
			Optional<QualifyOpportunityDto> dto = TO_QUALIFY_OPPORTUNITY_DTO.apply(opportunityData);
			dto.ifPresent(e -> {
				e.setPrimaryContact(TO_CONTACT_DTO
						.apply(contacts.stream().filter(Contacts::getPrimary).findFirst()
								.orElseThrow(() -> new ResourceNotFoundException("Priamry Contact")))
						.orElseThrow(ResourceNotFoundException::new));
				e.setAssignTo(opportunityData.getEmployee().getStaffId());
				e.setLeadSourceId(opportunityData.getLeads().getLeadSourceMaster().getLeadSourceId());
				e.setContacts(TO_CONTACT_DTOS.apply(contacts));
				e.setAttachments(TO_OPTY_ATTACHMENT_DTOS.apply(attachments));
				e.setClients(TO_CONTACT_DTOS.apply(contacts.stream()
						.filter(cl -> nonNull(cl.getClient()) && TRUE.equals(cl.getClient())).collect(toList())));
			});
			qualifyData.put(DATA, dto);
			qualifyData.put(SUCCESS, true);
			return new ResponseEntity<>(qualifyData, OK);
		} catch (Exception e) {
			log.error("Got Exception in Opportunity while getting qualify data...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateQualifyPopUpData(QualifyOpportunityDto dto,
			Integer opportunityId) {
		log.info("inside the Opportunity updateQualifyPopUpData method...{}", opportunityId);
		EnumMap<ApiResponse, Object> updateQualifyData = new EnumMap<>(ApiResponse.class);
		try {
			boolean status = false;
			String phase = QUALIFY;
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));

			opportunityData.setEmployee(employeeService.getById(dto.getAssignTo())
					.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, dto.getAssignTo())));
			opportunityData.setTopic(dto.getTopic());
			opportunityData.setProposedSolution(dto.getProposedSolution());
			opportunityData.setClosedOn(dto.getUpdatedClosedOn());
			opportunityData.setBudgetAmount(dto.getBudgetAmount());
			opportunityData.setProgressStatus(dto.getProgressStatus());
			opportunityData.setCurrentPhase(dto.getCurrentPhase());

			List<Integer> clientList = dto.getClients().stream().filter(ContactDto::getClient)
					.map(ContactDto::getContactId).collect(toList());

			opportunityData.getLeads().getContacts().stream().filter(con -> clientList.contains(con.getContactId()))
					.forEach(con -> {
						Contacts contact = contactDaoService.findById(con.getContactId()).orElseThrow(
								() -> new ResourceNotFoundException("Contact", "contactId", con.getContactId()));
						contact.setClient(true);
						contactDaoService.addContact(contact);
					});
			List<OpprtAttachmentDto> isAttachments = dto.getAttachments();
			status = updateAttachmentsOfAllPhases(opportunityData, phase, isAttachments);
			if (status) {
				updateQualifyData.put(SUCCESS, true);
				updateQualifyData.put(MESSAGE, "Opportunity Qualify Successfully..!!");
			} else {
				updateQualifyData.put(SUCCESS, false);
				updateQualifyData.put(MESSAGE, "Opportunity Not Qualify");
			}
			return new ResponseEntity<>(updateQualifyData, CREATED);
		} catch (Exception e) {
			log.error("Got Exception in Opportunity while updating the qualify data...{}", e.getMessage());
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
			List<OpprtAttachment> attachments = opportunityData.getOprtAttachment().stream()
					.filter(e -> nonNull(e.getAttachmentOf()) && ANALYSIS.equalsIgnoreCase(e.getAttachmentOf()))
					.collect(toList());
			Optional<AnalysisOpportunityDto> dto = TO_ANALYSIS_OPPORTUNITY_DTO.apply(opportunityData);
			dto.ifPresent(l -> l.setAttachments(TO_OPPORTUNITY_ATTACHMENT_DTOS.apply(attachments)));
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
			boolean status = false;
			String phase = ANALYSIS;
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			opportunityData.setTechnicalNeed(dto.getTechnicalNeed());
			opportunityData.setIntegrationPoint(dto.getIntegrationPoint());
			opportunityData.setSecAndComp(dto.getSecAndComp());
			opportunityData.setRiskMinigation(dto.getRiskMinigation());
			opportunityData.setInitialTimeline(dto.getUpdatedInitialTimeline());
			opportunityData.setProgressStatus(dto.getProgressStatus());
			opportunityData.setCurrentPhase(dto.getCurrentPhase());

			List<OpprtAttachmentDto> isAttachments = dto.getAttachments();
			status = updateAttachmentsOfAllPhases(opportunityData, phase, isAttachments);
			if (status) {
				updateAnalysisData.put(SUCCESS, true);
				updateAnalysisData.put(MESSAGE, "Opportunity Analysis Successfully..!!");
			} else {
				updateAnalysisData.put(SUCCESS, false);
				updateAnalysisData.put(MESSAGE, "Opportunity Not Analysis");
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
			List<OpprtAttachment> attachments = opportunityData.getOprtAttachment().stream()
					.filter(e -> nonNull(e.getAttachmentOf()) && PROPOSE.equalsIgnoreCase(e.getAttachmentOf()))
					.collect(toList());
			Optional<ProposeOpportunityDto> dto = TO_PROPOSE_OPPORTUNITY_DTO.apply(opportunityData);
			dto.ifPresent(l -> l.setAttachments(TO_OPPORTUNITY_ATTACHMENT_DTOS.apply(attachments)));
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
			boolean status = false;
			String phase = PROPOSE;
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			opportunityData.setLicAndPricDetails(dto.getLicAndPricDetails());
			opportunityData.setDevPlan(dto.getDevPlan());
			opportunityData.setPropAcceptCriteria(dto.getPropAcceptCriteria());
			opportunityData.setPropExpDate(dto.getUpdatedPropExpDate());
			opportunityData.setPresentation(dto.getPresentation());
			opportunityData.setProposition(dto.getProposition());
			opportunityData.setTermsAndConditions(dto.getTermsAndConditions());
			opportunityData.setScopeOfWork(dto.getScopeOfWork());
			opportunityData.setProgressStatus(dto.getProgressStatus());
			opportunityData.setCurrentPhase(dto.getCurrentPhase());
			List<OpprtAttachmentDto> isAttachments = dto.getAttachments();
			status = updateAttachmentsOfAllPhases(opportunityData, phase, isAttachments);
			if (status) {
				updateProposeData.put(SUCCESS, true);
				updateProposeData.put(MESSAGE, "Opportunity Propose Successfully..!!");
			} else {
				updateProposeData.put(SUCCESS, false);
				updateProposeData.put(MESSAGE, "Opportunity Not Propose");
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
			List<OpprtAttachment> attachments = opportunityData.getOprtAttachment().stream()
					.filter(e -> nonNull(e.getAttachmentOf()) && CLOSE.equalsIgnoreCase(e.getAttachmentOf()))
					.collect(toList());
			Optional<CloseOpportunityDto> dto = TO_CLOSE_OPPORTUNITY_DTO.apply(opportunityData);
			dto.ifPresent(l -> l.setAttachments(TO_OPPORTUNITY_ATTACHMENT_DTOS.apply(attachments)));
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
			boolean status = false;
			String phase = CLOSE;
			Opportunity opportunityData = opportunityDaoService.findOpportunity(opportunityId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY2, OPPORTUNITY_ID, opportunityId));
			opportunityData.setWinLoseReason(dto.getWinLoseReason());
			opportunityData.setContract(dto.getContract());
			opportunityData.setPaymentTerms(dto.getPaymentTerms());
			opportunityData.setSupportPlan(dto.getSupportPlan());
			opportunityData.setFinalBudget(dto.getFinalBudget());
			opportunityData.setProgressStatus(dto.getProgressStatus());
			opportunityData.setCurrentPhase(dto.getCurrentPhase());
			List<OpprtAttachmentDto> isAttachments = dto.getAttachments();
			status = updateAttachmentsOfAllPhases(opportunityData, phase, isAttachments);
			if (status) {
				updateCloseData.put(SUCCESS, true);
				updateCloseData.put(MESSAGE, "Opportunity Close Successfully..!!");
			} else {
				updateCloseData.put(SUCCESS, false);
				updateCloseData.put(MESSAGE, "Opportunity Not Close");
			}
			return new ResponseEntity<>(updateCloseData, CREATED);
		} catch (Exception e) {
			log.error("Got Exception in Opportunity while updating the close popup data...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateOpportunity(UpdateLeadDto dto, Integer opportunityId) {
		log.info("inside the updateOpportunity method...{}", opportunityId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
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
				result.put(MESSAGE, "Opportunity Details Updated Successfully !!");
			else
				result.put(MESSAGE, "Opportunity Details Not Updated !!");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while updateOpportunity..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	private boolean updateAttachmentsOfAllPhases(Opportunity opportunityData, String phase,
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