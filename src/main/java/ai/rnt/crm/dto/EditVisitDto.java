package ai.rnt.crm.dto;

import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 21/09/2023.
 *
 */
@Data
public class EditVisitDto implements TimeLineActivityDto {

	private Integer id;

	private String location;

	private String subject;

	private String body;

	private String dueDate;

	private String type;
	
	private String shortName;
	
	private String createdOn;
}
