package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class TaskNotificationsDtoTest {

	TaskNotificationsDto dto = new TaskNotificationsDto();
	TaskNotificationsDto dto1 = new TaskNotificationsDto();

	@Test
	void testSetAndGetTaskNotificationData() {
		TaskNotificationsDto taskNotificationsDto = new TaskNotificationsDto();
		taskNotificationsDto.setNotifId(1);
		taskNotificationsDto.setNotifStatus(true);
		taskNotificationsDto.setMessage("Task completed successfully.");
		Integer notifId = taskNotificationsDto.getNotifId();
		boolean notifStatus = taskNotificationsDto.isNotifStatus();
		String message = taskNotificationsDto.getMessage();
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertNotNull(notifId);
		assertEquals(1, notifId);
		assertEquals(true, notifStatus);
		assertEquals("Task completed successfully.", message);
	}
}
