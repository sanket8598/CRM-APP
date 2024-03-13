package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Date;

import org.junit.jupiter.api.Test;

class CallDtoTest {

	  @Test
	    void testCallDto() {
	        CallDto callDto = new CallDto();
	        EmployeeDto employeeDto = new EmployeeDto();
	        employeeDto.setStaffId(1);
	        callDto.setCallId(1);
	        callDto.setCallFrom(employeeDto);
	        callDto.setCallTo("Test Caller");
	        callDto.setSubject("Test Subject");
	        callDto.setDirection("Incoming");
	        callDto.setPhoneNo("1234567890");
	        callDto.setComment("Test Comment");
	        callDto.setDuration("30 minutes");
	        callDto.setStartTime("10:00 AM");
	        callDto.setEndTime("10:30 AM");
	        callDto.setAllDay(false);
	        callDto.setIsOpportunity(false);
	        Date startDate = new Date();
	        callDto.setStartDate(startDate);
	        Date endDate = new Date();
	        callDto.setEndDate(endDate);
	        Date actualStartDate = callDto.getStartDate();
	        assertEquals(startDate, actualStartDate);
	        Date actualEndDate = callDto.getEndDate();
	        assertEquals(endDate, actualEndDate);
	        assertEquals(1, callDto.getCallId());
	        assertEquals(employeeDto, callDto.getCallFrom());
	        assertEquals("Test Caller", callDto.getCallTo());
	        assertEquals("Test Subject", callDto.getSubject());
	        assertEquals("Incoming", callDto.getDirection());
	        assertEquals("1234567890", callDto.getPhoneNo());
	        assertEquals("Test Comment", callDto.getComment());
	        assertEquals("30 minutes", callDto.getDuration());
	        assertEquals("10:00 AM", callDto.getStartTime());
	        assertEquals("10:30 AM", callDto.getEndTime());
	        assertFalse(callDto.isAllDay());
	        assertFalse(callDto.getIsOpportunity());
}
}
