package ai.rnt.crm.dto.opportunity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 09/02/2024
 * @version 1.2
 *
 */
@Getter
@Setter
public class QualifyOpportunityDto {

	private Integer opportunityId;

	private String budgetAmount;

	private String progressStatus;

	private String currentPhase;

	private Boolean requirementShared;

	private Boolean identifyDecisionMaker;

	private Boolean firstMeetingDone;

	private String customerReadiness;
}
