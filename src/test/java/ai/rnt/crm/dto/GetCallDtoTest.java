package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class GetCallDtoTest {

	GetCallDto dto = new GetCallDto();
	GetCallDto dto1 = new GetCallDto();

	@Test
	void testGetCallDto() {
		GetCallDto getCallDto = new GetCallDto();
		Integer callId = 1;
		String subject = "Client Meeting";
		EmployeeDto callFrom = new EmployeeDto();
		String callTo = "John Doe";
		String direction = "Outbound";
		String phoneNo = "1234567890";
		String comment = "No comments";
		String duration = "30 minutes";
		Date startDate = new Date();
		Date endDate = new Date();
		String startTime = "09:00";
		String endTime = "09:30";
		String startTime12Hours = "09:00 AM";
		String endTime12Hours = "09:30 AM";
		boolean allDay = false;
		List<GetCallTaskDto> callTasks = new ArrayList<>();
		getCallDto.setCallId(callId);
		getCallDto.setSubject(subject);
		getCallDto.setCallFrom(callFrom);
		getCallDto.setCallTo(callTo);
		getCallDto.setDirection(direction);
		getCallDto.setPhoneNo(phoneNo);
		getCallDto.setComment(comment);
		getCallDto.setDuration(duration);
		getCallDto.setStartDate(startDate);
		getCallDto.setEndDate(endDate);
		getCallDto.setStartTime(startTime);
		getCallDto.setEndTime(endTime);
		getCallDto.setStartTime12Hours(startTime12Hours);
		getCallDto.setEndTime12Hours(endTime12Hours);
		getCallDto.setAllDay(allDay);
		getCallDto.setCallTasks(callTasks);
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(callId, getCallDto.getCallId());
		assertEquals(subject, getCallDto.getSubject());
		assertEquals(callFrom, getCallDto.getCallFrom());
		assertEquals(callTo, getCallDto.getCallTo());
		assertEquals(direction, getCallDto.getDirection());
		assertEquals(phoneNo, getCallDto.getPhoneNo());
		assertEquals(comment, getCallDto.getComment());
		assertEquals(duration, getCallDto.getDuration());
		assertEquals(startDate, getCallDto.getStartDate());
		assertEquals(endDate, getCallDto.getEndDate());
		assertEquals(startTime, getCallDto.getStartTime());
		assertEquals(endTime, getCallDto.getEndTime());
		assertEquals(startTime12Hours, getCallDto.getStartTime12Hours());
		assertEquals(endTime12Hours, getCallDto.getEndTime12Hours());
		assertEquals(allDay, getCallDto.isAllDay());
		assertEquals(callTasks, getCallDto.getCallTasks());
	}
}