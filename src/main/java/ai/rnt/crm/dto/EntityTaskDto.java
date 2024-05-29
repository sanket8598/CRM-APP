package ai.rnt.crm.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.validation.ValidDueAndRemainderDateTime;
import ai.rnt.crm.validation.ValidReminderVia;
import ai.rnt.crm.validation.ValidTaskPriority;
import ai.rnt.crm.validation.ValidTaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidDueAndRemainderDateTime(timefieldOne = "dueTime", timefieldSec = "remainderDueAt", dateFieldOne = "dueDate", dateFieldSec = "remainderDueOn", remainderField = "remainderOn", message = "Date/time is not valid!!")
public class EntityTaskDto implements Serializable {

	private static final long serialVersionUID = -9190241517074358877L;

	private String subject;

	@ValidTaskStatus(message = "Please Select Valid Task Status!!")
	private String status;

	@ValidTaskPriority(message = "Please Select Valid Task Priority!!")
	private String priority;

	@NotNull(message = "Due date should not be null!!")
	private LocalDate dueDate;

	private String dueTime;

	private String description;

	private boolean remainderOn;

	@ValidReminderVia(message = "Please Select Valid RemainderVia!!")
	private String remainderVia;

	private String remainderDueAt;

	private LocalDate remainderDueOn;

	private EmployeeMaster assignTo;
}
