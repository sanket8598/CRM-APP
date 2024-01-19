package ai.rnt.crm.dto;

import static java.util.Objects.nonNull;

import ai.rnt.crm.util.ContentTypeUtil;
import lombok.Getter;
import lombok.Setter;
/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 12/09/2023.
 */
@Getter
@Setter
public class AttachmentDto {

	private Integer emailAttchId;

	private String attachmentData;

	private String attachType;

	private String attachName;
	
	public String getType() {
		return nonNull(getAttachType())?ContentTypeUtil.getContentTypeName(getAttachType()):"OTHER";
	}

	
	
}
