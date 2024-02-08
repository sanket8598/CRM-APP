package ai.rnt.crm.dto.opportunity;

import com.fasterxml.jackson.annotation.JsonProperty;

import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.dto.LeadDashboardDto;
import lombok.Data;

@Data
public class OpportunityDto {

	private Integer opportunityId;

	private String status;

	private String topic;

	private String pseudoName;

	private String budgetAmount;

	private String customerNeed;

	private String proposedSolution;

	@JsonProperty("lead")
	private LeadDashboardDto leadDashboardDto;
	
	@JsonProperty("assignOpportunity")
	private EmployeeDto employee;
	
	private String createdOn;
}
