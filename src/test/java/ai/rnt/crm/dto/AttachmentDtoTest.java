package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AttachmentDtoTest {
	@Test
	void testCreateObject() {
		AttachmentDto attachmentDto = new AttachmentDto();
		assertNotNull(attachmentDto);
	}

	@Test
	void testSetAndGetFields() {
		AttachmentDto attachmentDto = new AttachmentDto();
		attachmentDto.setEmailAttchId(1);
		attachmentDto.setAttachmentData("asdfghjkjhgfd");
		attachmentDto.setAttachType("application/pdf");
		attachmentDto.setAttachName("myData");
		Integer emailAttchId = attachmentDto.getEmailAttchId();
		String attachmentData = attachmentDto.getAttachmentData();
		String attachType = attachmentDto.getAttachType();
		String attachName = attachmentDto.getAttachName();
		assertEquals(1, emailAttchId);
		assertEquals("asdfghjkjhgfd", attachmentData);
		assertEquals("application/pdf", attachType);
		assertEquals("myData", attachName);
	}

	@Test
	void testGetType() {
		AttachmentDto attachmentDto = new AttachmentDto();
		attachmentDto.setAttachType("OTHER");
		String type = attachmentDto.getType();
		assertEquals("OTHER", type);
	}
}
