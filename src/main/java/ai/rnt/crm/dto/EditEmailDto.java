package ai.rnt.crm.dto;

import java.util.List;

import lombok.Data;

@Data
public class EditEmailDto implements TimeLineActivityDto{

	
	private Integer id;
	private String type;
	private String subject;
	private String body;
	private List<AttachmentDto> attachments;
	private String createdOn;
}
