package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class CallTaskDtoTest {

	CallTaskDto dto = new CallTaskDto();
	CallTaskDto dto1 = new CallTaskDto();

	@Test
	void getterData() {
		dto.equals(dto1);
		dto.getCallTaskId();
		dto.getSubject();
		dto.getStatus();
		dto.getPriority();
		dto.getDueDate();
		dto.getDueTime();
		dto.getDescription();
		dto.isRemainderOn();
		dto.getRemainderVia();
		dto.getRemainderDueAt();
		dto.getRemainderDueOn();
		dto.getAssignTo();
		dto.canEqual(dto);
		dto.hashCode();
		dto.toString();
		assertEquals(dto, dto1);

	}

	EmployeeDto assignTo = new EmployeeDto();

	@Test
	void setterData() {
		dto.setCallTaskId(1);
		dto.setSubject("test");
		dto.setStatus("On Hold");
		dto.setPriority("High");
		LocalDate localDate = LocalDate.of(2024, 1, 1);
		dto.setDueDate(localDate);
		dto.setDueTime("10:10");
		dto.setDescription("test");
		dto.setRemainderOn(true);
		dto.setRemainderVia("Email");
		dto.setRemainderDueAt("11:11");
		dto.setRemainderDueOn(localDate);
		dto.setAssignTo(assignTo);
		dto1.setCallTaskId(1);
		dto1.setSubject("test");
		dto1.setStatus("On Hold");
		dto1.setPriority("High");
		assertNotNull(dto);
	}
}
