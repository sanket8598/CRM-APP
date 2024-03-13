package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class MeetingTaskDtoTest {

	@Test
	void testSetAndGetMeetingTaskData() {
		MeetingTaskDto meetingTaskDto = new MeetingTaskDto();
		MeetingTaskDto dto = new MeetingTaskDto();
		MeetingTaskDto dto1 = new MeetingTaskDto();
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
		dto1.setMeetingTaskId(1);
		dto1.setSubject("Discuss project status");
		dto1.setStatus("Pending");
		dto1.setPriority("High");
		dto1.setDueDate(LocalDate.of(2024, 3, 10));
		dto1.setDueTime("09:00 AM");
		dto1.setDescription("Discuss the progress of the project with the team");
		dto1.setRemainderOn(true);
		dto1.setRemainderVia("Email");
		dto1.setRemainderDueAt("08:30 AM");
		dto1.setRemainderDueOn(LocalDate.of(2024, 3, 9));
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
		meetingTaskDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
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
