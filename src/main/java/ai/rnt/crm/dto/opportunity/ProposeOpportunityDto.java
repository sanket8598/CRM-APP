package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ProposeOpportunityDto {

	private String licAndPricDetails;

	private String devPlan;

	private String propAcceptCriteria;
	
	private String presentation;
	
	private String scopeOfWork;
	
	private String termsAndConditions;
	
	private String proposition;
	
	private String progressStatus;
	
	private String currentPhase;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate propExpDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate updatedPropExpDate;
	
	private List<OpprtAttachmentDto> attachments;
}
