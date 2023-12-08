package ai.rnt.crm.util;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import ai.rnt.crm.entity.PhoneCallTask;
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
			log.info("Got Exception while checking the DuplicateTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	private static boolean compareDatesIgnoringTime(Date oldDate, Date newDate) {
		return oldDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
				.equals(newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	}
}
