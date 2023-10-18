package ai.rnt.crm.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import ai.rnt.crm.validation.PhoneNumValid;
import lombok.Data;

/**
 * @author Sanket Wakankar
 * @version 1.0
 * @since 11/09/2023.
 */
@Data
public class AddCallDto {

	private Integer addCallId;

	//@NotBlank(message = "Call From should not be null or empty!!")
	private EmployeeDto callFrom;

	private String callTo;

	private String subject;

	private String direction;
	
	@NotBlank(message = "Phone Number should not be null or empty!!")
	@Size(min = 10, max = 14)
	//@Pattern(regexp = "^(\\+\\d{1,2}\\s?)?(\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4})$", message = "Please Enter a valid Phone Number!!")
	@PhoneNumValid(message = "Please Enter a Valid Phone Number!!")
	private String phoneNo;

	private String comment;

	private String duration;
	
	@NotNull(message = "Due Date should not be null!!")
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date dueDate;

}
