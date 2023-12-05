package ai.rnt.crm.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditMeetingDto extends TimeLineActivityDto{
	
	private Integer id;

	private String subject;
	
	private String body;

	private String type;

	private String shortName;
	
	private String dueDate;
	
	private List<MeetingAttachmentsDto> attachments;
}
