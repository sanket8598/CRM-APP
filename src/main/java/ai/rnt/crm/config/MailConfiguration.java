package ai.rnt.crm.config;

import static java.util.Objects.nonNull;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.util.PropertyUtil;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 26/08/2024
 * @version 1.2
 *
 */
@Component
@RequiredArgsConstructor
public class MailConfiguration extends PropertyUtil {

	private final EmailDaoService emailDaoService;

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setPort(587);

		mailSender.setUsername(userName);
		if (nonNull(getUserName()) && getUserName().endsWith(".com"))
			mailSender.setHost("smtp.zoho.com");
		else
			mailSender.setHost("smtp.office365.com");
		mailSender.setPassword(getMailPassword(getUserName()));

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.debug", true);

		return mailSender;
	}

	public String getMailPassword(String userName) {
		return emailDaoService.findPasswordByMailId(userName);
	}
}
