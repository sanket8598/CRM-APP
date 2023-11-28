package ai.rnt.crm.dto;

import static java.util.Objects.nonNull;

import ai.rnt.crm.util.ContentTypeUtil;
import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 */
@Data
public class MeetingAttachmentsDto {

	private Integer meetingAttchId;

	private String meetingAttachmentData;

	private String meetingAttachType;

	private String meetingAttachName;

	public String getType() {
		return nonNull(getMeetingAttachType()) ? ContentTypeUtil.getContentTypeName(getMeetingAttachType()) : "OTHER";
	}
}