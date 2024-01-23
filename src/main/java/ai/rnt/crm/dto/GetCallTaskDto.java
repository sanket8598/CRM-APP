package ai.rnt.crm.dto;

import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GetCallTaskDto {

	private Integer callTaskId;

	private String subject;

	private String description;

	private String status;

	private String priority;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dueDate;
	
	private String dueTime;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date updateDueDate;
	
	private String dueTime12Hours;
	
	private boolean remainderOn;

	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date remainderDueOn;
	
	public String getCallTaskDueDate() {
		return convertDateDateWithTime(getDueDate(),getDueTime12Hours());
	}
}
