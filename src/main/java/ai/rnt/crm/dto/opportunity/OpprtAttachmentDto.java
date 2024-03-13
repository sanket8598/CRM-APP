package ai.rnt.crm.dto.opportunity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 17-02-2024
 * @version 1.2
 *
 */
@Getter
@Setter
public class OpprtAttachmentDto {

	private Integer optAttchId;

	private String attachmentData;

	private String attachType;

	private String attachName;

	private String attachmentOf;
}
