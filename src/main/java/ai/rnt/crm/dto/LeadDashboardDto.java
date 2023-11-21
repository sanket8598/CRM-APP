package ai.rnt.crm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LeadDashboardDto {

	private Integer leadId;

	private String firstName;

	private String lastName;

	private String topic;

	private String disqualifyAs;

	private String email;
	
	private String phoneNumber;
	
	private String companyWebsite;
	
	@JsonProperty("assignLead")
	private EmployeeDto employee;
	
	private String budgetAmount;
	
	private String businessCard;
	
	private String designation;
	
	@JsonProperty("serviceFallsInto")
	private ServiceFallsDto serviceFallsMaster;
	
	@JsonProperty("leadSource")
	private LeadSourceDto leadSourceMaster;

	@JsonProperty("company")
	private CompanyDto companyMaster;
	
	private String createdOn;
	

}
