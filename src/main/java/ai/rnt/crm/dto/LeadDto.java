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

	private Integer companyWebsite;

	private Integer leadSourceId;

	private Float budgetAmount;

	private String assignTo;
	
	private CompanyDto companyDto;

}
