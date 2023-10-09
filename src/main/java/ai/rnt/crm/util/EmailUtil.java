package ai.rnt.crm.util;

import static java.util.Objects.nonNull;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.stereotype.Component;

import ai.rnt.crm.entity.AddEmail;
import ai.rnt.crm.entity.Attachment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmailUtil {

	private static final Properties PROPERTIES = new Properties();
	private static final String USERNAME = "nik.gaikwad@rnt.ai"; // change it
	private static final String PASSWORD = "xcHsf$8y"; // change it
	private static final String HOST = "smtp.zoho.com";

	static {
		PROPERTIES.put("mail.smtp.host", HOST);
		PROPERTIES.put("mail.smtp.port", "587");
		PROPERTIES.put("mail.smtp.auth", true);
		PROPERTIES.put("mail.smtp.starttls.enable", true);
	}

	public static boolean sendEmail(AddEmail sendEmail) throws AddressException {

		try {

			// create a message with headers
			Message msg = new MimeMessage(getSession());
			msg.setFrom(new InternetAddress(USERNAME));// change it to mail from.

			List<String> recipientList = Stream.of(sendEmail.getToMail().split(",")).map(String::trim)
					.collect(Collectors.toList());
			InternetAddress[] recipientAddress = new InternetAddress[recipientList.size()];
			int counter = 0;
			for (String recipient : recipientList)
				recipientAddress[counter++] = new InternetAddress(recipient.trim());
			msg.setRecipients(Message.RecipientType.TO, recipientAddress);

			if (nonNull(sendEmail.getCcMail()) && !sendEmail.getCcMail().isEmpty()) {
				List<String> ccAddresses = Stream.of(sendEmail.getCcMail().split(",")).map(String::trim)
						.collect(Collectors.toList());
				InternetAddress[] ccAddressList = new InternetAddress[ccAddresses.size()];
				int count = 0;
				for (String cc : ccAddresses)
					ccAddressList[count++] = new InternetAddress(cc.trim());
				msg.setRecipients(Message.RecipientType.CC, ccAddressList);
			}
			if (nonNull(sendEmail.getBccMail()) && !sendEmail.getBccMail().isEmpty()) {
				List<String> bccAddress = Stream.of(sendEmail.getBccMail().split(",")).map(String::trim)
						.collect(Collectors.toList());
				InternetAddress[] bccAddressList = new InternetAddress[bccAddress.size()];
				int index = 0;
				for (String bcc : bccAddress)
					bccAddressList[index++] = new InternetAddress(bcc.trim());
				msg.setRecipients(Message.RecipientType.BCC, bccAddressList);
			}
			msg.setSubject(sendEmail.getSubject());
			msg.setSentDate(new Date());

			// create body of the mail
			StringBuilder content = new StringBuilder()
					.append(String.format("Hi %s,",
							sendEmail.getLead().getFirstName() + " " + sendEmail.getLead().getLastName()))
					.append("<br>").append(String.format("%s", sendEmail.getContent())).append("<br><br>")
					.append("Regards,").append("<br>").append("RNT Team");
			if (sendEmail.getAttachment().isEmpty())
				msg = sendAsPlainText(msg, content.toString());
			else
				msg = sendWithAttachments(msg, content.toString(), sendEmail.getAttachment());

			// send the message
			Transport.send(msg);
			return true;
		} catch (MessagingException ex) {
			log.error("error occured while sending the email..{}", ex.getMessage());
			return false;
		}
	}

	private static Session getSession() {
		return Session.getInstance(PROPERTIES, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
	}

	public static Message sendWithAttachments(Message msg, String content, List<Attachment> list)
			throws MessagingException {
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		MimeMultipart multipart = new MimeMultipart();
		for (Attachment data : list) {
			if (nonNull(data.getAttachmentData())) {
				MimeBodyPart attachemntBodyPart = new MimeBodyPart();
				attachemntBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(
						Base64.getDecoder().decode(data.getAttachmentData().split(",")[1]), data.getAttachType())));
				attachemntBodyPart.setDisposition(Part.ATTACHMENT);
				attachemntBodyPart.setFileName(data.getAttachName());
				multipart.addBodyPart(attachemntBodyPart);
			}
		}
		messageBodyPart.setContent(content, "text/html");
		multipart.addBodyPart(messageBodyPart);
		msg.setContent(multipart);
		return msg;

	}

	public static Message sendAsPlainText(Message msg, String content) throws MessagingException {
		// create message body for plain text mail
		msg.setContent(content, "text/html");
		return msg;
	}

}
