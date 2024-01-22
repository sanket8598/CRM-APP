package ai.rnt.crm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import ai.rnt.crm.validation.ValidTaskPriority;
import ai.rnt.crm.validation.ValidTaskStatus;
import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 27/11/2023.
 *
 */
@Data
public class MeetingTaskDto {

	private Integer meetingTaskId;

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

//	@JsonFormat(pattern = "hh:mm aa")
	private String remainderDueAt;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date remainderDueOn;
}
