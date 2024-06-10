package ai.rnt.crm.dto;

import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Temporal;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import ai.rnt.crm.validation.ValidDueAndRemainderDateTime;
import ai.rnt.crm.validation.ValidReminderVia;
import ai.rnt.crm.validation.ValidTaskPriority;
import ai.rnt.crm.validation.ValidTaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidDueAndRemainderDateTime(timefieldOne = "dueTime", timefieldSec = "remainderDueAt", dateFieldOne = "updateDueDate", dateFieldSec = "updatedRemainderDueOn", remainderField = "remainderOn", message = "Date/time is not valid!!")
public class GetTaskDto implements Serializable {

	private static final long serialVersionUID = 3121574599672156106L;

	private String subject;

	private String description;

	@NotBlank(message = "Status should not be null or empty!!")
	@ValidTaskStatus(message = "Invalid !!")
	private String status;

	@NotBlank(message = "Priority should not be null or empty!!")
	@ValidTaskPriority(message = "Invalid !!")
	private String priority;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dueDate;

	private String dueTime;

	@Temporal(DATE)
	@FutureOrPresent(message = "Due date must not be smaller than today's date!!")
	private LocalDate updateDueDate;

	private String dueTime12Hours;

	private boolean remainderOn;

	@ValidReminderVia(message = "Invalid !!")
	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate remainderDueOn;

	@Temporal(DATE)
	@FutureOrPresent(message = "Remainder due on must not be smaller than today's date!!")
	private LocalDate updatedRemainderDueOn;
}
