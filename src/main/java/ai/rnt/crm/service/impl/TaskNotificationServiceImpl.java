package ai.rnt.crm.service.impl;

import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.TaskNotificationDaoService;
import ai.rnt.crm.dto.TaskNotificationsDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.TaskNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TaskNotificationServiceImpl implements TaskNotificationService {

	private final TaskNotificationDaoService taskNotificationDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getNotification(Integer staffId) {
		log.info("inside the getNotification method...{}", staffId);
		EnumMap<ApiResponse, Object> notification = new EnumMap<>(ApiResponse.class);
		try {
			Map<String, Object> countData = new HashMap<>();
			List<TaskNotifications> notifyData = taskNotificationDaoService.getNotifications(staffId);
			List<TaskNotificationsDto> notifications = notifyData.stream().map(this::getMessage).collect(toList());
			countData.put("Count", notifyData.stream().count());
			countData.put("Notifications", notifications);
			notification.put(DATA, countData);
			notification.put(SUCCESS, true);
			return new ResponseEntity<>(notification, OK);
		} catch (Exception e) {
			log.error("Got exception while getting notification..{}" + e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> seenNotification(Integer notifId) {
		log.info("inside the seenNotification method...{}", notifId);
		EnumMap<ApiResponse, Object> notification = new EnumMap<>(ApiResponse.class);
		try {
			notification.put(SUCCESS, false);
			TaskNotifications notifyData = taskNotificationDaoService.getNotificationById(notifId)
					.orElseThrow(() -> new ResourceNotFoundException("TaskNotifications", "NotifId", notifId));
			notifyData.setNotifStatus(false);
			if (nonNull(taskNotificationDaoService.seenNotification(notifyData)))
				notification.put(SUCCESS, true);
			return new ResponseEntity<>(notification, CREATED);
		} catch (Exception e) {
			log.error("Got exception while updating notification by Id..{}" + e.getMessage());
			throw new CRMException(e);
		}
	}

	public TaskNotificationsDto getMessage(TaskNotifications notification) {
		log.info("inside the getMessage for Notification method...");
		try {
			if (nonNull(notification)) {
				String msg = "Task Reminder : %s : The task is scheduled for completion by %s";
				String leadMsg = "New Lead Assigned : %s by %s Assigned date %s";
				String optyMsg = "New Opportunity Assigned : %s by %s Assigned date %s";
				TaskNotificationsDto dto = new TaskNotificationsDto();
				dto.setNotifStatus(notification.isNotifStatus());
				dto.setNotifId(notification.getNotifId());
				if (nonNull(notification.getCallTask())) {
					dto.setMessage(format(msg, notification.getCallTask().getSubject(), convertDateDateWithTime(
							notification.getCallTask().getDueDate(), notification.getCallTask().getDueTime12Hours())));
					dto.setCallTaskId(notification.getCallTask().getCallTaskId());
				} else if (nonNull(notification.getVisitTask())) {
					dto.setMessage(format(msg, notification.getVisitTask().getSubject(),
							convertDateDateWithTime(notification.getVisitTask().getDueDate(),
									notification.getVisitTask().getDueTime12Hours())));
					dto.setVisitTaskId(notification.getVisitTask().getVisitTaskId());
				} else if (nonNull(notification.getMeetingTask())) {
					dto.setMessage(format(msg, notification.getMeetingTask().getSubject(),
							convertDateDateWithTime(notification.getMeetingTask().getDueDate(),
									notification.getMeetingTask().getDueTime12Hours())));
					dto.setMeetingTaskId(notification.getMeetingTask().getMeetingTaskId());
				} else if (nonNull(notification.getLeadTask())) {
					dto.setMessage(format(msg, notification.getLeadTask().getSubject(), convertDateDateWithTime(
							notification.getLeadTask().getDueDate(), notification.getLeadTask().getDueTime12Hours())));
					dto.setLeadTaskId(notification.getLeadTask().getLeadTaskId());
				} else if (nonNull(notification.getLeads())) {
					dto.setMessage(format(leadMsg,
							notification.getLeads().getContacts().stream().filter(Contacts::getPrimary)
									.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse(""),
							notification.getLeads().getAssignBy().getFirstName() + " "
									+ notification.getLeads().getAssignBy().getLastName(),
							notification.getLeads().getAssignDate().toString()));
					dto.setLeadId(notification.getLeads().getLeadId());
				} else if (nonNull(notification.getOpportunity())) {
					dto.setMessage(format(optyMsg,
							notification.getOpportunity().getLeads().getContacts().stream().filter(Contacts::getPrimary)
									.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse(""),
							notification.getOpportunity().getAssignBy().getFirstName() + " "
									+ notification.getOpportunity().getAssignBy().getLastName(),
							notification.getOpportunity().getAssignDate().toString()));
					dto.setOptyId(notification.getOpportunity().getOpportunityId());
				}
				return dto;
			}
			return null;
		} catch (Exception e) {
			log.error("Got exception while getMessage for Notification..{}" + e.getMessage());
			return null;
		}
	}
}
