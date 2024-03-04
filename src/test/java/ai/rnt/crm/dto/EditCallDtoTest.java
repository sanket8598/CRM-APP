package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EditCallDtoTest {

	EditCallDto dto = new EditCallDto();
	EditCallDto dto1 = new EditCallDto();

	@Test
	void testEditCallDto() {
		EditCallDto editCallDto = new EditCallDto();
		Integer id = 1;
		String type = "Incoming";
		String subject = "Meeting";
		String body = "Discuss project updates";
		String shortName = "JD";
		String callFrom = "John";
		String callTo = "Jane";
		String direction = "Inbound";
		String phoneNo = "1234567890";
		String comment = "No comments";
		String duration = "30 minutes";
		String dueDate = "2024-03-05";
		String status = "Completed";
		Integer assignTo = 2;
		editCallDto.setId(id);
		editCallDto.setType(type);
		editCallDto.setSubject(subject);
		editCallDto.setBody(body);
		editCallDto.setShortName(shortName);
		editCallDto.setCallFrom(callFrom);
		editCallDto.setCallTo(callTo);
		editCallDto.setDirection(direction);
		editCallDto.setPhoneNo(phoneNo);
		editCallDto.setComment(comment);
		editCallDto.setDuration(duration);
		editCallDto.setDueDate(dueDate);
		editCallDto.setStatus(status);
		editCallDto.setAssignTo(assignTo);
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(id, editCallDto.getId());
		assertEquals(type, editCallDto.getType());
		assertEquals(subject, editCallDto.getSubject());
		assertEquals(body, editCallDto.getBody());
		assertEquals(shortName, editCallDto.getShortName());
		assertEquals(callFrom, editCallDto.getCallFrom());
		assertEquals(callTo, editCallDto.getCallTo());
		assertEquals(direction, editCallDto.getDirection());
		assertEquals(phoneNo, editCallDto.getPhoneNo());
		assertEquals(comment, editCallDto.getComment());
		assertEquals(duration, editCallDto.getDuration());
		assertEquals(dueDate, editCallDto.getDueDate());
		assertEquals(status, editCallDto.getStatus());
		assertEquals(assignTo, editCallDto.getAssignTo());
	}
}
