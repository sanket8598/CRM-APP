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

import ai.rnt.crm.dto.AttachmentDto;
import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.Leads;
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
	private static final String USERNAME = "nik.gaikwad@rnt.ai"; // change it
	private static final String PASSWORD = "%tb9bbRg"; // change it
	private static final String HOST = "smtp.zoho.com";

	static {
		PROPERTIES.put("mail.smtp.host", HOST);
		PROPERTIES.put("mail.smtp.port", "587");
		PROPERTIES.put("mail.smtp.auth", true);
		PROPERTIES.put("mail.smtp.starttls.enable", true);
	}

	public static boolean sendEmail(EmailDto email) throws AddressException {
		log.info("inside the sendEmail method...");
		try {
			// create a message with headers
			Message msg = new MimeMessage(getSession());
			msg.setFrom(new InternetAddress(USERNAME));// change it to mail from.

			List<String> recipientList = email.getMailTo();
			InternetAddress[] recipientAddress = new InternetAddress[recipientList.size()];
			int counter = 0;
			for (String recipient : recipientList)
				recipientAddress[counter++] = new InternetAddress(recipient.trim());
			msg.setRecipients(TO, recipientAddress);

			if (nonNull(email.getCc()) && !email.getCc().isEmpty()) {
				List<String> ccAddresses = email.getCc();
				InternetAddress[] ccAddressList = new InternetAddress[ccAddresses.size()];
				int count = 0;
				for (String cc : ccAddresses)
					ccAddressList[count++] = new InternetAddress(cc.trim());
				msg.setRecipients(CC, ccAddressList);
			}
			if (nonNull(email.getBcc()) && !email.getBcc().isEmpty()) {
				List<String> bccAddress = email.getBcc();
				InternetAddress[] bccAddressList = new InternetAddress[bccAddress.size()];
				int index = 0;
				for (String bcc : bccAddress)
					bccAddressList[index++] = new InternetAddress(bcc.trim());
				msg.setRecipients(BCC, bccAddressList);
			}
			msg.setSubject(email.getSubject());
			msg.setSentDate(new Date());
			// create body of the mail
			StringBuilder content = new StringBuilder().append("<br>").append(String.format("%s", email.getContent()))
					.append("<br><br>").append("Regards,").append("<br>").append("Rabbit & Tortoise Technology.");
			if (email.getAttachment().isEmpty())
				msg = sendAsPlainText(msg, content.toString());

			else
				msg = sendWithAttachments(msg, content.toString(), email.getAttachment());

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

	public static Message sendWithAttachments(Message msg, String content, List<AttachmentDto> list)
			throws MessagingException {
		log.info("inside the sendWithAttachments method...}");
		try {
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			MimeMultipart multipart = new MimeMultipart();
			for (AttachmentDto data : list) {
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
			log.error("Got Exception while sendWithAttachments the mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static Message sendAsPlainText(Message msg, String content) throws MessagingException {
		// create message body for plain text mail
		msg.setContent(content, "text/html");
		return msg;
	}

	public static void sendCallTaskReminderMail(PhoneCallTask callTask, String emailId) {
		log.info("inside the sendCallTaskReminderMail method...}");
		try {
			Message msg = new MimeMessage(getSession());

			InternetAddress[] recipientAddress = new InternetAddress[1];
			recipientAddress[0] = new InternetAddress(callTask.getAssignTo().getEmailId());
			if (nonNull(emailId) && !emailId.isEmpty()) {
				recipientAddress = new InternetAddress[2];
				recipientAddress[1] = new InternetAddress(emailId);
			}
			msg.setFrom(new InternetAddress(USERNAME));
			msg.setRecipients(TO, recipientAddress);
			msg.setSubject("Task Reminder : " + callTask.getSubject() + " - " + formatDate(callTask.getDueDate()));
			msg.setSentDate(new Date());
			StringBuilder content = new StringBuilder().append("<br>")
					.append(String.format("%s",
							"Dear " + callTask.getAssignTo().getFirstName() + " " + callTask.getAssignTo().getLastName()
									+ ",<br><br>")
							+ "I hope this email finds you well. This is a friendly reminder about the Task  for the "
							+ callTask.getCall().getLead().getTopic() + " by "
							+ callTask.getCall().getLead().getContacts().stream().filter(Contacts::getPrimary)
									.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse("")
							+ "." + " The task is scheduled for completion by " + formatDate(callTask.getDueDate())
							+ " At " + callTask.getDueTime() + ".")
					.append("<br><br>").append("Regards,").append("<br>").append("Rabbit & Tortoise Technology.");
			msg.setContent(content.toString(), "text/html");
			send(msg);
		} catch (Exception e) {
			log.error("Got Exception while sending call task reminder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static void sendVisitTaskReminderMail(VisitTask visitTask, String emailId) {
		log.info("inside the sendVisitTaskReminderMail method...}");
		try {
			Message msg = new MimeMessage(getSession());
			InternetAddress[] recipientAddress = new InternetAddress[1];
			recipientAddress[0] = new InternetAddress(visitTask.getAssignTo().getEmailId());
			if (nonNull(emailId) && !emailId.isEmpty()) {
				recipientAddress = new InternetAddress[2];
				recipientAddress[1] = new InternetAddress(emailId);
			}
			msg.setFrom(new InternetAddress(USERNAME));
			msg.setRecipients(TO, recipientAddress);
			msg.setSubject("Task Reminder : " + visitTask.getSubject() + " - " + formatDate(visitTask.getDueDate()));
			msg.setSentDate(new Date());
			StringBuilder content = new StringBuilder().append("<br>")
					.append(String.format("%s",
							"Dear " + visitTask.getAssignTo().getFirstName() + " "
									+ visitTask.getAssignTo().getLastName() + ",<br><br>")
							+ "I hope this email finds you well. This is a friendly reminder about the Task for the "
							+ visitTask.getVisit().getSubject() + " by "
							+ visitTask.getVisit().getLead().getContacts().stream().filter(Contacts::getPrimary)
									.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse("")
							+ "." + " The task is scheduled for completion by " + formatDate(visitTask.getDueDate())
							+ " At " + visitTask.getDueTime12Hours() + ".")
					.append("<br><br>").append("Regards,").append("<br>").append("Rabbit & Tortoise Technology.");
			msg.setContent(content.toString(), "text/html");
			send(msg);
		} catch (Exception e) {
			log.error("Got Exception while sending visit task reminder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static void sendMeetingTaskReminderMail(MeetingTask meetingTask, String emailId) {
		log.info("inside the sendMeetingTaskReminderMail method...}");
		try {
			Message msg = new MimeMessage(getSession());

			InternetAddress[] recipientAddress = new InternetAddress[1];
			recipientAddress[0] = new InternetAddress(meetingTask.getAssignTo().getEmailId());
			if (nonNull(emailId) && !emailId.isEmpty()) {
				recipientAddress = new InternetAddress[2];
				recipientAddress[1] = new InternetAddress(emailId);
			}
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
							+ meetingTask.getMeetings().getMeetingTitle() + " by "
							+ meetingTask.getMeetings().getLead().getContacts().stream().filter(Contacts::getPrimary)
							.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse("")
							+ "." + " The task is scheduled for completion by " + formatDate(meetingTask.getDueDate())
							+ " At " + meetingTask.getDueTime12Hours() + ".")
					.append("<br><br>").append("Regards,").append("<br>").append("Rabbit & Tortoise Technology.");
			msg.setContent(content.toString(), "text/html");
			send(msg);
		} catch (Exception e) {
			log.error("Got Exception while sending meeting task reminder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static void sendLeadTaskReminderMail(LeadTask leadTask, String emailId) {
		log.info("inside the sendLeadTaskReminderMail method...}");
		try {
			Message msg = new MimeMessage(getSession());

			InternetAddress[] recipientAddress = new InternetAddress[1];
			recipientAddress[0] = new InternetAddress(leadTask.getAssignTo().getEmailId());
			if (nonNull(emailId) && !emailId.isEmpty()) {
				recipientAddress = new InternetAddress[2];
				recipientAddress[1] = new InternetAddress(emailId);
			}
			msg.setFrom(new InternetAddress(USERNAME));
			msg.setRecipients(TO, recipientAddress);
			msg.setSubject("Task Reminder : " + leadTask.getSubject() + " - " + formatDate(leadTask.getDueDate()));
			msg.setSentDate(new Date());
			StringBuilder content = new StringBuilder().append("<br>")
					.append(String.format("%s",
							"Dear " + leadTask.getAssignTo().getFirstName() + " " + leadTask.getAssignTo().getLastName()
									+ ",<br><br>")
							+ "I hope this email finds you well. This is a friendly reminder about the Task for the "
							+ leadTask.getLead().getTopic() + " by "
							+ leadTask.getLead().getContacts().stream().filter(Contacts::getPrimary).findAny()
									.map(con -> con.getFirstName() + " " + con.getLastName()).toString()
							+ "." + " The task is scheduled for completion by " + formatDate(leadTask.getDueDate())
							+ " At " + leadTask.getDueTime12Hours() + ".")
					.append("<br><br>").append("Regards,").append("<br>").append("Rabbit & Tortoise Technology.");
			msg.setContent(content.toString(), "text/html");
			send(msg);
		} catch (Exception e) {
			log.error("Got Exception while sending lead task reminder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static void sendFollowUpLeadReminderMail(Leads leads, String emailId) {
		log.info("inside the sendFollowUpLeadReminderMail method...}");
		try {
			Message msg = new MimeMessage(getSession());
			InternetAddress[] recipientAddress = new InternetAddress[1];
			recipientAddress[0] = new InternetAddress(leads.getEmployee().getEmailId());
			if (nonNull(emailId) && !emailId.isEmpty()) {
				recipientAddress = new InternetAddress[2];
				recipientAddress[1] = new InternetAddress(emailId);
			}
			msg.setFrom(new InternetAddress(USERNAME));
			msg.setRecipients(TO, recipientAddress);
			msg.setSubject("Follow-Up Reminder : " + leads.getTopic() + " by "
					+ leads.getContacts().stream().filter(Contacts::getPrimary).findAny()
							.map(e -> e.getFirstName() + " " + e.getLastName()).toString()
					+ "-" + formatDate(leads.getRemainderDueOn()) + " At " + leads.getRemainderDueAt());
			msg.setSentDate(new Date());
			StringBuilder content = new StringBuilder().append("<br>").append(String.format("%s",
					"Dear " + leads.getEmployee().getFirstName() + " " + leads.getEmployee().getLastName()
							+ ",<br><br>")
					+ "I hope this email finds you well. This is a friendly reminder about the follow-up for the lead: "
					+ leads.getTopic() + " by "
					+ leads.getContacts().stream().filter(Contacts::getPrimary).findAny()
							.map(e -> e.getFirstName() + " " + e.getLastName()).toString()
					+ ".").append("<br><br>").append("Regards,").append("<br>").append("Rabbit & Tortoise Technology.");
			msg.setContent(content.toString(), "text/html");
			send(msg);
		} catch (Exception e) {
			log.error("Got Exception while sending follow up lead reminder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	private static String formatDate(Date date) {
		log.info("inside the formatDate method...{}", date);
		try {
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
			return outputFormat.format(date);
		} catch (Exception e) {
			log.error("Got Exception while converting task due date..{}" + e.getMessage());
			throw new CRMException(e);
		}
	}
}
