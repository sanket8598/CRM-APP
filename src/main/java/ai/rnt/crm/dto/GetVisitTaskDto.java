package ai.rnt.crm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GetVisitTaskDto {

	private Integer visitTaskId;

	private String subject;

	private String description;

	private String status;

	private String priority;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dueDate;

	private String dueTime;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date updateDueDate;

	private boolean remainderOn;

	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date remainderDueOn;
}
