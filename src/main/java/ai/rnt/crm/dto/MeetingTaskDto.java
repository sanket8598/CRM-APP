package ai.rnt.crm.dto;

import static javax.persistence.TemporalType.DATE;

import java.time.LocalDate;

import javax.persistence.Temporal;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ai.rnt.crm.validation.ValidTaskPriority;
import ai.rnt.crm.validation.ValidTaskStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 27/11/2023.
 *
 */
@Getter
@Setter
public class MeetingTaskDto {

	private Integer meetingTaskId;

	@NotBlank(message = "Subject should not be null or empty!!")
	private String subject;

	@ValidTaskStatus(message = "Please Enter Valid Task Status!!")
	private String status;

	@ValidTaskPriority(message = "Please Enter Valid Task Priority!!")
	private String priority;

	@Temporal(DATE)
	@NotNull(message = "Due Date should not be null!!")
	@FutureOrPresent(message = "Date must not be smaller than today's date!!")
	private LocalDate dueDate;

	@NotBlank(message = "Due time should not be null or empty!!")
	private String dueTime;

	private String description;

	private boolean remainderOn;

	private String remainderVia;

	private String remainderDueAt;

	@Temporal(DATE)
	@FutureOrPresent(message = "Date must not be smaller than today's date!!")
	private LocalDate remainderDueOn;
}
