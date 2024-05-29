package ai.rnt.crm.dto;

import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Temporal;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

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
public class GetEntityTaskDto implements Serializable {

	private static final long serialVersionUID = -533746260646381108L;

	private String subject;

	@ValidTaskStatus(message = "Please Select Valid Task Status!!")
	private String status;

	@ValidTaskPriority(message = "Please Select Valid Task Priority!!")
	private String priority;

	@Temporal(DATE)
	@NotNull(message = "Due date should not be null!!")
	@FutureOrPresent(message = "Due Date must not be smaller than today's date!!")
	private LocalDate updateDueDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dueDate;

	private String dueTime;

	private String dueTime12Hours;

	private String description;

	private boolean remainderOn;

	@ValidReminderVia(message = "Please Select Valid RemainderVia!!")
	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate remainderDueOn;

	@Temporal(DATE)
	@FutureOrPresent(message = "Remainder due on must not be smaller than today's date!!")
	private LocalDate updatedRemainderDueOn;
}
