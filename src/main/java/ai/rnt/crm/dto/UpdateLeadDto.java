package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class UpdateLeadDto {

	private String firstName;

	private String lastName;

	private String phoneNumber;

	private String topic;

	private String email;
	
	private Double budgetAmount;
	
	private Integer serviceFallsId;
	
	private Integer leadSourceId;
	private String companyWebsite;
	private String companyName;
	private String country;
	private String state;
	private String city;
	private String zipCode;
	private String addressLineOne;
}
