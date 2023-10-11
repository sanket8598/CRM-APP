package ai.rnt.crm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 */
@Data
public class AddCallDto {

	private Integer addCallId;

	private EmployeeDto callFrom;

	private String callTo;

	private String subject;

	private String direction;

	private String phoneNo;

	private String comment;

	private String duration;
	
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date dueDate;

}
