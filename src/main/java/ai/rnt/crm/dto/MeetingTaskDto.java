package ai.rnt.crm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	private String status;

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
