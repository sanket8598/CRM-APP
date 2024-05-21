package ai.rnt.crm.dto;

import static javax.persistence.TemporalType.DATE;

import java.util.Date;

import javax.persistence.Temporal;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ai.rnt.crm.validation.PhoneNumValid;
import ai.rnt.crm.validation.ValidCallDirection;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sanket Wakankar
 * @version 1.0
 * @since 11/09/2023.
 */
@Getter
@Setter
public class CallDto {

	private Integer callId;

	// @NotBlank(message = "Call From should not be null or empty!!")
	private EmployeeDto callFrom;

	private String callTo;

	@NotBlank(message = "Subject should not be null or empty!!")
	private String subject;

	@ValidCallDirection(message = "Please Select Valid Call Direction!!")
	private String direction;

	@NotBlank(message = "Phone Number should not be null or empty!!")
	@Size(min = 10, max = 14)
	// @Pattern(regexp =
	// "^(\\+\\d{1,2}\\s?)?(\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4})$", message =
	// "Please Enter a valid Phone Number!!")
	@Pattern(regexp = "\\+?\\d+", message = "Invalid Phone Number!!")
	@PhoneNumValid(message = "Please Enter a Valid Phone Number!!")
	private String phoneNo;

	private String comment;

	private String duration;

	@NotNull(message = "Start Date should not be null!!")
	@Temporal(DATE)
	@FutureOrPresent(message = "Date must not be smaller than today's date!!")
	private Date startDate;

	@Temporal(DATE)
	@NotNull(message = "End Date should not be null!!")
	@FutureOrPresent(message = "Date must not be smaller than today's date!!")
	private Date endDate;

	private String startTime;

	private String endTime;

	private boolean allDay;

	private Boolean isOpportunity = false;

}
