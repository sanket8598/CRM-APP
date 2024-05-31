package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class EmailTest {

	Email email = new Email();

	@Test
	void getterTest() {
		email.getMailId();
		email.getMailFrom();
		email.getToMail();
		email.getBccMail();
		email.getCcMail();
		email.getSubject();
		email.getStatus();
		email.getContent();
		email.getScheduled();
		email.getScheduledOn();
		email.getScheduledAt();
		email.getIsOpportunity();
		email.getLead();
		email.getAttachment();
		assertNull(email.getMailFrom());
	}

	Leads lead = new Leads();
	List<Attachment> attachment = new ArrayList<>();

	@Test
	void setterTest() {
		email.setMailId(1);
		email.setMailFrom("nik.gaikwad@rnt.ai");
		email.setToMail("s.wakankar@rnt.ai");
		email.setBccMail("p.desai@rnt.ai");
		email.setCcMail("s.gurav@rnt.ai");
		email.setSubject("test");
		email.setStatus("Save");
		email.setContent("this is email");
		email.setScheduled(true);
		LocalDate localDate = LocalDate.of(2024, 1, 1);
		email.setScheduledOn(localDate);
		email.setScheduledAt("10:10");
		email.setIsOpportunity(false);
		email.setLead(lead);
		email.setAttachment(attachment);
		assertEquals(1, email.getMailId());

	}

	@Test
	void testGetScheduledAtTime12Hours() throws ParseException {
		email.setScheduledAt("10:30:00");
		String expected = "10:30 AM";
		String actual = email.getScheduledAtTime12Hours();
		assertEquals(expected.toLowerCase(), actual.toLowerCase());
		email.setScheduledAt(null);
		assertNull(email.getScheduledAtTime12Hours());
		email.setScheduledAt("invalid_time_format");
		assertEquals("invalid_time_format", email.getScheduledAtTime12Hours());
		email.setScheduledAt("10:30 AM");
		assertEquals("10:30 AM", email.getScheduledAtTime12Hours());
	}
}
