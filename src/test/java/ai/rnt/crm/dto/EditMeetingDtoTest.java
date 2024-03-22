package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
		editMeetingDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
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
	
	@Test
     void testEqualityWithDifferentSuperclassFields() {
        EditMeetingDto dto1 = new EditMeetingDto();
        dto1.setId(1);
        dto1.setSubject("Subject");
        dto1.setBody("Body");
        dto1.setType("Type");
        dto1.setShortName("ShortName");
        dto1.setDueDate("2024-03-22");
        dto1.setStatus("Pending");
        dto1.setAssignTo(10);
        dto1.setAttachments(new ArrayList<>());
        dto1.setCreatedOn("2024-03-22");
        dto1.setWaitTwoDays(false);
        dto1.setOverDue(false);
        
        EditMeetingDto dto2 = new EditMeetingDto();
        dto2.setId(1);
        dto2.setSubject("Subject");
        dto2.setBody("Body");
        dto2.setType("Type");
        dto2.setShortName("ShortName");
        dto2.setDueDate("2024-03-22");
        dto2.setStatus("Pending");
        dto2.setAssignTo(10);
        dto2.setAttachments(new ArrayList<>());
        dto2.setCreatedOn("2024-03-22");
        dto2.setWaitTwoDays(true);
        dto2.setOverDue(false);
        
        dto1.getCreatedOn();
        dto1.isWaitTwoDays();
        dto1.getOverDue();
        dto1.equals(dto2);
        assertEquals(dto1, dto2, "The two objects should be considered equal despite differences in superclass fields.");
        assertEquals(dto1.hashCode(), dto2.hashCode(), "Hash codes should be equal for equal objects.");
    }

    @Test
    void testInequalityBasedOnOwnFields() {
        EditMeetingDto dto1 = new EditMeetingDto();
        dto1.setId(1);
        dto1.setSubject("Subject 1");

        EditMeetingDto dto2 = new EditMeetingDto();
        dto2.setId(2);
        dto2.setSubject("Subject 2");
        assertNotEquals(dto1, dto2, "The two objects should not be considered equal because of differences in their own fields.");
    }

}
