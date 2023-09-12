package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class LeadDto {

	private Integer leadId;

	private String firstName;

	private String lastName;

	private String phoneNumber;

	private String topic;

	private String email;

	private String companyWebsite;

	private Integer leadSourceId;

	private Integer serviceFallsId;

	private String companyName;

	private String businessCard;

	private Double budgetAmount;

	private Integer assignTo;
	
	private String status;
	

}
