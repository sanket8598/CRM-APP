package ai.rnt.crm.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ai.rnt.crm.validation.DisqualifiedLead;
import ai.rnt.crm.validation.LeadAdvanceInfo;
import ai.rnt.crm.validation.ValidDisqualifiedStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeadDto {

	private Integer leadId;

	@NotBlank(message = "First Name should not be null or empty!!", groups = LeadAdvanceInfo.class)
	private String firstName;

	@NotBlank(message = "Last Name should not be null or empty!!", groups = LeadAdvanceInfo.class)
	private String lastName;

	// @Size(min = 10, max = 14)
	// @Pattern(regexp ="^(\+\d{1,2}\s?)?(\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4})$",
	// message = "Please Enter a valid Phone Number!!")
	// @PhoneNumValid(message = "Please Enter a Valid Phone Number!!")
	@Pattern(regexp = "\\+?\\d+", message = "Please Enter a Valid Phone Number!!")
	private String phoneNumber;

	@Size(max = 1000, message = "Topic field limit exceeded! Please shorten your input.")
	private String topic;

	@NotBlank(message = "Email Address should not be null or empty!!", groups = LeadAdvanceInfo.class)
	@Email(message = "Please enter a valid Email Address", groups = LeadAdvanceInfo.class)
	private String email;

	private String companyWebsite;

	@NotBlank(message = "Lead Source should not be null or empty!!", groups = LeadAdvanceInfo.class)
	private String leadSourceId;

	private String serviceFallsId;

	// @NotBlank(message = "Domain should not be null or empty!!")
	private String domainId;

	private String companyName;

	private String businessCard;

	private String businessCardName;

	private String businessCardType;

	// @Pattern(regexp = "^[0-9,\\.]+$", message = "Please Enter Valid Budget
	// Amount!!")
	private String budgetAmount;

	// @NotNull(message = "Please select the Assign To!!")
	private Integer assignTo;

	private String status;

	@ValidDisqualifiedStatus(message = "Disqualified-As is not valid!!", groups = DisqualifiedLead.class)
	private String disqualifyAs;

	private String disqualifyReason;

	private String designation;

	private String pseudoName;

	private String location;

	private String linkedinId;

	private Integer assignBy;

	private LocalDate assignDate;
}
