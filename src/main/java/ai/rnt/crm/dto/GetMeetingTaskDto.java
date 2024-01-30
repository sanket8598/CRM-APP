package ai.rnt.crm.dto;

import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;
import static javax.persistence.TemporalType.DATE;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Temporal;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GetMeetingTaskDto {

	private Integer meetingTaskId;

	private String subject;

	private String description;

	private String status;

	private String priority;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dueDate;

	private String dueTime;

	@Temporal(DATE)
	private LocalDate updateDueDate;

	private String dueTime12Hours;

	private boolean remainderOn;

	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date remainderDueOn;

	@Temporal(DATE)
	private LocalDate updatedRemainderDueOn;

	public String getMeetingTaskDueDate() {
		return convertDateDateWithTime(getDueDate(), getDueTime12Hours());
	}
}
