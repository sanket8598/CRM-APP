package ai.rnt.crm.dto;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QualifyLeadDto {

	private Integer leadId;

	@NotBlank(message = "Budget amount should not be null or empty!!")
	private String budgetAmount;

	private String progressStatus;

	private String currentPhase;

	private Boolean requirementShared = false;

	private Boolean identifyDecisionMaker = false;

	private Boolean firstMeetingDone = false;

	private String customerReadiness;

	private String qualifyRemarks;

	private Boolean qualify;

	@NotNull(message = "Closed Date should not be null!!")
	@FutureOrPresent(message = "Closed date must not be smaller than today's date!!")
	private LocalDate closedOn;
	
	@JsonFormat(pattern = "dd-MMM-yyyy")
	private LocalDate closedDate;
	
	private CurrencyDto currency;
}
