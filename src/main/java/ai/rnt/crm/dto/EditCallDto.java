package ai.rnt.crm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditCallDto extends TimeLineActivityDto{
	
	private Integer id;
	private String type;
	private String subject;
	private String dueDate;
	private String body;
	private String shortName;
	private String callFrom;
	

}
