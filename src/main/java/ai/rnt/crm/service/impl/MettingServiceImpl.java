package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.MettingAttachmentDtoMapper.TO_METTING_ATTACHMENT;
import static ai.rnt.crm.dto_mapper.MettingDtoMapper.TO_METTING;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.EnumMap;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.MettingAttachmentDaoService;
import ai.rnt.crm.dao.service.MettingDaoService;
import ai.rnt.crm.dto.MettingAttachmentsDto;
import ai.rnt.crm.dto.MettingDto;
import ai.rnt.crm.entity.MeetingAttachments;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.MettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MettingServiceImpl implements MettingService {

	private final MettingDaoService mettingDaoService;
	private final MettingAttachmentDaoService mettingAttachmetDaoService;
	private final LeadDaoService leadDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addMetting(@Valid MettingDto dto, Integer leadsId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			boolean saveStatus = false;

			Meetings metting = TO_METTING.apply(dto).orElseThrow(ResourceNotFoundException::new);
			metting.setParticipates(dto.getParticipates().stream().collect(Collectors.joining(",")));
			leadDaoService.getLeadById(leadsId).ifPresent(metting::setLead);
			if (isNull(dto.getMettingAttachments()) || dto.getMettingAttachments().isEmpty()) {
				if (nonNull(mettingDaoService.addMetting(metting)))
					saveStatus = true;
			} else {
				for (MettingAttachmentsDto attach : dto.getMettingAttachments()) {
					MeetingAttachments meetingAttachments = TO_METTING_ATTACHMENT.apply(attach)
							.orElseThrow(ResourceNotFoundException::new);
					meetingAttachments.setMeetings(metting);
					if (nonNull(mettingAttachmetDaoService.addMettingAttachment(meetingAttachments)))
						saveStatus = true;
				}
			}
			if (saveStatus) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Metting Added Successfully..!!");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Metting Not Added");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("error occured while adding metting..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
