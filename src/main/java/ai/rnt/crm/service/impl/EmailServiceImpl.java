package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.AttachmentDtoMapper.TO_ATTACHMENT;
import static ai.rnt.crm.dto_mapper.EmailDtoMapper.TO_EMAIL;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.EnumMap;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.AttachmentDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.AttachmentDto;
import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.entity.AddEmail;
import ai.rnt.crm.entity.Attachment;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmailService;
import ai.rnt.crm.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sanket Wakankar
 * @version 1.0
 * @since 12/09/2023.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

	private final EmailDaoService emailDaoService;
	private final LeadDaoService leadDaoService;
	private final AttachmentDaoService attachmentDaoService;

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> addEmail(EmailDto dto, Integer leadId, String status) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			boolean saveStatus = false;
			AddEmail sendEmail = null;
			
			AddEmail addEmail = TO_EMAIL.apply(dto).orElseThrow(ResourceNotFoundException::new);
			Integer addEmailId=dto.getAddMailId();
			if ("send".equalsIgnoreCase(status) && nonNull(addEmailId))
				sendEmail =emailDaoService.findById(addEmail.getAddMailId());
			else {
			addEmail.setToMail(dto.getMailTo().stream().collect(Collectors.joining(",")));
			addEmail.setBccMail(dto.getBcc().stream().collect(Collectors.joining(",")));
			addEmail.setCcMail(dto.getCc().stream().collect(Collectors.joining(",")));
			leadDaoService.getLeadById(leadId).ifPresent(addEmail::setLead);
			addEmail.setAddMailId(null);
			if (dto.getAttachment().isEmpty()) {
				sendEmail = emailDaoService.addEmail(addEmail);
				saveStatus = nonNull(sendEmail);
			} else {
				for (AttachmentDto attach : dto.getAttachment()) {
					Attachment attachment = TO_ATTACHMENT.apply(attach).orElseThrow(ResourceNotFoundException::new);
					attachment.setMail(addEmail);
					Attachment addAttachment = attachmentDaoService.addAttachment(attachment);
					sendEmail = addAttachment.getMail();
					saveStatus = nonNull(addAttachment);
				}
			}
			}
			if (saveStatus && "save".equalsIgnoreCase(status)) {
				result.put(MESSAGE, "Email Added Successfully");
				result.put(DATA, sendEmail.getAddMailId());
			} else if ("send".equalsIgnoreCase(status)) {
				boolean sendEmailStatus = EmailUtil.sendEmail(sendEmail);
				if(nonNull(addEmailId) && sendEmailStatus)
					result.put(MESSAGE, "Email Sent Successfully!!");
				else if(saveStatus && sendEmailStatus)
					result.put(MESSAGE, "Email Saved and Sent Successfully!!");
				else 
					result.put(MESSAGE, "Problem while Sending Email!!");
			}else
				result.put(MESSAGE, "Email Not Added");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("error occured while sending and saving add email for the Lead Api..{}", e.getMessage());
			throw new CRMException(e);
		}

	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> checkMailId(Integer addMailId, Integer leadId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			if (Boolean.TRUE.equals(emailDaoService.emailPresentForLeadLeadId(addMailId, leadId))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "This email is already saved");
				result.put(DATA, addMailId);
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "This email is not saved");
			}
		} catch (Exception e) {
			log.error("error occured while checking add email for the Lead Id..{}", e.getMessage());
			throw new CRMException(e);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
