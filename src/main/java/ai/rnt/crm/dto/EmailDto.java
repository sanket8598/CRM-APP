package ai.rnt.crm.dto;

import java.util.List;

import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 12/09/2023.
 */

@Data
public class EmailDto {

	private Integer addMailId;

	private String mailFrom;

	private List<String> mailTo;

	private List<String> cc;

	private List<String> bcc;

	private String subject;

	private String content;
	
	private List<AttachmentDto> attachment;

}
