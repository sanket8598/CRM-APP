package ai.rnt.crm.util;

import static java.util.Objects.nonNull;
import static javax.mail.Message.RecipientType.TO;
import static javax.mail.Transport.send;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.stereotype.Component;

import ai.rnt.crm.dto.MeetingAttachmentsDto;
import ai.rnt.crm.dto.MeetingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 21/12/2023
 * @version 1.0
 *
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MeetingUtil {

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

	public void scheduleMeetingInOutlook(MeetingDto dto) throws Exception {
		try {
			Message msg = new MimeMessage(getSession());
			msg.setFrom(new InternetAddress(USERNAME));
			((MimePart) msg).addHeaderLine("method=REQUEST");
			((MimePart) msg).addHeaderLine("charset=UTF-8");
			((MimePart) msg).addHeaderLine("component=VEVENT");

			String toMail = null;
			if (nonNull(dto.getParticipates())) {
				toMail = dto.getParticipates().stream().collect(Collectors.joining(","));
				msg.setRecipients(TO, InternetAddress.parse(toMail));
			}
			msg.setSubject(dto.getMeetingTitle());
			// msg.setHeader("Content-class", "urn:content-classes:calendarmessage");
			String content = getBufferString(dto);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
			messageBodyPart.setHeader("Content-ID", "calendar_message");
			messageBodyPart
					.setDataHandler(new DataHandler(new ByteArrayDataSource(content, "text/calendar;method=REQUEST")));
			MimeBodyPart bc = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			if (dto.getMeetingAttachments() != null && !dto.getMeetingAttachments().isEmpty()) {
				for (MeetingAttachmentsDto attachment : dto.getMeetingAttachments()) {
					bc = createAttachment(attachment);
					multipart.addBodyPart(bc);
				}
			}
			bc.setContent(dto.getDescription(), "text/html");
			// multipart.addBodyPart(bc);
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);
			send(msg);
		} catch (Exception ex) {
			log.error("exception occured while adding the meeting in outlook..{}", ex.getMessage());
		}
	}

	private String getBufferString(MeetingDto dto) {
		StringBuilder sb = new StringBuilder();
		String dtStamp = formatDate(dto.getStartDate());
		StringBuilder buffer = sb.append("BEGIN:VCALENDAR\n" + "PRODID: Asset View 2.0\n" + "VERSION:2.0\n"
				+ "METHOD:REQUEST\n" + "BEGIN:VEVENT\n" + "DTSTAMP:" + dtStamp + "\n" + extractDstartDend(dto) + "UID:"
				+ UUID.randomUUID().toString() + "\n" + "DESCRIPTION:" + dto.getDescription() + "\n"
				+ "X-ALT-DESC;FMTTYPE=text/html:" + dto.getDescription() + "\n" + "SUMMARY:" + dto.getMeetingTitle()
				+ "\n" + "ORGANIZER:MAILTO:" + USERNAME + "\n"
				+ String.format("ATTENDEE;CN=%s;RSVP=TRUE:mailto:%s",
						dto.getParticipates().stream().collect(Collectors.joining(",")),
						"\n" + "X-MICROSOFT-CDO-BUSYSTATUS:FREE" + "\n" + "BEGIN:VALARM\n" + "ACTION:DISPLAY\n"
								+ "DESCRIPTION:Reminder\n" + "TRIGGER:-P1D\n" + "END:VALARM\n" + "END:VEVENT\n"
								+ "END:VCALENDAR"));

		return buffer.toString();
	}

	private String extractDstartDend(MeetingDto dto) {
		StringBuilder res = new StringBuilder();
		if (dto.isAllDay()) {
			res.append("DTSTART;VALUE=DATE:" + formatDate(dto.getStartDate()) + "\n"); // Format DTSTART for all-day
			res.append("DTEND;VALUE=DATE:" + formatDate(dto.getEndDate()) + "\n");
		} else {
			res.append("DTSTART:" + formatDate(dto.getStartDate()) + "\n");
			res.append("DTEND:" + formatDate(dto.getEndDate()) + "\n");
		}
		return res.toString();
	}

	private static Session getSession() {
		return Session.getInstance(PROPERTIES, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
	}

	private MimeBodyPart createAttachment(MeetingAttachmentsDto attachment) throws MessagingException, IOException {
		MimeBodyPart attachmentPart = new MimeBodyPart();
		DataSource source = new ByteArrayDataSource(attachment.getMeetingAttachmentData(), "application/octet-stream");
		attachmentPart.setDataHandler(new DataHandler(source));
		attachmentPart.setFileName(attachment.getMeetingAttachName());
		return attachmentPart;
	}

	private String formatDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);
	}
}
