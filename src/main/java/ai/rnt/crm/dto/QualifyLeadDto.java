package ai.rnt.crm.dto;

import static javax.persistence.TemporalType.DATE;

import java.time.LocalDate;

import javax.persistence.Temporal;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QualifyLeadDto {

	private Integer leadId;

	private String customerNeed;

	private String proposedSolution;

	private ServiceFallsDto serviceFallsMaster;

	private Boolean isFollowUpRemainder = false;

	private String remainderVia;

	private String remainderDueAt;

	@Temporal(DATE)
	@NotNull(message = "Due date should not be null!!")
	@FutureOrPresent(message = "Date must not be smaller than today's date!!")
	private LocalDate remainderDueOn;
}
