package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MainTaskDtoTest {

	MainTaskDto dto = new MainTaskDto();
	MainTaskDto dto1 = new MainTaskDto();

	@Test
	void testMainTaskDto() {
		MainTaskDto mainTaskDto = new MainTaskDto();
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
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
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
