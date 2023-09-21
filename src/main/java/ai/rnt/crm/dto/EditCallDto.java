package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class EditCallDto implements TimeLineActivityDto{
	
	private Integer id;
	private String type;
	private String subject;
	private String dueDate;
	private String body;
	private String shortName;
	private String callFrom;
	private String createdOn;

}
