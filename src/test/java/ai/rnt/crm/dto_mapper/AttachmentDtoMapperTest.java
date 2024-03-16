package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.AttachmentDto;
import ai.rnt.crm.entity.Attachment;

class AttachmentDtoMapperTest {

	@Test
	void testToAttachment() {
		AttachmentDto attachmentDto = new AttachmentDto();
		attachmentDto.setAttachmentData("test");
		attachmentDto.setAttachName("test.txt");

		Optional<Attachment> attachmentOptional = AttachmentDtoMapper.TO_ATTACHMENT.apply(attachmentDto);
		assertTrue(attachmentOptional.isPresent());
		Attachment attachment = attachmentOptional.get();
		assertEquals("test", attachment.getAttachmentData());
		assertEquals("test.txt", attachment.getAttachName());
	}

	@Test
	void testToAttachments() {
		List<AttachmentDto> attachmentDtos = new ArrayList<>();
		AttachmentDto attachmentDto1 = new AttachmentDto();
		attachmentDto1.setAttachmentData("test");
		attachmentDto1.setAttachName("file1.txt");
		attachmentDtos.add(attachmentDto1);

		AttachmentDto attachmentDto2 = new AttachmentDto();
		attachmentDto2.setAttachmentData("test");
		attachmentDto2.setAttachName("file2.txt");
		attachmentDtos.add(attachmentDto2);

		Collection<Attachment> attachments = AttachmentDtoMapper.TO_ATTACHMENTS.apply(attachmentDtos);
		assertEquals(2, attachments.size());
		assertTrue(attachments.stream().anyMatch(att -> att.getAttachName().equals("file1.txt")));
		assertTrue(attachments.stream().anyMatch(att -> att.getAttachName().equals("file2.txt")));
	}

	@Test
	void testToAttachmentDto() {
		Attachment attachment = new Attachment();
		attachment.setAttachmentData("test");
		attachment.setAttachName("test.txt");

		Optional<AttachmentDto> attachmentDtoOptional = AttachmentDtoMapper.TO_ATTACHMENT_DTO.apply(attachment);
		assertTrue(attachmentDtoOptional.isPresent());
		AttachmentDto attachmentDto = attachmentDtoOptional.get();
		assertEquals("test", attachmentDto.getAttachmentData());
		assertEquals("test.txt", attachmentDto.getAttachName());
	}

	@Test
	void testToAttachmentDtos() {
		List<Attachment> attachments = new ArrayList<>();
		Attachment attachment1 = new Attachment();
		attachment1.setAttachmentData("test");
		attachment1.setAttachName("file1.txt");
		attachments.add(attachment1);

		Attachment attachment2 = new Attachment();
		attachment2.setAttachmentData("test");
		attachment2.setAttachName("file2.txt");
		attachments.add(attachment2);

		List<AttachmentDto> attachmentDtos = AttachmentDtoMapper.TO_ATTACHMENT_DTOS.apply(attachments);
		assertEquals(2, attachmentDtos.size());
		assertTrue(attachmentDtos.stream().anyMatch(attDto -> attDto.getAttachName().equals("file1.txt")));
		assertTrue(attachmentDtos.stream().anyMatch(attDto -> attDto.getAttachName().equals("file2.txt")));
	}
}
