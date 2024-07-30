package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EditVisitDtoTest {

	@Test
	void testEditVisitDto() {
		EditVisitDto editVisitDto = new EditVisitDto();
		EditVisitDto dto = new EditVisitDto();
		EditVisitDto dto1 = new EditVisitDto();
		Integer id = 1;
		String location = "Office";
		String subject = "Client Meeting";
		String body = "Discussion about new project";
		String type = "External";
		String shortName = "JD";
		String dueDate = "2024-03-05";
		String status = "Scheduled";
		Integer parentId =1;
		 String activityFrom = "Lead";
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
		editVisitDto.setParentId(parentId);
		editVisitDto.setActivityFrom(activityFrom);
		
		dto1.setId(id);
		dto1.setParentId(parentId);
		dto1.setActivityFrom(activityFrom);
		dto1.setLocation(location);
		dto1.setSubject(subject);
		dto1.setBody(body);
		dto1.setType(type);
		dto1.setShortName(shortName);
		dto1.setDueDate(dueDate);
		dto1.setStatus(status);
		dto1.setAssignTo(assignTo);
		dto.canEqual(dto);
		editVisitDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
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
		assertEquals(parentId, editVisitDto.getParentId());
		assertEquals(activityFrom, editVisitDto.getActivityFrom());
	}
}
