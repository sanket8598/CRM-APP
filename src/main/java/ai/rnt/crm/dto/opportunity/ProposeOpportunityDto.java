package ai.rnt.crm.dto.opportunity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposeOpportunityDto {

	private Integer opportunityId;

	private String progressStatus;

	private String currentPhase;

	private Boolean identifySme;

	private Boolean developProposal;

	private Boolean complInternalReview;

	private Boolean presentProposal;

	private Boolean finalCommAndTimeline;
	
	private String proposeRemarks;
}
