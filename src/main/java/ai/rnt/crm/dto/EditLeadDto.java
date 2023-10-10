package ai.rnt.crm.dto;

import ai.rnt.crm.util.LeadsCardUtil;
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
	
	private String message;
	
	private String generatedBy;
	
	private String leadRequirements;
	
	private String pseudoName;
	
	
	public String getShortName() {
		return LeadsCardUtil.shortName(getFirstName(), getLastName());
	}

	public String getFullName() {
		return getFirstName()+" "+getLastName();
	}

}
