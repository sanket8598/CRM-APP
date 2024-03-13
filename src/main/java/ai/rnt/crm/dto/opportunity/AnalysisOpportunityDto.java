package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisOpportunityDto {

	private Integer opportunityId;

	private String technicalNeed;

	private String integrationPoint;

	private String secAndComp;

	private String riskMinigation;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate initialTimeline;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate updatedInitialTimeline;

	private String currentPhase;

	private String progressStatus;

	private List<OpprtAttachmentDto> attachments;

}
