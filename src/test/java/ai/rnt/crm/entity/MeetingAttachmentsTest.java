package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MeetingAttachmentsTest {

	@Test
	void testMeetingAttachmentsObject() {
		MeetingAttachments attachment = new MeetingAttachments();
		attachment.setMeetingAttchId(1);
		attachment.setMeetingAttachmentData("Sample attachment data");
		attachment.setMeetingAttachType("img");
		attachment.setMeetingAttachName("myData");
		Meetings meeting = new Meetings();
		meeting.setMeetingId(1);
		attachment.setMeetings(meeting);
		assertEquals(1, attachment.getMeetingAttchId());
		assertEquals("Sample attachment data", attachment.getMeetingAttachmentData());
		assertEquals("img", attachment.getMeetingAttachType());
		assertEquals("myData", attachment.getMeetingAttachName());
		assertEquals(meeting, attachment.getMeetings());
	}
}
