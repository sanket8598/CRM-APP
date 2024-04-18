package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class TaskNotificationsDtoTest {

	@Test
	void testSetAndGetTaskNotificationData() {
		TaskNotificationsDto taskNotificationsDto = new TaskNotificationsDto();
		TaskNotificationsDto dto = new TaskNotificationsDto();
		TaskNotificationsDto dto1 = new TaskNotificationsDto();
		taskNotificationsDto.setNotifId(1);
		taskNotificationsDto.setNotifStatus(true);
		taskNotificationsDto.setMessage("Task completed successfully.");
		taskNotificationsDto.setCallTaskId(1);
		taskNotificationsDto.setLeadId(2);
		taskNotificationsDto.setLeadTaskId(3);
		taskNotificationsDto.setMeetingTaskId(4);
		taskNotificationsDto.setOptyId(5);
		taskNotificationsDto.setVisitTaskId(6);
		dto1.setNotifId(1);
		dto1.setNotifStatus(true);
		dto1.setMessage("Task completed successfully.");
		Integer notifId = taskNotificationsDto.getNotifId();
		taskNotificationsDto.getCallTaskId();
		taskNotificationsDto.getLeadId();
		taskNotificationsDto.getLeadTaskId();
		taskNotificationsDto.getMeetingTaskId();
		taskNotificationsDto.getOptyId();
		taskNotificationsDto.getVisitTaskId();
		boolean notifStatus = taskNotificationsDto.isNotifStatus();
		String message = taskNotificationsDto.getMessage();
		taskNotificationsDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertNotNull(notifId);
		assertEquals(1, notifId);
		assertEquals(true, notifStatus);
		assertEquals("Task completed successfully.", message);
	}
}
