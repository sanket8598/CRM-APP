package ai.rnt.crm.dto;

import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Temporal;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ai.rnt.crm.validation.ValidTaskPriority;
import ai.rnt.crm.validation.ValidTaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto implements Serializable {

	private static final long serialVersionUID = -6108424214030836688L;

	@NotBlank(message = "Subject should not be null or empty!!")
	private String subject;

	@ValidTaskStatus(message = "Please Select Valid Task Status!!")
	private String status;

	@ValidTaskPriority(message = "Please Select Valid Task Priority!!")
	private String priority;

	// @JsonFormat(pattern = "yyyy-MM-dd") we have configered it as global in config
	// for our application so no need.
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
