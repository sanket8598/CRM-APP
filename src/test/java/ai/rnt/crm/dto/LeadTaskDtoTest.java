package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.entity.EmployeeMaster;

class LeadTaskDtoTest {

	LeadTaskDto dto = new LeadTaskDto();
	LeadTaskDto dto1 = new LeadTaskDto();

	@Test
	void testLeadTaskDto() {
		LeadTaskDto leadTaskDto = new LeadTaskDto();
		Integer leadTaskId = 1;
		String subject = "Follow-up";
		String status = "Pending";
		String priority = "High";
		LocalDate dueDate = LocalDate.of(2024, 3, 5);
		String dueTime = "09:00 AM";
		String description = "Follow up on client's request";
		boolean remainderOn = true;
		String remainderVia = "Email";
		String remainderDueAt = "08:30 AM";
		LocalDate remainderDueOn = LocalDate.of(2024, 3, 5);
		EmployeeMaster assignTo = new EmployeeMaster(); // Assuming EmployeeMaster is instantiated properly
		leadTaskDto.setLeadTaskId(leadTaskId);
		leadTaskDto.setSubject(subject);
		leadTaskDto.setStatus(status);
		leadTaskDto.setPriority(priority);
		leadTaskDto.setDueDate(dueDate);
		leadTaskDto.setDueTime(dueTime);
		leadTaskDto.setDescription(description);
		leadTaskDto.setRemainderOn(remainderOn);
		leadTaskDto.setRemainderVia(remainderVia);
		leadTaskDto.setRemainderDueAt(remainderDueAt);
		leadTaskDto.setRemainderDueOn(remainderDueOn);
		leadTaskDto.setAssignTo(assignTo);
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(leadTaskId, leadTaskDto.getLeadTaskId());
		assertEquals(subject, leadTaskDto.getSubject());
		assertEquals(status, leadTaskDto.getStatus());
		assertEquals(priority, leadTaskDto.getPriority());
		assertEquals(dueDate, leadTaskDto.getDueDate());
		assertEquals(dueTime, leadTaskDto.getDueTime());
		assertEquals(description, leadTaskDto.getDescription());
		assertEquals(remainderOn, leadTaskDto.isRemainderOn());
		assertEquals(remainderVia, leadTaskDto.getRemainderVia());
		assertEquals(remainderDueAt, leadTaskDto.getRemainderDueAt());
		assertEquals(remainderDueOn, leadTaskDto.getRemainderDueOn());
		assertEquals(assignTo, leadTaskDto.getAssignTo());
	}
}
