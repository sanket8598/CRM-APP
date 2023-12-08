package ai.rnt.crm.functional.predicates;

import static ai.rnt.crm.constants.StatusConstants.TASK_COMPLETED;
import static ai.rnt.crm.constants.StatusConstants.TASK_IN_PROGRESS;
import static ai.rnt.crm.constants.StatusConstants.TASK_NOT_STARTED;
import static ai.rnt.crm.constants.StatusConstants.TASK_ON_HOLD;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

import java.util.function.Predicate;

import ai.rnt.crm.dto.MainTaskDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class TaskPredicates {

	/*
	 * * This Predicate return true if it the task status is not started.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<MainTaskDto> NOT_STARTED_TASK = task -> nonNull(task.getStatus())
			&& task.getStatus().equalsIgnoreCase(TASK_NOT_STARTED);
	/*
	 * * This Predicate return true if it the task status is in progress.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<MainTaskDto> IN_PROGRESS_TASK = task -> nonNull(task.getStatus())
			&& task.getStatus().equalsIgnoreCase(TASK_IN_PROGRESS);
	/*
	 * * This Predicate return true if it the task status is on hold.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<MainTaskDto> ON_HOLD_TASK = task -> nonNull(task.getStatus())
			&& task.getStatus().equalsIgnoreCase(TASK_ON_HOLD);
	/*
	 * * This Predicate return true if it the task status is completed.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<MainTaskDto> COMPLETED_TASK = task -> nonNull(task.getStatus())
			&& task.getStatus().equalsIgnoreCase(TASK_COMPLETED);

}
