package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.constants.StatusConstants.SAVE;
import static ai.rnt.crm.constants.StatusConstants.SEND;
import static ai.rnt.crm.dto_mapper.AttachmentDtoMapper.TO_ATTACHMENT;
import static ai.rnt.crm.dto_mapper.EmailDtoMapper.TO_EMAIL;
import static ai.rnt.crm.dto_mapper.EmailDtoMapper.TO_EMAIL_DTO;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.EmailUtil.sendEmail;
import static java.lang.Boolean.TRUE;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.AttachmentDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.AttachmentDto;
import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.entity.Attachment;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.security.UserDetail;
import ai.rnt.crm.service.EmailService;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;
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
	private final EmployeeService employeeService;
	private final AuditAwareUtil auditAwareUtil;

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> addEmail(EmailDto dto, Integer leadId, String status) {
		log.info("inside the addEmail method...{} {}", leadId, status);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			boolean saveStatus = false;
			Email sendEmail = null;

			Email email = TO_EMAIL.apply(dto).orElseThrow(ResourceNotFoundException::new);
			email.setToMail(dto.getMailTo().stream().collect(Collectors.joining(",")));
			if (nonNull(dto.getBcc()) && !dto.getBcc().isEmpty())
				email.setBccMail(dto.getBcc().stream().collect(Collectors.joining(",")));
			if (nonNull(dto.getCc()) && !dto.getCc().isEmpty())
				email.setCcMail(dto.getCc().stream().collect(Collectors.joining(",")));
			email.setStatus(status);
			leadDaoService.getLeadById(leadId).ifPresent(email::setLead);
			if (dto.getAttachment().isEmpty()) {
				sendEmail = emailDaoService.email(email);
				saveStatus = nonNull(sendEmail);
			} else {
				for (AttachmentDto attach : dto.getAttachment()) {
					Attachment attachment = TO_ATTACHMENT.apply(attach).orElseThrow(ResourceNotFoundException::new);
					attachment.setMail(email);
					Attachment addAttachment = attachmentDaoService.addAttachment(attachment);
					sendEmail = addAttachment.getMail();
					saveStatus = nonNull(addAttachment);
				}
			}
			if (saveStatus && SAVE.equalsIgnoreCase(status)) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Email Added Successfully");
				result.put(DATA, sendEmail.getMailId());
			} else if (SEND.equalsIgnoreCase(status)) {
				boolean sendEmailStatus = sendEmail(sendEmail);
				if (saveStatus && sendEmailStatus) {
					result.put(SUCCESS, true);
					result.put(MESSAGE, "Email Saved and Sent Successfully!!");
				} else {
					result.put(SUCCESS, true);
					if (saveStatus && !sendEmailStatus) {
						email.setStatus(SAVE);
						emailDaoService.email(email);
						result.put(MESSAGE, "Email Saved but Problem while Sending Email!!");
					} else
						result.put(MESSAGE, "Problem Occured while saving the Email");
				}
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Email Not Added");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("error occured while sending and saving add email for the Lead Api..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> checkMailId(Integer addMailId, Integer leadId) {
		log.info("inside the checkMailId method...{} {}", addMailId, leadId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			if (TRUE.equals(emailDaoService.emailPresentForLeadLeadId(addMailId, leadId))) {
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
		return new ResponseEntity<>(result, OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteEmail(Integer mailId) {
		log.info("inside the deleteEmail method...{}", mailId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		Email updatedEmail = null;
		try {
			if (nonNull(getContext()) && nonNull(getContext().getAuthentication())
					&& nonNull(getContext().getAuthentication().getDetails())) {
				UserDetail details = (UserDetail) getContext().getAuthentication().getDetails();

				Email mail = emailDaoService.findById(mailId);
				List<Attachment> attachment = mail.getAttachment();
				if (nonNull(attachment) && !attachment.isEmpty()) {
					List<Attachment> attach = attachment.stream().map(e -> {
						e.setDeletedBy(details.getStaffId());
						e.setDeletedDate(now().atZone(systemDefault())
					            .withZoneSameInstant(of(INDIA_ZONE))
					            .toLocalDateTime());
						e.getMail().setDeletedBy(details.getStaffId());
						e.getMail().setDeletedDate(now().atZone(systemDefault())
					            .withZoneSameInstant(of(INDIA_ZONE))
					            .toLocalDateTime());
						e.setMail(e.getMail());
						return e;
					}).collect(Collectors.toList());
					for (Attachment e : attach) {
						attachmentDaoService.addAttachment(e);
						updatedEmail = emailDaoService.email(mail);
					}
				} else {
					mail.setDeletedBy(details.getStaffId());
					mail.setDeletedDate(now().atZone(systemDefault())
				            .withZoneSameInstant(of(INDIA_ZONE))
				            .toLocalDateTime());
					updatedEmail = emailDaoService.email(mail);
				}
			}
			if (nonNull(updatedEmail)) {
				result.put(MESSAGE, "Email deleted SuccessFully.");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Email Not deleted.");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.error("Got Exception while deleting the mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignEmail(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign Email staffId: {} addMailId:{}", map.get(STAFF_ID), map.get("addMailId"));
		try {
			Email email = emailDaoService.findById(map.get("addMailId"));
			EmployeeMaster employee = employeeService.getById(map.get(STAFF_ID))
					.orElseThrow(() -> new ResourceNotFoundException("Employee", STAFF_ID, map.get(STAFF_ID)));
			email.setMailFrom(employee.getEmailId());
			if (nonNull(emailDaoService.email(email))) {
				resultMap.put(MESSAGE, "Email Assigned SuccessFully");
				resultMap.put(SUCCESS, true);
			} else {
				resultMap.put(MESSAGE, "Email Not Assigned");
				resultMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(resultMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while assign the mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getEmail(Integer mailId) {
		log.info("inside the getEmail method...{}", mailId);
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		try {
			Email email = emailDaoService.findById(mailId);
			Optional<EmailDto> mailDto = TO_EMAIL_DTO.apply(email);
			mailDto.ifPresent(e -> {
				e.setBcc(nonNull(email.getBccMail()) && !email.getBccMail().isEmpty()
						? Stream.of(email.getBccMail().split(",")).map(String::trim).collect(Collectors.toList())
						: Collections.emptyList());
				e.setCc(nonNull(email.getCcMail()) && !email.getCcMail().isEmpty()
						? Stream.of(email.getCcMail().split(",")).map(String::trim).collect(Collectors.toList())
						: Collections.emptyList());
				e.setMailTo(nonNull(email.getToMail()) && !email.getToMail().isEmpty()
						? Stream.of(email.getToMail().split(",")).map(String::trim).collect(Collectors.toList())
						: Collections.emptyList());
			});
			resultMap.put(DATA, mailDto);
			resultMap.put(SUCCESS, true);
			return new ResponseEntity<>(resultMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Transactional
	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateEmail(EmailDto dto, String status, Integer mailId) {
		log.info("inside the updateEmail method...{} {}", mailId, status);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			boolean saveStatus = false;
			Email sendEmail = null;
			Email email = emailDaoService.findById(mailId);
			email.setToMail(dto.getMailTo().stream().collect(Collectors.joining(",")));
			email.setBccMail(dto.getBcc().stream().collect(Collectors.joining(",")));
			email.setCcMail(dto.getCc().stream().collect(Collectors.joining(",")));
			email.setContent(dto.getContent());
			email.setSubject(dto.getSubject());
			email.setMailFrom(dto.getMailFrom());
			email.setStatus(status);
			email.setScheduled(dto.getScheduled());
			email.setScheduledOn(dto.getScheduledOn());
			email.setScheduledAt(dto.getScheduledAt());

			if (dto.getAttachment().isEmpty()) {
				sendEmail = emailDaoService.email(email);
				saveStatus = nonNull(sendEmail);
			} else {
				for (AttachmentDto attach : dto.getAttachment()) {
					List<Integer> newIds = dto.getAttachment().stream().map(AttachmentDto::getEmailAttchId)
							.collect(Collectors.toList());
					for (Attachment existingAttachment : email.getAttachment()) {
						if (!newIds.contains(existingAttachment.getEmailAttchId())) {
							Attachment data = attachmentDaoService.findById(existingAttachment.getEmailAttchId())
									.orElse(null);
							data.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
							data.setDeletedDate(now().atZone(systemDefault())
						            .withZoneSameInstant(of(INDIA_ZONE))
						            .toLocalDateTime());
							attachmentDaoService.addAttachment(data);
						}
					}
					Attachment attachment = TO_ATTACHMENT.apply(attach).orElseThrow(ResourceNotFoundException::new);
					attachment.setMail(email);
					Attachment addAttachment = attachmentDaoService.addAttachment(attachment);
					sendEmail = addAttachment.getMail();
					saveStatus = nonNull(addAttachment);
				}
			}
			if (saveStatus && SAVE.equalsIgnoreCase(status)) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Email Updated Successfully");
			} else if (SEND.equalsIgnoreCase(status)) {
				boolean sendEmailStatus = sendEmail(sendEmail);
				if (saveStatus && sendEmailStatus) {
					result.put(SUCCESS, true);
					result.put(MESSAGE, "Email Updated and Sent Successfully!!");
				} else {
					result.put(SUCCESS, false);
					if (saveStatus && !sendEmailStatus)
						result.put(MESSAGE, "Email Updated but Problem while Sending Email!!");
					else
						result.put(MESSAGE, "Problem while Sending Email!!");
				}
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Email Not Updated");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("error occured while updating email for the Email Api..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
