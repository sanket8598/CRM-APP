package ai.rnt.crm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardCardDto {

	private Integer leadId;

	private String shortName;

	private String status;

	private LeadDashboardDto leads;

}
