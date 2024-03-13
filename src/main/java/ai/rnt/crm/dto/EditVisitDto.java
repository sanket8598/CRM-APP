package ai.rnt.crm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 21/09/2023.
 *
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class EditVisitDto extends TimeLineActivityDto {

	private Integer id;

	private String location;

	private String subject;

	private String body;

	private String type;

	private String shortName;

	private String dueDate;

	private String status;

	private Integer assignTo;

}
