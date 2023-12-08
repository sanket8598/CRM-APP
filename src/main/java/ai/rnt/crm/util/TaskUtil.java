package ai.rnt.crm.util;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

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
						&& compareDates(task.getDueDate(), phoneCallTask.getDueDate())
						&& task.getRemainderVia().equals(phoneCallTask.getRemainderVia())
						&& task.getRemainderDueAt().equals(phoneCallTask.getRemainderDueAt())
						&& task.getRemainderDueOn().equals(phoneCallTask.getRemainderDueOn())
						&& task.getDescription().equals(phoneCallTask.getDescription())
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

	private static boolean compareDates(Date date1, Date date2) {
		Date oldDate = clearTimeComponents(date1);
		Date newDate = clearTimeComponents(date2);
		return oldDate.equals(newDate);
	}

	private static Date clearTimeComponents(Date date) {
		// Set the time components to midnight (00:00:00)
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		date.setTime((date.getTime() / 86400000) * 86400000); // Clear milliseconds
		return date;
	}
}
