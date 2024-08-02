package ai.rnt.crm.util;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ai.rnt.crm.config.WebSocketService;
import ai.rnt.crm.dao.service.TaskNotificationDaoService;
import ai.rnt.crm.dto.TaskNotificationsDto;
import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.repository.TaskNotificationsRepository;
import ai.rnt.crm.service.impl.TaskNotificationServiceImpl;

class TaskNotificationsUtilTest {

	@Mock
	private TaskNotificationsRepository taskNotificationsRepository;

	@Mock
	private WebSocketService webSocketService;

	@Mock
	private TaskNotificationServiceImpl taskNotificationServiceImpl;

	@Mock
	private TaskNotificationDaoService taskNotificationDaoService;

	@InjectMocks
	private TaskNotificationsUtil taskNotificationsUtil;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSendTaskNotification() {
		TaskNotifications taskNotifications = mock(TaskNotifications.class);
		TaskNotifications notification = mock(TaskNotifications.class);
		TaskNotificationsDto dto = mock(TaskNotificationsDto.class);
		when(taskNotificationDaoService.addNotification(taskNotifications)).thenReturn(notification);
		when(taskNotificationServiceImpl.getMessage(notification)).thenReturn(dto);
		taskNotificationsUtil.sendTaskNotification(taskNotifications);
		verify(taskNotificationDaoService).addNotification(taskNotifications);
		verify(taskNotificationServiceImpl).getMessage(notification);
		verify(webSocketService).sendNotification(anyString(), eq(dto));
	}
}
