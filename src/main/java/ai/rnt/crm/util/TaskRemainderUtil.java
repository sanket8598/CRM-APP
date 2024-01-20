package ai.rnt.crm.util;

import static ai.rnt.crm.constants.StatusConstants.SEND;
import static ai.rnt.crm.dto_mapper.EmailDtoMapper.TO_EMAIL;
import static ai.rnt.crm.dto_mapper.EmailDtoMapper.TO_EMAIL_DTO;
import static ai.rnt.crm.util.EmailUtil.sendCallTaskReminderMail;
import static ai.rnt.crm.util.EmailUtil.sendEmail;
import static ai.rnt.crm.util.EmailUtil.sendFollowUpLeadReminderMail;
import static ai.rnt.crm.util.EmailUtil.sendLeadTaskReminderMail;
import static ai.rnt.crm.util.EmailUtil.sendMeetingTaskReminderMail;
import static ai.rnt.crm.util.EmailUtil.sendVisitTaskReminderMail;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Date.from;
import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadTaskDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
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

	private static final String EMAIL = "Email";

	private static final String NOTIFICATIONS = "Notifications";

	private static final String BOTH = "Both";

	private final CallDaoService callDaoService;

	private final VisitDaoService visitDaoService;

	private final MeetingDaoService meetingDaoService;

	private final LeadTaskDaoService leadTaskDaoService;

	private final LeadDaoService leadDaoService;

	private final EmailDaoService emailDaoService;

	private final EmployeeDaoService employeeDaoService;

	private final TaskNotificationsUtil taskNotificationsUtil;

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Scheduled(cron = "0 * * * * ?") // for every minute.
	public void reminderForTask() throws Exception {
		log.info("inside the reminderForTask method...{}");
		try {
			LocalDateTime todayDate = LocalDate.now().atStartOfDay();
			Date todayAsDate = from(todayDate.atZone(systemDefault()).toInstant());
			LocalDateTime currentTime = null;
			if ("uat".equalsIgnoreCase(activeProfile)) {
				log.info("inside the if block reminderForTask method...{}", activeProfile);
				currentTime = now().plusHours(5).plusMinutes(30);
			} else
				currentTime = now();
			String time = currentTime.format(ofPattern("HH:mm"));
			log.info("inside the time is in reminderForTask method...{}", currentTime);
			log.info("inside the currenttime is in reminderForTask method...{}", time);
			List<PhoneCallTask> callTaskList = callDaoService.getTodaysCallTask(todayAsDate, time);
			callTaskList.forEach(e -> {
				String emailId = null;
				if (!e.getAssignTo().getStaffId().equals(e.getCreatedBy()))
					emailId = employeeDaoService.getEmailId(e.getCreatedBy());
				if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(BOTH)) {
					sendCallTaskReminderMail(e, emailId);
					callNotification(e.getCallTaskId());
				} else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(EMAIL))
					sendCallTaskReminderMail(e, emailId);

				else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(NOTIFICATIONS))
					callNotification(e.getCallTaskId());
			});

			List<VisitTask> visitTaskList = visitDaoService.getTodaysAllVisitTask(todayAsDate, time);
			visitTaskList.forEach(e -> {
				String emailId = null;
				if (!e.getAssignTo().getStaffId().equals(e.getCreatedBy()))
					emailId = employeeDaoService.getEmailId(e.getCreatedBy());
				if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(BOTH)) {
					sendVisitTaskReminderMail(e, emailId);
					visitNotification(e.getVisitTaskId());
				} else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(EMAIL))
					sendVisitTaskReminderMail(e, emailId);

				else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(NOTIFICATIONS))
					visitNotification(e.getVisitTaskId());
			});

			List<MeetingTask> meetingTasksList = meetingDaoService.getTodaysMeetingTask(todayAsDate, time);
			meetingTasksList.forEach(e -> {
				String emailId = null;
				if (!e.getAssignTo().getStaffId().equals(e.getCreatedBy()))
					emailId = employeeDaoService.getEmailId(e.getCreatedBy());
				if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(BOTH)) {
					sendMeetingTaskReminderMail(e, emailId);
					meetingNotification(e.getMeetingTaskId());
				} else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(EMAIL))
					sendMeetingTaskReminderMail(e, emailId);
				else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(NOTIFICATIONS))
					meetingNotification(e.getMeetingTaskId());
			});

			List<LeadTask> leadTasksList = leadTaskDaoService.getTodaysLeadTask(todayAsDate, time);
			leadTasksList.forEach(e -> {
				String emailId = null;
				if (!e.getAssignTo().getStaffId().equals(e.getCreatedBy()))
					emailId = employeeDaoService.getEmailId(e.getCreatedBy());
				if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(BOTH)) {
					sendLeadTaskReminderMail(e, emailId);
					leadNotification(e.getLeadTaskId());
				} else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(EMAIL))
					sendLeadTaskReminderMail(e, emailId);
				else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(NOTIFICATIONS))
					leadNotification(e.getLeadTaskId());
			});

			List<Leads> followUpLeads = leadDaoService.getFollowUpLeads(todayAsDate, time);
			followUpLeads.forEach(e -> {
				String emailId = null;
				if (!e.getEmployee().getStaffId().equals(e.getCreatedBy()))
					emailId = employeeDaoService.getEmailId(e.getCreatedBy());
				if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(BOTH)) {
					sendFollowUpLeadReminderMail(e, emailId);
					followUpLeadNotification(e.getLeadId());
				} else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(EMAIL))
					sendFollowUpLeadReminderMail(e, emailId);
				else if (nonNull(e.getRemainderVia()) && e.getRemainderVia().equalsIgnoreCase(NOTIFICATIONS))
					followUpLeadNotification(e.getLeadId());
			});

			List<Email> emails = emailDaoService.isScheduledEmails(todayAsDate, time);
			emails.forEach(email -> {
				Optional<EmailDto> newEmail = TO_EMAIL_DTO.apply(email);
				newEmail.ifPresent(e -> {
					e.setBcc(nonNull(email.getBccMail()) && !email.getBccMail().isEmpty()
							? Stream.of(email.getBccMail().split(",")).map(String::trim).collect(Collectors.toList())
							: Collections.emptyList());
					e.setCc(nonNull(email.getCcMail()) && !email.getCcMail().isEmpty()
							? Stream.of(email.getCcMail().split(",")).map(String::trim).collect(Collectors.toList())
							: Collections.emptyList());
					e.setMailTo(nonNull(email.getToMail()) && !email.getToMail().isEmpty()
							? Stream.of(email.getToMail().split(",")).map(String::trim).collect(Collectors.toList())
							: Collections.emptyList());
					try {
						if (sendEmail(e))
							TO_EMAIL.apply(e).ifPresent(em -> {
								em.setStatus(SEND);
								emailDaoService.email(em);
							});
					} catch (AddressException e1) {
						log.error("Got exception while sending the scheduled emails...{}", e1);
					}
				});
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
			taskNotificationsUtil.sendCallTaskNotification(taskNotifications);
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
			taskNotificationsUtil.sendVisitTaskNotification(taskNotifications);
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
			taskNotificationsUtil.sendMeetingTaskNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending meetingTask notification..{}", e);
			throw new CRMException(e);
		}
	}

	private void leadNotification(Integer leadTaskId) {
		log.info("inside leadNotification method...{}", leadTaskId);
		try {
			TaskNotifications taskNotifications = new TaskNotifications();
			taskNotifications.setLeadTask(leadTaskDaoService.getTaskById(leadTaskId)
					.orElseThrow(() -> new ResourceNotFoundException("LeadTask", "leadTaskId", leadTaskId)));
			taskNotifications.setCreatedBy(1375);
			taskNotifications.setNotifTo(taskNotifications.getLeadTask().getAssignTo());
			taskNotifications.setNotifStatus(true);
			taskNotificationsUtil.sendLeadTaskNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending leadTask notification..{}", e);
			throw new CRMException(e);
		}
	}

	private void followUpLeadNotification(Integer leadId) {
		log.info("inside followUpLeadNotification method...{}", leadId);
		try {
			TaskNotifications taskNotifications = new TaskNotifications();
			taskNotifications.setLeads(leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException("Leads", "leadId", leadId)));
			taskNotifications.setCreatedBy(1375);
			taskNotifications.setNotifTo(taskNotifications.getLeads().getEmployee());
			taskNotifications.setNotifStatus(true);
			taskNotificationsUtil.sendFollowUpLeadNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending follow up lead notification..{}", e);
			throw new CRMException(e);
		}
	}
}
