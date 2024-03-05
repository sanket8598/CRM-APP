package ai.rnt.crm.api.restcontroller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.TaskNotificationService;

class TaskNotificationsControllerTest {

	@Mock
	private TaskNotificationService taskNotificationService;

	@InjectMocks
	private TaskNotificationsController taskNotificationsController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void getNotificationTest() {
		TaskNotificationService taskNotificationService = mock(TaskNotificationService.class);
		Integer staffId = 1;
		TaskNotificationsController controller = new TaskNotificationsController(taskNotificationService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(taskNotificationService.getNotification(staffId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.getNotification(staffId);
		verify(taskNotificationService).getNotification(staffId);
	}

	@Test
	void seenNotificationTest() {
		TaskNotificationService taskNotificationService = mock(TaskNotificationService.class);
		Integer notifId = 1;
		TaskNotificationsController controller = new TaskNotificationsController(taskNotificationService);
		ResponseEntity<EnumMap<ApiResponse, Object>> mockResponse = ResponseEntity.ok().build();
		when(taskNotificationService.seenNotification(notifId)).thenReturn(mockResponse);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = controller.seenNotification(notifId);
		verify(taskNotificationService).seenNotification(notifId);
	}
}
