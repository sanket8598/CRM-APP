package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class TimeLineAndActivityDtoTest {

	TimeLineAndActivityDto dto = new TimeLineAndActivityDto();
	TimeLineAndActivityDto dto1 = new TimeLineAndActivityDto();

	@Test
	void testSetAndGetTimeLineAndActivityData() {
		TimeLineAndActivityDto timeLineAndActivityDto = new TimeLineAndActivityDto();
		timeLineAndActivityDto.setId(1);
		timeLineAndActivityDto.setType("Type");
		timeLineAndActivityDto.setSubject("Subject");
		timeLineAndActivityDto.setBody("Body");
		timeLineAndActivityDto.setShortName("ShortName");
		timeLineAndActivityDto.setCreatedOn("2024-03-04");
		timeLineAndActivityDto.setDueDate("2024-03-10");
		Integer id = timeLineAndActivityDto.getId();
		String type = timeLineAndActivityDto.getType();
		String subject = timeLineAndActivityDto.getSubject();
		String body = timeLineAndActivityDto.getBody();
		String shortName = timeLineAndActivityDto.getShortName();
		String createdOn = timeLineAndActivityDto.getCreatedOn();
		String dueDate = timeLineAndActivityDto.getDueDate();
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertNotNull(id);
		assertEquals(1, id);
		assertEquals("Type", type);
		assertEquals("Subject", subject);
		assertEquals("Body", body);
		assertEquals("ShortName", shortName);
		assertEquals("2024-03-04", createdOn);
		assertEquals("2024-03-10", dueDate);
	}
}
