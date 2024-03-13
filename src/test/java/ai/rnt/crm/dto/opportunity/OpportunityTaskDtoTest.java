package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.entity.EmployeeMaster;

class OpportunityTaskDtoTest {

	@Test
    void testOpportunityTaskDto() {
        OpportunityTaskDto opportunityTaskDto = new OpportunityTaskDto();
        EmployeeMaster assignTo = new EmployeeMaster();
        assignTo.setStaffId(1477);
        opportunityTaskDto.setOptyTaskId(1);
        opportunityTaskDto.setAssignTo(assignTo);
        opportunityTaskDto.setSubject("Test Subject");
        opportunityTaskDto.setStatus("In Progress");
        opportunityTaskDto.setPriority("High");
        opportunityTaskDto.setDueDate(LocalDate.of(2024, 3, 13));
        opportunityTaskDto.setDueTime("10:00 AM");
        opportunityTaskDto.setDescription("Test Description");
        opportunityTaskDto.setRemainderOn(true);
        opportunityTaskDto.setRemainderVia("Email");
        opportunityTaskDto.setRemainderDueAt("Test Due At");
        opportunityTaskDto.setRemainderDueOn(LocalDate.of(2024, 3, 15));
        assertEquals(1, opportunityTaskDto.getOptyTaskId());
        assertEquals("Test Subject", opportunityTaskDto.getSubject());
        assertEquals("In Progress", opportunityTaskDto.getStatus());
        assertEquals("High", opportunityTaskDto.getPriority());
        assertEquals(LocalDate.of(2024, 3, 13), opportunityTaskDto.getDueDate());
        assertEquals("10:00 AM", opportunityTaskDto.getDueTime());
        assertEquals("Test Description", opportunityTaskDto.getDescription());
        assertTrue(opportunityTaskDto.isRemainderOn());
        assertEquals("Email", opportunityTaskDto.getRemainderVia());
        assertEquals("Test Due At", opportunityTaskDto.getRemainderDueAt());
        assertEquals(LocalDate.of(2024, 3, 15), opportunityTaskDto.getRemainderDueOn());
        assertEquals(assignTo, opportunityTaskDto.getAssignTo());
    }
}
