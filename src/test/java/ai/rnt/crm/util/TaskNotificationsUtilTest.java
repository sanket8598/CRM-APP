package ai.rnt.crm.util;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.repository.TaskNotificationsRepository;

class TaskNotificationsUtilTest {

	 @Mock
	    private TaskNotificationsRepository taskNotificationsRepository;

	    @InjectMocks
	    private TaskNotificationsUtil taskNotificationsUtil;

	    @BeforeEach
	    public void setup() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testSendCallTaskNotification() {
	        TaskNotifications taskNotifications = new TaskNotifications();
	        taskNotificationsUtil.sendCallTaskNotification(taskNotifications);
	        verify(taskNotificationsRepository).save(taskNotifications);
	    }

	    @Test
	    public void testSendVisitTaskNotification() {
	        TaskNotifications taskNotifications = new TaskNotifications();
	        taskNotificationsUtil.sendVisitTaskNotification(taskNotifications);
	        verify(taskNotificationsRepository).save(taskNotifications);
	    }

	    @Test
	    public void testSendMeetingTaskNotification() {
	        TaskNotifications taskNotifications = new TaskNotifications();
	        taskNotificationsUtil.sendMeetingTaskNotification(taskNotifications);
	        verify(taskNotificationsRepository).save(taskNotifications);
	    }

	    @Test
	    public void testSendFollowUpLeadNotification() {
	        TaskNotifications taskNotifications = new TaskNotifications();
	        taskNotificationsUtil.sendFollowUpLeadNotification(taskNotifications);
	        verify(taskNotificationsRepository).save(taskNotifications);
	    }

	    @Test
	    public void testSendLeadTaskNotification() {
	        TaskNotifications taskNotifications = new TaskNotifications();
	        taskNotificationsUtil.sendLeadTaskNotification(taskNotifications);
	        verify(taskNotificationsRepository).save(taskNotifications);
	    }

}
