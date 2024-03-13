package ai.rnt.crm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
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

	 //@Test
	void testAddEmail_Success_SaveStatusSave_WithAttachment() {
		// Arrange
		EmailDto dto = new EmailDto(); // Provide appropriate values for the DTO
		Integer leadId = 1;
		List<String> toMail = Arrays.asList("s.wakankar@rnt.ai", "t.pagare@rnt.ai");
		dto.setMailTo(toMail);
		List<String> ccMail = Arrays.asList("s.wakankar@rnt.ai", "t.pagare@rnt.ai");
		dto.setCc(ccMail);
		List<String> bccMail = Arrays.asList("s.wakankar@rnt.ai", "t.pagare@rnt.ai");
		dto.setBcc(bccMail);
		String status = "SAVE";
		// Set appropriate values
		when(leadDaoService.getLeadById(leadId)).thenReturn(java.util.Optional.of(new Leads())); // Provide appropriate
																									// lead object
		List<AttachmentDto> attachmentDtos = new ArrayList<>();
		AttachmentDto attachmentDto = new AttachmentDto();
		// Set up attachmentDto properties
		attachmentDtos.add(attachmentDto);
		dto.setAttachment(attachmentDtos);
		Attachment attachment = new Attachment(); // Provide appropriate attachment object
		when(attachmentDaoService.addAttachment(any(Attachment.class))).thenReturn(attachment);
		// Set up behavior for emailDaoService.email()
		Email sendEmail = new Email(); // Create a mock Email object
		sendEmail.setMailId(1);
		if (!dto.getAttachment().isEmpty()) {
			when(emailDaoService.email(any(Email.class))).thenReturn(new Email());
		} else {
			when(emailDaoService.email(any(Email.class))).thenReturn(sendEmail);
		}

		// Set up behavior for attachmentDaoService.addAttachment()
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.addEmail(dto, leadId, status);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
		assertEquals("Email Added Successfully", response.getBody().get(ApiResponse.MESSAGE));
		assertEquals(1, response.getBody().get(ApiResponse.DATA));
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
		assertEquals("Email Assigned SuccessFully", responseEntity.getBody().get(ApiResponse.MESSAGE));
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
	void sendEmailSuccessWithoutAttachments() {
		when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(lead));
		when(emailDaoService.email(any(Email.class))).thenReturn(email);
		
		ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.addEmail(emailDto, 1, "SEND");
		assertEquals(201, response.getStatusCodeValue());
	}
	
	@Test
    void addEmailSuccessWithAttachments() {
		EmailDto emailDto1=mock(EmailDto.class);
		List<AttachmentDto> dto=new ArrayList<>();
		AttachmentDto attach1=new AttachmentDto();
		attach1.setAttachName("abc");
		dto.add(attach1);
		emailDto1.setAttachment(dto);
        when(leadDaoService.getLeadById(anyInt())).thenReturn(Optional.of(lead));
        when(emailDaoService.email(any(Email.class))).thenReturn(email);
        when(attachmentDaoService.addAttachment(any(Attachment.class))).thenReturn(new Attachment());

        ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.addEmail(emailDto1, 1, "SAVE");

        assertEquals(201, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey(ApiResponse.SUCCESS));
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
        when(emailDaoService.emailPresentForLeadLeadId(addMailId, leadId)).thenThrow(new RuntimeException("Database error"));
        Exception exception = assertThrows(CRMException.class, () -> emailServiceImpl.checkMailId(addMailId, leadId));
        assertNotNull(exception);
        assertEquals("Database error", exception.getCause().getMessage());
    }
    
    @Test
    void whenEmailExists_thenSuccessResponse() {
        Integer mailId = 1;
        Email emails=new Email();
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
    void whenDeleteEmailWithAttachments_thenSuccess() {
        Integer mailId = 1;
        Attachment attachment = new Attachment(); 
        List<Attachment> dto=new ArrayList<>();
        dto.add(attachment);
        email.setAttachment(dto);
        when(emailDaoService.findById(mailId)).thenReturn(email);
        when(attachmentDaoService.addAttachment(any())).thenReturn(attachment);
        when(emailDaoService.email(email)).thenReturn(email);
        ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.deleteEmail(mailId);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
        assertEquals("Email deleted SuccessFully.", response.getBody().get(ApiResponse.MESSAGE));
    }

    @Test
    void deleteEmail_withAttachments_success() {
    	 Integer mailId = 1;
    	Attachment attachment = new Attachment(); 
        List<Attachment> dto=new ArrayList<>();
        dto.add(attachment);
        email.setAttachment(dto);
        when(emailDaoService.findById(mailId)).thenReturn(email);
        when(emailDaoService.email(email)).thenReturn(email); 
        ResponseEntity<EnumMap<ApiResponse, Object>> response = emailServiceImpl.deleteEmail(mailId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
        assertEquals("Email deleted SuccessFully.", response.getBody().get(ApiResponse.MESSAGE));
    }

}
