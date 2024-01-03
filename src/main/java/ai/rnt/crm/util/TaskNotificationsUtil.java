package ai.rnt.crm.util;

import org.springframework.stereotype.Service;

import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.repository.TaskNotificationsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskNotificationsUtil {

	private final TaskNotificationsRepository taskNotificationsRepository;

	public void sendCallTaskNotification(TaskNotifications taskNotifications) {
		taskNotificationsRepository.save(taskNotifications);
	}

	public void sendVisitTaskNotification(TaskNotifications taskNotifications) {
		taskNotificationsRepository.save(taskNotifications);
	}

	public void sendMeetingTaskNotification(TaskNotifications taskNotifications) {
		taskNotificationsRepository.save(taskNotifications);
	}

	public void sendFollowUpLeadNotification(TaskNotifications taskNotifications) {
		taskNotificationsRepository.save(taskNotifications);
	}

	public void sendLeadTaskNotification(TaskNotifications taskNotifications) {
		taskNotificationsRepository.save(taskNotifications);
	}
}
