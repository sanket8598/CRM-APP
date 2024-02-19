package ai.rnt.crm.dto.opportunity;

import java.util.List;

import lombok.Data;

@Data
public class AnalysisOpportunityDto {

	private Integer opportunityId;
	
	private String technicalNeed;
	
	private String integrationPoint;
	
	private String secAndComp;
	
	private String riskMinigation;
	
	private String initialTimeline;
	
	private String currentPhase;
	
	private List<OpprtAttachmentDto> attachments;
}
