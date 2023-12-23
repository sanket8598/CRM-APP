package ai.rnt.crm.dao.service;

import java.util.List;

import ai.rnt.crm.dto.TaskNotificationsDto;
import ai.rnt.crm.entity.TaskNotifications;

public interface TaskNotificationDaoService extends CrudService<TaskNotifications, TaskNotificationsDto> {

	List<TaskNotifications> getNotifications(Integer staffId);

}
