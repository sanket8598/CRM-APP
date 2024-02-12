package ai.rnt.crm.util;

import static ai.rnt.crm.constants.CRMConstants.ALL_TASK;
import static ai.rnt.crm.constants.CRMConstants.ALL_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.COMPLETED_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.COMPLETED_TASK_KEY;
import static ai.rnt.crm.constants.CRMConstants.COUNT_BY_STATUS;
import static ai.rnt.crm.constants.CRMConstants.IN_PROGRESS_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.IN_PROGRESS_TASK_KEY;
import static ai.rnt.crm.constants.CRMConstants.NOT_STARTED_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.NOT_STARTED_TASK_KEY;
import static ai.rnt.crm.constants.CRMConstants.ON_HOLD_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.ON_HOLD_TASK_KEY;
import static ai.rnt.crm.constants.CRMConstants.UPNEXT_DATA_KEY;
import static ai.rnt.crm.constants.DateFormatterConstant.DATE_TIME_WITH_AM_OR_PM;
import static ai.rnt.crm.constants.MessageConstants.MSG;
import static ai.rnt.crm.constants.MessageConstants.NO_ACTIVITY;
import static ai.rnt.crm.constants.MessageConstants.SOON_MORE;
import static ai.rnt.crm.constants.MessageConstants.WAIT_FOR;
import static ai.rnt.crm.constants.StatusConstants.CALL;
import static ai.rnt.crm.constants.StatusConstants.LEAD;
import static ai.rnt.crm.constants.StatusConstants.MEETING;
import static ai.rnt.crm.constants.StatusConstants.VISIT;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_EMPLOYEE;
import static ai.rnt.crm.functional.predicates.TaskPredicates.COMPLETED_TASK;
import static ai.rnt.crm.functional.predicates.TaskPredicates.IN_PROGRESS_TASK;
import static ai.rnt.crm.functional.predicates.TaskPredicates.NOT_STARTED_TASK;
import static ai.rnt.crm.functional.predicates.TaskPredicates.ON_HOLD_TASK;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;
import static java.lang.String.format;
import static java.time.LocalDateTime.parse;
import static java.util.Comparator.naturalOrder;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ai.rnt.crm.dto.MainTaskDto;
import ai.rnt.crm.dto.TimeLineActivityDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.Visit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {
	
	
	public static Map<String, Object> getTaskDataMap(List<Call> calls, List<Visit> visits, List<Meetings> meetings,
			Leads lead) {
		log.info("inside the getTaskDataMap method...");
		Map<String, Object> taskData = new HashMap<>();
		Map<String, Object> taskCount = new HashMap<>();
		List<MainTaskDto> allTask = getCallRelatedTasks(calls);
		allTask.addAll(getVistRelatedTasks(visits));
		allTask.addAll(getMeetingRelatedTasks(meetings));
		allTask.addAll(getLeadRelatedTasks(lead));
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
		return calls.stream().flatMap(call -> call.getCallTasks().stream())
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
		return visits.stream().flatMap(visit -> visit.getVisitTasks().stream())
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
		return meetings.stream().flatMap(meet -> meet.getMeetingTasks().stream())
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
		return lead.getLeadTasks().stream()
				.map(e -> new MainTaskDto(e.getLeadTaskId(), e.getSubject(), e.getStatus(), LEAD,
						convertDateDateWithTime(e.getDueDate(), e.getDueTime12Hours()),
						TO_EMPLOYEE.apply(e.getAssignTo()).orElse(null), e.getLead().getLeadId(), e.isRemainderOn(),
						e.getLead().getStatus(),
						e.isRemainderOn() ? convertDateDateWithTime(e.getRemainderDueOn(), e.getRemainderDueAt12Hours())
								: null))
				.collect(toList());
	}
	
	public static Map<String, Object> upNextActivities(LinkedHashMap<Long, List<TimeLineActivityDto>> upNextActivities) {
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

}
