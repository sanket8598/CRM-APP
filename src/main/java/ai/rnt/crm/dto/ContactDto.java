package ai.rnt.crm.dto;

import static java.util.Objects.nonNull;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDto {

	private Integer contactId;

	private String firstName;

	private String lastName;

	private String designation;

	private String workEmail;

	private String personalEmail;

	private String contactNumberPrimary;

	private String contactNumberSecondary;

	private CompanyDto companyMaster;

	private String linkedinId;

	private Boolean primary;

	private String businessCard;

	private String businessCardName;

	private String businessCardType;

	private Boolean client = false;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime createdDate;

	@NotBlank(message = "First/last name should not be empty or null!!")
	@Pattern(regexp = "^[A-Za-z]+\\s[A-Za-z]+$", message = "Plase enter valid first/last name!!")
	private String name;

	public String getName() {
		return nonNull(name) ? name : getFirstName() + " " + getLastName();

	}
}
