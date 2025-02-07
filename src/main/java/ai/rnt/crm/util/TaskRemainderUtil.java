package ai.rnt.crm.util;

import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.constants.StatusConstants.BOTH;
import static ai.rnt.crm.constants.StatusConstants.EMAIL;
import static ai.rnt.crm.constants.StatusConstants.NOTIFICATION;
import static ai.rnt.crm.constants.StatusConstants.SEND;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.mail.internet.AddressException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadTaskDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.OpportunityTaskDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.OpportunityTask;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.LeadService;
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

	private final LeadTaskDaoService leadTaskDaoService;

	private final OpportunityTaskDaoService opportunityTaskDaoService;

	private final LeadDaoService leadDaoService;

	private final EmailDaoService emailDaoService;

	private final EmployeeDaoService employeeDaoService;

	private final TaskNotificationsUtil taskNotificationsUtil;

	private final LeadService leadService;

	private final EmailUtil emailUtil;

	 @Scheduled(cron = "0 * * * * ?") // for every minute.
	public void reminderForTask() throws Exception {
		log.info("inside the reminderForTask method...{}");
		try {
			LocalDate todayAsDate = LocalDate.now();
			LocalDateTime currentTime = now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE))
					.toLocalDateTime();
			String time = currentTime.format(ofPattern("HH:mm"));
			log.info("inside the time is in reminderForTask method...{}.. and currenttime ..{}", currentTime, time);
			List<PhoneCallTask> callTaskList = callDaoService.getTodaysCallTask(todayAsDate, time);
			callTaskList.forEach(e -> {
				String emailId = null;
				if (!e.getAssignTo().getStaffId().equals(e.getCreatedBy()))
					emailId = employeeDaoService.getEmailId(e.getCreatedBy());
				if (nonNull(e.getRemainderVia()) && BOTH.equalsIgnoreCase(e.getRemainderVia())) {
					emailUtil.sendCallTaskReminderMail(e, emailId);
					callNotification(e.getCallTaskId());
				} else if (nonNull(e.getRemainderVia()) && EMAIL.equalsIgnoreCase(e.getRemainderVia()))
					emailUtil.sendCallTaskReminderMail(e, emailId);

				else if (nonNull(e.getRemainderVia()) && NOTIFICATION.equalsIgnoreCase(e.getRemainderVia()))
					callNotification(e.getCallTaskId());
			});

			List<VisitTask> visitTaskList = visitDaoService.getTodaysAllVisitTask(todayAsDate, time);
			visitTaskList.forEach(e -> {
				String emailId = null;
				if (!e.getAssignTo().getStaffId().equals(e.getCreatedBy()))
					emailId = employeeDaoService.getEmailId(e.getCreatedBy());
				if (nonNull(e.getRemainderVia()) && BOTH.equalsIgnoreCase(e.getRemainderVia())) {
					emailUtil.sendVisitTaskReminderMail(e, emailId);
					visitNotification(e.getVisitTaskId());
				} else if (nonNull(e.getRemainderVia()) && EMAIL.equalsIgnoreCase(e.getRemainderVia()))
					emailUtil.sendVisitTaskReminderMail(e, emailId);

				else if (nonNull(e.getRemainderVia()) && NOTIFICATION.equalsIgnoreCase(e.getRemainderVia()))
					visitNotification(e.getVisitTaskId());
			});

			List<MeetingTask> meetingTasksList = meetingDaoService.getTodaysMeetingTask(todayAsDate, time);
			meetingTasksList.forEach(e -> {
				String emailId = null;
				if (!e.getAssignTo().getStaffId().equals(e.getCreatedBy()))
					emailId = employeeDaoService.getEmailId(e.getCreatedBy());
				if (nonNull(e.getRemainderVia()) && BOTH.equalsIgnoreCase(e.getRemainderVia())) {
					emailUtil.sendMeetingTaskReminderMail(e, emailId);
					meetingNotification(e.getMeetingTaskId());
				} else if (nonNull(e.getRemainderVia()) && EMAIL.equalsIgnoreCase(e.getRemainderVia()))
					emailUtil.sendMeetingTaskReminderMail(e, emailId);
				else if (nonNull(e.getRemainderVia()) && NOTIFICATION.equalsIgnoreCase(e.getRemainderVia()))
					meetingNotification(e.getMeetingTaskId());
			});

			List<LeadTask> leadTasksList = leadTaskDaoService.getTodaysLeadTask(todayAsDate, time);
			leadTasksList.forEach(e -> {
				String emailId = null;
				if (!e.getAssignTo().getStaffId().equals(e.getCreatedBy()))
					emailId = employeeDaoService.getEmailId(e.getCreatedBy());
				if (nonNull(e.getRemainderVia()) && BOTH.equalsIgnoreCase(e.getRemainderVia())) {
					emailUtil.sendLeadTaskReminderMail(e, emailId);
					leadNotification(e.getLeadTaskId());
				} else if (nonNull(e.getRemainderVia()) && EMAIL.equalsIgnoreCase(e.getRemainderVia()))
					emailUtil.sendLeadTaskReminderMail(e, emailId);
				else if (nonNull(e.getRemainderVia()) && NOTIFICATION.equalsIgnoreCase(e.getRemainderVia()))
					leadNotification(e.getLeadTaskId());
			});

			List<OpportunityTask> optyTasksList = opportunityTaskDaoService.getTodaysOptyTask(todayAsDate, time);
			optyTasksList.forEach(e -> {
				String emailId = null;
				if (!e.getAssignTo().getStaffId().equals(e.getCreatedBy()))
					emailId = employeeDaoService.getEmailId(e.getCreatedBy());
				if (nonNull(e.getRemainderVia()) && BOTH.equalsIgnoreCase(e.getRemainderVia())) {
					emailUtil.sendOptyTaskReminderMail(e, emailId);
					optyNotification(e.getOptyTaskId());
				} else if (nonNull(e.getRemainderVia()) && EMAIL.equalsIgnoreCase(e.getRemainderVia()))
					emailUtil.sendOptyTaskReminderMail(e, emailId);
				else if (nonNull(e.getRemainderVia()) && NOTIFICATION.equalsIgnoreCase(e.getRemainderVia()))
					optyNotification(e.getOptyTaskId());
			});

			List<Leads> followUpLeads = leadDaoService.getFollowUpLeads(todayAsDate, time);
			followUpLeads.forEach(e -> {
				String emailId = null;
				if (!e.getEmployee().getStaffId().equals(e.getCreatedBy()))
					emailId = employeeDaoService.getEmailId(e.getCreatedBy());
				if (nonNull(e.getRemainderVia()) && BOTH.equalsIgnoreCase(e.getRemainderVia())) {
					emailUtil.sendFollowUpLeadReminderMail(e, emailId);
					followUpLeadNotification(e.getLeadId());
					leadService.updateLeadsStatus(e.getLeadId());
				} else if (nonNull(e.getRemainderVia()) && EMAIL.equalsIgnoreCase(e.getRemainderVia())) {
					emailUtil.sendFollowUpLeadReminderMail(e, emailId);
					leadService.updateLeadsStatus(e.getLeadId());
				} else if (nonNull(e.getRemainderVia()) && NOTIFICATION.equalsIgnoreCase(e.getRemainderVia())) {
					followUpLeadNotification(e.getLeadId());
					leadService.updateLeadsStatus(e.getLeadId());
				}
			});

			List<Email> emails = emailDaoService.isScheduledEmails(todayAsDate, time);
			emails.forEach(email -> {
				try {
					if (emailUtil.sendEmail(email)) {
						email.setStatus(SEND);
						emailDaoService.email(email);
					}
				} catch (AddressException e1) {
					log.error("Got exception while sending the scheduled emails...{}", e1);
				}
			});
		} catch (Exception e) {
			log.error("Got Exception while sending mails to the task of call, visit and meeting..{}", e);
			throw new CRMException(e);
		}
	}

	public void callNotification(Integer callTaskId) {
		log.info("inside callNotification method...{}", callTaskId);
		try {
			TaskNotifications taskNotifications = new TaskNotifications();
			taskNotifications.setCallTask(callDaoService.getCallTaskById(callTaskId)
					.orElseThrow(() -> new ResourceNotFoundException("PhoneCallTask", "callTaskId", callTaskId)));
			taskNotifications.setCreatedBy(1375);
			taskNotifications.setNotifTo(taskNotifications.getCallTask().getAssignTo());
			taskNotifications.setNotifStatus(true);
			taskNotificationsUtil.sendTaskNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending callTask notification..{}", e);
			throw new CRMException(e);
		}
	}

	public void visitNotification(Integer visitTaskId) {
		log.info("inside visitNotification method...{}", visitTaskId);
		try {
			TaskNotifications taskNotifications = new TaskNotifications();
			taskNotifications.setVisitTask(visitDaoService.getVisitTaskById(visitTaskId)
					.orElseThrow(() -> new ResourceNotFoundException("VisitTask", "visitTaskId", visitTaskId)));
			taskNotifications.setCreatedBy(1375);
			taskNotifications.setNotifTo(taskNotifications.getVisitTask().getAssignTo());
			taskNotifications.setNotifStatus(true);
			taskNotificationsUtil.sendTaskNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending visitTask notification..{}", e);
			throw new CRMException(e);
		}
	}

	public void meetingNotification(Integer meetingTaskId) {
		log.info("inside meetingNotification method...{}", meetingTaskId);
		try {
			TaskNotifications taskNotifications = new TaskNotifications();
			taskNotifications.setMeetingTask(meetingDaoService.getMeetingTaskById(meetingTaskId)
					.orElseThrow(() -> new ResourceNotFoundException("MeetingTask", "meetingTaskId", meetingTaskId)));
			taskNotifications.setCreatedBy(1375);
			taskNotifications.setNotifTo(taskNotifications.getMeetingTask().getAssignTo());
			taskNotifications.setNotifStatus(true);
			taskNotificationsUtil.sendTaskNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending meetingTask notification..{}", e);
			throw new CRMException(e);
		}
	}

	public void leadNotification(Integer leadTaskId) {
		log.info("inside leadNotification method...{}", leadTaskId);
		try {
			TaskNotifications taskNotifications = new TaskNotifications();
			taskNotifications.setLeadTask(leadTaskDaoService.getTaskById(leadTaskId)
					.orElseThrow(() -> new ResourceNotFoundException("LeadTask", "leadTaskId", leadTaskId)));
			taskNotifications.setCreatedBy(1375);
			taskNotifications.setNotifTo(taskNotifications.getLeadTask().getAssignTo());
			taskNotifications.setNotifStatus(true);
			taskNotificationsUtil.sendTaskNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending leadTask notification..{}", e);
			throw new CRMException(e);
		}
	}

	public void optyNotification(Integer optyTaskId) {
		log.info("inside optyNotification method...{}", optyTaskId);
		try {
			TaskNotifications taskNotifications = new TaskNotifications();
			taskNotifications.setOptyTask(opportunityTaskDaoService.getOptyTaskById(optyTaskId)
					.orElseThrow(() -> new ResourceNotFoundException("OpportunityTask", "optyTaskId", optyTaskId)));
			taskNotifications.setCreatedBy(1375);
			taskNotifications.setNotifTo(taskNotifications.getOptyTask().getAssignTo());
			taskNotifications.setNotifStatus(true);
			taskNotificationsUtil.sendTaskNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending OpportunityTask notification..{}", e);
			throw new CRMException(e);
		}
	}

	public void followUpLeadNotification(Integer leadId) {
		log.info("inside followUpLeadNotification method...{}", leadId);
		try {
			TaskNotifications taskNotifications = new TaskNotifications();
			taskNotifications.setLeads(leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException("Leads", "leadId", leadId)));
			taskNotifications.setCreatedBy(1375);
			taskNotifications.setNotifTo(taskNotifications.getLeads().getEmployee());
			taskNotifications.setNotifStatus(true);
			taskNotificationsUtil.sendTaskNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending follow up lead notification..{}", e);
			throw new CRMException(e);
		}
	}
}
