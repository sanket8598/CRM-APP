package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.GetLeadTaskDto;
import ai.rnt.crm.dto.LeadTaskDto;
import ai.rnt.crm.dto.TaskNotificationsDto;
import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.TaskNotifications;

class LeadTaskDtoMapperTest {

	@Test
	void testToLeadTask() {
		LeadTaskDto leadTaskDto = new LeadTaskDto();
		Optional<LeadTask> leadTaskOptional = LeadTaskDtoMapper.TO_LEAD_TASK.apply(leadTaskDto);
		assertNotNull(leadTaskOptional);
	}

	@Test
	void testToLeadTasks() {
		Collection<LeadTaskDto> leadTaskDtoCollection = new ArrayList<>();
		List<LeadTask> leadTaskList = LeadTaskDtoMapper.TO_LEAD_TASKS.apply(leadTaskDtoCollection);
		assertNotNull(leadTaskList);
	}

	@Test
	void testToLeadTaskDto() {
		LeadTask leadTask = new LeadTask();
		Optional<LeadTaskDto> leadTaskDtoOptional = LeadTaskDtoMapper.TO_LEAD_TASK_DTO.apply(leadTask);
		assertNotNull(leadTaskDtoOptional);
	}

	@Test
	void testToLeadTaskDtos() {
		Collection<LeadTask> leadTaskCollection = new ArrayList<>();
		List<LeadTaskDto> leadTaskDtoList = LeadTaskDtoMapper.TO_LEAD_TASK_DTOS.apply(leadTaskCollection);
		assertNotNull(leadTaskDtoList);
	}

	@Test
	void testToGetLeadTaskDto() {
		LeadTask leadTask = new LeadTask();
		Optional<GetLeadTaskDto> getLeadTaskDtoOptional = LeadTaskDtoMapper.TO_GET_LEAD_TASK_DTO.apply(leadTask);
		assertNotNull(getLeadTaskDtoOptional);
	}

	@Test
	void testToGetNotificationDto() {
		TaskNotifications taskNotifications = new TaskNotifications();
		Optional<TaskNotificationsDto> taskNotificationsDtoOptional = LeadTaskDtoMapper.TO_GET_NOTIFICATION_DTO
				.apply(taskNotifications);
		assertNotNull(taskNotificationsDtoOptional);
	}

	@Test
	void testToGetNotificationDtos() {
		Collection<TaskNotifications> taskNotificationsCollection = new ArrayList<>();
		List<TaskNotificationsDto> taskNotificationsDtoList = LeadTaskDtoMapper.TO_GET_NOTIFICATION_DTOS
				.apply(taskNotificationsCollection);
		assertNotNull(taskNotificationsDtoList);
	}
}
