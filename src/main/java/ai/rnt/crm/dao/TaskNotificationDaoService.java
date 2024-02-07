package ai.rnt.crm.dao;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.TaskNotificationsDto;
import ai.rnt.crm.entity.TaskNotifications;

public interface TaskNotificationDaoService extends CrudService<TaskNotifications, TaskNotificationsDto> {

	List<TaskNotifications> getNotifications(Integer staffId);

	Optional<TaskNotifications> getNotificationById(Integer notifId);

	TaskNotifications seenNotification(TaskNotifications notifyData);

}
