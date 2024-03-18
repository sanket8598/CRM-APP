package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.GetVisitTaskDto;
import ai.rnt.crm.dto.VisitTaskDto;
import ai.rnt.crm.entity.VisitTask;

class VisitTaskDtoMapperTest {

	@Test
	void testToVisitTask() {
		VisitTaskDto visitTaskDto = new VisitTaskDto();
		Optional<VisitTask> visitTaskOptional = VisitTaskDtoMapper.TO_VISIT_TASK.apply(visitTaskDto);
		assertNotNull(visitTaskOptional);
	}

	@Test
	void testToVisitTasks() {
		Collection<VisitTaskDto> visitTaskDtoCollection = new ArrayList<>();
		List<VisitTask> visitTaskList = VisitTaskDtoMapper.TO_VISIT_TASKS.apply(visitTaskDtoCollection);
		assertNotNull(visitTaskList);
	}

	@Test
	void testToVisitTaskDto() {
		VisitTask visitTask = new VisitTask();
		Optional<VisitTaskDto> visitTaskDtoOptional = VisitTaskDtoMapper.TO_VISIT_TASK_DTO.apply(visitTask);
		assertNotNull(visitTaskDtoOptional);
	}

	@Test
	void testToVisitTaskDtos() {
		Collection<VisitTask> visitTaskCollection = new ArrayList<>();
		List<VisitTaskDto> visitTaskDtoList = VisitTaskDtoMapper.TO_VISIT_TASK_DTOS.apply(visitTaskCollection);
		assertNotNull(visitTaskDtoList);
	}

	@Test
	void testToGetVisitTaskDto() {
		VisitTask visitTask = new VisitTask();
		Optional<GetVisitTaskDto> getVisitTaskDtoOptional = VisitTaskDtoMapper.TO_GET_VISIT_TASK_DTO.apply(visitTask);
		assertNotNull(getVisitTaskDtoOptional);
	}
}
