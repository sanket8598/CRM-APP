package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class MeetingTaskDtoTest {

	MeetingTaskDto dto = new MeetingTaskDto();
	MeetingTaskDto dto1 = new MeetingTaskDto();

	@Test
	void testSetAndGetMeetingTaskData() {
		MeetingTaskDto meetingTaskDto = new MeetingTaskDto();
		meetingTaskDto.setMeetingTaskId(1);
		meetingTaskDto.setSubject("Discuss project status");
		meetingTaskDto.setStatus("Pending");
		meetingTaskDto.setPriority("High");
		meetingTaskDto.setDueDate(LocalDate.of(2024, 3, 10));
		meetingTaskDto.setDueTime("09:00 AM");
		meetingTaskDto.setDescription("Discuss the progress of the project with the team");
		meetingTaskDto.setRemainderOn(true);
		meetingTaskDto.setRemainderVia("Email");
		meetingTaskDto.setRemainderDueAt("08:30 AM");
		meetingTaskDto.setRemainderDueOn(LocalDate.of(2024, 3, 9));
		Integer meetingTaskId = meetingTaskDto.getMeetingTaskId();
		String subject = meetingTaskDto.getSubject();
		String status = meetingTaskDto.getStatus();
		String priority = meetingTaskDto.getPriority();
		LocalDate dueDate = meetingTaskDto.getDueDate();
		String dueTime = meetingTaskDto.getDueTime();
		String description = meetingTaskDto.getDescription();
		boolean remainderOn = meetingTaskDto.isRemainderOn();
		String remainderVia = meetingTaskDto.getRemainderVia();
		String remainderDueAt = meetingTaskDto.getRemainderDueAt();
		LocalDate remainderDueOn = meetingTaskDto.getRemainderDueOn();
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertNotNull(meetingTaskId);
		assertEquals(1, meetingTaskId);
		assertEquals("Discuss project status", subject);
		assertEquals("Pending", status);
		assertEquals("High", priority);
		assertEquals(LocalDate.of(2024, 3, 10), dueDate);
		assertEquals("09:00 AM", dueTime);
		assertEquals("Discuss the progress of the project with the team", description);
		assertEquals(true, remainderOn);
		assertEquals("Email", remainderVia);
		assertEquals("08:30 AM", remainderDueAt);
		assertEquals(LocalDate.of(2024, 3, 9), remainderDueOn);
	}

}
