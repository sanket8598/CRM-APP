package ai.rnt.crm.dto.opportunity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 22/02/2024
 * @version 1.2
 *
 */
@Getter
@Setter
public class CloseOpportunityDto {

	private String winLoseReason;

	private String paymentTerms;

	private String contract;

	private String supportPlan;

	private String finalBudget;

	private String progressStatus;

	private String currentPhase;

	private List<OpprtAttachmentDto> attachments;
}
