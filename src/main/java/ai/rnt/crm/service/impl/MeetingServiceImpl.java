package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.StatusConstants.SAVE;
import static ai.rnt.crm.dto_mapper.MeetingAttachmentDtoMapper.TO_METTING_ATTACHMENT;
import static ai.rnt.crm.dto_mapper.MeetingDtoMapper.TO_GET_MEETING_DTO;
import static ai.rnt.crm.dto_mapper.MeetingDtoMapper.TO_MEETING;
import static ai.rnt.crm.dto_mapper.MeetingTaskDtoMapper.TO_GET_MEETING_TASK_DTO;
import static ai.rnt.crm.dto_mapper.MeetingTaskDtoMapper.TO_MEETING_TASK;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.MeetingAttachmentDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dto.GetMeetingTaskDto;
import ai.rnt.crm.dto.MeetingAttachmentsDto;
import ai.rnt.crm.dto.MeetingDto;
import ai.rnt.crm.dto.MeetingTaskDto;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.MeetingAttachments;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.MeetingService;
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
public class MeetingServiceImpl implements MeetingService {

	private final MeetingDaoService meetingDaoService;
	private final MeetingAttachmentDaoService meetingAttachmetDaoService;
	private final LeadDaoService leadDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addMeeting(@Valid MeetingDto dto, Integer leadsId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			boolean saveStatus = false;
			Meetings metting = TO_MEETING.apply(dto).orElseThrow(ResourceNotFoundException::new);
			metting.setParticipates(dto.getParticipates().stream().collect(Collectors.joining(",")));
			metting.setMeetingStatus(SAVE);
			leadDaoService.getLeadById(leadsId).ifPresent(metting::setLead);
			if (isNull(dto.getMeetingAttachments()) || dto.getMeetingAttachments().isEmpty()) {
				if (nonNull(meetingDaoService.addMeeting(metting)))
					saveStatus = true;
			} else {
				for (MeetingAttachmentsDto attach : dto.getMeetingAttachments()) {
					MeetingAttachments meetingAttachments = TO_METTING_ATTACHMENT.apply(attach)
							.orElseThrow(ResourceNotFoundException::new);
					meetingAttachments.setMeetings(metting);
					if (nonNull(meetingAttachmetDaoService.addMeetingAttachment(meetingAttachments)))
						saveStatus = true;
				}
			}
			if (saveStatus) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Meeting Added Successfully..!!");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Meeting Not Added");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("error occured while adding meeting..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addMeetingTask(@Valid MeetingTaskDto dto, Integer leadsId,
			Integer meetingId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			MeetingTask meetingTask = TO_MEETING_TASK.apply(dto).orElseThrow(ResourceNotFoundException::new);
			Optional<Leads> lead = leadDaoService.getLeadById(leadsId);
			if (lead.isPresent())
				meetingTask.setAssignTo(lead.get().getEmployee());
			meetingDaoService.getMeetingById(meetingId).ifPresent(meetingTask::setMeetings);
			if (nonNull(meetingDaoService.addMeetingTask(meetingTask))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Task Added Successfully..!!");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Not Added");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("error occured while adding meeting tasks..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> editMeeting(Integer meetingId) {
		EnumMap<ApiResponse, Object> meeting = new EnumMap<>(ApiResponse.class);
		try {
			meeting.put(SUCCESS, true);
			meeting.put(DATA, TO_GET_MEETING_DTO.apply(meetingDaoService.getMeetingById(meetingId)
					.orElseThrow(() -> new ResourceNotFoundException("Meeting", "meetingId", meetingId))));
			return new ResponseEntity<>(meeting, FOUND);
		} catch (Exception e) {
			log.info("Got Exception while geting meeting for edit..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getMeetingTask(Integer taskId) {
		EnumMap<ApiResponse, Object> meetingTask = new EnumMap<>(ApiResponse.class);
		try {
			meetingTask.put(SUCCESS, true);
			meetingTask.put(DATA, TO_GET_MEETING_TASK_DTO.apply(meetingDaoService.getMeetingTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException("MeetingTask", "meetingTaskId", taskId))));
			return new ResponseEntity<>(meetingTask, FOUND);
		} catch (Exception e) {
			log.error("Got Exception while getting the meeting task by id..{} " + taskId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateMeetingTask(GetMeetingTaskDto dto, Integer taskId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			MeetingTask meetingTask = meetingDaoService.getMeetingTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException("MeetingTask", "taskId", taskId));
			meetingTask.setSubject(dto.getSubject());
			meetingTask.setStatus(dto.getStatus());
			meetingTask.setPriority(dto.getPriority());
			meetingTask.setDueDate(dto.getUpdateDueDate());
			meetingTask.setRemainderOn(dto.isRemainderOn());
			meetingTask.setRemainderDueAt(dto.getRemainderDueAt());
			meetingTask.setRemainderDueOn(dto.getRemainderDueOn());
			meetingTask.setRemainderVia(dto.getRemainderVia());
			meetingTask.setDescription(dto.getDescription());
			meetingTask.setUpdatedDate(LocalDateTime.now());
			if (nonNull(meetingDaoService.addMeetingTask(meetingTask))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Task Updated Successfully..!!");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Not Updated");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while updating the meeting task by id..{} " + taskId, e.getMessage());
			throw new CRMException(e);
		}
	}
}
