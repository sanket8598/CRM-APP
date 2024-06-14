package ai.rnt.crm.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class EditMeetingDto extends TimeLineActivityDto {

	private Integer id;

	private Integer parentId;

	private String activityFrom;

	private String subject;

	private String body;

	private String type;

	private String shortName;

	private String dueDate;

	private String status;

	private Integer assignTo;

	private List<MeetingAttachmentsDto> attachments;
}
