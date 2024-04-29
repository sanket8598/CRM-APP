package ai.rnt.crm.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLeadDto {

	@NotBlank(message = "First Name should not be null or empty!!")
	private String firstName;

	@NotBlank(message = "Last Name should not be null or empty!!")
	private String lastName;

	// @Size(min = 10, max = 13)
	// @Pattern(regexp =
	// ^(\\+\\d{1,2}\\s?)?(\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4})$, message =
	// "Please Enter a valid Phone Number!!")
	// @PhoneNumValid(message = "Please Enter a Valid Phone Number!!")
	private String phoneNumber;

	@Size(max = 1000, message = "Requirement field limit exceeded! Please shorten your input.")
	private String topic;

	@NotBlank(message = "Email Address should not be null or empty!!")
	@Email(message = "Please enter a valid Email Address")
	private String email;

	@Pattern(regexp = "^[0-9,\\.]+$", message = "Please Enter Valid Budget Amount!!")
	private String budgetAmount;

	@NotBlank(message = "Service Falls Into should not be null or empty!!")
	private String serviceFallsId;

	@NotBlank(message = "Lead Source should not be null or empty!!")
	private String leadSourceId;

	private String domainId;
	private String companyWebsite;
	private String companyName;
	private String country;
	private String state;
	private String city;
	private String zipCode;
	private String addressLineOne;
	private String fullName;

	@Size(max = 2000, message = "Customer Need field limit exceeded! Please shorten your input.")
	private String customerNeed;

	@Size(max = 2000, message = "Proposed Solution field limit exceeded! Please shorten your input.")
	private String proposedSolution;
	private String pseudoName;
	private String linkedinId;

}
