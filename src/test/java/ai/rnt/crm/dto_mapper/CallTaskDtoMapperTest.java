package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.CallTaskDto;
import ai.rnt.crm.dto.GetCallTaskDto;
import ai.rnt.crm.entity.PhoneCallTask;

class CallTaskDtoMapperTest {

	@Test
	void testToCallTask() {
		CallTaskDto callTaskDto = new CallTaskDto();
		Optional<PhoneCallTask> result = CallTaskDtoMapper.TO_CALL_TASK.apply(callTaskDto);
		assertTrue(result.isPresent());
	}

	@Test
	void testToCallTasks() {
		List<CallTaskDto> callTaskDtoList = new ArrayList<>();
		List<PhoneCallTask> result = CallTaskDtoMapper.TO_CALL_TASKS.apply(callTaskDtoList);
		assertTrue(result.isEmpty());
	}

	@Test
	void testToCallTaskDto() {
		PhoneCallTask phoneCallTask = new PhoneCallTask();
		Optional<CallTaskDto> result = CallTaskDtoMapper.TO_CALL_TASK_DTO.apply(phoneCallTask);
		assertTrue(result.isPresent());
	}

	@Test
	void testToCallTaskDtos() {
		List<PhoneCallTask> phoneCallTaskList = new ArrayList<>();
		List<CallTaskDto> result = CallTaskDtoMapper.TO_CALL_TASK_DTOS.apply(phoneCallTaskList);
		assertTrue(result.isEmpty());
	}

	@Test
	void testToGetCallTaskDto() {
		PhoneCallTask phoneCallTask = new PhoneCallTask();
		Optional<GetCallTaskDto> result = CallTaskDtoMapper.TO_GET_CALL_TASK_DTO.apply(phoneCallTask);
		assertTrue(result.isPresent());
	}
}
