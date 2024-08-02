package ai.rnt.crm.util;

import org.springframework.stereotype.Service;

import ai.rnt.crm.config.WebSocketService;
import ai.rnt.crm.dao.service.TaskNotificationDaoService;
import ai.rnt.crm.dto.TaskNotificationsDto;
import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.service.impl.TaskNotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskNotificationsUtil {

	private final TaskNotificationDaoService taskNotificationDaoService;
	private final WebSocketService webSocketService;
	private final TaskNotificationServiceImpl taskNotificationServiceImpl;

	public void sendNotificationIfPresent() {
		log.info("inside the sendNotificationIfPresent method...{}");
		taskNotificationDaoService.allNotifications().stream().map(taskNotificationServiceImpl::getMessage)
				.forEach(this::sentNotification);
	}

	public void sentNotification(TaskNotificationsDto dto) {
		log.info("inside the sentNotification method sending the notification to the websocket...{}",
				dto.getAssignTo());
		webSocketService.sendNotification("/crm/notification/" + dto.getAssignTo(), dto);
	}

	public void sendTaskNotification(TaskNotifications taskNotifications) {
		log.info("inside the sendTaskNotification method...{}");
		sentNotification(
				taskNotificationServiceImpl.getMessage(taskNotificationDaoService.addNotification(taskNotifications)));
	}

}
