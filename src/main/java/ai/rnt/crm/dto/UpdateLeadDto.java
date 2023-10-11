package ai.rnt.crm.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ai.rnt.crm.validation.PhoneNumValid;
import lombok.Data;

@Data
public class UpdateLeadDto {

	@NotBlank(message = "First Name should not be null or empty!!")
	private String firstName;

	@NotBlank(message = "Last Name should not be null or empty!!")
	private String lastName;

	@Size(min = 10, max = 13)
	//@Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message = "Please Enter a valid Phone Number!!")
	@PhoneNumValid(message = "Please Enter a Valid Phone Number!!")
	private String phoneNumber;

	private String topic;

	@NotBlank(message = "Email Address should not be null or empty!!")
	@Email(message = "Please enter a valid Email Address")
	private String email;
	
	@Pattern(regexp = "^[0-9,\\.]+$", message = "Please Enter Valid Budget Amount!!")
	private String budgetAmount;
	
	private Integer serviceFallsId;
	
	private Integer leadSourceId;
	private String companyWebsite;
	private String companyName;
	private String country;
	private String state;
	private String city;
	private String zipCode;
	private String addressLineOne;
	private String fullName;
	private String customerNeed;
	private String proposedSolution;
	private String pseudoName;

}
