package ai.rnt.crm.util;

import static java.time.LocalDate.now;
import static javax.mail.Message.RecipientType.TO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;

import ai.rnt.crm.entity.Attachment;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.OpportunityTask;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.exception.CRMException;

class EmailUtilTest {

	@Autowired
	MockMvc mockMvc;

	@Mock
	private Environment environment;

	@Mock
	private MimeMessage mimeMessage;

	@Mock
	private JavaMailSender emailSender;

	@InjectMocks
	EmailUtil emailUtil;

	@Captor
	private ArgumentCaptor<MimeMessage> mimeMessageCaptor;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
		emailUtil.setUserName("test@example.com");
	}

	@Test
	void testFormatDateException() {
		Throwable exception = assertThrows(CRMException.class, () -> {
			EmailUtil.formatDate(null);
		});
		String expectedErrorMessage = "Got Exception while converting task due date..null";
		assertFalse(exception.getMessage().contains(expectedErrorMessage));
	}

	@Test
	void testEmailWithoutAttachment() {
		String[] to = { "recipient@example.com" };
		String[] cc = {};
		String[] bcc = {};
		String subject = "Test Subject";
		String content = "Test Content";
		assertDoesNotThrow(() -> {
			emailUtil.emailWithoutAttachment(to, cc, bcc, subject, content);
		});
		verify(emailSender, times(1)).createMimeMessage();
		verify(emailSender, times(1)).send(mimeMessage);
	}

	@Test
	void testEmailWithAttachment() throws MessagingException {
		String[] to = { "recipient@example.com" };
		String[] cc = {};
		String[] bcc = {};
		String subject = "Test Subject with Attachment";
		String content = "Test Content with Attachment";
		Attachment attachment = new Attachment();
		attachment.setAttachType("application/pdf");
		attachment.setAttachName("test.pdf");
		attachment.setAttachmentData("data:application/pdf;base64,SGVsbG8gd29ybGQ=");
		List<Attachment> attachments = Collections.singletonList(attachment);
		assertDoesNotThrow(() -> {
			emailUtil.emailWithAttachment(to, cc, bcc, subject, content, attachments);
		});
		verify(emailSender, times(1)).createMimeMessage();
		verify(emailSender, times(1)).send(mimeMessage);
	}

	@Test
	void testSendEmailAllFieldsSet() throws AddressException {
		Email email = new Email();
		email.setToMail("recipient@example.com");
		email.setCcMail("cc@example.com");
		email.setBccMail("bcc@example.com");
		email.setSubject("Test Subject");
		email.setContent("Test Content");
		email.setAttachment(Collections.emptyList());
		boolean result = emailUtil.sendEmail(email);
		assertTrue(result);
		verify(emailSender, times(1)).send(mimeMessage);
	}

	@Test
	void testSendEmailNoRecipients() throws AddressException {
		Email email = new Email();
		email.setToMail(null); // No recipients
		email.setSubject("Test Subject");
		email.setContent("Test Content");
		boolean result = emailUtil.sendEmail(email);
		assertTrue(result);
		verify(emailSender, times(1)).send(mimeMessage);
	}

	@Test
	void testSendEmailWithAttachments() throws AddressException {
		Email email = new Email();
		email.setToMail("recipient@example.com");
		email.setSubject("Test Subject with Attachment");
		email.setContent("Test Content with Attachment");
		Attachment attachment = new Attachment();
		attachment.setAttachType("application/pdf");
		attachment.setAttachName("test.pdf");
		attachment.setAttachmentData("data:application/pdf;base64,SGVsbG8gd29ybGQ="); // Example Base64 data
		email.setAttachment(Collections.singletonList(attachment));
		boolean result = emailUtil.sendEmail(email);
		assertTrue(result);
		verify(emailSender, times(1)).send(mimeMessage);
	}

	@Test
	void testSendEmailExceptionThrown() throws AddressException {
		Email email = new Email();
		email.setToMail("recipient@example.com");
		email.setSubject("Test Subject");
		email.setContent("Test Content");
		doThrow(new RuntimeException("Simulated Exception")).when(emailSender).send(any(MimeMessage.class));
		boolean result = emailUtil.sendEmail(email);
		assertFalse(result);
		verify(emailSender, times(1)).send(mimeMessage);
	}

	@Test
	void testSendCallTaskReminderMailValidInputs() throws MessagingException {
		PhoneCallTask callTask = createMockPhoneCallTask();
		String emailId = "additional@example.com";
		MimeMessage mimeMessage = mock(MimeMessage.class);
		when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
		assertDoesNotThrow(() -> emailUtil.sendCallTaskReminderMail(callTask, emailId));
		verify(emailSender, times(1)).send(mimeMessageCaptor.capture());

	}

	private PhoneCallTask createMockPhoneCallTask() {
		PhoneCallTask task = mock(PhoneCallTask.class);
		Call call = new Call();
		Leads leads = new Leads();
		Contacts contacts = new Contacts();
		List<Contacts> con = new ArrayList<>();
		contacts.setContactId(1);
		contacts.setPrimary(true);
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setEmailId("test@gmail.com");
		employeeMaster.setStaffId(1375);
		con.add(contacts);
		leads.setLeadId(1);
		leads.setContacts(con);
		call.setCallId(1);
		call.setLead(leads);
		task.setCall(call);
		when(task.getAssignTo()).thenReturn(employeeMaster);
		when(task.getCall()).thenReturn(call);
		when(task.getSubject()).thenReturn("Test Subject");
		when(task.getDueDate()).thenReturn(now());
		when(task.getDueTime12Hours()).thenReturn("12:00 PM");
		return task;
	}

	@Test
	void testSendCallTaskReminderMailExceptionHandling() {
		PhoneCallTask callTask = createMockPhoneCallTask();
		String emailId = "additional@example.com";
		doThrow(new RuntimeException("Simulated exception")).when(emailSender).send(any(MimeMessage.class));
		CRMException exception = assertThrows(CRMException.class,
				() -> emailUtil.sendCallTaskReminderMail(callTask, emailId));
		assertEquals("Simulated exception", exception.getCause().getMessage());
	}

	@Test
	void testSendVisitTaskReminderMailValidInputs() throws MessagingException {
		VisitTask visitTask = createMockVisitTask();
		String emailId = "additional@example.com";
		MimeMessage mimeMessage = mock(MimeMessage.class);
		when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
		assertDoesNotThrow(() -> emailUtil.sendVisitTaskReminderMail(visitTask, emailId));
		verify(emailSender, times(1)).send(mimeMessageCaptor.capture());
	}

	private VisitTask createMockVisitTask() {
		VisitTask task = mock(VisitTask.class);
		Visit visit = new Visit();
		Leads leads = new Leads();
		Contacts contacts = new Contacts();
		List<Contacts> con = new ArrayList<>();
		contacts.setContactId(1);
		contacts.setPrimary(true);
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setEmailId("test@gmail.com");
		employeeMaster.setStaffId(1375);
		con.add(contacts);
		leads.setLeadId(1);
		leads.setContacts(con);
		visit.setVisitId(1);
		visit.setLead(leads);
		task.setVisit(visit);
		when(task.getAssignTo()).thenReturn(employeeMaster);
		when(task.getVisit()).thenReturn(visit);
		when(task.getSubject()).thenReturn("Test Subject");
		when(task.getDueDate()).thenReturn(now());
		when(task.getDueTime12Hours()).thenReturn("12:00 PM");
		return task;
	}

	@Test
	void testSendVisitTaskReminderMailExceptionHandling() {
		VisitTask visitTask = createMockVisitTask();
		String emailId = "additional@example.com";
		doThrow(new RuntimeException("Simulated exception")).when(emailSender).send(any(MimeMessage.class));
		CRMException exception = assertThrows(CRMException.class,
				() -> emailUtil.sendVisitTaskReminderMail(visitTask, emailId));
		assertEquals("Simulated exception", exception.getCause().getMessage());
	}

	@Test
	void testSendMeetingTaskReminderMailValidInputs() throws MessagingException {
		MeetingTask meetingTask = createMockMeetingTask();
		String emailId = "additional@example.com";
		MimeMessage mimeMessage = mock(MimeMessage.class);
		when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
		assertDoesNotThrow(() -> emailUtil.sendMeetingTaskReminderMail(meetingTask, emailId));
		verify(emailSender, times(1)).send(mimeMessageCaptor.capture());
	}

	private MeetingTask createMockMeetingTask() {
		MeetingTask task = mock(MeetingTask.class);
		Meetings meeting = new Meetings();
		Leads leads = new Leads();
		Contacts contacts = new Contacts();
		List<Contacts> con = new ArrayList<>();
		contacts.setContactId(1);
		contacts.setPrimary(true);
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setEmailId("test@gmail.com");
		employeeMaster.setStaffId(1375);
		con.add(contacts);
		leads.setLeadId(1);
		leads.setContacts(con);
		meeting.setMeetingId(1);
		meeting.setLead(leads);
		task.setMeetings(meeting);
		when(task.getAssignTo()).thenReturn(employeeMaster);
		when(task.getMeetings()).thenReturn(meeting);
		when(task.getSubject()).thenReturn("Test Subject");
		when(task.getDueDate()).thenReturn(now());
		when(task.getDueTime12Hours()).thenReturn("12:00 PM");
		return task;
	}

	@Test
	void testSendMeetingTaskReminderMailExceptionHandling() {
		MeetingTask meetingTask = createMockMeetingTask();
		String emailId = "additional@example.com";
		doThrow(new RuntimeException("Simulated exception")).when(emailSender).send(any(MimeMessage.class));
		CRMException exception = assertThrows(CRMException.class,
				() -> emailUtil.sendMeetingTaskReminderMail(meetingTask, emailId));
		assertEquals("Simulated exception", exception.getCause().getMessage());
	}

	@Test
	void testSendLeadTaskReminderMailValidInputs() throws MessagingException {
		LeadTask leadTask = createMockLeadTask();
		String emailId = "additional@example.com";
		MimeMessage mimeMessage = mock(MimeMessage.class);
		when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
		assertDoesNotThrow(() -> emailUtil.sendLeadTaskReminderMail(leadTask, emailId));
		verify(emailSender, times(1)).send(mimeMessageCaptor.capture());
	}

	private LeadTask createMockLeadTask() {
		LeadTask task = mock(LeadTask.class);
		Leads leads = new Leads();
		Contacts contacts = new Contacts();
		List<Contacts> con = new ArrayList<>();
		contacts.setContactId(1);
		contacts.setPrimary(true);
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setEmailId("test@gmail.com");
		employeeMaster.setStaffId(1375);
		con.add(contacts);
		leads.setLeadId(1);
		leads.setContacts(con);
		when(task.getAssignTo()).thenReturn(employeeMaster);
		when(task.getLead()).thenReturn(leads);
		when(task.getSubject()).thenReturn("Test Subject");
		when(task.getDueDate()).thenReturn(now());
		when(task.getDueTime12Hours()).thenReturn("12:00 PM");
		return task;
	}

	@Test
	void testSendLeadTaskReminderMailExceptionHandling() {
		LeadTask meetingTask = createMockLeadTask();
		String emailId = "additional@example.com";
		doThrow(new RuntimeException("Simulated exception")).when(emailSender).send(any(MimeMessage.class));
		CRMException exception = assertThrows(CRMException.class,
				() -> emailUtil.sendLeadTaskReminderMail(meetingTask, emailId));
		assertEquals("Simulated exception", exception.getCause().getMessage());
	}

	@Test
	void testSendOptyTaskReminderMailValidInputs() throws MessagingException {
		OpportunityTask optyTask = createMockOptyTask();
		String emailId = "additional@example.com";
		MimeMessage mimeMessage = mock(MimeMessage.class);
		when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
		assertDoesNotThrow(() -> emailUtil.sendOptyTaskReminderMail(optyTask, emailId));
		verify(emailSender, times(1)).send(mimeMessageCaptor.capture());
	}

	private OpportunityTask createMockOptyTask() {
		OpportunityTask task = mock(OpportunityTask.class);
		Leads leads = new Leads();
		Opportunity opportunity = new Opportunity();
		Contacts contacts = new Contacts();
		List<Contacts> con = new ArrayList<>();
		contacts.setContactId(1);
		contacts.setPrimary(true);
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setEmailId("test@gmail.com");
		employeeMaster.setStaffId(1375);
		con.add(contacts);
		leads.setLeadId(1);
		leads.setContacts(con);
		opportunity.setLeads(leads);
		when(task.getAssignTo()).thenReturn(employeeMaster);
		when(task.getOpportunity()).thenReturn(opportunity);
		when(task.getSubject()).thenReturn("Test Subject");
		when(task.getDueDate()).thenReturn(now());
		when(task.getDueTime12Hours()).thenReturn("12:00 PM");
		return task;
	}

	@Test
	void testSendOptyTaskReminderMailExceptionHandling() {
		OpportunityTask opportunityTask = createMockOptyTask();
		String emailId = "additional@example.com";
		doThrow(new RuntimeException("Simulated exception")).when(emailSender).send(any(MimeMessage.class));
		CRMException exception = assertThrows(CRMException.class,
				() -> emailUtil.sendOptyTaskReminderMail(opportunityTask, emailId));
		assertEquals("Simulated exception", exception.getCause().getMessage());
	}

	@Test
	void testSendLeadAssignMailValidInput() throws MessagingException {
		Leads leads = createMockLeads();
		MimeMessage mimeMessage = mock(MimeMessage.class);
		when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
		emailUtil.sendLeadAssignMail(leads);
		verify(emailSender, times(1)).send(mimeMessageCaptor.capture());
		MimeMessage capturedMessage = mimeMessageCaptor.getValue();
		verify(capturedMessage).setRecipients(eq(TO), any(InternetAddress[].class));
	}

	private Leads createMockLeads() {
		Leads leads = mock(Leads.class);
		EmployeeMaster employee = new EmployeeMaster();
		List<Contacts> con = new ArrayList<>();
		employee.setStaffId(1375);
		employee.setEmailId("test@gmail.com");
		when(leads.getEmployee()).thenReturn(employee);
		Contacts contact = mock(Contacts.class);
		when(contact.getPrimary()).thenReturn(true);
		when(contact.getFirstName()).thenReturn("Jane");
		when(contact.getLastName()).thenReturn("Smith");
		when(contact.getContactNumberPrimary()).thenReturn("123-456-7890");
		con.add(contact);
		LeadSourceMaster leadSourceMaster = mock(LeadSourceMaster.class);
		when(leadSourceMaster.getSourceName()).thenReturn("Referral");
		when(leads.getContacts()).thenReturn(con);
		when(leads.getLeadSourceMaster()).thenReturn(leadSourceMaster);
		when(leads.getAssignBy()).thenReturn(employee);
		when(leads.getAssignDate()).thenReturn(now());
		return leads;
	}

	@Test
	void testSendOptyAssignMailValidInput() throws MessagingException {
		Opportunity opportunity = createMockOpportunity();
		MimeMessage mimeMessage = mock(MimeMessage.class);
		when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
		emailUtil.sendOptyAssignMail(opportunity);
		verify(emailSender, times(1)).send(mimeMessageCaptor.capture());
		MimeMessage capturedMessage = mimeMessageCaptor.getValue();
		verify(capturedMessage).setRecipients(eq(TO), any(InternetAddress[].class));
	}

	private Opportunity createMockOpportunity() {
		Leads leads = mock(Leads.class);
		Opportunity opportunity = mock(Opportunity.class);
		EmployeeMaster employee = new EmployeeMaster();
		List<Contacts> con = new ArrayList<>();
		employee.setStaffId(1375);
		employee.setEmailId("test@gmail.com");
		when(opportunity.getEmployee()).thenReturn(employee);
		Contacts contact = mock(Contacts.class);
		when(contact.getPrimary()).thenReturn(true);
		when(contact.getFirstName()).thenReturn("Jane");
		when(contact.getLastName()).thenReturn("Smith");
		when(contact.getContactNumberPrimary()).thenReturn("123-456-7890");
		con.add(contact);
		when(opportunity.getLeads()).thenReturn(leads);
		when(opportunity.getLeads().getContacts()).thenReturn(con);
		LeadSourceMaster leadSourceMaster = mock(LeadSourceMaster.class);
		when(leadSourceMaster.getSourceName()).thenReturn("Referral");
		when(leads.getContacts()).thenReturn(con);
		when(leads.getLeadSourceMaster()).thenReturn(leadSourceMaster);
		when(opportunity.getAssignBy()).thenReturn(employee);
		when(opportunity.getAssignDate()).thenReturn(now());
		return opportunity;
	}
}
