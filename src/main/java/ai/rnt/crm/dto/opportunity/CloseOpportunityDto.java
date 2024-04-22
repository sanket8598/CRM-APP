package ai.rnt.crm.dto.opportunity;

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

	private Integer opportunityId;

	private String progressStatus;

	private String currentPhase;

	private Boolean projectKickoff = false;

	private Boolean finalisingTeam = false;

	private Boolean slaSigned = false;

	private Boolean sowSigned = false;

	private Boolean ndaSigned = false;
	
	private String feedback;

	private String status;

}
