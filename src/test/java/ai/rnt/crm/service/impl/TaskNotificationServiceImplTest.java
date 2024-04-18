package ai.rnt.crm.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.TaskNotificationDaoService;
import ai.rnt.crm.dto.TaskNotificationsDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;

class TaskNotificationServiceImplTest {

	@Mock
	private TaskNotificationDaoService taskNotificationDaoService;

	@InjectMocks
	private TaskNotificationServiceImpl taskNotificationService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getNotificationTest() {
		Integer staffId = 1477;
		TaskNotifications notification1 = new TaskNotifications();
		TaskNotifications notification2 = new TaskNotifications();
		List<TaskNotifications> notificationsList = Arrays.asList(notification1, notification2);
		when(taskNotificationDaoService.getNotifications(staffId)).thenReturn(notificationsList);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = taskNotificationService.getNotification(staffId);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
		Map<String, Object> responseData = (Map<String, Object>) response.getBody().get(ApiResponse.DATA);
		assertNotNull(responseData);
		assertEquals(2, (long) responseData.get("Count"));
		assertTrue(responseData.get("Notifications") instanceof List);
		assertEquals(2, ((List<?>) responseData.get("Notifications")).size());
	}

	@Test
	void getNotificationEmptyNotificationsListTest() {
		Integer staffId = 1477;
		when(taskNotificationDaoService.getNotifications(staffId)).thenReturn(Collections.emptyList());
		ResponseEntity<EnumMap<ApiResponse, Object>> response = taskNotificationService.getNotification(staffId);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
		Map<String, Object> responseData = (Map<String, Object>) response.getBody().get(ApiResponse.DATA);
		assertNotNull(responseData);
		assertEquals(0, (long) responseData.get("Count"));
		assertTrue(((List<?>) responseData.get("Notifications")).isEmpty());
	}

	@Test
	void getNotificationExceptionTest() {
		Integer staffId = 1477;
		when(taskNotificationDaoService.getNotifications(staffId)).thenThrow(new RuntimeException("Mock exception"));
		assertThrows(CRMException.class, () -> taskNotificationService.getNotification(staffId));
	}

	@Test
	void seenNotificationTest() {
		Integer notifId = 1;
		TaskNotifications notification = new TaskNotifications();
		when(taskNotificationDaoService.getNotificationById(notifId)).thenReturn(Optional.of(notification));
		when(taskNotificationDaoService.seenNotification(notification)).thenReturn(notification);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = taskNotificationService.seenNotification(notifId);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue((boolean) response.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	void seenNotificationExceptionHandlingTest() {
		Integer notifId = 1;
		when(taskNotificationDaoService.getNotificationById(notifId)).thenThrow(new RuntimeException("Mock exception"));
		assertThrows(CRMException.class, () -> taskNotificationService.seenNotification(notifId));
	}

	@Test
	void getMessage_CallTaskNotNull() {
		TaskNotifications notification = new TaskNotifications();
		PhoneCallTask callTask = new PhoneCallTask();
		callTask.setSubject("Call Subject");
		callTask.setDueDate(convertToDateViaInstant(new Date()));
		callTask.setDueTime("12:00 PM");
		notification.setCallTask(callTask);
		TaskNotificationsDto result = taskNotificationService.getMessage(notification);
		assertNotNull(result);
		assertFalse(result.isNotifStatus());
		assertNotNull(result.getMessage());
	}

	@Test
	void getMessageVisitTaskNotNull() {
		TaskNotifications notification = new TaskNotifications();
		VisitTask visitTask = new VisitTask();
		visitTask.setSubject("Visit Subject");
		visitTask.setDueDate(convertToDateViaInstant(new Date()));
		visitTask.setDueTime("12:00 PM");
		notification.setVisitTask(visitTask);
		TaskNotificationsDto result = taskNotificationService.getMessage(notification);
		assertNotNull(result);
		assertFalse(result.isNotifStatus());
		assertNotNull(result.getMessage());
	}

	@Test
	void getMessage_NotificationNull() {
		TaskNotificationsDto result = taskNotificationService.getMessage(null);
		assertNull(result);
	}

	public static LocalDate convertToDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	@Test
	void getMessageMeetingTaskNotNull() {
		TaskNotifications notification = new TaskNotifications();
		MeetingTask meetingTask = new MeetingTask();
		meetingTask.setSubject("Meeting Subject");
		meetingTask.setDueDate(convertToDateViaInstant(new Date()));
		meetingTask.setDueTime("12:00 PM");
		notification.setMeetingTask(meetingTask);
		TaskNotificationsDto result = taskNotificationService.getMessage(notification);
		assertNotNull(result);
		assertFalse(result.isNotifStatus());
		assertNotNull(result.getMessage());
	}

	@Test
	void getMessageLeadTaskNotNull() {
		TaskNotifications notification = new TaskNotifications();
		LeadTask leadTask = new LeadTask();
		leadTask.setSubject("Lead Subject");
		leadTask.setDueDate(convertToDateViaInstant(new Date()));
		leadTask.setDueTime("12:00 PM");
		notification.setLeadTask(leadTask);
		TaskNotificationsDto result = taskNotificationService.getMessage(notification);
		assertNotNull(result);
		assertFalse(result.isNotifStatus());
		assertNotNull(result.getMessage());
	}

	@Test
	void getMessageLeadsNotNull() {
		TaskNotifications notification = new TaskNotifications();
		Leads leads = new Leads();
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setStaffId(1);
		leads.setAssignBy(employeeMaster);
		leads.setAssignDate(LocalDate.now());
		List<Contacts> contacts = new ArrayList<>();
		leads.setContacts(contacts);
		notification.setLeads(leads);
		TaskNotificationsDto result = taskNotificationService.getMessage(notification);
		assertNotNull(result);
		assertFalse(result.isNotifStatus());
		assertNotNull(result.getMessage());
	}

	@Test
	void getMessageOpportunityNotNull() {
		TaskNotifications notification = new TaskNotifications();
		Opportunity opportunity = new Opportunity();
		Leads leads = new Leads();
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setStaffId(1);
		opportunity.setAssignBy(employeeMaster);
		opportunity.setAssignDate(LocalDate.now());
		List<Contacts> contacts = new ArrayList<>();
		leads.setContacts(contacts);
		opportunity.setLeads(leads);
		notification.setOpportunity(opportunity);
		TaskNotificationsDto result = taskNotificationService.getMessage(notification);
		assertNotNull(result);
		assertFalse(result.isNotifStatus());
		assertNotNull(result.getMessage());
	}
}
