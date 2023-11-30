package ai.rnt.crm.dto;

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

	@JsonFormat(pattern = "dd-MMM-yyyy")
	private Date dueDate;

}
