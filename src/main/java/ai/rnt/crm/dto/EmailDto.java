package ai.rnt.crm.dto;

import static javax.persistence.TemporalType.DATE;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sanket Wakankar
 * @version 1.0
 * @since 12/09/2023.
 */

@Getter
@Setter
public class EmailDto {

	private Integer mailId;

	private String mailFrom;

	@NotBlank(message = "Please enter email address!!")
	private List<@NotBlank(message = "Email Address should not be null or empty!!") @Email(message = "Please enter a valid Email Address") String> mailTo;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<@Email(message = "Please enter a valid CC Email Address") String> cc;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<@Email(message = "Please enter a valid BCC Email Address") String> bcc;

	@NotBlank(message = "Please Enter Subject!!")
	private String subject;

	private String content;

	private Boolean scheduled;

	@Temporal(DATE)
	@FutureOrPresent(message="Date must not be smaller than today's date!!")
	private Date scheduledOn;

	private String scheduledAt;

	private List<AttachmentDto> attachment;

}
