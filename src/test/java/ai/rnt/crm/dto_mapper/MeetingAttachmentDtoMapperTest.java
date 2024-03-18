package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.MeetingAttachmentsDto;
import ai.rnt.crm.entity.MeetingAttachments;

class MeetingAttachmentDtoMapperTest {

	@Test
	void testToMeetingAttachment() {
		MeetingAttachmentsDto meetingAttachmentsDto = new MeetingAttachmentsDto();
		Optional<MeetingAttachments> meetingAttachmentsOptional = MeetingAttachmentDtoMapper.TO_METTING_ATTACHMENT
				.apply(meetingAttachmentsDto);
		assertNotNull(meetingAttachmentsOptional);
	}

	@Test
	void testToMeetingAttachments() {
		Collection<MeetingAttachmentsDto> meetingAttachmentsDtoCollection = new ArrayList<>();
		Collection<MeetingAttachments> meetingAttachmentsCollection = MeetingAttachmentDtoMapper.TO_METTING_ATTACHMENTS
				.apply(meetingAttachmentsDtoCollection);
		assertNotNull(meetingAttachmentsCollection);
	}

	@Test
	void testToMeetingAttachmentDto() {
		MeetingAttachments meetingAttachments = new MeetingAttachments();
		Optional<MeetingAttachmentsDto> meetingAttachmentsDtoOptional = MeetingAttachmentDtoMapper.TO_METTING_ATTACHMENT_DTO
				.apply(meetingAttachments);
		assertNotNull(meetingAttachmentsDtoOptional);
	}

	@Test
	void testToMeetingAttachmentDtos() {
		Collection<MeetingAttachments> meetingAttachmentsCollection = new ArrayList<>();
		List<MeetingAttachmentsDto> meetingAttachmentsDtoList = MeetingAttachmentDtoMapper.TO_METTING_ATTACHMENT_DTOS
				.apply(meetingAttachmentsCollection);
		assertNotNull(meetingAttachmentsDtoList);
	}
}
