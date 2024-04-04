package ai.rnt.crm.dto.opportunity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposeOpportunityDto {

	private Integer opportunityId;

	private String progressStatus;

	private String currentPhase;

	private Boolean identifySme = false;

	private Boolean developProposal = false;

	private Boolean complInternalReview = false;

	private Boolean presentProposal = false;

	private Boolean finalCommAndTimeline = false;

	private String proposeRemarks;
}
