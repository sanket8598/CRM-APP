package ai.rnt.crm.util;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import java.util.List;

import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.exception.CRMException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 08/12/2023.
 * @version 1.0
 *
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class TaskUtil {

	public static boolean checkDuplicateTask(List<PhoneCallTask> allTask, PhoneCallTask phoneCallTask) {
		log.info("inside the call checkDuplicateTask method...{}");
		boolean status = false;
		try {
			if (isNull(allTask) || allTask.isEmpty())
				status = false;
			for (PhoneCallTask task : allTask) {
				if (task.getSubject().equals(phoneCallTask.getSubject())
						&& task.getStatus().equals(phoneCallTask.getStatus())
						&& task.getPriority().equals(phoneCallTask.getPriority())
						&& compareDatesIgnoringTime(task.getDueDate(), phoneCallTask.getDueDate())
						&& task.getRemainderVia().equals(phoneCallTask.getRemainderVia())
						&& task.getRemainderDueAt().equals(phoneCallTask.getRemainderDueAt())
						&& compareDatesIgnoringTime(task.getRemainderDueOn(), phoneCallTask.getRemainderDueOn())
						&& task.getDescription().equals(phoneCallTask.getDescription())
						&& task.getDueTime().equals(phoneCallTask.getDueTime())
						&& task.isRemainderOn() == phoneCallTask.isRemainderOn()) {
					status = true;
					break;
				}
			}
			return status;
		} catch (Exception e) {
			log.info("Got Exception while checking the DuplicateCallTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static boolean checkDuplicateVisitTask(List<VisitTask> allTask, VisitTask visitTask) {
		log.info("inside the checkDuplicateVisitTask method...{}");
		boolean status = false;
		try {
			if (isNull(allTask) || allTask.isEmpty())
				status = false;
			for (VisitTask task : allTask) {
				if (task.getSubject().equals(visitTask.getSubject()) && task.getStatus().equals(visitTask.getStatus())
						&& task.getPriority().equals(visitTask.getPriority())
						&& compareDatesIgnoringTime(task.getDueDate(), visitTask.getDueDate())
						&& task.getRemainderVia().equals(visitTask.getRemainderVia())
						&& task.getRemainderDueAt().equals(visitTask.getRemainderDueAt())
						&& compareDatesIgnoringTime(task.getRemainderDueOn(), visitTask.getRemainderDueOn())
						&& task.getDescription().equals(visitTask.getDescription())
						&& task.getDueTime().equals(visitTask.getDueTime())
						&& task.isRemainderOn() == visitTask.isRemainderOn()) {
					status = true;
					break;
				}
			}
			return status;
		} catch (Exception e) {
			log.info("Got Exception while checking the DuplicateVisitTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static boolean checkDuplicateLeadTask(List<LeadTask> allTask, LeadTask leadTask) {
		log.info("inside the checkDuplicateLeadTask method...{}");
		boolean status = false;
		try {
			if (isNull(allTask) || allTask.isEmpty())
				status = false;
			for (LeadTask task : allTask) {
				if (task.getSubject().equals(leadTask.getSubject()) && task.getStatus().equals(leadTask.getStatus())
						&& task.getPriority().equals(leadTask.getPriority())
						&& compareDatesIgnoringTime(task.getDueDate(), leadTask.getDueDate())
						&& task.getRemainderVia().equals(leadTask.getRemainderVia())
						&& task.getRemainderDueAt().equals(leadTask.getRemainderDueAt())
						&& compareDatesIgnoringTime(task.getRemainderDueOn(), leadTask.getRemainderDueOn())
						&& task.getDescription().equals(leadTask.getDescription())
						&& task.getDueTime().equals(leadTask.getDueTime())
						&& task.isRemainderOn() == leadTask.isRemainderOn()) {
					status = true;
					break;
				}
			}
			return status;
		} catch (Exception e) {
			log.info("Got Exception while checking the DuplicateLeadTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static boolean checkDuplicateMeetingTask(List<MeetingTask> allMeetingTask, MeetingTask meetingTask) {
		log.info("inside the checkDuplicateMeetingTask method...{}");
		boolean status = false;
		try {
			if (isNull(allMeetingTask) || allMeetingTask.isEmpty())
				status = false;
			for (MeetingTask task : allMeetingTask) {
				if (task.getSubject().equals(meetingTask.getSubject())
						&& task.getStatus().equals(meetingTask.getStatus())
						&& task.getPriority().equals(meetingTask.getPriority())
						&& compareDatesIgnoringTime(task.getDueDate(), meetingTask.getDueDate())
						&& task.getRemainderVia().equals(meetingTask.getRemainderVia())
						&& task.getRemainderDueAt().equals(meetingTask.getRemainderDueAt())
						&& compareDatesIgnoringTime(task.getRemainderDueOn(), meetingTask.getRemainderDueOn())
						&& task.getDescription().equals(meetingTask.getDescription())
						&& task.getDueTime().equals(meetingTask.getDueTime())
						&& task.isRemainderOn() == meetingTask.isRemainderOn()) {
					status = true;
					break;
				}
			}
			return status;
		} catch (Exception e) {
			log.info("Got Exception while checking the DuplicateMeetingTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	private static boolean compareDatesIgnoringTime(LocalDate oldDate, LocalDate newDate) {
		log.info("inside the compareDatesIgnoringTime method...{}");
		return oldDate.equals(newDate);
	}
}