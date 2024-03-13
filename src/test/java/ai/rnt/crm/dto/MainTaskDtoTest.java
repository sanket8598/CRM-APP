package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MainTaskDtoTest {

	@Test
	void testMainTaskDto() {
		MainTaskDto mainTaskDto = new MainTaskDto();
		MainTaskDto dto = new MainTaskDto();
		MainTaskDto dto1 = new MainTaskDto();
		Integer id = 1;
		String subject = "Task Subject";
		String status = "Pending";
		String type = "Type A";
		String dueDate = "2024-03-05";
		EmployeeDto assignTo = new EmployeeDto();
		Integer parentId = 123;
		boolean remainderOn = true;
		String parentStatus = "Parent Status";
		String remainderDate = "2024-03-05";
		mainTaskDto.setId(id);
		mainTaskDto.setSubject(subject);
		mainTaskDto.setStatus(status);
		mainTaskDto.setType(type);
		mainTaskDto.setDueDate(dueDate);
		mainTaskDto.setAssignTo(assignTo);
		mainTaskDto.setParentId(parentId);
		mainTaskDto.setRemainderOn(remainderOn);
		mainTaskDto.setParentStatus(parentStatus);
		mainTaskDto.setRemainderDate(remainderDate);
		dto1.setId(id);
		dto1.setSubject(subject);
		dto1.setStatus(status);
		dto1.setType(type);
		dto1.setDueDate(dueDate);
		dto1.setAssignTo(assignTo);
		dto1.setParentId(parentId);
		dto1.setRemainderOn(remainderOn);
		dto1.setParentStatus(parentStatus);
		dto1.setRemainderDate(remainderDate);
		mainTaskDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertEquals(id, mainTaskDto.getId());
		assertEquals(subject, mainTaskDto.getSubject());
		assertEquals(status, mainTaskDto.getStatus());
		assertEquals(type, mainTaskDto.getType());
		assertEquals(dueDate, mainTaskDto.getDueDate());
		assertEquals(assignTo, mainTaskDto.getAssignTo());
		assertEquals(parentId, mainTaskDto.getParentId());
		assertEquals(remainderOn, mainTaskDto.isRemainderOn());
		assertEquals(parentStatus, mainTaskDto.getParentStatus());
		assertEquals(remainderDate, mainTaskDto.getRemainderDate());
	}

	@Test
	void testMainTaskDtoConstructor() {
		Integer id = 1;
		String subject = "Task Subject";
		String status = "Pending";
		String type = "Type A";
		String dueDate = "2024-03-05";
		EmployeeDto assignTo = new EmployeeDto();
		Integer parentId = 123;
		boolean remainderOn = true;
		String parentStatus = "Parent Status";
		String remainderDate = "2024-03-05";
		MainTaskDto mainTaskDto = new MainTaskDto(id, subject, status, type, dueDate, assignTo, parentId, remainderOn,
				parentStatus, remainderDate);
		assertEquals(id, mainTaskDto.getId());
		assertEquals(subject, mainTaskDto.getSubject());
		assertEquals(status, mainTaskDto.getStatus());
		assertEquals(type, mainTaskDto.getType());
		assertEquals(dueDate, mainTaskDto.getDueDate());
		assertEquals(assignTo, mainTaskDto.getAssignTo());
		assertEquals(parentId, mainTaskDto.getParentId());
		assertEquals(remainderOn, mainTaskDto.isRemainderOn());
		assertEquals(parentStatus, mainTaskDto.getParentStatus());
		assertEquals(remainderDate, mainTaskDto.getRemainderDate());
	}
}
