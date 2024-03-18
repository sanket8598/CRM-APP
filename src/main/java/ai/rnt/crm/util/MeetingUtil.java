package ai.rnt.crm.util;

import static java.lang.String.format;
import static java.time.LocalDateTime.ofInstant;
import static java.time.LocalTime.parse;
import static java.time.ZoneId.of;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.US;
import static java.util.Objects.nonNull;
import static java.util.TimeZone.getTimeZone;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.joining;
import static javax.mail.Message.RecipientType.TO;
import static javax.mail.Transport.send;
import static javax.mail.internet.InternetAddress.parse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Properties;
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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:confidential.properties")
public class MeetingUtil {

	@Value("${email.userName}")
	private String userName;

	@Value("${email.password}")
	private String password;

	private static final Properties PROPERTIES = new Properties();
	private static final String HOST = "smtp.zoho.com";

	static {
		PROPERTIES.put("mail.smtp.host", HOST);
		PROPERTIES.put("mail.smtp.port", "587");
		PROPERTIES.put("mail.smtp.auth", true);
		PROPERTIES.put("mail.smtp.starttls.enable", true);
	}

	public void scheduleMeetingInOutlook(MeetingDto dto) throws Exception {
		log.info("inside the scheduleMeetingInOutlook method...{}");
		try {
			Message msg = new MimeMessage(getSession());
			msg.setFrom(new InternetAddress(userName));
			((MimePart) msg).addHeaderLine("method=REQUEST");
			((MimePart) msg).addHeaderLine("charset=UTF-8");
			((MimePart) msg).addHeaderLine("component=VEVENT");

			String toMail = null;
			if (nonNull(dto.getParticipates())) {
				toMail = dto.getParticipates().stream().collect(Collectors.joining(","));
				msg.setRecipients(TO, parse(toMail));
			}
			msg.setSubject(dto.getMeetingTitle());
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
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);
			send(msg);
		} catch (Exception ex) {
			log.error("exception occured while adding the meeting in outlook..{}", ex.getMessage());
		}
	}

	public String getBufferString(MeetingDto dto) {
		log.info("inside the meetingUtil getBufferString method...{}");
		StringBuilder sb = new StringBuilder();
		String dtStamp = formatDate(dto.getStartDate());
		StringBuilder buffer = sb.append("BEGIN:VCALENDAR\n" + "PRODID: Asset View 2.0\n" + "VERSION:2.0\n"
				+ "METHOD:REQUEST\n" + "BEGIN:VEVENT\n" + "DTSTAMP:" + dtStamp + "\n" + extractDstartDend(dto) + "UID:"
				+ randomUUID().toString() + "\n" + "DESCRIPTION:" + dto.getDescription() + "\n"
				+ "X-ALT-DESC;FMTTYPE=text/html:" + dto.getDescription() + "\n" + "SUMMARY:" + dto.getMeetingTitle()
				+ "\n" + "ORGANIZER:MAILTO:" + userName + "\n"
				+ format("ATTENDEE;CN=%s;RSVP=TRUE:mailto:%s", dto.getParticipates().stream().collect(joining(",")),
						"\n" + "X-MICROSOFT-CDO-BUSYSTATUS:FREE" + "\n" + "BEGIN:VALARM\n" + "ACTION:DISPLAY\n"
								+ "DESCRIPTION:Reminder\n" + "TRIGGER:-P1D\n" + "END:VALARM\n" + "END:VEVENT\n"
								+ "END:VCALENDAR"));

		return buffer.toString();
	}

	public String extractDstartDend(MeetingDto dto) {
		log.info("inside the meetingUtil extractDstartDend method...{}");
		StringBuilder res = new StringBuilder();
		if (dto.isAllDay()) {
			res.append("DTSTART;VALUE=DATE:" + formatDate(dto.getStartDate()) + "\n"); // Format DTSTART for all-day
			res.append("DTEND;VALUE=DATE:" + formatDate(dto.getEndDate()) + "\n");
		} else {
			res.append("DTSTART:" + formatDateTime(dto.getStartDate(), dto.getStartTime()) + "\n");
			res.append("DTEND:" + formatDateTime(dto.getEndDate(), dto.getEndTime()) + "\n");
		}
		return res.toString();
	}

	private Session getSession() {
		return Session.getInstance(PROPERTIES, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
	}

	private MimeBodyPart createAttachment(MeetingAttachmentsDto attachment) throws MessagingException, IOException {
		log.info("inside the meetingUtil createAttachment method...");
		MimeBodyPart attachmentPart = new MimeBodyPart();
		DataSource source = new ByteArrayDataSource(attachment.getMeetingAttachmentData(), "application/octet-stream");
		attachmentPart.setDataHandler(new DataHandler(source));
		attachmentPart.setFileName(attachment.getMeetingAttachName());
		return attachmentPart;
	}

	public String formatDate(Date date) {
		log.info("inside the meetingUtil formatDate method...{}", date);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		dateFormat.setTimeZone(getTimeZone("UTC+05:30"));
		return dateFormat.format(date);
	}

	public String formatDateTime(Date date, String time) {
		log.info("inside the meetingUtil formatDateTime method...{}", date);
		return ofInstant(date.toInstant(), of("UTC+05:30")).toLocalDate().atTime(parseTime(time))
				.format(ofPattern("yyyyMMdd'T'HHmmss"));
	}

	public LocalTime parseTime(String time) {
		log.info("inside the meetingUtil parseTime method...{}", time);
		return parse(time, ofPattern("HH:mm", US));
	}
}
