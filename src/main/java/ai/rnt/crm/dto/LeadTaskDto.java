package ai.rnt.crm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import ai.rnt.crm.entity.EmployeeMaster;
import lombok.Data;

@Data
public class LeadTaskDto {

	private Integer leadTaskId;

	private String subject;

	private String status;

	private String priority;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dueDate;
	
	private String dueTime;

	private String description;

	private boolean remainderOn;

	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date remainderDueOn;

	private EmployeeMaster assignTo;
}
