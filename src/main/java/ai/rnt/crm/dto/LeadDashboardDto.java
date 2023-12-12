package ai.rnt.crm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LeadDashboardDto {

	private Integer leadId;

	private String topic;

	private String disqualifyAs;

	
	@JsonProperty("assignLead")
	private EmployeeDto employee;
	
	private String budgetAmount;
	
	
	@JsonProperty("serviceFallsInto")
	private ServiceFallsDto serviceFallsMaster;
	
	@JsonProperty("leadSource")
	private LeadSourceDto leadSourceMaster;

	@JsonProperty("contact")
	private ContactDto primaryContact;
	
	private String createdOn;
	

}
