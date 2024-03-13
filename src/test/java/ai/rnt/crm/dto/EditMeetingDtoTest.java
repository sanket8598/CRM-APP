package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class EditMeetingDtoTest {

	@Test
	void testEditMeetingDto() {
		EditMeetingDto editMeetingDto = new EditMeetingDto();
		EditMeetingDto dto = new EditMeetingDto();
		EditMeetingDto dto1 = new EditMeetingDto();

		Integer id = 1;
		String subject = "Project Review";
		String body = "Review progress of ongoing projects";
		String type = "Internal";
		String shortName = "JD";
		String dueDate = "2024-03-05";
		String status = "Scheduled";
		Integer assignTo = 2;
		List<MeetingAttachmentsDto> attachments = new ArrayList<>();
		attachments.add(new MeetingAttachmentsDto());
		attachments.add(new MeetingAttachmentsDto());
		editMeetingDto.setId(id);
		editMeetingDto.setSubject(subject);
		editMeetingDto.setBody(body);
		editMeetingDto.setType(type);
		editMeetingDto.setShortName(shortName);
		editMeetingDto.setDueDate(dueDate);
		editMeetingDto.setStatus(status);
		editMeetingDto.setAssignTo(assignTo);
		editMeetingDto.setAttachments(attachments);
		dto1.setId(id);
		dto1.setSubject(subject);
		dto1.setBody(body);
		dto1.setType(type);
		dto1.setShortName(shortName);
		dto1.setDueDate(dueDate);
		dto1.setStatus(status);
		dto1.setAssignTo(assignTo);
		dto1.setAttachments(attachments);
		dto.canEqual(dto);
		editMeetingDto.equals(dto1);
		dto.hashCode();
		editMeetingDto.hashCode();
		dto.toString();
		assertEquals(id, editMeetingDto.getId());
		assertEquals(subject, editMeetingDto.getSubject());
		assertEquals(body, editMeetingDto.getBody());
		assertEquals(type, editMeetingDto.getType());
		assertEquals(shortName, editMeetingDto.getShortName());
		assertEquals(dueDate, editMeetingDto.getDueDate());
		assertEquals(status, editMeetingDto.getStatus());
		assertEquals(assignTo, editMeetingDto.getAssignTo());
		assertEquals(attachments, editMeetingDto.getAttachments());
	}

}
