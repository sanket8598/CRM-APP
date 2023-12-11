package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class ContactDto {
	
	private Integer contactId;

	private String firstName;

	private String lastName;

	private String designtion;

	private String workEmail;

	private String personalEmail;

	private String contactNumberPrimary;

	private String contactNumberSecondary;

	private CompanyDto companyMaster;

	private String linkedinId;
	
	private Boolean primary;


}
