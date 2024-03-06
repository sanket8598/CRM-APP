package ai.rnt.crm.dao.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.repository.TaskNotificationsRepository;

/**
 * @author Nikhil Gaikwad
 * @since 06/03/2024.
 *
 */
class TaskNotificationDaoServiceImplTest {

	@InjectMocks
	TaskNotificationDaoServiceImpl taskNotificationDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	TaskNotifications taskNotifications;

	@Mock
	private TaskNotificationsRepository taskNotificationsRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(taskNotificationDaoServiceImpl).build();
	}

	@Test
	void testGetNotifications() {
		Integer staffId = 1477;
		List<TaskNotifications> notificationsList = new ArrayList<>();
		when(taskNotificationsRepository.findByNotifToStaffIdAndNotifStatusOrderByCreatedDateDesc(staffId, true))
				.thenReturn(notificationsList);
		taskNotificationDaoServiceImpl.getNotifications(staffId);
		verify(taskNotificationsRepository).findByNotifToStaffIdAndNotifStatusOrderByCreatedDateDesc(staffId, true);
	}

	@Test
	void testGetNotificationById() {
		Integer notifId = 4;
		TaskNotifications notification = new TaskNotifications();
		when(taskNotificationsRepository.findById(notifId)).thenReturn(Optional.of(notification));
		taskNotificationDaoServiceImpl.getNotificationById(notifId);
		verify(taskNotificationsRepository).findById(notifId);
	}

	@Test
	void testSeenNotification() {
		TaskNotifications notification = new TaskNotifications();
		when(taskNotificationsRepository.save(notification)).thenReturn(notification);
		taskNotificationDaoServiceImpl.seenNotification(notification);
		verify(taskNotificationsRepository).save(notification);
	}
}
