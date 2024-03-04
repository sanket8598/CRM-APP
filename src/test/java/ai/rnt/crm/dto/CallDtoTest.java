package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

class CallDtoTest {

	@Test
	void testGetAndSetCallId() {
		CallDto callDto = new CallDto();
		callDto.setCallId(1);
		assertEquals(1, callDto.getCallId());
	}

	@Test
	void testGetAndSetCallFrom() {
		EmployeeDto employeeDto = new EmployeeDto();
		CallDto callDto = new CallDto();
		callDto.setCallFrom(employeeDto);
		assertEquals(employeeDto, callDto.getCallFrom());
	}

	@Test
	void testGetAndSetCallTo() {
		CallDto callDto = new CallDto();
		callDto.setCallTo("1234567890");
		assertEquals("1234567890", callDto.getCallTo());
	}

	@Test
	void testGetAndSetSubject() {
		CallDto callDto = new CallDto();
		callDto.setSubject("Test Subject");
		assertEquals("Test Subject", callDto.getSubject());
	}

	@Test
	void testGetAndSetDirection() {
		CallDto callDto = new CallDto();
		callDto.setDirection("Inbound");
		assertEquals("Inbound", callDto.getDirection());
	}

	@Test
	void testGetAndSetPhoneNo() {
		CallDto callDto = new CallDto();
		callDto.setPhoneNo("1234567890");
		assertEquals("1234567890", callDto.getPhoneNo());
	}

	@Test
	void testGetAndSetComment() {
		CallDto callDto = new CallDto();
		callDto.setComment("Test Comment");
		assertEquals("Test Comment", callDto.getComment());
	}

	@Test
	void testGetAndSetDuration() {
		CallDto callDto = new CallDto();
		callDto.setDuration("10 minutes");
		assertEquals("10 minutes", callDto.getDuration());
	}

	@Test
	void testGetAndSetStartDate() {
		CallDto callDto = new CallDto();
		callDto.setStartDate(new Date());
		assertNotNull(callDto.getStartDate());
	}

	@Test
	void testGetAndSetEndDate() {
		CallDto callDto = new CallDto();
		callDto.setEndDate(new Date());
		assertNotNull(callDto.getEndDate());
	}

	@Test
	void testGetAndSetStartTime() {
		CallDto callDto = new CallDto();
		callDto.setStartTime("10:00 AM");
		assertEquals("10:00 AM", callDto.getStartTime());
	}

	@Test
	void testGetAndSetEndTime() {
		CallDto callDto = new CallDto();
		callDto.setEndTime("11:00 AM");
		assertEquals("11:00 AM", callDto.getEndTime());
	}

	@Test
	void testIsAndGetAllDay() {
		CallDto callDto = new CallDto();
		callDto.setAllDay(true);
		assertTrue(callDto.isAllDay());
	}

	@Test
	void testGetAndSetIsOpportunity() {
		CallDto callDto = new CallDto();
		callDto.setIsOpportunity(true);
		assertTrue(callDto.getIsOpportunity());
	}

	@Test
	void testEqualsAndHashCode() {
		CallDto dto1 = new CallDto();
		dto1.setCallId(1);
		dto1.setSubject("Subject");
		CallDto dto2 = new CallDto();
		dto2.setCallId(1);
		dto2.setSubject("Subject");
		assertTrue(dto1.equals(dto2));
		assertEquals(dto1.hashCode(), dto2.hashCode());
	}

	@Test
	void testToString() {
		CallDto dto = new CallDto();
		dto.setCallId(1);
		dto.setSubject("Subject");
		String expectedToString = "CallDto(callId=1, callFrom=null, callTo=null, subject=Subject, direction=null, phoneNo=null, comment=null, duration=null, startDate=null, endDate=null, startTime=null, endTime=null, allDay=false, isOpportunity=false)";
		assertEquals(expectedToString, dto.toString());
	}
}
