package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.AttachmentDtoMapper.TO_ATTACHMENT;
import static ai.rnt.crm.dto_mapper.EmailDtoMapper.TO_EMAIL;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
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
	public ResponseEntity<EnumMap<ApiResponse, Object>> addEmail(EmailDto dto, Integer leadId,String status) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			boolean saveStatus=false;
			AddEmail addEmail = TO_EMAIL.apply(dto).orElseThrow(ResourceNotFoundException::new);
			leadDaoService.getLeadById(leadId).ifPresent(addEmail::setLead);
			if(dto.getAttachment().isEmpty())
				saveStatus=nonNull(emailDaoService.addEmail(addEmail));
			else {
				for(AttachmentDto attach:dto.getAttachment()){
					Attachment attachment = TO_ATTACHMENT.apply(attach).orElseThrow(ResourceNotFoundException::new);
					attachment.setMail(addEmail);
					saveStatus=nonNull(attachmentDaoService.save(attachment).orElseThrow(ResourceNotFoundException::new));
				}
			}
			if (saveStatus && "save".equalsIgnoreCase(status) )
				result.put(MESSAGE, "Email Added Successfully");
			else if(saveStatus && "send".equalsIgnoreCase(status) ) //here we will add send email functionality
				result.put(MESSAGE, "Email Sent Successfully!!");	
			else
				result.put(MESSAGE, "Email Not Added");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("error occured while sending and saving add email for the Lead Api..{}",e.getMessage());
			throw new CRMException(e);
		}

	}
}
