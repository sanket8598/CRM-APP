package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class EmailDtoTest {

	EmailDto dto = new EmailDto();
	EmailDto dto1 = new EmailDto();

	@Test
	void testEmailDto() {
		EmailDto emailDto = new EmailDto();
		Integer mailId = 1;
		String mailFrom = "sender@example.com";
		List<String> mailTo = Arrays.asList("recipient1@example.com", "recipient2@example.com");
		List<String> cc = Arrays.asList("cc1@example.com", "cc2@example.com");
		List<String> bcc = Arrays.asList("bcc1@example.com", "bcc2@example.com");
		String subject = "Test Email";
		String content = "This is a test email.";
		Boolean scheduled = true;
		LocalDate scheduledOn = LocalDate.now();
		LocalDate updateScheduledOn = LocalDate.now();
		String scheduledAt = "10:00 AM";
		Boolean isOpportunity = true;
		List<AttachmentDto> attachments = new ArrayList<>();
		emailDto.setMailId(mailId);
		emailDto.setMailFrom(mailFrom);
		emailDto.setMailTo(mailTo);
		emailDto.setCc(cc);
		emailDto.setBcc(bcc);
		emailDto.setSubject(subject);
		emailDto.setContent(content);
		emailDto.setScheduled(scheduled);
		emailDto.setScheduledOn(scheduledOn);
		emailDto.setUpdateScheduledOn(updateScheduledOn);
		emailDto.setScheduledAt(scheduledAt);
		emailDto.setIsOpportunity(isOpportunity);
		emailDto.setAttachment(attachments);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(mailId, emailDto.getMailId());
		assertEquals(mailFrom, emailDto.getMailFrom());
		assertEquals(mailTo, emailDto.getMailTo());
		assertEquals(cc, emailDto.getCc());
		assertEquals(bcc, emailDto.getBcc());
		assertEquals(subject, emailDto.getSubject());
		assertEquals(content, emailDto.getContent());
		assertEquals(scheduled, emailDto.getScheduled());
		assertEquals(scheduledOn, emailDto.getScheduledOn());
		assertEquals(updateScheduledOn, emailDto.getUpdateScheduledOn());
		assertEquals(scheduledAt, emailDto.getScheduledAt());
		assertEquals(isOpportunity, emailDto.getIsOpportunity());
		assertEquals(attachments, emailDto.getAttachment());
	}

}
