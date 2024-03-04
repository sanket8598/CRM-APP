package ai.rnt.crm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

@ExtendWith(MockitoExtension.class)
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
	private AttachmentDaoService attachmentDaoService;

	// @Test
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
}
