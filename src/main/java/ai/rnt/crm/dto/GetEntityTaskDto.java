package ai.rnt.crm.dto;

import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Temporal;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetEntityTaskDto implements Serializable {

	private static final long serialVersionUID = -533746260646381108L;

	private String subject;

	private String status;

	private String priority;

	@Temporal(DATE)
	private LocalDate updateDueDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dueDate;

	private String dueTime;

	private String dueTime12Hours;

	private String description;

	private boolean remainderOn;

	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate remainderDueOn;

	@Temporal(DATE)
	private LocalDate updatedRemainderDueOn;
}
