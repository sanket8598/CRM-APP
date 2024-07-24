package ai.rnt.crm.util;

import static ai.rnt.crm.constants.CRMConstants.ALL_TASK;
import static ai.rnt.crm.constants.CRMConstants.ALL_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.COMPLETED_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.COMPLETED_TASK_KEY;
import static ai.rnt.crm.constants.CRMConstants.COUNT_BY_STATUS;
import static ai.rnt.crm.constants.CRMConstants.IN_PROGRESS_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.IN_PROGRESS_TASK_KEY;
import static ai.rnt.crm.constants.CRMConstants.LEAD_SOURCE_MASTER;
import static ai.rnt.crm.constants.CRMConstants.LEAD_SOURCE_NAME;
import static ai.rnt.crm.constants.CRMConstants.NOT_STARTED_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.NOT_STARTED_TASK_KEY;
import static ai.rnt.crm.constants.CRMConstants.ON_HOLD_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.ON_HOLD_TASK_KEY;
import static ai.rnt.crm.constants.CRMConstants.OTHER;
import static ai.rnt.crm.constants.CRMConstants.SERVICE_FALLS_MASTER;
import static ai.rnt.crm.constants.CRMConstants.SERVICE_FALL_ID;
import static ai.rnt.crm.constants.CRMConstants.UPNEXT_DATA_KEY;
import static ai.rnt.crm.constants.DateFormatterConstant.DATE_TIME_WITH_AM_OR_PM;
import static ai.rnt.crm.constants.MessageConstants.MSG;
import static ai.rnt.crm.constants.MessageConstants.NO_ACTIVITY;
import static ai.rnt.crm.constants.MessageConstants.SOON_MORE;
import static ai.rnt.crm.constants.MessageConstants.WAIT_FOR;
import static ai.rnt.crm.constants.RegexConstant.IS_DIGIT;
import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.constants.StatusConstants.CALL;
import static ai.rnt.crm.constants.StatusConstants.DESCRIPTION;
import static ai.rnt.crm.constants.StatusConstants.EMAIL;
import static ai.rnt.crm.constants.StatusConstants.LEAD;
import static ai.rnt.crm.constants.StatusConstants.MEETING;
import static ai.rnt.crm.constants.StatusConstants.OPPORTUNITY;
import static ai.rnt.crm.constants.StatusConstants.VISIT;
import static ai.rnt.crm.dto_mapper.AttachmentDtoMapper.TO_ATTACHMENT_DTOS;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_EMPLOYEE;
import static ai.rnt.crm.dto_mapper.LeadSourceDtoMapper.TO_LEAD_SOURCE;
import static ai.rnt.crm.dto_mapper.MeetingAttachmentDtoMapper.TO_METTING_ATTACHMENT_DTOS;
import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER;
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
import static ai.rnt.crm.functional.predicates.OverDueActivity.OVER_DUE;
import static ai.rnt.crm.functional.predicates.TaskPredicates.COMPLETED_TASK;
import static ai.rnt.crm.functional.predicates.TaskPredicates.IN_PROGRESS_TASK;
import static ai.rnt.crm.functional.predicates.TaskPredicates.NOT_STARTED_TASK;
import static ai.rnt.crm.functional.predicates.TaskPredicates.ON_HOLD_TASK;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDate;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;
import static ai.rnt.crm.util.LeadsCardUtil.shortName;
import static ai.rnt.crm.util.XSSUtil.sanitize;
import static java.lang.Boolean.TRUE;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.parse;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Comparator.naturalOrder;
import static java.util.Map.Entry.comparingByKey;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ai.rnt.crm.dao.service.DomainMasterDaoService;
import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dto.DescriptionDto;
import ai.rnt.crm.dto.EditCallDto;
import ai.rnt.crm.dto.EditEmailDto;
import ai.rnt.crm.dto.EditMeetingDto;
import ai.rnt.crm.dto.EditVisitDto;
import ai.rnt.crm.dto.MainTaskDto;
import ai.rnt.crm.dto.TimeLineActivityDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.Description;
import ai.rnt.crm.entity.DomainMaster;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class CommonUtil {

	public static Map<String, Object> getTaskDataMap(List<Call> calls, List<Visit> visits, List<Meetings> meetings,
			Leads lead, Opportunity opportunity) {
		log.info("inside the getTaskDataMap method...");
		Map<String, Object> taskData = new HashMap<>();
		Map<String, Object> taskCount = new HashMap<>();
		List<MainTaskDto> allTask = getCallRelatedTasks(calls);
		allTask.addAll(getVistRelatedTasks(visits));
		allTask.addAll(getMeetingRelatedTasks(meetings));
		allTask.addAll(getLeadRelatedTasks(lead));
		if (nonNull(opportunity))
			allTask.addAll(getOpportunityRelatedTasks(opportunity));
		List<MainTaskDto> notStartedTask = allTask.stream().filter(NOT_STARTED_TASK)
				.sorted((t1, t2) -> parse(t1.getDueDate(), DATE_TIME_WITH_AM_OR_PM)
						.compareTo(parse(t2.getDueDate(), DATE_TIME_WITH_AM_OR_PM)))
				.collect(toList());
		List<MainTaskDto> inProgressTask = allTask.stream().filter(IN_PROGRESS_TASK)
				.sorted((t1, t2) -> parse(t1.getDueDate(), DATE_TIME_WITH_AM_OR_PM)
						.compareTo(parse(t2.getDueDate(), DATE_TIME_WITH_AM_OR_PM)))
				.collect(toList());
		List<MainTaskDto> onHoldTask = allTask.stream().filter(ON_HOLD_TASK)
				.sorted((t1, t2) -> parse(t1.getDueDate(), DATE_TIME_WITH_AM_OR_PM)
						.compareTo(parse(t2.getDueDate(), DATE_TIME_WITH_AM_OR_PM)))
				.collect(toList());
		List<MainTaskDto> completedTask = allTask.stream().filter(COMPLETED_TASK)
				.sorted((t1, t2) -> parse(t1.getDueDate(), DATE_TIME_WITH_AM_OR_PM)
						.compareTo(parse(t2.getDueDate(), DATE_TIME_WITH_AM_OR_PM)))
				.collect(toList());
		taskData.put(ALL_TASK, allTask.stream().sorted((t1, t2) -> parse(t1.getDueDate(), DATE_TIME_WITH_AM_OR_PM)
				.compareTo(parse(t2.getDueDate(), DATE_TIME_WITH_AM_OR_PM))).collect(toList()));
		taskData.put(COMPLETED_TASK_KEY, completedTask);
		taskData.put(IN_PROGRESS_TASK_KEY, inProgressTask);
		taskData.put(ON_HOLD_TASK_KEY, onHoldTask);
		taskData.put(NOT_STARTED_TASK_KEY, notStartedTask);
		taskCount.put(ALL_TASK_COUNT, allTask.stream().count());
		taskCount.put(COMPLETED_TASK_COUNT, completedTask.stream().count());
		taskCount.put(IN_PROGRESS_TASK_COUNT, inProgressTask.stream().count());
		taskCount.put(ON_HOLD_TASK_COUNT, onHoldTask.stream().count());
		taskCount.put(NOT_STARTED_TASK_COUNT, notStartedTask.stream().count());
		taskData.put(COUNT_BY_STATUS, taskCount);
		return taskData;
	}

	public static List<MainTaskDto> getCallRelatedTasks(List<Call> calls) {
		log.info("inside the getCallRelatedTasks method...");
		return calls.stream().flatMap(call -> call.getCallTasks().stream()).filter(e -> isNull(e.getDeletedBy()))
				.map(e -> new MainTaskDto(e.getCallTaskId(), e.getSubject(), e.getStatus(), CALL,
						convertDateDateWithTime(e.getDueDate(), e.getDueTime12Hours()),
						TO_EMPLOYEE.apply(e.getAssignTo()).orElse(null), e.getCall().getCallId(), e.isRemainderOn(),
						e.getCall().getStatus(),
						e.isRemainderOn() ? convertDateDateWithTime(e.getRemainderDueOn(), e.getRemainderDueAt12Hours())
								: null))
				.collect(toList());
	}

	public static List<MainTaskDto> getVistRelatedTasks(List<Visit> visits) {
		log.info("inside the getVistRelatedTasks method...");
		return visits.stream().flatMap(visit -> visit.getVisitTasks().stream()).filter(e -> isNull(e.getDeletedBy()))
				.map(e -> new MainTaskDto(e.getVisitTaskId(), e.getSubject(), e.getStatus(), VISIT,
						convertDateDateWithTime(e.getDueDate(), e.getDueTime12Hours()),
						TO_EMPLOYEE.apply(e.getAssignTo()).orElse(null), e.getVisit().getVisitId(), e.isRemainderOn(),
						e.getVisit().getStatus(),
						e.isRemainderOn() ? convertDateDateWithTime(e.getRemainderDueOn(), e.getRemainderDueAt12Hours())
								: null))
				.collect(toList());
	}

	public static List<MainTaskDto> getMeetingRelatedTasks(List<Meetings> meetings) {
		log.info("inside the getMeetingRelatedTasks method...");
		return meetings.stream().flatMap(meet -> meet.getMeetingTasks().stream()).filter(e -> isNull(e.getDeletedBy()))
				.map(e -> new MainTaskDto(e.getMeetingTaskId(), e.getSubject(), e.getStatus(), MEETING,
						convertDateDateWithTime(e.getDueDate(), e.getDueTime12Hours()),
						TO_EMPLOYEE.apply(e.getAssignTo()).orElse(null), e.getMeetings().getMeetingId(),
						e.isRemainderOn(), e.getMeetings().getMeetingStatus(),
						e.isRemainderOn() ? convertDateDateWithTime(e.getRemainderDueOn(), e.getRemainderDueAt12Hours())
								: null))
				.collect(toList());
	}

	public static List<MainTaskDto> getLeadRelatedTasks(Leads lead) {
		log.info("inside the getLeadRelatedTasks method...");
		return lead.getLeadTasks().stream().filter(e -> isNull(e.getDeletedBy()))
				.map(e -> new MainTaskDto(e.getLeadTaskId(), e.getSubject(), e.getStatus(), LEAD,
						convertDateDateWithTime(e.getDueDate(), e.getDueTime12Hours()),
						TO_EMPLOYEE.apply(e.getAssignTo()).orElse(null), e.getLead().getLeadId(), e.isRemainderOn(),
						e.getLead().getStatus(),
						e.isRemainderOn() ? convertDateDateWithTime(e.getRemainderDueOn(), e.getRemainderDueAt12Hours())
								: null))
				.collect(toList());
	}

	public static List<MainTaskDto> getOpportunityRelatedTasks(Opportunity oppt) {
		log.info("inside the getOpportunityRelatedTasks method...");
		return oppt.getOpportunityTasks().stream().filter(e -> isNull(e.getDeletedBy()))
				.map(e -> new MainTaskDto(e.getOptyTaskId(), e.getSubject(), e.getStatus(), OPPORTUNITY,
						convertDateDateWithTime(e.getDueDate(), e.getDueTime12Hours()),
						TO_EMPLOYEE.apply(e.getAssignTo()).orElse(null), e.getOpportunity().getOpportunityId(),
						e.isRemainderOn(), e.getOpportunity().getStatus(),
						e.isRemainderOn() ? convertDateDateWithTime(e.getRemainderDueOn(), e.getRemainderDueAt12Hours())
								: null))
				.collect(toList());
	}

	public static Map<String, Object> upNextActivities(
			LinkedHashMap<Long, List<TimeLineActivityDto>> upNextActivities) {
		log.info("inside the upNextActivities method...{}", upNextActivities);
		Map<String, Object> upNextDataMap = new HashMap<>();
		upNextDataMap.put(MSG, NO_ACTIVITY);
		if (nonNull(upNextActivities) && !upNextActivities.isEmpty()) {
			Long lowestDayDiff = upNextActivities.keySet().stream().min(naturalOrder()).orElse(null);
			if (nonNull(lowestDayDiff)) {
				if (upNextActivities.size() == 1)
					upNextDataMap.put(MSG, SOON_MORE);
				int count = 0;
				List<TimeLineActivityDto> upNextData = new ArrayList<>();
				for (Map.Entry<Long, List<TimeLineActivityDto>> data : upNextActivities.entrySet()) {
					data.getValue().forEach(e -> {
						e.setWaitTwoDays(!lowestDayDiff.equals(data.getKey()));
						upNextData.add(e);
					});
					if (count == 1 && !data.getKey().equals(0L))
						upNextDataMap.put(MSG, format(WAIT_FOR, data.getKey()));
					count++;
				}
				upNextDataMap.put(UPNEXT_DATA_KEY, upNextData);
			}
		}
		return upNextDataMap;
	}

	public static void setServiceFallToLead(String serviceFallsName, Leads leads,
			ServiceFallsDaoSevice serviceFallsDaoSevice) throws Exception {
		log.info("inside the setServiceFallToLead method...{}", serviceFallsName);
		if (nonNull(serviceFallsName) && compile(IS_DIGIT).matcher(serviceFallsName).matches())
			serviceFallsDaoSevice.getServiceFallById(parseInt(serviceFallsName))
					.ifPresent(leads::setServiceFallsMaster);
		else if (isNull(serviceFallsName) || serviceFallsName.isEmpty() || OTHER.equals(serviceFallsName))
			serviceFallsDaoSevice.findByName(OTHER).ifPresent(leads::setServiceFallsMaster);
		else {
			ServiceFallsMaster serviceFalls = new ServiceFallsMaster();
			serviceFalls.setServiceName(sanitize(serviceFallsName));
			TO_SERVICE_FALL_MASTER.apply(serviceFallsDaoSevice.save(serviceFalls).orElseThrow(
					() -> new ResourceNotFoundException(SERVICE_FALLS_MASTER, SERVICE_FALL_ID, serviceFallsName)))
					.ifPresent(leads::setServiceFallsMaster);
		}
	}

	public static void setLeadSourceToLead(String leadSourceName, Leads leads,
			LeadSourceDaoService leadSourceDaoService) throws Exception {
		log.info("inside the setLeadSourceToLead method...{} ", leadSourceName);
		if (nonNull(leadSourceName) && compile(IS_DIGIT).matcher(leadSourceName).matches())
			leadSourceDaoService.getLeadSourceById(parseInt(leadSourceName)).ifPresent(leads::setLeadSourceMaster);
		else if (isNull(leadSourceName) || leadSourceName.isEmpty() || OTHER.equals(leadSourceName))
			leadSourceDaoService.getByName(OTHER).ifPresent(leads::setLeadSourceMaster);
		else {
			LeadSourceMaster leadSource = new LeadSourceMaster();
			leadSource.setSourceName(sanitize(leadSourceName));
			TO_LEAD_SOURCE
					.apply(leadSourceDaoService.save(leadSource).orElseThrow(
							() -> new ResourceNotFoundException(LEAD_SOURCE_MASTER, LEAD_SOURCE_NAME, leadSourceName)))
					.ifPresent(leads::setLeadSourceMaster);
		}

	}

	public static void setDomainToLead(String domainName, Leads leads, DomainMasterDaoService domainMasterDaoService)
			throws Exception {
		log.info("inside the setDomainToLead method...{} ", domainName);
		if (nonNull(domainName) && compile(IS_DIGIT).matcher(domainName).matches())
			domainMasterDaoService.findById(parseInt(domainName)).ifPresent(leads::setDomainMaster);
		else if (isNull(domainName) || domainName.isEmpty() || OTHER.equals(domainName))
			domainMasterDaoService.findByName(OTHER).ifPresent(leads::setDomainMaster);
		else {
			DomainMaster domainMaster = new DomainMaster();
			domainMaster.setDomainName(sanitize(domainName));
			domainMasterDaoService.addDomain(domainMaster).ifPresent(leads::setDomainMaster);
		}
	}

	public static List<TimeLineActivityDto> getTimelineData(List<Call> calls, List<Visit> visits, List<Email> emails,
			List<Meetings> meetings, EmployeeService employeeService) {
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
			employeeService.getById(meet.getCreatedBy())
					.ifPresent(byId -> meetDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
			meetDto.setSubject(meet.getMeetingTitle());
			meetDto.setBody(meet.getDescription());
			meetDto.setStatus(meet.getMeetingStatus());
			meetDto.setAttachments(TO_METTING_ATTACHMENT_DTOS.apply(meet.getMeetingAttachments()));
			meetDto.setCreatedOn(convertDate(meet.getUpdatedDate()));
			return meetDto;
		}).collect(toList()));
		timeLine.sort((t1, t2) -> parse(t2.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)
				.compareTo(parse(t1.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)));
		return timeLine;
	}

	public static List<TimeLineActivityDto> getActivityData(List<Call> calls, List<Visit> visits, List<Email> emails,
			List<Meetings> meetings, EmployeeService employeeService) {
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
			employeeService.getById(meet.getCreatedBy())
					.ifPresent(byId -> meetDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
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
		return activity;

	}

	public static LinkedHashMap<Long, List<TimeLineActivityDto>> getUpnextData(List<Call> calls, List<Visit> visits,
			List<Email> emails, List<Meetings> meetings, EmployeeService employeeService) {
		return getUpnextActivities(calls, visits, emails, meetings, employeeService).stream()
				.collect(groupingBy(e -> DAYS.between(
						now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime(),
						parse(e.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM))))
				.entrySet().stream().sorted(comparingByKey()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
						(oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}

	public static List<TimeLineActivityDto> getUpnextActivities(List<Call> calls, List<Visit> visits,
			List<Email> emails, List<Meetings> meetings, EmployeeService employeeService) {
		List<TimeLineActivityDto> upNext = calls.stream().filter(UPNEXT_CALL).map(call -> {
			EditCallDto callDto = new EditCallDto();
			if (TRUE.equals(call.getIsOpportunity())) {
				callDto.setParentId(call.getLead().getOpportunity().getOpportunityId());
				callDto.setActivityFrom(OPPORTUNITY);
			} else {
				callDto.setParentId(call.getLead().getLeadId());
				callDto.setActivityFrom(LEAD);
			}
			callDto.setId(call.getCallId());
			callDto.setSubject(call.getSubject());
			callDto.setType(CALL);
			callDto.setBody(call.getComment());
			callDto.setShortName(shortName(call.getCallTo()));
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
			if (TRUE.equals(visit.getIsOpportunity())) {
				editVisitDto.setParentId(visit.getLead().getOpportunity().getOpportunityId());
				editVisitDto.setActivityFrom(OPPORTUNITY);
			} else {
				editVisitDto.setParentId(visit.getLead().getLeadId());
				editVisitDto.setActivityFrom(LEAD);
			}
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
			if (TRUE.equals(meet.getIsOpportunity())) {
				meetDto.setParentId(meet.getLead().getOpportunity().getOpportunityId());
				meetDto.setActivityFrom(OPPORTUNITY);
			} else {
				meetDto.setParentId(meet.getLead().getLeadId());
				meetDto.setActivityFrom(LEAD);
			}
			meetDto.setId(meet.getMeetingId());
			meetDto.setType(MEETING);
			employeeService.getById(meet.getCreatedBy())
					.ifPresent(byId -> meetDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
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
		return upNext;
	}

	public static List<DescriptionDto> getDescData(List<Description> descriptions) {
		return descriptions.stream().map(description -> {
			DescriptionDto descriptionDto = new DescriptionDto();
			descriptionDto.setDescId(description.getDescId());
			descriptionDto.setSubject(description.getSubject());
			descriptionDto.setType(DESCRIPTION);
			descriptionDto.setDesc(description.getDesc());
			descriptionDto.setStatus(description.getStatus());
			descriptionDto.setGetDate(description.getDate());
			descriptionDto.setAction(description.getAction());
			return descriptionDto;
		}).collect(toList());
	}
}
