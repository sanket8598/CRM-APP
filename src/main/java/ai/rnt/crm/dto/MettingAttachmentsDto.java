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
public class MettingAttachmentsDto {

	private Integer mettingAttchId;

	private String mettingAttachmentData;

	private String mettingAttachType;

	private String mettingAttachName;

	public String getType() {
		return nonNull(getMettingAttachType()) ? ContentTypeUtil.getContentTypeName(getMettingAttachType()) : "OTHER";
	}
}
