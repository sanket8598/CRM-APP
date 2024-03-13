package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MeetingAttachmentsDtoTest {

	@Test
	void testCreateObject() {
		MeetingAttachmentsDto dto = new MeetingAttachmentsDto();
		MeetingAttachmentsDto dto1 = new MeetingAttachmentsDto();
		dto.setMeetingAttchId(1);
		dto1.setMeetingAttchId(1);
		dto.setMeetingAttachmentData("qwerthjfv");
		dto1.setMeetingAttachmentData("qwerthjfv");
		dto.setMeetingAttachType("image/jpeg");
		dto1.setMeetingAttachType("image/jpeg");
		dto.setMeetingAttachName("Mydata");
		dto1.setMeetingAttachName("Mydata");
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(1, dto.getMeetingAttchId());
		assertEquals("qwerthjfv", dto.getMeetingAttachmentData());
		assertEquals("image/jpeg", dto.getMeetingAttachType());
		assertEquals("Mydata", dto.getMeetingAttachName());
	}

	@Test
	void testGetTypeNotNull() {
		MeetingAttachmentsDto dto = new MeetingAttachmentsDto();
		dto.setMeetingAttachType("image/jpeg");
		assertEquals("IMAGE", dto.getType());
	}

	@Test
	void testGetTypeNull() {
		MeetingAttachmentsDto dto = new MeetingAttachmentsDto();
		dto.setMeetingAttachType(null);
		assertEquals("OTHER", dto.getType());
	}
}
