package ai.rnt.crm.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import ai.rnt.crm.validation.PhoneNumValid;
import lombok.Data;

@Data
public class LeadDto {

	private Integer leadId;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$",
            message="Please Enter a valid Phone Number!!")
	@PhoneNumValid
	private String phoneNumber;

	private String topic;

	@NotBlank
	private String email;

	private String companyWebsite;

	private Integer leadSourceId;

	private Integer serviceFallsId;

	private String companyName;

	private String businessCard;

	private Double budgetAmount;

	@NotNull
	private Integer assignTo;
	
	private String status;
	
	private String disqualifyAs;
	
	private String disqualifyReason;
	

}
