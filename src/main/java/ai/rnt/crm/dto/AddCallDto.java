package ai.rnt.crm.dto;

import java.util.Date;

import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 */
@Data
public class AddCallDto {

	private Integer addCallId;

	private String callFrom;

	private String callTo;

	private String subject;

	private String direction;

	private Integer phoneNo;

	private String comment;

	private String duration;
	
	private Date dueDate;

}
