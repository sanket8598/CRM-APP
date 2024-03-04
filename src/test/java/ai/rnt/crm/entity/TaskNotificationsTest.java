package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TaskNotificationsTest {

	@Test
	void testGetAndSetData() {
		TaskNotifications taskNotifications = new TaskNotifications();
		taskNotifications.setNotifId(1);
		taskNotifications.setNotifStatus(true);
		PhoneCallTask callTask = new PhoneCallTask();
		callTask.setSubject("Phone Call Task Subject");
		taskNotifications.setCallTask(callTask);
		VisitTask visitTask = new VisitTask();
		visitTask.setSubject("Visit Task Subject");
		taskNotifications.setVisitTask(visitTask);
		MeetingTask meetingTask = new MeetingTask();
		meetingTask.setSubject("Meeting Task Subject");
		taskNotifications.setMeetingTask(meetingTask);
		LeadTask leadTask = new LeadTask();
		leadTask.setSubject("Lead Task Subject");
		taskNotifications.setLeadTask(leadTask);
		Leads leads = new Leads();
		leads.setLeadId(123);
		taskNotifications.setLeads(leads);
		EmployeeMaster notifTo = new EmployeeMaster();
		notifTo.setStaffId(456);
		taskNotifications.setNotifTo(notifTo);
		int notifId = taskNotifications.getNotifId();
		boolean notifStatus = taskNotifications.isNotifStatus();
		PhoneCallTask retrievedCallTask = taskNotifications.getCallTask();
		String callTaskSubject = retrievedCallTask.getSubject();
		VisitTask retrievedVisitTask = taskNotifications.getVisitTask();
		String visitTaskSubject = retrievedVisitTask.getSubject();
		MeetingTask retrievedMeetingTask = taskNotifications.getMeetingTask();
		String meetingTaskSubject = retrievedMeetingTask.getSubject();
		LeadTask retrievedLeadTask = taskNotifications.getLeadTask();
		String leadTaskSubject = retrievedLeadTask.getSubject();
		Leads retrievedLeads = taskNotifications.getLeads();
		int leadsId = retrievedLeads.getLeadId();
		EmployeeMaster retrievedNotifTo = taskNotifications.getNotifTo();
		int notifToStaffId = retrievedNotifTo.getStaffId();
		assertEquals(1, notifId);
		assertEquals(true, notifStatus);
		assertEquals("Phone Call Task Subject", callTaskSubject);
		assertEquals("Visit Task Subject", visitTaskSubject);
		assertEquals("Meeting Task Subject", meetingTaskSubject);
		assertEquals("Lead Task Subject", leadTaskSubject);
		assertEquals(123, leadsId);
		assertEquals(456, notifToStaffId);
	}
}
