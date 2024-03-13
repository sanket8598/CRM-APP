package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.validation.ValidTaskPriority;
import ai.rnt.crm.validation.ValidTaskStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 19-02-2024
 * @version 1.2
 *
 */
@Getter
@Setter
public class OpportunityTaskDto {

	private Integer optyTaskId;

	private String subject;

	@ValidTaskStatus(message = "Please Enter Valid Task Status!!")
	private String status;

	@ValidTaskPriority(message = "Please Enter Valid Task Priority!!")
	private String priority;

	@NotNull(message = "Due date should not be null!!")
	@FutureOrPresent(message = "Date must not be smaller than today's date!!")
	private LocalDate dueDate;

	private String dueTime;

	private String description;

	private boolean remainderOn;

	private String remainderVia;

	private String remainderDueAt;

	@FutureOrPresent(message = "Date must not be smaller than today's date!!")
	private LocalDate remainderDueOn;

	private EmployeeMaster assignTo;
}
