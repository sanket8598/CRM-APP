package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class EditLeadDto {

	
	private Integer leadId;

	private String firstName;

	private String lastName;

	private String phoneNumber;

	private String topic;

	private String email;
	
	private Double budgetAmount;

	private Integer assignTo;
	
	private String status;
	
	private String customerNeed;

	private String proposedSolution;
	
	private ServiceFallsDto serviceFallsMaster;
	
	private LeadSourceDto leadSourceMaster;
	
	private CompanyDto companyMaster;
	
	private String fullName;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		
		this.fullName = getFirstName()+" "+getLastName();
	}
	
}
