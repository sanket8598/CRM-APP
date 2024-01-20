package ai.rnt.crm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.validation.ValidTaskPriority;
import ai.rnt.crm.validation.ValidTaskStatus;
import lombok.Data;

@Data
public class LeadTaskDto {

	private Integer leadTaskId;

	private String subject;

	@ValidTaskStatus(message = "Please Enter Valid Task Status!!")
	private String status;

	@ValidTaskPriority(message = "Please Enter Valid Task Priority!!")
	private String priority;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dueDate;
	
	private String dueTime;

	private String description;

	private boolean remainderOn;

	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date remainderDueOn;

	private EmployeeMaster assignTo;
}
