package ai.rnt.crm.dto.opportunity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 *
 */
@Getter
@Setter
public class CloseAsLostOpportunityDto {

	private Integer opportunityId;

	private String progressStatus;

	private String currentPhase;

	private String lostReason;

	private Boolean thankMailSent = false;

	private String description;

	private String status;

}
