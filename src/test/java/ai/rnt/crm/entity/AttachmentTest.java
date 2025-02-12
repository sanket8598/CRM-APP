package ai.rnt.crm.entity;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AttachmentTest {

	Attachment attachment = new Attachment();

	@Test
	void getterTest() {
		attachment.getEmailAttchId();
		attachment.getAttachmentData();
		attachment.getAttachType();
		attachment.getAttachName();
		attachment.getMail();
		assertNull(attachment.getEmailAttchId());
	}

	Email email = new Email();

	@Test
	void setterTest() {
		attachment.setEmailAttchId(1);
		attachment.setAttachmentData("wertyuiosdfghjkcvbu");
		attachment.setAttachType("pdf");
		attachment.setAttachName("test");
		attachment.setMail(email);
		assertEquals(1, attachment.getEmailAttchId());
	}
}
