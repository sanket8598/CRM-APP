package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.GetMeetingTaskDto;
import ai.rnt.crm.dto.MeetingTaskDto;
import ai.rnt.crm.entity.MeetingTask;

class MeetingTaskDtoMapperTest {

	@Test
	void testToMeetingTask() {
		MeetingTaskDto meetingTaskDto = new MeetingTaskDto();
		Optional<MeetingTask> meetingTaskOptional = MeetingTaskDtoMapper.TO_MEETING_TASK.apply(meetingTaskDto);
		assertNotNull(meetingTaskOptional);
	}

	@Test
	void testToMeetingTasks() {
		Collection<MeetingTaskDto> meetingTaskDtoCollection = new ArrayList<>();
		List<MeetingTask> meetingTaskList = MeetingTaskDtoMapper.TO_MEETINGS_TASK.apply(meetingTaskDtoCollection);
		assertNotNull(meetingTaskList);
	}

	@Test
	void testToMeetingTaskDto() {
		MeetingTask meetingTask = new MeetingTask();
		Optional<MeetingTaskDto> meetingTaskDtoOptional = MeetingTaskDtoMapper.TO_MEETING_TASK_DTO.apply(meetingTask);
		assertNotNull(meetingTaskDtoOptional);
	}

	@Test
	void testToMeetingTaskDtos() {
		Collection<MeetingTask> meetingTaskCollection = new ArrayList<>();
		List<MeetingTaskDto> meetingTaskDtoList = MeetingTaskDtoMapper.TO_MEETING_TASK_DTOS
				.apply(meetingTaskCollection);
		assertNotNull(meetingTaskDtoList);
	}

	@Test
	void testToGetMeetingTaskDto() {
		MeetingTask meetingTask = new MeetingTask();
		Optional<GetMeetingTaskDto> getMeetingTaskDtoOptional = MeetingTaskDtoMapper.TO_GET_MEETING_TASK_DTO
				.apply(meetingTask);
		assertNotNull(getMeetingTaskDtoOptional);
	}
}
