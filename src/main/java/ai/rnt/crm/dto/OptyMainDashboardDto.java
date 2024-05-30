package ai.rnt.crm.dto;

import ai.rnt.crm.dto.opportunity.OpportunityDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptyMainDashboardDto {

	private Integer opportunityId;

	private String shortName;

	private String status;

	private OpportunityDto opty;

}
