package ai.rnt.crm.util;

import static ai.rnt.crm.util.EmailUtil.sendCallTaskRemainderMail;
import static ai.rnt.crm.util.EmailUtil.sendMeetingTaskRemainderMail;
import static ai.rnt.crm.util.EmailUtil.sendVisitTaskRemainderMail;
import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 11/12/2023
 * @version 1.0
 *
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TaskRemainderUtil {

	private final CallDaoService callDaoService;

	private final VisitDaoService visitDaoService;

	private final MeetingDaoService meetingDaoService;

	private final EmployeeDaoService employeeDaoService;

	private final TaskNotificationsUtil taskNotificationsUtil;

	private final AuditAwareUtil auditAwareUtil;

	private final EmployeeService employeeService;

	@Scheduled(cron = "0 * * * * ?") // for every minute.
	public void remainderForCallTask() throws Exception {
		try {
			LocalDateTime todayDate = LocalDate.now().atStartOfDay();
			Date todayAsDate = Date.from(todayDate.atZone(ZoneId.systemDefault()).toInstant());
			String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));

			List<PhoneCallTask> callTaskList = callDaoService.getTodaysCallTask(todayAsDate, time);
			callTaskList.forEach(e -> {
				String emailId = employeeDaoService.getEmailId(e.getAssignTo().getStaffId());
				if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase("Both")) {
					sendCallTaskRemainderMail(emailId);
					callNotification(e.getCallTaskId(), e.getAssignTo().getStaffId(), e.getSubject());
				} else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase("Email"))
					sendCallTaskRemainderMail(emailId);

				else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase("Notifications"))
					callNotification(e.getCallTaskId(), e.getAssignTo().getStaffId(), e.getSubject());
			});

			List<VisitTask> visitTaskList = visitDaoService.getTodaysAllVisitTask(todayAsDate, time);
			visitTaskList.forEach(e -> {
				String emailId = employeeDaoService.getEmailId(e.getAssignTo().getStaffId());
				if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase("Both")) {
					sendVisitTaskRemainderMail(emailId);
					visitNotification(e.getVisitTaskId(), e.getAssignTo().getStaffId(), e.getSubject());
				} else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase("Email"))
					sendVisitTaskRemainderMail(emailId);

				else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase("Notifications"))
					visitNotification(e.getVisitTaskId(), e.getAssignTo().getStaffId(), e.getSubject());
			});

			List<MeetingTask> meetingTasksList = meetingDaoService.getTodaysMeetingTask(todayAsDate, time);
			meetingTasksList.forEach(e -> {
				String emailId = employeeDaoService.getEmailId(e.getAssignTo().getStaffId());
				if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase("Both")) {
					sendMeetingTaskRemainderMail(emailId);
					meetingNotification(e.getMeetingTaskId(), e.getAssignTo().getStaffId(), e.getSubject());
				} else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase("Email"))
					sendMeetingTaskRemainderMail(emailId);
				else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase("Notifications"))
					meetingNotification(e.getMeetingTaskId(), e.getAssignTo().getStaffId(), e.getSubject());
			});

		} catch (Exception e) {
			log.error("Got Exception while sending mails to the task of call, visit and meeting..{}", e);
			throw new CRMException(e);
		}
	}

	public void callNotification(Integer callTaskId, Integer assignTo, String subject) {
		try {
			TaskNotifications taskNotifications = new TaskNotifications();
			taskNotifications.setCallTask(callDaoService.getCallTaskById(callTaskId)
					.orElseThrow(() -> new ResourceNotFoundException("PhoneCallTask", "taskId", callTaskId)));
			taskNotifications.setCreatedBy(1375);
			taskNotifications.setNotifTo(employeeService.getById(assignTo)
					.orElseThrow(() -> new ResourceNotFoundException("EmployeeMaster", "StaffId", assignTo)));
			taskNotifications.setNotifStatus(true);
			taskNotifications.setNotifMessage(subject);
			taskNotificationsUtil.sendCallTaskNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending callTask notification..{}", e);
			throw new CRMException(e);
		}
	}

	public void visitNotification(Integer visitTaskId, Integer assignTo, String subject) {
		try {
			TaskNotifications taskNotifications = new TaskNotifications();
			taskNotifications.setVisitTask(visitDaoService.getVisitTaskById(visitTaskId)
					.orElseThrow(() -> new ResourceNotFoundException("VisitTask", "taskId", visitTaskId)));
			taskNotifications.setCreatedBy(1375);
			taskNotifications.setNotifTo(employeeService.getById(assignTo)
					.orElseThrow(() -> new ResourceNotFoundException("EmployeeMaster", "StaffId", assignTo)));
			taskNotifications.setNotifStatus(true);
			taskNotifications.setNotifMessage(subject);
			taskNotificationsUtil.sendVisitTaskNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending visitTask notification..{}", e);
			throw new CRMException(e);
		}
	}

	public void meetingNotification(Integer meetingTaskId, Integer assignTo, String subject) {
		try {
			TaskNotifications taskNotifications = new TaskNotifications();
			taskNotifications.setMeetingTask(meetingDaoService.getMeetingTaskById(meetingTaskId)
					.orElseThrow(() -> new ResourceNotFoundException("MeetingTask", "taskId", meetingTaskId)));
			taskNotifications.setCreatedBy(1375);
			taskNotifications.setNotifTo(employeeService.getById(assignTo)
					.orElseThrow(() -> new ResourceNotFoundException("EmployeeMaster", "StaffId", assignTo)));
			taskNotifications.setNotifStatus(true);
			taskNotifications.setNotifMessage(subject);
			taskNotificationsUtil.sendMeetingTaskNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending meetingTask notification..{}", e);
			throw new CRMException(e);
		}
	}
}
