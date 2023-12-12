package ai.rnt.crm.util;

import static ai.rnt.crm.util.EmailUtil.sendCallTaskRemainderMail;
import static ai.rnt.crm.util.EmailUtil.sendMeetingTaskRemainderMail;
import static ai.rnt.crm.util.EmailUtil.sendVisitTaskRemainderMail;

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
import ai.rnt.crm.entity.VisitTask;
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

	@Scheduled(cron = "0 * * * * ?") // for every minute.
	public void remainderForCallTask() throws Exception {
		try {
			LocalDateTime todayDate = LocalDate.now().atStartOfDay();
			Date todayAsDate = Date.from(todayDate.atZone(ZoneId.systemDefault()).toInstant());
			String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));

			List<PhoneCallTask> callTaskList = callDaoService.getTodaysCallTask(todayAsDate, time);
			callTaskList.forEach(
					e -> sendCallTaskRemainderMail(employeeDaoService.getEmailId(e.getAssignTo().getStaffId())));
			List<VisitTask> visitTaskList = visitDaoService.getTodaysAllVisitTask(todayAsDate, time);
			visitTaskList.forEach(
					e -> sendVisitTaskRemainderMail(employeeDaoService.getEmailId(e.getAssignTo().getStaffId())));
			List<MeetingTask> meetingTasksList = meetingDaoService.getTodaysMeetingTask(todayAsDate, time);
			meetingTasksList.forEach(
					e -> sendMeetingTaskRemainderMail(employeeDaoService.getEmailId(e.getAssignTo().getStaffId())));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Got Exception while sending mails to the task of call, visit and meeting..{}", e);
		}
	}
}
