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

	private Boolean requirementShared = false;

	private Boolean identifyDecisionMaker = false;

	private Boolean firstMeetingDone = false;

	private String customerReadiness;

	private String qualifyRemarks;

	private Boolean qualify;
}
