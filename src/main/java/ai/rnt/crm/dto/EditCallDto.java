package ai.rnt.crm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditCallDto extends TimeLineActivityDto{
	
	private Integer id;
	private String type;
	private String subject;
	
	@JsonFormat(pattern="dd-MM-yyyy")
	private String dueDate;
	
	private String body;
	private String shortName;
	private String callFrom;
	private String callTo;
	private String direction;
	private String phoneNo;
	private String comment;
	private String duration;
	

}
