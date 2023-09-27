package ai.rnt.crm.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

	private List<@NotBlank(message = "Email Address should not be null or empty!!") @Email(message="Please enter a valid Email Address") String> mailTo;

	private List<String> cc;

	private List<String> bcc;

	private String subject;

	private String content;

	private List<AttachmentDto> attachment;

}
