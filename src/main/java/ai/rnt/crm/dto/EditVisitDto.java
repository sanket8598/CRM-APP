package ai.rnt.crm.dto;

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

	private String type;

	private String shortName;
	
	private String dueDate;
	
}
