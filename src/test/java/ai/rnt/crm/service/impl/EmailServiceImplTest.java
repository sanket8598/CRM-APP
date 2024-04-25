package ai.rnt.crm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.AddressException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.dao.service.AttachmentDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.AttachmentDto;
import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.entity.Attachment;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;
import ai.rnt.crm.util.EmailUtil;

class EmailServiceImplTest {

	@InjectMocks
	private EmailServiceImpl emailServiceImpl;

	@Mock
	private EmailDaoService emailDaoService;

	@Mock
	private LeadDaoService leadDaoService;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private AuditAwareUtil auditAwareUtil;
	@Mock
	private EmailUtil emailUtil;

	@Mock
	private Leads lead;
	@Mock
	private Email email;
	@Mock
	private EmailDto emailDto;

	@Mock
	private AttachmentDaoService attachmentDaoService;

	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
		this.mockMvc = MockMvcBuilders.standaloneSetup(emailServiceImpl).build();
	}

	@Test
	void assignEmailTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("addMailId", 1);
		Email email = new Email();
		EmployeeMaster employee = new EmployeeMaster();
		when(emailDaoService.findById(1)).thenReturn(email);
		when(employeeService.getById(1477)).thenReturn(java.util.Optional.of(employee));
		when(emailDaoService.email(email)).thenReturn(email);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = emailServiceImpl.assignEmail(map);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Email Assigned Successfully", responseEntity.getBody().get(ApiResponse.MESSAGE));
		assertEquals(true, responseEntity.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	void notAssignEmailTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("addMailId", 1);
		Email email = new Email();
		EmployeeMaster employee = new EmployeeMaster();
		when(emailDaoService.findById(1)).thenReturn(email);
		when(employeeService.getById(1477)).thenReturn(Optional.of(employee));
		when(emailDaoService.email(email)).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = emailServiceImpl.assignEmail(map);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Email Not Assigned", responseEntity.getBody().get(ApiResponse.MESSAGE));
		assertEquals(false, responseEntity.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	void assignMailTestException() {
		Map<String, Integer> map = new HashMap<>();
		map.put("staffId", 1477);
		map.put("meetingId", 1);
		when(emailDaoService.findById(1)).thenThrow(ResourceNotFoundException.class);
		assertThrows(CRMException.class, () -> emailServiceImpl.assignEmail(map));
	}

	@Test
    void addEmailSuccessWithoutAttachments() {
        when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(lead));
        when(emailDaoService.email(any(Email.class))).thenReturn(email);

        ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.addEmail(emailDto, 1, "SAVE");

        verify(emailDaoService, times(1)).email(any(Email.class));
        assertEquals(201, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
    }

	@Test
	void sendEmailSuccessWithoutAttachments() throws AddressException {
		when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(lead));
		when(emailDaoService.email(any(Email.class))).thenReturn(email);
		when(emailUtil.sendEmail(any(Email.class))).thenReturn(true);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.addEmail(emailDto, 1, "SEND");
		assertEquals(201, response.getStatusCodeValue());
	}

	@Test
    void addEmailExceptionHandling() {
        when(leadDaoService.getLeadById(anyInt())).thenThrow(new RuntimeException("Database Error"));
        Exception exception = assertThrows(CRMException.class, () ->
        emailServiceImpl.addEmail(emailDto, 1, "SAVE"));
        assertEquals("Database Error", exception.getCause().getMessage());
    }

	@Test
	void whenEmailExistsForLeadId_thenSuccessResponse() {
		Integer addMailId = 1;
		Integer leadId = 100;
		when(emailDaoService.emailPresentForLeadLeadId(addMailId, leadId)).thenReturn(true);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.checkMailId(addMailId, leadId);
		assertEquals(200, response.getStatusCodeValue());
		assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("This email is already saved", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void whenEmailExistsForLeadId_thenErrorResponse() {
		Integer addMailId = 1;
		Integer leadId = 100;
		when(emailDaoService.emailPresentForLeadLeadId(addMailId, leadId)).thenReturn(false);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.checkMailId(addMailId, leadId);
		assertEquals(200, response.getStatusCodeValue());
		assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
	}

	@Test
	void whenExceptionOccurs_thenThrowsCRMException() {
		Integer addMailId = 3;
		Integer leadId = 102;
		when(emailDaoService.emailPresentForLeadLeadId(addMailId, leadId))
				.thenThrow(new RuntimeException("Database error"));
		Exception exception = assertThrows(CRMException.class, () -> emailServiceImpl.checkMailId(addMailId, leadId));
		assertNotNull(exception);
		assertEquals("Database error", exception.getCause().getMessage());
	}

	@Test
	void whenEmailExists_thenSuccessResponse() {
		Integer mailId = 1;
		Email emails = new Email();
		emails.setBccMail("s.waknkar@rnt.ai,nik.gaikwad@rnt.ai");
		emails.setCcMail("s.waknkar@rnt.ai,nik.gaikwad@rnt.ai");
		emails.setToMail("s.waknkar@rnt.ai,nik.gaikwad@rnt.ai");
		when(emailDaoService.findById(mailId)).thenReturn(emails);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.getEmail(mailId);
		assertEquals(200, response.getStatusCodeValue());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertTrue(response.getBody().containsKey(ApiResponse.DATA));
	}

	@Test
	void whenEmailExistsWithEmptyData() {
		Integer mailId = 1;
		when(emailDaoService.findById(mailId)).thenReturn(email);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.getEmail(mailId);
		assertEquals(200, response.getStatusCodeValue());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertTrue(response.getBody().containsKey(ApiResponse.DATA));
	}

	@Test
	void getEmailExceptionTest() {
		Integer mailId = 3;
		when(emailDaoService.findById(mailId)).thenThrow(new RuntimeException("Unexpected Error"));
		Exception exception = assertThrows(CRMException.class, () -> emailServiceImpl.getEmail(mailId));
		assertNotNull(exception);
		assertEquals("Unexpected Error", exception.getCause().getMessage());
	}

	@Test
	void deleteEmailwithAttachmentssuccess() {
		Integer mailId = 1;
		Attachment attachment = new Attachment();
		List<Attachment> dto = new ArrayList<>();
		dto.add(attachment);
		email.setAttachment(dto);
		when(emailDaoService.findById(mailId)).thenReturn(email);
		when(emailDaoService.email(email)).thenReturn(email);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.deleteEmail(mailId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Email deleted Successfully.", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void deleteEmailSuccessWithAttachments() {
	    when(emailDaoService.findById(anyInt())).thenReturn(email);
	    when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
	    Attachment attachmentMock = mock(Attachment.class);
	    List<Attachment> attachments = Arrays.asList(attachmentMock); 
	    when(attachmentMock.getMail()).thenReturn(new Email());
	    when(email.getAttachment()).thenReturn(attachments);
	    ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.deleteEmail(1);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertEquals("Email Not deleted.", response.getBody().get(ApiResponse.MESSAGE));
	    verify(attachmentMock, times(1)).setDeletedBy(anyInt());
	    verify(attachmentMock, times(1)).setDeletedDate(any());
	    verify(attachmentDaoService, times(1)).addAttachment(attachmentMock);
	    verify(emailDaoService, times(1)).email(email);
	}

	@Test
	void deleteEmailExceptionThrown() {
		Integer mailId = 1;
		when(emailDaoService.findById(mailId)).thenThrow(new RuntimeException("Database error"));
		assertThrows(CRMException.class, () -> emailServiceImpl.deleteEmail(mailId));
	}

	@Test
	void updateEmailExceptionThrown() {
		Integer mailId = 1;
		when(emailDaoService.findById(mailId)).thenThrow(new RuntimeException("Database error"));
		assertThrows(CRMException.class, () -> emailServiceImpl.updateEmail(emailDto, "SEND", mailId));
	}

	@Test
    void updateEmailSuccessNoAttachments() {
        when(emailDaoService.findById(anyInt())).thenReturn(email);
        when(emailDaoService.email(any(Email.class))).thenReturn(email);
        when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
        Attachment attachment1 = new Attachment();
        Attachment attachment2 = new Attachment();
        List<Attachment> attachments = Arrays.asList(attachment1, attachment2); 
        when(email.getAttachment()).thenReturn(attachments);
        ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.updateEmail(emailDto, "SAVE", 1);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
        assertEquals("Email Updated Successfully", response.getBody().get(ApiResponse.MESSAGE));
        verify(emailDaoService, times(1)).email(any(Email.class));
        verify(attachmentDaoService, times(attachments.size())).addAttachment(any(Attachment.class));
    }

	@Test
    void updateEmailSuccessSend() throws AddressException {
        when(emailDaoService.findById(anyInt())).thenReturn(email);
        when(emailDaoService.email(any(Email.class))).thenReturn(email);
        when(emailUtil.sendEmail(any(Email.class))).thenReturn(true);
        when(attachmentDaoService.addAttachment(any(Attachment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
        ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.updateEmail(emailDto, "SEND", 1);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(emailUtil, times(1)).sendEmail(any(Email.class));
        verify(attachmentDaoService, never()).addAttachment(any(Attachment.class));
    }

	@Test
    void updateEmailSuccessSendWithAttachments() throws ResourceNotFoundException, AddressException {
        when(emailDaoService.findById(anyInt())).thenReturn(email);
        when(emailDaoService.email(any(Email.class))).thenReturn(email);
        when(attachmentDaoService.addAttachment(any(Attachment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
        Attachment attachmentMock = mock(Attachment.class);
        doNothing().when(attachmentMock).setMail(email);
        AttachmentDto attachmentDto1 = new AttachmentDto();
        AttachmentDto attachmentDto2 = new AttachmentDto();
        List<AttachmentDto> attachmentDtos = Arrays.asList(attachmentDto1, attachmentDto2); 
        when(emailDto.getAttachment()).thenReturn(attachmentDtos);
        ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.updateEmail(emailDto, "SEND", 1);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(attachmentDaoService, times(attachmentDtos.size())).addAttachment(any(Attachment.class));
    }

	@Test
	void updateEmailFailureStatusNotSend() {
	    when(emailDaoService.findById(anyInt())).thenReturn(email);
	    when(emailDaoService.email(any(Email.class))).thenReturn(email);
	    when(auditAwareUtil.getLoggedInStaffId()).thenReturn(1);
	    ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.updateEmail(emailDto, "SOME_OTHER_STATUS", 1);
	    assertEquals(HttpStatus.CREATED, response.getStatusCode());
	    assertFalse((Boolean) response.getBody().get(ApiResponse.SUCCESS));
	    assertEquals("Email Not Updated", response.getBody().get(ApiResponse.MESSAGE));
	}

	@Test
	void addEmailSuccessWithAttachments() throws ResourceNotFoundException {
	    when(attachmentDaoService.addAttachment(any(Attachment.class))).thenAnswer(invocation -> invocation.getArgument(0));
	    when(emailDaoService.email(any(Email.class))).thenReturn(email);
	    List<AttachmentDto> attachmentDtos = new ArrayList<>();
	    AttachmentDto attachmentDto1 = new AttachmentDto();
	    attachmentDtos.add(attachmentDto1);
         EmailDto dto = new EmailDto();
         List<String> mailTo = new ArrayList<>();
          mailTo.add("testmail@rnt.ai");
          dto.setMailTo(mailTo);
           dto.setAttachment(attachmentDtos);
         int leadId = 1;
            String status = "Save";
	    ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.addEmail(dto, leadId, status);
	    assertEquals(HttpStatus.CREATED, response.getStatusCode());
	    assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
	    assertEquals("Email Added Successfully", response.getBody().get(ApiResponse.MESSAGE));
	    verify(attachmentDaoService, times(attachmentDtos.size())).addAttachment(any(Attachment.class));
	}
}
