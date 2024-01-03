package ai.rnt.crm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditCallDto extends TimeLineActivityDto {

	private Integer id;
	private String type;
	private String subject;
	private String body;
	private String shortName;
	private String callFrom;
	private String callTo;
	private String direction;
	private String phoneNo;
	private String comment;
	private String duration;
	private String dueDate;
	private boolean overDue;
}
