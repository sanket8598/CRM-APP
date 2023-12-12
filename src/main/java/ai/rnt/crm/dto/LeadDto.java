package ai.rnt.crm.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LeadDto {

	private Integer leadId;

	@NotBlank(message = "First Name should not be null or empty!!")
	private String firstName;

	@NotBlank(message = "Last Name should not be null or empty!!")
	private String lastName;

	//@Size(min = 10, max = 14)
	//@Pattern(regexp ="^(\+\d{1,2}\s?)?(\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4})$", message = "Please Enter a valid Phone Number!!")
	//@PhoneNumValid(message = "Please Enter a Valid Phone Number!!")
	private String phoneNumber;

	private String topic;

	@NotBlank(message = "Email Address should not be null or empty!!")
	@Email(message = "Please enter a valid Email Address")
	private String email;

	private String companyWebsite;

	private String leadSourceId;

	private String serviceFallsId;
	
	private String domainId;

	private String companyName;

	private String businessCard;
	
	private String businessCardName;
	
	private String businessCardType;

	//@Pattern(regexp = "^[0-9,\\.]+$", message = "Please Enter Valid Budget Amount!!")
	private String budgetAmount;

	//@NotNull(message = "Please select the Assign To!!")
	private Integer assignTo;

	private String status;

	private String disqualifyAs;

	private String disqualifyReason;

	private String designation;

	private String pseudoName;

}
