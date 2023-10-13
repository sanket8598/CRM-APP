package ai.rnt.crm.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	
	@NotNull(message = "Due Date should not be null!!")
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date dueDate;

	private Integer leadId;

	private Integer visitBy;

}
