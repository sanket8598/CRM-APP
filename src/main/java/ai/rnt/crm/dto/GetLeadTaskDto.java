package ai.rnt.crm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GetLeadTaskDto {

	private Integer leadTaskId;

	private String subject;

	private String status;

	private String priority;

	@JsonFormat(pattern = "dd-MMM-yyyy")
	private Date dueDate;

	private String description;

	private boolean remainderOn;

	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "dd-MMM-yyyy")
	private Date remainderDueOn;
}
