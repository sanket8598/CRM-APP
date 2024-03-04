package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EditVisitDtoTest {

	EditVisitDto dto = new EditVisitDto();
	EditVisitDto dto1 = new EditVisitDto();

	@Test
	void testEditVisitDto() {
		EditVisitDto editVisitDto = new EditVisitDto();
		Integer id = 1;
		String location = "Office";
		String subject = "Client Meeting";
		String body = "Discussion about new project";
		String type = "External";
		String shortName = "JD";
		String dueDate = "2024-03-05";
		String status = "Scheduled";
		Integer assignTo = 2;
		editVisitDto.setId(id);
		editVisitDto.setLocation(location);
		editVisitDto.setSubject(subject);
		editVisitDto.setBody(body);
		editVisitDto.setType(type);
		editVisitDto.setShortName(shortName);
		editVisitDto.setDueDate(dueDate);
		editVisitDto.setStatus(status);
		editVisitDto.setAssignTo(assignTo);
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(id, editVisitDto.getId());
		assertEquals(location, editVisitDto.getLocation());
		assertEquals(subject, editVisitDto.getSubject());
		assertEquals(body, editVisitDto.getBody());
		assertEquals(type, editVisitDto.getType());
		assertEquals(shortName, editVisitDto.getShortName());
		assertEquals(dueDate, editVisitDto.getDueDate());
		assertEquals(status, editVisitDto.getStatus());
		assertEquals(assignTo, editVisitDto.getAssignTo());
	}
}
