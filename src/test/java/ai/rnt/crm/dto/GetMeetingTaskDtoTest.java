package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class GetMeetingTaskDtoTest {

	@Test
	void testGetMeetingTaskDto() {
		GetMeetingTaskDto meetingTaskDto = new GetMeetingTaskDto();
		GetMeetingTaskDto dto = new GetMeetingTaskDto();
		GetMeetingTaskDto dto1 = new GetMeetingTaskDto();
		Integer meetingTaskId = 1;
		String subject = "Prepare presentation";
		String description = "Create slides for the upcoming meeting.";
		String status = "Pending";
		String priority = "High";
		LocalDate dueDate = LocalDate.of(2024, 3, 5);
		String dueTime = "09:00 AM";
		LocalDate updateDueDate = LocalDate.of(2024, 3, 4);
		String dueTime12Hours = "09:00 AM";
		boolean remainderOn = true;
		String remainderVia = "Email";
		String remainderDueAt = "08:30 AM";
		LocalDate remainderDueOn = LocalDate.of(2024, 3, 5);
		LocalDate updatedRemainderDueOn = LocalDate.of(2024, 3, 4);
		meetingTaskDto.setMeetingTaskId(meetingTaskId);
		meetingTaskDto.setSubject(subject);
		meetingTaskDto.setDescription(description);
		meetingTaskDto.setStatus(status);
		meetingTaskDto.setPriority(priority);
		meetingTaskDto.setDueDate(dueDate);
		meetingTaskDto.setDueTime(dueTime);
		meetingTaskDto.setUpdateDueDate(updateDueDate);
		meetingTaskDto.setDueTime12Hours(dueTime12Hours);
		meetingTaskDto.setRemainderOn(remainderOn);
		meetingTaskDto.setRemainderVia(remainderVia);
		meetingTaskDto.setRemainderDueAt(remainderDueAt);
		meetingTaskDto.setRemainderDueOn(remainderDueOn);
		meetingTaskDto.setUpdatedRemainderDueOn(updatedRemainderDueOn);
		dto1.setMeetingTaskId(meetingTaskId);
		dto1.setSubject(subject);
		dto1.setDescription(description);
		dto1.setStatus(status);
		dto1.setPriority(priority);
		dto1.setDueDate(dueDate);
		dto1.setDueTime(dueTime);
		dto1.setUpdateDueDate(updateDueDate);
		dto1.setDueTime12Hours(dueTime12Hours);
		dto1.setRemainderOn(remainderOn);
		dto1.setRemainderVia(remainderVia);
		dto1.setRemainderDueAt(remainderDueAt);
		dto1.setRemainderDueOn(remainderDueOn);
		dto1.setUpdatedRemainderDueOn(updatedRemainderDueOn);
		meetingTaskDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertEquals(meetingTaskId, meetingTaskDto.getMeetingTaskId());
		assertEquals(subject, meetingTaskDto.getSubject());
		assertEquals(description, meetingTaskDto.getDescription());
		assertEquals(status, meetingTaskDto.getStatus());
		assertEquals(priority, meetingTaskDto.getPriority());
		assertEquals(dueDate, meetingTaskDto.getDueDate());
		assertEquals(dueTime, meetingTaskDto.getDueTime());
		assertEquals(updateDueDate, meetingTaskDto.getUpdateDueDate());
		assertEquals(dueTime12Hours, meetingTaskDto.getDueTime12Hours());
		assertEquals(remainderOn, meetingTaskDto.isRemainderOn());
		assertEquals(remainderVia, meetingTaskDto.getRemainderVia());
		assertEquals(remainderDueAt, meetingTaskDto.getRemainderDueAt());
		assertEquals(remainderDueOn, meetingTaskDto.getRemainderDueOn());
		assertEquals(updatedRemainderDueOn, meetingTaskDto.getUpdatedRemainderDueOn());
	}

	@Test
	void testGetMeetingTaskDueDate() {
		GetMeetingTaskDto meetingTaskDto = new GetMeetingTaskDto();
		LocalDate dueDate = LocalDate.of(2024, 3, 5);
		String dueTime12Hours = "09:00 AM";
		meetingTaskDto.setDueDate(dueDate);
		meetingTaskDto.setDueTime12Hours(dueTime12Hours);
		String meetingTaskDueDate = meetingTaskDto.getMeetingTaskDueDate();
		assertEquals("05-Mar-2024 09:00 AM", meetingTaskDueDate);
	}
}
