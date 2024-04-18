package ai.rnt.crm.dto;

import javax.validation.constraints.NotBlank;

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
}
