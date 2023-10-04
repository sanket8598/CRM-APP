package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.AttachmentDtoMapper.TO_ATTACHMENT;
import static ai.rnt.crm.dto_mapper.EmailDtoMapper.TO_EMAIL;
import static ai.rnt.crm.dto_mapper.EmailDtoMapper.TO_EMAIL_DTO;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.security.UserDetail;
import ai.rnt.crm.service.EmailService;
import ai.rnt.crm.service.EmployeeService;
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

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> addEmail(EmailDto dto, Integer leadId, String status) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			boolean saveStatus = false;
			AddEmail sendEmail = null;

			AddEmail addEmail = TO_EMAIL.apply(dto).orElseThrow(ResourceNotFoundException::new);
			Integer addEmailId = dto.getAddMailId();
			if ("send".equalsIgnoreCase(status) && nonNull(addEmailId))
				sendEmail = emailDaoService.findById(addEmail.getAddMailId());
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
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Email Added Successfully");
				result.put(DATA, sendEmail.getAddMailId());
			} else if ("send".equalsIgnoreCase(status)) {
				boolean sendEmailStatus = EmailUtil.sendEmail(sendEmail);
				if (nonNull(addEmailId) && sendEmailStatus) {
					result.put(SUCCESS, true);
					result.put(MESSAGE, "Email Sent Successfully!!");
				} else if (saveStatus && sendEmailStatus) {
					result.put(SUCCESS, true);
					result.put(MESSAGE, "Email Saved and Sent Successfully!!");
				} else {
					result.put(MESSAGE, "Problem while Sending Email!!");
					result.put(SUCCESS, false);
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

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteEmail(Integer mailId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		AddEmail updatedEmail = null;

		if (nonNull(getContext()) && nonNull(getContext().getAuthentication())
				&& nonNull(getContext().getAuthentication().getDetails())) {
			UserDetail details = (UserDetail) getContext().getAuthentication().getDetails();

			AddEmail mail = emailDaoService.findById(mailId);
			List<Attachment> attachment = mail.getAttachment();
			if (nonNull(attachment)&& !attachment.isEmpty()) {
				List<Attachment> attach = attachment.stream().map(e->{e.setDeletedBy(details.getStaffId());
				e.setDeletedDate(LocalDateTime.now());
				e.getMail().setDeletedBy(details.getStaffId());
				e.getMail().setDeletedDate(LocalDateTime.now());
				e.setMail(e.getMail());
				return e;
				}).collect(Collectors.toList());
				for(Attachment e:attach)  {
					attachmentDaoService.addAttachment(e);
					updatedEmail = emailDaoService.addEmail(mail);
				}
			} else {
				mail.setDeletedBy(details.getStaffId());
				mail.setDeletedDate(LocalDateTime.now());
				updatedEmail = emailDaoService.addEmail(mail);
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
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignEmail(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign Email staffId: {} addMailId:{}", map.get("staffId"), map.get("addMailId"));
		try {
			AddEmail email = emailDaoService.findById(map.get("addMailId"));
			EmployeeMaster employee = employeeService.getById(map.get("staffId"))
					.orElseThrow(() -> new ResourceNotFoundException("Employee", "staffId", map.get("staffId")));
			email.setMailFrom(employee.getEmailId());
			if (nonNull(emailDaoService.addEmail(email))) {
				resultMap.put(MESSAGE, "Email Assigned SuccessFully");
				resultMap.put(SUCCESS, true);
			} else {
				resultMap.put(MESSAGE, "Email Not Assigned");
				resultMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(resultMap, OK);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getEmail(Integer mailId) {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		try {
			AddEmail addEmail = emailDaoService.findById(mailId);
			Optional<EmailDto> mailDto = TO_EMAIL_DTO.apply(addEmail);
			mailDto.ifPresent(e -> {
				e.setBcc(Stream.of(addEmail.getBccMail().split(",")).map(String::trim).collect(Collectors.toList()));
				e.setCc(Stream.of(addEmail.getCcMail().split(",")).map(String::trim).collect(Collectors.toList()));
				e.setMailTo(Stream.of(addEmail.getToMail().split(",")).map(String::trim).collect(Collectors.toList()));
			});
			resultMap.put(DATA, mailDto);
			resultMap.put(SUCCESS, true);
			return new ResponseEntity<>(resultMap, OK);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> editEmail(EmailDto dto, Integer leadId, String status,
			Integer mailId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			boolean saveStatus = false;
			AddEmail sendEmail = null;

			AddEmail addEmail = TO_EMAIL.apply(dto).orElseThrow(ResourceNotFoundException::new);
			Integer addEmailId = mailId;
			if ("send".equalsIgnoreCase(status))
				sendEmail = emailDaoService.findById(mailId);
			else {
				addEmail.setToMail(dto.getMailTo().stream().collect(Collectors.joining(",")));
				addEmail.setBccMail(dto.getBcc().stream().collect(Collectors.joining(",")));
				addEmail.setCcMail(dto.getCc().stream().collect(Collectors.joining(",")));
				leadDaoService.getLeadById(leadId).ifPresent(addEmail::setLead);
				addEmail.setUpdatedDate(LocalDateTime.now());
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
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Email Updated Successfully");
			} else if ("send".equalsIgnoreCase(status)) {
				boolean sendEmailStatus = EmailUtil.sendEmail(sendEmail);
				if (nonNull(addEmailId) && sendEmailStatus) {
					result.put(SUCCESS, true);
					result.put(MESSAGE, "Email Sent Successfully!!");
				} else if (saveStatus && sendEmailStatus) {
					result.put(SUCCESS, true);
					result.put(MESSAGE, "Email Updated and Sent Successfully!!");
				} else {
					result.put(MESSAGE, "Problem while Sending Email!!");
					result.put(SUCCESS, false);
				}
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Email Not Updated");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("error occured while sending and saving add email for the Lead Api..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
