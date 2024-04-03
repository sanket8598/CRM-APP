package ai.rnt.crm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QualifyLeadDto {

	private Integer leadId;

	private String budgetAmount;

	private String progressStatus;

	private String currentPhase;

	private Boolean requirementShared;

	private Boolean identifyDecisionMaker;

	private Boolean firstMeetingDone;

	private String customerReadiness;

	private String qualifyRemarks;
	
	private Boolean qualify;
}
