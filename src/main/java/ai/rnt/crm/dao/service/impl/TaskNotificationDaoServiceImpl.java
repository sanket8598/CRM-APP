package ai.rnt.crm.dao.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.TaskNotificationDaoService;
import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.repository.TaskNotificationsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskNotificationDaoServiceImpl implements TaskNotificationDaoService {

	private final TaskNotificationsRepository taskNotificationsRepository;

	@Override
	public List<TaskNotifications> getNotifications(Integer staffId) {
		return taskNotificationsRepository.findByNotifToStaffIdAndNotifStatus(staffId, true);
	}
}
