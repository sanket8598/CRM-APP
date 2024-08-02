package ai.rnt.crm.dao.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.TaskNotificationDaoService;
import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.repository.TaskNotificationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TaskNotificationDaoServiceImpl implements TaskNotificationDaoService {

	private final TaskNotificationsRepository taskNotificationsRepository;
	
	@CacheEvict(value="notifications",allEntries = true)
	@Override
	public TaskNotifications addNotification(TaskNotifications taskNotifications) {
		log.info("inside the addNotification method...");
		return taskNotificationsRepository.save(taskNotifications);
	}
	
	@Cacheable(value="notifications",key = "#staffId!=null")
	@Override
	public List<TaskNotifications> getNotifications(Integer staffId) {
		log.info("inside the getNotifications method with staffId...{}", staffId);
		return taskNotificationsRepository.findByNotifToStaffIdAndNotifStatusOrderByCreatedDateDesc(staffId, true);
	}

	@Override
	public Optional<TaskNotifications> getNotificationById(Integer notifId) {
		log.info("inside the getNotifications method with notification id...{}", notifId);
		return taskNotificationsRepository.findById(notifId);
	}

	@Override
	public TaskNotifications seenNotification(TaskNotifications notifyData) {
		log.info("inside the seenNotification method...");
		return addNotification(notifyData);
	}
}
