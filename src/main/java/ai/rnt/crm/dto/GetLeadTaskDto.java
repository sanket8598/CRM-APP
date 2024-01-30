package ai.rnt.crm.dto;

import static javax.persistence.TemporalType.DATE;

import java.time.LocalDate;

import javax.persistence.Temporal;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GetLeadTaskDto {

	private Integer leadTaskId;

	private String subject;

	private String status;

	private String priority;

	@Temporal(DATE)
	private LocalDate updateDueDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dueDate;

	private String dueTime;

	private String description;

	private boolean remainderOn;

	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate remainderDueOn;

	@Temporal(DATE)
	private LocalDate updatedRemainderDueOn;
}
