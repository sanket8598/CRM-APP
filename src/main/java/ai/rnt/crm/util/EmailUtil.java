package ai.rnt.crm.util;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class EmailUtil {

	private final JavaMailSender mailSender;

	@Value("${email.from}")
	private String emailFrom;

	public void sendMailWithAttachment(String to, String subject, String body, String fileToAttach) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
				mimeMessage.setFrom(new InternetAddress(emailFrom));
				mimeMessage.setSubject(subject);
				mimeMessage.setText(body);

				FileSystemResource file = new FileSystemResource(fileToAttach);
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.addAttachment("logo.jpg", file);
			}
		};
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			log.error("error occured while sending email with attachments...{}", ex.getMessage());
		}
	}

	/**
	 * This method will send compose and send the message
	 */
	public void sendMail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(emailFrom);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}
}
