package ai.rnt.crm.dto;

import java.util.Date;

import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @version - 1.0
 * @since 14-09-2023.
 *
 */
@Data
public class VisitDto {

	private Integer visitId;

	private String location;

	private String subject;

	private String content;

	private String comment;

	private String duration;

	private Date dueDate;
	
	private Integer leadId;
	
	private Integer visitBy;

}
