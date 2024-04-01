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

	private Boolean projectKickoff;

	private Boolean finalisingTeam;

	private Boolean slaSigned;

	private Boolean sowSigned;

	private Boolean ndaSigned;
	
	private String status;

}
