package ai.rnt.crm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 21/09/2023.
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EditVisitDto extends TimeLineActivityDto {

	private Integer id;

	private String location;

	private String subject;

	private String body;

	@JsonFormat(pattern="dd-MM-yyyy")
	private String dueDate;

	private String type;
	
	private String shortName;
}
