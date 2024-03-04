package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class EditEmailDtoTest {

	EditEmailDto dto = new EditEmailDto();
	EditEmailDto dto1 = new EditEmailDto();

	@Test
	void testEditEmailDto() {
		EditEmailDto editEmailDto = new EditEmailDto();
		Integer id = 1;
		String type = "Outgoing";
		String subject = "Project Updates";
		String body = "Please find attached project updates.";
		List<AttachmentDto> attachments = new ArrayList<>();
		attachments.add(new AttachmentDto());
		attachments.add(new AttachmentDto());
		String shortName = "JD";
		String status = "Pending";
		String scheduledDate = "2024-03-05";
		Integer assignTo = 2;
		editEmailDto.setId(id);
		editEmailDto.setType(type);
		editEmailDto.setSubject(subject);
		editEmailDto.setBody(body);
		editEmailDto.setAttachments(attachments);
		editEmailDto.setShortName(shortName);
		editEmailDto.setStatus(status);
		editEmailDto.setScheduledDate(scheduledDate);
		editEmailDto.setAssignTo(assignTo);
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(id, editEmailDto.getId());
		assertEquals(type, editEmailDto.getType());
		assertEquals(subject, editEmailDto.getSubject());
		assertEquals(body, editEmailDto.getBody());
		assertEquals(attachments, editEmailDto.getAttachments());
		assertEquals(shortName, editEmailDto.getShortName());
		assertEquals(status, editEmailDto.getStatus());
		assertEquals(scheduledDate, editEmailDto.getScheduledDate());
		assertEquals(assignTo, editEmailDto.getAssignTo());
	}

}
