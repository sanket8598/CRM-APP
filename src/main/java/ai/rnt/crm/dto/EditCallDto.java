package ai.rnt.crm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class EditCallDto extends TimeLineActivityDto {

	private Integer id;
	private Integer parentId;
	private String activityFrom;
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
	private String status;
	private Integer assignTo;
}
