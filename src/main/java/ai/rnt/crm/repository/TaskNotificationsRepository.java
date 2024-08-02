package ai.rnt.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.TaskNotifications;

public interface TaskNotificationsRepository extends JpaRepository<TaskNotifications, Integer> {

	List<TaskNotifications> findByNotifToStaffIdAndNotifStatusOrderByCreatedDateDesc(Integer staffId,
			boolean notifyStatus);

	List<TaskNotifications> findByNotifStatusOrderByCreatedDateDesc(boolean notifyStatus);
}
