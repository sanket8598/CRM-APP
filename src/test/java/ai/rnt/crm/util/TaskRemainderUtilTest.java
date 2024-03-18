package ai.rnt.crm.util;

import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadTaskDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.LeadService;

class TaskRemainderUtilTest {

	@Mock
	CallDaoService callDaoService;

	@Mock
	VisitDaoService visitDaoService;

	@Mock
	MeetingDaoService meetingDaoService;

	@Mock
	LeadTaskDaoService leadTaskDaoService;

	@Mock
	LeadDaoService leadDaoService;

	@Mock
	EmailDaoService emailDaoService;

	@Mock
	EmployeeDaoService employeeDaoService;

	@Mock
	TaskNotificationsUtil taskNotificationsUtil;

	@Mock
	LeadService leadService;
	@Mock
	EmailUtil emailUtil;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	TaskRemainderUtil taskRemainderUtil;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(taskRemainderUtil).build();
	}

	@Test
	void testReminderForTask() throws Exception {
		LocalDate todayAsDate = LocalDate.now();
		when(callDaoService.getTodaysCallTask(any(), any())).thenReturn(createMockCallTaskList());
		when(visitDaoService.getTodaysAllVisitTask(any(), any())).thenReturn(createMockVisitTaskList());
		when(meetingDaoService.getTodaysMeetingTask(any(), any())).thenReturn(createMockMeetingTaskList());
		when(leadTaskDaoService.getTodaysLeadTask(any(), any())).thenReturn(createMockLeadTaskList());
		when(leadDaoService.getFollowUpLeads(any(), any())).thenReturn(createMockLeaList());
		when(callDaoService.getCallTaskById(any())).thenReturn(Optional.of(new PhoneCallTask()));
		when(visitDaoService.getVisitTaskById(any())).thenReturn(Optional.of(new VisitTask()));
		when(meetingDaoService.getMeetingTaskById(any())).thenReturn(Optional.of(new MeetingTask()));
		when(leadTaskDaoService.getTaskById(any())).thenReturn(Optional.of(new LeadTask()));
		when(leadDaoService.getLeadById(any())).thenReturn(Optional.of(new Leads()));
		LocalDateTime currentTime = now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime();
		String time = currentTime.format(ofPattern("HH:mm"));
		taskRemainderUtil.reminderForTask();
		verify(callDaoService).getTodaysCallTask(todayAsDate, time);
	}

	private List<PhoneCallTask> createMockCallTaskList() {
		List<PhoneCallTask> callTaskList = new ArrayList<>();
		EmployeeMaster employeeMaster = new EmployeeMaster();
		Call call = new Call();
		Leads lead = new Leads();
		List<Contacts> contacts = new ArrayList<>();
		Contacts contact = new Contacts();
		contact.setPrimary(true);
		contact.setFirstName("test");
		contact.setLastName("testdata");
		lead.setLeadId(1);
		contacts.add(contact);
		lead.setContacts(contacts);
		employeeMaster.setStaffId(1477);
		PhoneCallTask callTask1 = new PhoneCallTask();
		callTask1.setCallTaskId(1);
		callTask1.setRemainderVia("Both");
		employeeMaster.setEmailId("s.wakankar@rnt.ai");
		callTask1.setAssignTo(employeeMaster);
		callTask1.setCreatedBy(1477);
		callTask1.setSubject("test");
		callTask1.setDueDate(LocalDate.now());
		call.setCallId(1);
		call.setLead(lead);
		callTask1.setCall(call);
		callTask1.setRemainderVia("Both");
		callTaskList.add(callTask1);
		return callTaskList;
	}

	private List<VisitTask> createMockVisitTaskList() {
		List<VisitTask> visitTaskList = new ArrayList<>();
		EmployeeMaster employeeMaster = new EmployeeMaster();
		Visit visit = new Visit();
		Leads lead = new Leads();
		List<Contacts> contacts = new ArrayList<>();
		Contacts contact = new Contacts();
		contact.setPrimary(true);
		contact.setFirstName("test");
		contact.setLastName("testdata");
		lead.setLeadId(1);
		contacts.add(contact);
		lead.setContacts(contacts);
		employeeMaster.setStaffId(1477);
		VisitTask visitTask1 = new VisitTask();
		visitTask1.setVisitTaskId(1);
		visitTask1.setRemainderVia("Both");
		employeeMaster.setEmailId("s.wakankar@rnt.ai");
		visitTask1.setAssignTo(employeeMaster);
		visitTask1.setCreatedBy(1477);
		visitTask1.setSubject("test");
		visitTask1.setDueDate(LocalDate.now());
		visit.setVisitId(1);
		visit.setLead(lead);
		visitTask1.setVisit(visit);
		visitTask1.setRemainderVia("Both");
		visitTaskList.add(visitTask1);
		return visitTaskList;
	}

	private List<MeetingTask> createMockMeetingTaskList() {
		List<MeetingTask> meetingTaskList = new ArrayList<>();
		EmployeeMaster employeeMaster = new EmployeeMaster();
		Meetings meeting = new Meetings();
		Leads lead = new Leads();
		List<Contacts> contacts = new ArrayList<>();
		Contacts contact = new Contacts();
		contact.setPrimary(true);
		contact.setFirstName("test");
		contact.setLastName("testdata");
		lead.setLeadId(1);
		contacts.add(contact);
		lead.setContacts(contacts);
		employeeMaster.setStaffId(1477);
		MeetingTask meetingTask1 = new MeetingTask();
		meetingTask1.setMeetingTaskId(1);
		meetingTask1.setRemainderVia("Both");
		employeeMaster.setEmailId("s.wakankar@rnt.ai");
		meetingTask1.setAssignTo(employeeMaster);
		meetingTask1.setCreatedBy(1477);
		meetingTask1.setSubject("test");
		meetingTask1.setDueDate(LocalDate.now());
		meeting.setMeetingId(1);
		meeting.setLead(lead);
		meetingTask1.setMeetings(meeting);
		meetingTask1.setRemainderVia("Both");
		meetingTaskList.add(meetingTask1);
		return meetingTaskList;
	}

	private List<LeadTask> createMockLeadTaskList() {
		List<LeadTask> leadTaskList = new ArrayList<>();
		EmployeeMaster employeeMaster = new EmployeeMaster();
		Leads lead = new Leads();
		List<Contacts> contacts = new ArrayList<>();
		Contacts contact = new Contacts();
		contact.setPrimary(true);
		contact.setFirstName("test");
		contact.setLastName("testdata");
		lead.setLeadId(1);
		contacts.add(contact);
		lead.setContacts(contacts);
		employeeMaster.setStaffId(1477);
		LeadTask leadTask1 = new LeadTask();
		leadTask1.setLeadTaskId(1);
		leadTask1.setRemainderVia("Both");
		employeeMaster.setEmailId("s.wakankar@rnt.ai");
		leadTask1.setAssignTo(employeeMaster);
		leadTask1.setCreatedBy(1477);
		leadTask1.setSubject("test");
		leadTask1.setDueDate(LocalDate.now());
		leadTask1.setLead(lead);
		leadTask1.setRemainderVia("Both");
		leadTaskList.add(leadTask1);
		return leadTaskList;
	}

	private List<Leads> createMockLeaList() {
		List<Leads> leadList = new ArrayList<>();
		EmployeeMaster employeeMaster = new EmployeeMaster();
		Leads lead = new Leads();
		List<Contacts> contacts = new ArrayList<>();
		Contacts contact = new Contacts();
		contact.setPrimary(true);
		contact.setFirstName("test");
		contact.setLastName("testdata");
		lead.setLeadId(1);
		contacts.add(contact);
		lead.setContacts(contacts);
		employeeMaster.setStaffId(1477);
		lead.setRemainderVia("Both");
		lead.setTopic("test");
		employeeMaster.setEmailId("s.wakankar@rnt.ai");
		lead.setCreatedBy(1477);
		lead.setEmployee(employeeMaster);
		lead.setRemainderDueOn(LocalDate.now());
		lead.setRemainderVia("Both");
		leadList.add(lead);
		return leadList;
	}

	@Test
    void testCallNotificationException() {
		when(callDaoService.getCallTaskById(1)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> taskRemainderUtil.callNotification(123));
	}

	@Test
	void testVisitNotificationException() {
		when(visitDaoService.getVisitTaskById(1)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> taskRemainderUtil.visitNotification(123));
	}

	@Test
	void testMeetingNotificationException() {
		when(meetingDaoService.getMeetingTaskById(1)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> taskRemainderUtil.meetingNotification(123));
	}

	@Test
	void testLeadNotificationException() {
		when(leadTaskDaoService.getTaskById(1)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> taskRemainderUtil.leadNotification(123));
	}

	@Test
	void testFollowUpLeadNotificationException() {
		when(leadDaoService.getLeadById(1)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> taskRemainderUtil.followUpLeadNotification(123));
	}
}
