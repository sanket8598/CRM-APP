package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class MeetingDtoTest {

	@Test
	void testCreateObject() {
		MeetingDto dto = new MeetingDto();
		MeetingDto dto1 = new MeetingDto();
		dto.setMeetingId(1);
		dto1.setMeetingId(1);
		dto.setMeetingTitle("Meeting Title");
		dto1.setMeetingTitle("Meeting Title");
		dto.setParticipates(Arrays.asList("Participant 1", "Participant 2"));
		dto1.setParticipates(Arrays.asList("Participant 1", "Participant 2"));
		dto.setStartDate(new Date());
		dto1.setStartDate(new Date());
		dto.setEndDate(new Date());
		dto1.setEndDate(new Date());
		dto.setStartTime("09:00 AM");
		dto1.setStartTime("09:00 AM");
		dto.setDuration("1 hour");
		dto1.setDuration("1 hour");
		dto.setEndTime("10:00 AM");
		dto1.setEndTime("10:00 AM");
		dto.setAllDay(false);
		dto1.setAllDay(false);
		dto.setLocation("Meeting Room");
		dto1.setLocation("Meeting Room");
		dto.setDescription("Meeting Description");
		dto1.setDescription("Meeting Description");
		dto.setMeetingMode("Online");
		dto1.setMeetingMode("Online");
		dto.setIsOpportunity(false);
		dto1.setIsOpportunity(false);
		MeetingAttachmentsDto attachment1 = new MeetingAttachmentsDto();
		attachment1.setMeetingAttchId(1);
		attachment1.setMeetingAttachName("Attachment 1");
		MeetingAttachmentsDto attachment2 = new MeetingAttachmentsDto();
		attachment2.setMeetingAttchId(2);
		attachment2.setMeetingAttachName("Attachment 2");
		List<MeetingAttachmentsDto> attachments = new ArrayList<>();
		attachments.add(attachment1);
		attachments.add(attachment2);
		dto.setMeetingAttachments(attachments);
		dto1.setMeetingAttachments(attachments);
		dto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertEquals(1, dto.getMeetingId());
		assertEquals("Meeting Title", dto.getMeetingTitle());
		assertNotNull(dto.getParticipates());
		assertEquals(2, dto.getParticipates().size());
		assertEquals("Participant 1", dto.getParticipates().get(0));
		assertEquals("Participant 2", dto.getParticipates().get(1));
		assertNotNull(dto.getStartDate());
		assertNotNull(dto.getEndDate());
		assertEquals("09:00 AM", dto.getStartTime());
		assertEquals("1 hour", dto.getDuration());
		assertEquals("10:00 AM", dto.getEndTime());
		assertEquals(false, dto.isAllDay());
		assertEquals("Meeting Room", dto.getLocation());
		assertEquals("Meeting Description", dto.getDescription());
		assertEquals("Online", dto.getMeetingMode());
		assertEquals(false, dto.getIsOpportunity());
		List<MeetingAttachmentsDto> retrievedAttachments = dto.getMeetingAttachments();
		assertNotNull(retrievedAttachments);
		assertEquals(2, retrievedAttachments.size());
		assertEquals("Attachment 1", retrievedAttachments.get(0).getMeetingAttachName());
		assertEquals("Attachment 2", retrievedAttachments.get(1).getMeetingAttachName());
	}
}
