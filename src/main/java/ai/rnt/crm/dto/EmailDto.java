package ai.rnt.crm.dto;

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

	private String mailTo;

	private String cc;

	private String bcc;

	private String subject;

	private String content;

}
