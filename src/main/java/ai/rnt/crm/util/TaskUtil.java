package ai.rnt.crm.util;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.OpportunityTask;
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
@Component
@NoArgsConstructor(access = PRIVATE)
public class TaskUtil {

	public boolean checkDuplicateTask(List<PhoneCallTask> allTask, PhoneCallTask phoneCallTask) {
		log.info("inside the call checkDuplicateTask method...{}");
		try {
			return nonNull(phoneCallTask) && allTask.stream().filter(Objects::nonNull)
					.anyMatch((task -> task.getSubject().equals(phoneCallTask.getSubject())
							&& task.getStatus().equals(phoneCallTask.getStatus())
							&& task.getPriority().equals(phoneCallTask.getPriority())
							&& compareDatesIgnoringTime(task.getDueDate(), phoneCallTask.getDueDate())
							&& Objects.equals(task.getRemainderVia(), phoneCallTask.getRemainderVia())
							&& Objects.equals(task.getRemainderDueAt(), phoneCallTask.getRemainderDueAt())
							&& compareDatesIgnoringTime(task.getRemainderDueOn(), phoneCallTask.getRemainderDueOn())
							&& task.getDescription().equals(phoneCallTask.getDescription())
							&& task.getDueTime().equals(phoneCallTask.getDueTime())
							&& task.isRemainderOn() == phoneCallTask.isRemainderOn()));
		} catch (Exception e) {
			log.info("Got Exception while checking the DuplicateCallTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static boolean checkDuplicateVisitTask(List<VisitTask> allTask, VisitTask visitTask) {
		log.info("inside the checkDuplicateVisitTask method...{}");
		try {
			return nonNull(visitTask) && allTask.stream().filter(Objects::nonNull)
					.anyMatch(task -> task.getSubject().trim().equals(visitTask.getSubject().trim())
							&& task.getStatus().equals(visitTask.getStatus())
							&& task.getPriority().equals(visitTask.getPriority())
							&& compareDatesIgnoringTime(task.getDueDate(), visitTask.getDueDate())
							&& Objects.equals(task.getRemainderVia(), visitTask.getRemainderVia())
							&& Objects.equals(task.getRemainderDueAt(), visitTask.getRemainderDueAt())
							&& compareDatesIgnoringTime(task.getRemainderDueOn(), visitTask.getRemainderDueOn())
							&& task.getDescription().trim().equals(visitTask.getDescription().trim())
							&& task.getDueTime().equals(visitTask.getDueTime())
							&& task.isRemainderOn() == visitTask.isRemainderOn());
		} catch (Exception e) {
			log.info("Got Exception while checking the DuplicateVisitTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static boolean checkDuplicateLeadTask(List<LeadTask> allTask, LeadTask leadTask) {
		log.info("inside the checkDuplicateLeadTask method...{}");
		try {
			return nonNull(leadTask) && allTask.stream().filter(Objects::nonNull)
					.anyMatch(task -> task.getSubject().trim().equals(leadTask.getSubject().trim())
							&& task.getStatus().equals(leadTask.getStatus())
							&& task.getPriority().equals(leadTask.getPriority())
							&& compareDatesIgnoringTime(task.getDueDate(), leadTask.getDueDate())
							&& Objects.equals(task.getRemainderVia(), leadTask.getRemainderVia())
							&& Objects.equals(task.getRemainderDueAt(), leadTask.getRemainderDueAt())
							&& compareDatesIgnoringTime(task.getRemainderDueOn(), leadTask.getRemainderDueOn())
							&& task.getDescription().trim().equals(leadTask.getDescription().trim())
							&& task.getDueTime().equals(leadTask.getDueTime())
							&& task.isRemainderOn() == leadTask.isRemainderOn());
		} catch (Exception e) {
			log.info("Got Exception while checking the DuplicateLeadTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static boolean checkDuplicateMeetingTask(List<MeetingTask> allMeetingTask, MeetingTask meetingTask) {
		log.info("inside the checkDuplicateMeetingTask method...{}");
		try {
			return nonNull(meetingTask) && allMeetingTask.stream().filter(Objects::nonNull)
					.anyMatch(task -> task.getSubject().equals(meetingTask.getSubject())
							&& task.getStatus().equals(meetingTask.getStatus())
							&& task.getPriority().equals(meetingTask.getPriority())
							&& compareDatesIgnoringTime(task.getDueDate(), meetingTask.getDueDate())
							&& Objects.equals(task.getRemainderVia(), meetingTask.getRemainderVia())
							&& Objects.equals(task.getRemainderDueAt(), meetingTask.getRemainderDueAt())
							&& compareDatesIgnoringTime(task.getRemainderDueOn(), meetingTask.getRemainderDueOn())
							&& task.getDescription().equals(meetingTask.getDescription())
							&& task.getDueTime().equals(meetingTask.getDueTime())
							&& task.isRemainderOn() == meetingTask.isRemainderOn());
		} catch (Exception e) {
			log.info("Got Exception while checking the DuplicateMeetingTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static boolean checkDuplicateOptyTask(List<OpportunityTask> allOptyTask, OpportunityTask opportunityTask) {
		log.info("inside the checkDuplicateOptyTask method...{}");
		try {
			return nonNull(opportunityTask) && allOptyTask.stream().filter(Objects::nonNull)
					.anyMatch(task -> task.getSubject().equals(opportunityTask.getSubject())
							&& task.getStatus().equals(opportunityTask.getStatus())
							&& task.getPriority().equals(opportunityTask.getPriority())
							&& compareDatesIgnoringTime(task.getDueDate(), opportunityTask.getDueDate())
							&& Objects.equals(task.getRemainderVia(), opportunityTask.getRemainderVia())
							&& Objects.equals(task.getRemainderDueAt(), opportunityTask.getRemainderDueAt())
							&& compareDatesIgnoringTime(task.getRemainderDueOn(), opportunityTask.getRemainderDueOn())
							&& task.getDescription().equals(opportunityTask.getDescription())
							&& task.getDueTime().equals(opportunityTask.getDueTime())
							&& task.isRemainderOn() == opportunityTask.isRemainderOn());
		} catch (Exception e) {
			log.info("Got Exception while checking the DuplicateOpportunityTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static boolean compareDatesIgnoringTime(LocalDate oldDate, LocalDate newDate) {
		log.info("inside the compareDatesIgnoringTime method...{}");
		if (nonNull(oldDate) && nonNull(newDate))
			return oldDate.compareTo(newDate) == 0;
		return isNull(oldDate) && isNull(newDate);
	}
}