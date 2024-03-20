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
import static java.lang.Boolean.TRUE;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import ai.rnt.crm.service.EmailService;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;
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
	private final EmployeeService employeeService;
	private final AuditAwareUtil auditAwareUtil;
	private final EmailUtil emailUtil;

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> addEmail(EmailDto dto, Integer leadId, String status) {
		log.info("inside the addEmail method...{} {}", leadId, status);
		EnumMap<ApiResponse, Object> addEmailMap = new EnumMap<>(ApiResponse.class);
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

			saveEmail(dto, email, sendEmail, saveStatus);

			if (saveStatus && SAVE.equalsIgnoreCase(status) && nonNull(sendEmail)) {
				addEmailMap.put(SUCCESS, true);
				addEmailMap.put(MESSAGE, "Email Added Successfully");
				addEmailMap.put(DATA, sendEmail.getMailId());
			} else if (SEND.equalsIgnoreCase(status)) {
				boolean sendEmailStatus = emailUtil.sendEmail(sendEmail);
				if (saveStatus && sendEmailStatus) {
					addEmailMap.put(SUCCESS, true);
					addEmailMap.put(MESSAGE, "Email Saved and Sent Successfully!!");
				} else {
					addEmailMap.put(SUCCESS, true);
					if (saveStatus && !sendEmailStatus) {
						email.setStatus(SAVE);
						emailDaoService.email(email);
						addEmailMap.put(MESSAGE, "Email Saved but Problem while Sending Email!!");
					} else
						addEmailMap.put(MESSAGE, "Problem Occured while saving the Email");
				}
			} else {
				addEmailMap.put(SUCCESS, false);
				addEmailMap.put(MESSAGE, "Email Not Added");
			}
			return new ResponseEntity<>(addEmailMap, CREATED);
		} catch (Exception e) {
			log.error("error occured while sending and saving add email for the Lead Api..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> checkMailId(Integer addMailId, Integer leadId) {
		log.info("inside the checkMailId method...{} {}", addMailId, leadId);
		EnumMap<ApiResponse, Object> verifyEmailMap = new EnumMap<>(ApiResponse.class);
		try {
			if (TRUE.equals(emailDaoService.emailPresentForLeadLeadId(addMailId, leadId))) {
				verifyEmailMap.put(SUCCESS, true);
				verifyEmailMap.put(MESSAGE, "This email is already saved");
				verifyEmailMap.put(DATA, addMailId);
			} else {
				verifyEmailMap.put(SUCCESS, false);
				verifyEmailMap.put(MESSAGE, "This email is not saved");
			}
		} catch (Exception e) {
			log.error("error occured while checking add email for the Lead Id..{}", e.getMessage());
			throw new CRMException(e);
		}
		return new ResponseEntity<>(verifyEmailMap, OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteEmail(Integer mailId) {
		log.info("inside the deleteEmail method...{}", mailId);
		EnumMap<ApiResponse, Object> delEmailMap = new EnumMap<>(ApiResponse.class);
		Email updatedEmail = null;
		try {
			Integer staffId = auditAwareUtil.getLoggedInStaffId();
			Email mail = emailDaoService.findById(mailId);
			List<Attachment> attachment = mail.getAttachment();
			if (nonNull(attachment) && !attachment.isEmpty()) {
				for (Attachment e : attachment) {
					e.setDeletedBy(staffId);
					e.setDeletedDate(
							now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
					e.getMail().setDeletedBy(staffId);
					e.getMail().setDeletedDate(
							now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
					e.setMail(e.getMail());
					attachmentDaoService.addAttachment(e);
					updatedEmail = emailDaoService.email(mail);
				}
			} else {
				mail.setDeletedBy(staffId);
				mail.setDeletedDate(
						now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
				updatedEmail = emailDaoService.email(mail);
			}
			if (nonNull(updatedEmail)) {
				delEmailMap.put(MESSAGE, "Email deleted SuccessFully.");
				delEmailMap.put(SUCCESS, true);
			} else {
				delEmailMap.put(MESSAGE, "Email Not deleted.");
				delEmailMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(delEmailMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while deleting the mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignEmail(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> asgmMailMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign Email staffId: {} addMailId:{}", map.get(STAFF_ID), map.get("addMailId"));
		try {
			Email email = emailDaoService.findById(map.get("addMailId"));
			EmployeeMaster employee = employeeService.getById(map.get(STAFF_ID))
					.orElseThrow(() -> new ResourceNotFoundException("Employee", STAFF_ID, map.get(STAFF_ID)));
			email.setMailFrom(employee.getEmailId());
			if (nonNull(emailDaoService.email(email))) {
				asgmMailMap.put(MESSAGE, "Email Assigned SuccessFully");
				asgmMailMap.put(SUCCESS, true);
			} else {
				asgmMailMap.put(MESSAGE, "Email Not Assigned");
				asgmMailMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(asgmMailMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while assign the mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getEmail(Integer mailId) {
		log.info("inside the getEmail method...{}", mailId);
		EnumMap<ApiResponse, Object> mailMap = new EnumMap<>(ApiResponse.class);
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
			mailMap.put(DATA, mailDto);
			mailMap.put(SUCCESS, true);
			return new ResponseEntity<>(mailMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting mail..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Transactional
	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateEmail(EmailDto dto, String status, Integer mailId) {
		log.info("inside the updateEmail method...{} {}", mailId, status);
		EnumMap<ApiResponse, Object> updEmailMap = new EnumMap<>(ApiResponse.class);
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

			List<Integer> newIds = dto.getAttachment().stream().map(AttachmentDto::getEmailAttchId).collect(toList());
			List<Attachment> emailList = email.getAttachment().stream()
					.filter(e -> !newIds.contains(e.getEmailAttchId())).filter(Objects::nonNull).collect(toList());
			emailList.stream().forEach(data -> {
				data.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
				data.setDeletedDate(
						now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
				attachmentDaoService.addAttachment(data);
			});

			saveEmail(dto, email, sendEmail, saveStatus);

			if (saveStatus && SAVE.equalsIgnoreCase(status)) {
				updEmailMap.put(SUCCESS, true);
				updEmailMap.put(MESSAGE, "Email Updated Successfully");
			} else if (SEND.equalsIgnoreCase(status)) {
				boolean sendEmailStatus = emailUtil.sendEmail(sendEmail);
				if (saveStatus && sendEmailStatus) {
					updEmailMap.put(SUCCESS, true);
					updEmailMap.put(MESSAGE, "Email Updated and Sent Successfully!!");
				} else {
					updEmailMap.put(SUCCESS, false);
					if (saveStatus && !sendEmailStatus)
						updEmailMap.put(MESSAGE, "Email Updated but Problem while Sending Email!!");
					else
						updEmailMap.put(MESSAGE, "Problem while Sending Email!!");
				}
			} else {
				updEmailMap.put(SUCCESS, false);
				updEmailMap.put(MESSAGE, "Email Not Updated");
			}
			return new ResponseEntity<>(updEmailMap, CREATED);
		} catch (Exception e) {
			log.error("error occured while updating email for the Email Api..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	private void saveEmail(EmailDto dto, Email email, Email sendEmail, boolean saveStatus) {
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
	}
}
