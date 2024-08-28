package ai.rnt.crm.util;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Base64.getDecoder;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Stream.of;
import static javax.mail.Part.ATTACHMENT;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import ai.rnt.crm.entity.Attachment;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.OpportunityTask;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.exception.CRMException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sanket Wakankar
 * @since 21/12/2023
 * @version 1.0
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailUtil extends PropertyUtil {

	private final JavaMailSender emailSender;

	private static final String THE_TASK_IS_SCHEDULED_FOR_COMPLETION_BY = " The task is scheduled for completion by ";
	private static final String I_HOPE_THIS_EMAIL_FINDS_YOU_WELL_THIS_IS_A_FRIENDLY_REMINDER_ABOUT_THE_TASK_FOR_THE = "I hope this email finds you well. This is a friendly reminder about the Task for the ";
	private static final String RABBIT_AND_TORTOISE_TECHNOLOGY = "Rabbit & Tortoise Technology.";
	private static final String COMMA_BR_BR = ",<br><br>";
	private static final String BR = "<br><br>";
	private static final String SINGLE_BR = "<br>";
	private static final String REGARDS = "Regards,";
	private static final String TASK_REMINDER = "Task Reminder : ";
	private static final String DEAR = "Dear ";

	public void emailWithAttachment(String[] to, String[] cc, String[] bcc, String subject, String content,
			List<Attachment> list) {
		log.info("inside the emailWithAttachment method...}");
		email(to, cc, bcc, subject, content, list);
	}

	public void emailWithoutAttachment(String[] to, String[] cc, String[] bcc, String subject, String content) {
		log.info("inside the emailWithoutAttachment method...}");
		email(to, cc, bcc, subject, content, null);
	}

	private void email(String[] to, String[] cc, String[] bcc, String subject, String content, List<Attachment> list) {
		log.info("inside the email method...}");
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, !(isNull(list) || list.isEmpty()));
			helper.setFrom(userName);
			helper.setTo(to);
			helper.setCc(cc);
			helper.setBcc(bcc);
			helper.setSubject(subject);
			helper.setSentDate(new Date());
			helper.setText(content, true);
			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(content, "text/html; charset=utf-8");
			multipart.addBodyPart(bodyPart);
			if (nonNull(list) && !list.isEmpty()) {
				for (Attachment data : list) {
					MimeBodyPart attachmentBodyPart = new MimeBodyPart();
					attachmentBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(
							getDecoder().decode(data.getAttachmentData().split(",")[1]), data.getAttachType())));
					attachmentBodyPart.setFileName(data.getAttachName());
					attachmentBodyPart.setDisposition(ATTACHMENT);
					multipart.addBodyPart(attachmentBodyPart);
				}
			}
			message.setContent(multipart);
			emailSender.send(message);
		} catch (MessagingException e) {
			log.error("Got Exception while sendingEmail the mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public boolean sendEmail(Email email) throws AddressException {
		log.info("inside the sendEmail method...");
		try {
			String[] recipientList = nonNull(email.getToMail())
					? of(email.getToMail().split(",")).map(String::trim).toArray(String[]::new)
					: new String[0];
			String[] ccAddresses = nonNull(email.getCcMail()) && !email.getCcMail().isEmpty()
					? of(email.getCcMail().split(",")).map(String::trim).toArray(String[]::new)
					: new String[0];
			String[] bccAddress = (nonNull(email.getBccMail()) && !email.getBccMail().isEmpty())
					? of(email.getBccMail().split(",")).map(String::trim).toArray(String[]::new)
					: new String[0];

			StringBuilder content = new StringBuilder().append("<br>").append(String.format("%s", email.getContent()))
					.append(BR).append(REGARDS).append("<br>").append(RABBIT_AND_TORTOISE_TECHNOLOGY);
			email(recipientList, ccAddresses, bccAddress, email.getSubject(), content.toString(),
					(email.getAttachment().isEmpty()) ? null : email.getAttachment());
			return true;
		} catch (Exception ex) {
			log.error("error occured while sending the email..{}", ex.getMessage());
			return false;
		}
	}

	public void sendCallTaskReminderMail(PhoneCallTask callTask, String emailId) {
		log.info("inside the sendCallTaskReminderMail method...}");
		try {
			String[] recipientAddress = new String[1];
			recipientAddress[0] = callTask.getAssignTo().getEmailId();
			if (nonNull(emailId) && !emailId.isEmpty()) {
				recipientAddress = new String[2];
				recipientAddress[0] = callTask.getAssignTo().getEmailId();
				recipientAddress[1] = emailId;
			}
			String leadName = callTask.getCall().getLead().getContacts().stream().filter(Contacts::getPrimary)
					.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse("");
			StringBuilder content = new StringBuilder().append("<br>")
					.append(format("%s",
							DEAR + callTask.getAssignTo().getFirstName() + " " + callTask.getAssignTo().getLastName()
									+ COMMA_BR_BR)
							+ "I hope this email finds you well. This is a friendly reminder about the Task  for the "
							+ callTask.getCall().getLead().getTopic() + " by " + leadName + "."
							+ THE_TASK_IS_SCHEDULED_FOR_COMPLETION_BY + formatDate(callTask.getDueDate()) + " At "
							+ callTask.getDueTime12Hours() + ".")
					.append(BR).append(REGARDS).append("<br>").append(RABBIT_AND_TORTOISE_TECHNOLOGY);
			emailWithoutAttachment(recipientAddress, new String[0], new String[0],
					TASK_REMINDER + callTask.getSubject() + " - " + formatDate(callTask.getDueDate()),
					content.toString());
		} catch (Exception e) {
			log.error("Got Exception while sending call task reminder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public void sendVisitTaskReminderMail(VisitTask visitTask, String emailId) {
		log.info("inside the sendVisitTaskReminderMail method...}");
		try {
			String[] recipientAddress = new String[1];
			recipientAddress[0] = visitTask.getAssignTo().getEmailId();
			if (nonNull(emailId) && !emailId.isEmpty()) {
				recipientAddress = new String[2];
				recipientAddress[0] = visitTask.getAssignTo().getEmailId();
				recipientAddress[1] = emailId;
			}
			String leadName = visitTask.getVisit().getLead().getContacts().stream().filter(Contacts::getPrimary)
					.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse("");
			StringBuilder content = new StringBuilder().append("<br>")
					.append(format("%s",
							DEAR + visitTask.getAssignTo().getFirstName() + " " + visitTask.getAssignTo().getLastName()
									+ COMMA_BR_BR)
							+ I_HOPE_THIS_EMAIL_FINDS_YOU_WELL_THIS_IS_A_FRIENDLY_REMINDER_ABOUT_THE_TASK_FOR_THE
							+ visitTask.getVisit().getSubject() + " by " + leadName + "."
							+ THE_TASK_IS_SCHEDULED_FOR_COMPLETION_BY + formatDate(visitTask.getDueDate()) + " At "
							+ visitTask.getDueTime12Hours() + ".")
					.append(BR).append(REGARDS).append("<br>").append(RABBIT_AND_TORTOISE_TECHNOLOGY);
			emailWithoutAttachment(recipientAddress, new String[0], new String[0],
					TASK_REMINDER + visitTask.getSubject() + " - " + formatDate(visitTask.getDueDate()),
					content.toString());
		} catch (Exception e) {
			log.error("Got Exception while sending visit task reminder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public void sendMeetingTaskReminderMail(MeetingTask meetingTask, String emailId) {
		log.info("inside the sendMeetingTaskReminderMail method...}");
		try {
			String[] recipientAddress = new String[1];
			recipientAddress[0] = meetingTask.getAssignTo().getEmailId();
			if (nonNull(emailId) && !emailId.isEmpty()) {
				recipientAddress = new String[2];
				recipientAddress[0] = meetingTask.getAssignTo().getEmailId();
				recipientAddress[1] = emailId;
			}
			String leadName = meetingTask.getMeetings().getLead().getContacts().stream().filter(Contacts::getPrimary)
					.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse("");
			StringBuilder content = new StringBuilder().append("<br>")
					.append(format("%s",
							DEAR + meetingTask.getAssignTo().getFirstName() + " "
									+ meetingTask.getAssignTo().getLastName() + COMMA_BR_BR)
							+ I_HOPE_THIS_EMAIL_FINDS_YOU_WELL_THIS_IS_A_FRIENDLY_REMINDER_ABOUT_THE_TASK_FOR_THE
							+ meetingTask.getMeetings().getMeetingTitle() + " by " + leadName + "."
							+ THE_TASK_IS_SCHEDULED_FOR_COMPLETION_BY + formatDate(meetingTask.getDueDate()) + " At "
							+ meetingTask.getDueTime12Hours() + ".")
					.append(BR).append(REGARDS).append("<br>").append(RABBIT_AND_TORTOISE_TECHNOLOGY);
			emailWithoutAttachment(recipientAddress, new String[0], new String[0],
					TASK_REMINDER + meetingTask.getSubject() + " - " + formatDate(meetingTask.getDueDate()),
					content.toString());
		} catch (Exception e) {
			log.error("Got Exception while sending meeting task reminder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public void sendLeadTaskReminderMail(LeadTask leadTask, String emailId) {
		log.info("inside the sendLeadTaskReminderMail method...{}", emailId);
		try {
			String[] recipientAddress = new String[1];
			recipientAddress[0] = leadTask.getAssignTo().getEmailId();
			if (nonNull(emailId) && !emailId.isEmpty()) {
				recipientAddress = new String[2];
				recipientAddress[0] = leadTask.getAssignTo().getEmailId();
				recipientAddress[1] = emailId;
			}
			String leadName = leadTask.getLead().getContacts().stream().filter(Contacts::getPrimary)
					.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse("");
			StringBuilder content = new StringBuilder().append("<br>")
					.append(format("%s",
							DEAR + leadTask.getAssignTo().getFirstName() + " " + leadTask.getAssignTo().getLastName()
									+ COMMA_BR_BR)
							+ I_HOPE_THIS_EMAIL_FINDS_YOU_WELL_THIS_IS_A_FRIENDLY_REMINDER_ABOUT_THE_TASK_FOR_THE
							+ leadTask.getLead().getTopic() + " by " + leadName + "."
							+ THE_TASK_IS_SCHEDULED_FOR_COMPLETION_BY + formatDate(leadTask.getDueDate()) + " At "
							+ leadTask.getDueTime12Hours() + ".")
					.append(BR).append(REGARDS).append("<br>").append(RABBIT_AND_TORTOISE_TECHNOLOGY);
			emailWithoutAttachment(recipientAddress, new String[0], new String[0],
					TASK_REMINDER + leadTask.getSubject() + " - " + formatDate(leadTask.getDueDate()),
					content.toString());
		} catch (Exception e) {
			log.error("Got Exception while sending lead task reminder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public void sendOptyTaskReminderMail(OpportunityTask optyTask, String emailId) {
		log.info("inside the sendOptyTaskReminderMail method...{}", emailId);
		try {
			String[] recipientAddress = new String[1];
			recipientAddress[0] = optyTask.getAssignTo().getEmailId();
			if (nonNull(emailId) && !emailId.isEmpty()) {
				recipientAddress = new String[2];
				recipientAddress[0] = optyTask.getAssignTo().getEmailId();
				recipientAddress[1] = emailId;
			}
			String optyName = optyTask.getOpportunity().getLeads().getContacts().stream().filter(Contacts::getPrimary)
					.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse("");
			StringBuilder content = new StringBuilder().append("<br>")
					.append(format("%s",
							DEAR + optyTask.getAssignTo().getFirstName() + " " + optyTask.getAssignTo().getLastName()
									+ COMMA_BR_BR)
							+ I_HOPE_THIS_EMAIL_FINDS_YOU_WELL_THIS_IS_A_FRIENDLY_REMINDER_ABOUT_THE_TASK_FOR_THE
							+ optyTask.getOpportunity().getTopic() + " by " + optyName + "."
							+ THE_TASK_IS_SCHEDULED_FOR_COMPLETION_BY + formatDate(optyTask.getDueDate()) + " At "
							+ optyTask.getDueTime12Hours() + ".")
					.append(BR).append(REGARDS).append("<br>").append(RABBIT_AND_TORTOISE_TECHNOLOGY);
			emailWithoutAttachment(recipientAddress, new String[0], new String[0],
					TASK_REMINDER + optyTask.getSubject() + " - " + formatDate(optyTask.getDueDate()),
					content.toString());
		} catch (Exception e) {
			log.error("Got Exception while sending opportunity task reminder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public void sendFollowUpLeadReminderMail(Leads leads, String emailId) {
		log.info("inside the sendFollowUpLeadReminderMail method...}");
		try {
			String[] recipientAddress = new String[1];
			recipientAddress[0] = leads.getEmployee().getEmailId();
			if (nonNull(emailId) && !emailId.isEmpty()) {
				recipientAddress = new String[2];
				recipientAddress[0] = leads.getEmployee().getEmailId();
				recipientAddress[1] = emailId;
			}
			String leadName = leads.getContacts().stream().filter(Contacts::getPrimary)
					.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse("");
			StringBuilder content = new StringBuilder().append("<br>").append(format("%s",
					DEAR + leads.getEmployee().getFirstName() + " " + leads.getEmployee().getLastName() + COMMA_BR_BR)
					+ "I hope this emailWithoutAttachment finds you well. This is a friendly reminder about the follow-up for the lead: "
					+ leads.getTopic() + " by " + leadName + ".").append(BR).append(REGARDS).append("<br>")
					.append(RABBIT_AND_TORTOISE_TECHNOLOGY);
			emailWithoutAttachment(recipientAddress, new String[0], new String[0],
					"Follow-Up Reminder : " + leads.getTopic() + " by " + leadName + "-"
							+ formatDate(leads.getRemainderDueOn()) + " At " + leads.getRemainderDueAt12Hours(),
					content.toString());
		} catch (Exception e) {
			log.error("Got Exception while sending follow up lead reminder mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static String formatDate(LocalDate date) {
		log.info("inside the formatDate method...{}", date);
		try {
			return ofPattern("dd-MMM-yyyy").format(date);
		} catch (Exception e) {
			log.error("Got Exception while converting task due date..{}" + e.getMessage());
			throw new CRMException(e);
		}
	}

	public boolean sendLeadAssignMail(Leads leads) {
		log.info("inside the sendLeadAssignMail method...}");
		try {
			if (nonNull(leads)) {
				String[] recipientAddress = new String[1];
				recipientAddress[0] = leads.getEmployee().getEmailId();
				String leadName = leads.getContacts().stream().filter(Contacts::getPrimary)
						.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse("");
				String primaryPhoneNumber = leads.getContacts().stream().filter(Contacts::getPrimary).findFirst()
						.map(Contacts::getContactNumberPrimary).orElse("No primary contact found.");
				StringBuilder content = new StringBuilder().append("<br>")
						.append(format("%s",
								DEAR + leads.getEmployee().getFirstName() + " " + leads.getEmployee().getLastName()
										+ COMMA_BR_BR)
								+ "A new lead has been assigned to you in CRM system:" + BR + "Lead Name : " + leadName
								+ "." + SINGLE_BR + "Contact Information : " + primaryPhoneNumber + SINGLE_BR
								+ "Source : " + leads.getLeadSourceMaster().getSourceName() + SINGLE_BR
								+ "Assigned By : " + leads.getAssignBy().getFirstName() + " "
								+ leads.getAssignBy().getLastName() + "." + SINGLE_BR + "Assigned Date : "
								+ leads.getAssignDate() + "." + BR + "Thank you for your attention to this lead.")
						.append(BR).append(REGARDS).append("<br>").append("RNT Sales Team");
				emailWithoutAttachment(recipientAddress, new String[0], new String[0],
						"New Lead Assigned : " + leadName, content.toString());
			}
			return true;
		} catch (Exception ex) {
			log.error("Got Exception while sending lead assigned reminder mail..{}", ex.getMessage());
			return false;
		}
	}

	public boolean sendOptyAssignMail(Opportunity opportunity) {
		log.info("inside the sendOptyAssignMail method...}");
		try {
			if (nonNull(opportunity)) {
				String[] recipientAddress = new String[1];
				recipientAddress[0] = opportunity.getEmployee().getEmailId();
				String optyName = opportunity.getLeads().getContacts().stream().filter(Contacts::getPrimary)
						.map(con -> con.getFirstName() + " " + con.getLastName()).findFirst().orElse("");
				String primaryPhoneNumber = opportunity.getLeads().getContacts().stream().filter(Contacts::getPrimary)
						.findFirst().map(Contacts::getContactNumberPrimary).orElse("No primary contact found.");
				StringBuilder content = new StringBuilder().append("<br>")
						.append(format("%s",
								DEAR + opportunity.getEmployee().getFirstName() + " "
										+ opportunity.getEmployee().getLastName() + COMMA_BR_BR)
								+ "A new opportunity has been assigned to you in CRM system:" + BR
								+ "Opportunity Name : " + optyName + "." + SINGLE_BR + "Contact Information : "
								+ primaryPhoneNumber + SINGLE_BR + "Source : "
								+ opportunity.getLeads().getLeadSourceMaster().getSourceName() + SINGLE_BR
								+ "Assigned By : " + opportunity.getAssignBy().getFirstName() + " "
								+ opportunity.getAssignBy().getLastName() + "." + SINGLE_BR + "Assigned Date : "
								+ opportunity.getAssignDate() + "." + BR
								+ "Thank you for your attention to this opportunity.")
						.append(BR).append(REGARDS).append("<br>").append("RNT Sales Team");
				emailWithoutAttachment(recipientAddress, new String[0], new String[0],
						"New Opportunity Assigned : " + optyName, content.toString());
			}
			return true;
		} catch (Exception ex) {
			log.error("Got Exception while sending opportunity assigned reminder mail..{}", ex.getMessage());
			return false;
		}
	}
}
