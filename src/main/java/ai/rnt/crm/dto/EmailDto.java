package ai.rnt.crm.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	@NotBlank(message = "Please enter email address!!")
	private List<@NotBlank(message = "Email Address should not be null or empty!!") @Email(message="Please enter a valid Email Address") String> mailTo;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<@Email(message="Please enter a valid CC Email Address") String> cc;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<@Email(message="Please enter a valid BCC Email Address") String> bcc;

	private String subject;

	private String content;

	private List<AttachmentDto> attachment;

}
