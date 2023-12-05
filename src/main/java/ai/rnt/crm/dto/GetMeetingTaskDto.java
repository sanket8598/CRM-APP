package ai.rnt.crm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GetMeetingTaskDto {

	private Integer meetingTaskId;

	private String subject;

	private String description;

	private String status;

	private String priority;

	@JsonFormat(pattern = "dd-MMM-yyyy")
	private Date dueDate;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date updateDueDate;

	private boolean remainderOn;

	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date remainderDueOn;
}