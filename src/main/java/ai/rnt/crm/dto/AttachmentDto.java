package ai.rnt.crm.dto;

import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 12/09/2023.
 */
@Data
public class AttachmentDto {

	private Integer emailAttchId;

	private String attachmentData;

	private String attachType;

	private String attachName;
	
}
