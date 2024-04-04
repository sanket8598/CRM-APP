package ai.rnt.crm.dto.opportunity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisOpportunityDto {

	private Integer opportunityId;

	private String currentPhase;

	private String progressStatus;

	private String customerNeed;

	private String proposedSolution;

	private String timeline;

	private String analysisRemarks;
}
