package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class LeadDto {

	private Integer leadId;

	private String firstName;

	private String lastName;

	private Integer phoneNumber;

	private String topic;

	private String email;

	private String companyWebsite;
	private Integer leadSourceId;
	private Integer serviceFallsId;
	private Integer companyId;

	private LeadSourceDto leadSourceMaster;
	
	private ServiceFallsDto serviceFallsMaster;

	private Float budgetAmount;

	private Integer assignTo;

	private CompanyDto companyMaster;

}
