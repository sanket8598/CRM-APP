package ai.rnt.crm.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditEmailDto extends TimeLineActivityDto{

	
	private Integer id;
	
	private String type;
	
	private String subject;
	
	private String body;
	
	private List<AttachmentDto> attachments;
	
	private String shortName;
	
	private String status;
	
	private String scheduledDate;
	
}
