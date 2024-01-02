package ai.rnt.crm.util;

import static java.util.Objects.nonNull;
import static javax.mail.Message.RecipientType.BCC;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;
import static javax.mail.Transport.send;
import static lombok.AccessLevel.PRIVATE;

import java.text.SimpleDateFormat;
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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.stereotype.Component;

import ai.rnt.crm.entity.Attachment;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.exception.CRMException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@NoArgsConstructor(access = PRIVATE)
public class EmailUtil {

	private static final Properties PROPERTIES = new Properties();
	private static final String USERNAME = "s.wakankar@rnt.ai"; // change it
	private static final String PASSWORD = "12345@Sanket"; // change it
	private static final String HOST = "smtp.zoho.com";

	static {
		PROPERTIES.put("mail.smtp.host", HOST);
		PROPERTIES.put("mail.smtp.port", "587");
		PROPERTIES.put("mail.smtp.auth", true);
		PROPERTIES.put("mail.smtp.starttls.enable", true);
	}

	public static boolean sendEmail(Email sendEmail) throws AddressException {
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
			msg.setRecipients(TO, recipientAddress);

			if (nonNull(sendEmail.getCcMail()) && !sendEmail.getCcMail().isEmpty()) {
				List<String> ccAddresses = Stream.of(sendEmail.getCcMail().split(",")).map(String::trim)
						.collect(Collectors.toList());
				InternetAddress[] ccAddressList = new InternetAddress[ccAddresses.size()];
				int count = 0;
				for (String cc : ccAddresses)
					ccAddressList[count++] = new InternetAddress(cc.trim());
				msg.setRecipients(CC, ccAddressList);
			}
			if (nonNull(sendEmail.getBccMail()) && !sendEmail.getBccMail().isEmpty()) {
				List<String> bccAddress = Stream.of(sendEmail.getBccMail().split(",")).map(String::trim)
						.collect(Collectors.toList());
				InternetAddress[] bccAddressList = new InternetAddress[bccAddress.size()];
				int index = 0;
				for (String bcc : bccAddress)
					bccAddressList[index++] = new InternetAddress(bcc.trim());
				msg.setRecipients(BCC, bccAddressList);
			}
			msg.setSubject(sendEmail.getSubject());
			msg.setSentDate(new Date());
			// create body of the mail
			StringBuilder content = new StringBuilder().append("<br>")
					.append(String.format("%s", sendEmail.getContent())).append("<br><br>").append("Regards,")
					.append("<br>");
			if (sendEmail.getAttachment().isEmpty())
				msg = sendAsPlainText(msg, content.toString());
			else
				msg = sendWithAttachments(msg, content.toString(), sendEmail.getAttachment());
			// send the message
			send(msg);
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
		try {
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
		} catch (Exception e) {
			log.info("Got Exception while sendWithAttachments the mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static Message sendAsPlainText(Message msg, String content) throws MessagingException {
		// create message body for plain text mail
		msg.setContent(content, "text/html");
		return msg;
	}

	public static void sendCallTaskReminderMail(PhoneCallTask callTask) {
		try {
			Message msg = new MimeMessage(getSession());

			InternetAddress[] recipientAddress = new InternetAddress[1];
			recipientAddress[0] = new InternetAddress(callTask.getAssignTo().getEmailId());
			msg.setFrom(new InternetAddress(USERNAME));
			msg.setRecipients(TO, recipientAddress);
			msg.setSubject("Task Reminder : " + callTask.getSubject() + " - " + formatDate(callTask.getDueDate()));
			msg.setSentDate(new Date());
			StringBuilder content = new StringBuilder().append("<br>")
					.append(String.format("%s",
							"Dear " + callTask.getAssignTo().getFirstName() + " " + callTask.getAssignTo().getLastName()
									+ ",<br><br>")
							+ "I hope this email finds you well. This is a friendly reminder about the Task  for the "
							+ callTask.getCall().getLead().getTopic() + " by " + callTask.getCall().getLead().getEmployee().getFirstName() + " " +callTask.getCall().getLead().getEmployee().getLastName()
							+ "The task is scheduled for completion by " + formatDate(callTask.getDueDate()) + " At "
							+ callTask.getDueTime() + ".")
					.append("<br><br>").append("Regards,").append("<br>").append("RNT-CRM Team.");
			msg.setContent(content.toString(), "text/html");
			send(msg);
		} catch (Exception e) {
			log.info("Got Exception while sending call task remainder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static void sendVisitTaskReminderMail(VisitTask visitTask) {
		try {
			Message msg = new MimeMessage(getSession());

			InternetAddress[] recipientAddress = new InternetAddress[1];
			recipientAddress[0] = new InternetAddress(visitTask.getAssignTo().getEmailId());
			msg.setFrom(new InternetAddress(USERNAME));
			msg.setRecipients(TO, recipientAddress);
			msg.setSubject("Task Reminder : " + visitTask.getSubject() + " - " + formatDate(visitTask.getDueDate()));
			msg.setSentDate(new Date());
			StringBuilder content = new StringBuilder().append("<br>")
					.append(String.format("%s",
							"Dear " + visitTask.getAssignTo().getFirstName() + " "
									+ visitTask.getAssignTo().getLastName() + ",<br><br>")
							+ "I hope this email finds you well. This is a friendly reminder about the Task for the "
							+ "The task is scheduled for completion by " + formatDate(visitTask.getDueDate()) + " At "
							+ visitTask.getDueTime() + ".")
					.append("<br><br>").append("Regards,").append("<br>").append("RNT-CRM Team.");
			msg.setContent(content.toString(), "text/html");
			send(msg);
		} catch (Exception e) {
			log.info("Got Exception while sending visit task remainder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static void sendMeetingTaskReminderMail(MeetingTask meetingTask) {
		try {
			Message msg = new MimeMessage(getSession());

			InternetAddress[] recipientAddress = new InternetAddress[1];
			recipientAddress[0] = new InternetAddress(meetingTask.getAssignTo().getEmailId());
			msg.setFrom(new InternetAddress(USERNAME));
			msg.setRecipients(TO, recipientAddress);
			msg.setSubject(
					"Task Reminder : " + meetingTask.getSubject() + " - " + formatDate(meetingTask.getDueDate()));
			msg.setSentDate(new Date());
			StringBuilder content = new StringBuilder().append("<br>")
					.append(String.format("%s",
							"Dear " + meetingTask.getAssignTo().getFirstName() + " "
									+ meetingTask.getAssignTo().getLastName() + ",<br><br>")
							+ "I hope this email finds you well. This is a friendly reminder about the Task for the "
							+ "The task is scheduled for completion by " + formatDate(meetingTask.getDueDate()) + " At "
							+ meetingTask.getDueTime() + ".")
					.append("<br><br>").append("Regards,").append("<br>").append("RNT-CRM Team.");
			msg.setContent(content.toString(), "text/html");
			send(msg);
		} catch (Exception e) {
			log.info("Got Exception while sending meeting task remainder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	private static String formatDate(Date date) {
		try {
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
			return outputFormat.format(date);
		} catch (Exception e) {
			log.info("Got Exception while converting task due date..{}" + e.getMessage());
			throw new CRMException(e);
		}
	}
}
