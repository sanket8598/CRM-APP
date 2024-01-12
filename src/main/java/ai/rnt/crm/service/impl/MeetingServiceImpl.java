package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.DateFormatterConstant.END_TIME;
import static ai.rnt.crm.constants.DateFormatterConstant.START_TIME;
import static ai.rnt.crm.constants.StatusConstants.COMPLETE;
import static ai.rnt.crm.constants.StatusConstants.SAVE;
import static ai.rnt.crm.dto_mapper.MeetingAttachmentDtoMapper.TO_METTING_ATTACHMENT;
import static ai.rnt.crm.dto_mapper.MeetingDtoMapper.TO_GET_MEETING_DTO;
import static ai.rnt.crm.dto_mapper.MeetingDtoMapper.TO_MEETING;
import static ai.rnt.crm.dto_mapper.MeetingTaskDtoMapper.TO_GET_MEETING_TASK_DTO;
import static ai.rnt.crm.dto_mapper.MeetingTaskDtoMapper.TO_MEETING_TASK;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.TaskUtil.checkDuplicateMeetingTask;
import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
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
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.MeetingAttachments;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.service.MeetingService;
import ai.rnt.crm.util.AuditAwareUtil;
import ai.rnt.crm.util.MeetingUtil;
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
	private final AuditAwareUtil auditAwareUtil;
	private final EmployeeService employeeService;
	private final MeetingUtil meetingUtil;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addMeeting(@Valid MeetingDto dto, Integer leadsId) {
		log.info("inside the addMeeting method...{}", leadsId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			boolean saveStatus = false;
			Meetings meeting = TO_MEETING.apply(dto).orElseThrow(ResourceNotFoundException::new);
			meeting.setParticipates(dto.getParticipates().stream().collect(Collectors.joining(",")));
			meeting.setAssignTo(employeeService.getById(auditAwareUtil.getLoggedInStaffId()).orElseThrow(
					() -> new ResourceNotFoundException("Employee", "staffId", auditAwareUtil.getLoggedInStaffId())));
			if (dto.isAllDay()) {
				meeting.setStartTime(START_TIME);
				meeting.setEndTime(END_TIME);
			}
			meeting.setMeetingStatus(SAVE);
			leadDaoService.getLeadById(leadsId).ifPresent(meeting::setLead);
			if (isNull(dto.getMeetingAttachments()) || dto.getMeetingAttachments().isEmpty()) {
				if (nonNull(meetingDaoService.addMeeting(meeting)))
					saveStatus = true;
			} else {
				for (MeetingAttachmentsDto attach : dto.getMeetingAttachments()) {
					MeetingAttachments meetingAttachments = TO_METTING_ATTACHMENT.apply(attach)
							.orElseThrow(ResourceNotFoundException::new);
					meetingAttachments.setMeetings(meeting);
					if (nonNull(meetingAttachmetDaoService.addMeetingAttachment(meetingAttachments)))
						saveStatus = true;
				}
			}
			if (saveStatus) {
				if (nonNull(dto.getMeetingMode()) && "Online".equalsIgnoreCase(dto.getMeetingMode()))
					meetingUtil.scheduleMeetingInOutlook(dto);
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
	public ResponseEntity<EnumMap<ApiResponse, Object>> editMeeting(Integer meetingId) {
		log.info("inside the editMeeting method...{}", meetingId);
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
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateMeeting(MeetingDto dto, Integer meetingId,
			String status) {
		log.info("inside the updateMeeting method...{} {}", meetingId, status);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			boolean meetingStatus = false;
			Meetings saveMeeting = null;
			Meetings meetings = meetingDaoService.getMeetingById(meetingId)
					.orElseThrow(() -> new ResourceNotFoundException("Meetings", "meetingId", meetingId));
			meetings.setMeetingTitle(dto.getMeetingTitle());
			meetings.setParticipates(dto.getParticipates().stream().collect(Collectors.joining(",")));
			meetings.setStartDate(dto.getStartDate());
			meetings.setEndDate(dto.getEndDate());
			if (dto.isAllDay()) {
				meetings.setStartTime(START_TIME);
				meetings.setEndTime(END_TIME);
			} else {
				meetings.setStartTime(dto.getStartTime());
				meetings.setEndTime(dto.getEndTime());
			}
			meetings.setDuration(dto.getDuration());
			meetings.setLocation(dto.getLocation());
			meetings.setAllDay(dto.isAllDay());
			meetings.setMeetingMode(dto.getMeetingMode());
			meetings.setDescription(dto.getDescription());
			meetings.setMeetingStatus(status);
			if (isNull(dto.getMeetingAttachments()) || dto.getMeetingAttachments().isEmpty()) {
				saveMeeting = meetingDaoService.addMeeting(meetings);
				meetingStatus = nonNull(saveMeeting);
			} else {
				for (MeetingAttachmentsDto attach : dto.getMeetingAttachments()) {
					List<Integer> newIds = dto.getMeetingAttachments().stream()
							.map(MeetingAttachmentsDto::getMeetingAttchId).collect(Collectors.toList());
					for (MeetingAttachments existingAttachment : meetings.getMeetingAttachments()) {
						if (!newIds.contains(existingAttachment.getMeetingAttchId())) {
							MeetingAttachments data = meetingAttachmetDaoService
									.findById(existingAttachment.getMeetingAttchId()).orElse(null);
							data.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
							data.setDeletedDate(now());
							meetingAttachmetDaoService.removeExistingMeetingAttachment(data);
						}
					}
					MeetingAttachments attachment = TO_METTING_ATTACHMENT.apply(attach)
							.orElseThrow(ResourceNotFoundException::new);
					attachment.setMeetings(meetings);
					MeetingAttachments addAttachment = meetingAttachmetDaoService.addMeetingAttachment(attachment);
					saveMeeting = addAttachment.getMeetings();
					meetingStatus = nonNull(saveMeeting);
				}
			}
			if (meetingStatus && SAVE.equalsIgnoreCase(status)) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Meeting Updated Successfully");
			} else if (meetingStatus && COMPLETE.equalsIgnoreCase(status)) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Meeting Updated And Completed Successfully");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Meeting Not Updated");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while updating the meeting by id..{} " + meetingId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteMeeting(Integer meetingId) {
		log.info("inside the deleteMeeting method...{}", meetingId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			Meetings meetings = meetingDaoService.getMeetingById(meetingId)
					.orElseThrow(() -> new ResourceNotFoundException("Meetings", "meetingId", meetingId));
			meetings.getMeetingTasks().stream().forEach(e -> {
				e.setDeletedBy(loggedInStaffId);
				e.setDeletedDate(now());
				meetingDaoService.addMeetingTask(e);
			});
			meetings.getMeetingAttachments().stream().forEach(e -> {
				e.setDeletedBy(loggedInStaffId);
				e.setDeletedDate(now());
				meetingAttachmetDaoService.addMeetingAttachment(e);
			});
			meetings.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
			meetings.setDeletedDate(now());
			if (nonNull(meetingDaoService.addMeeting(meetings))) {
				result.put(MESSAGE, "Meeting deleted SuccessFully.");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Meeting Not delete.");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.error("Got Exception while deleting the meeting by id..{} " + meetingId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addMeetingTask(@Valid MeetingTaskDto dto, Integer leadsId,
			Integer meetingId) {
		log.info("inside the addMeetingTask method...{} {}", leadsId, meetingId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			MeetingTask meetingTask = TO_MEETING_TASK.apply(dto).orElseThrow(ResourceNotFoundException::new);
			Optional<Leads> lead = leadDaoService.getLeadById(leadsId);
			if (lead.isPresent())
				meetingTask.setAssignTo(lead.get().getEmployee());
			meetingDaoService.getMeetingById(meetingId).ifPresent(meetingTask::setMeetings);
			if (checkDuplicateMeetingTask(meetingDaoService.getAllMeetingTask(), meetingTask)) {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Already Exists !!");
			} else if (nonNull(meetingDaoService.addMeetingTask(meetingTask))) {
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
	public ResponseEntity<EnumMap<ApiResponse, Object>> getMeetingTask(Integer taskId) {
		log.info("inside the getMeetingTask method...{}", taskId);
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
		log.info("inside the updateMeetingTask method...{}", taskId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			MeetingTask meetingTask = meetingDaoService.getMeetingTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException("MeetingTask", "taskId", taskId));
			meetingTask.setSubject(dto.getSubject());
			meetingTask.setStatus(dto.getStatus());
			meetingTask.setPriority(dto.getPriority());
			meetingTask.setDueDate(dto.getUpdateDueDate());
			meetingTask.setDueTime(dto.getDueTime());
			meetingTask.setRemainderOn(dto.isRemainderOn());
			meetingTask.setRemainderDueAt(dto.getRemainderDueAt());
			meetingTask.setRemainderDueOn(dto.getRemainderDueOn());
			meetingTask.setRemainderVia(dto.getRemainderVia());
			meetingTask.setDescription(dto.getDescription());
			meetingTask.setUpdatedDate(now());
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

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignMeetingTask(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		log.info("inside assign task staffId: {} taskId:{}", map.get("staffId"), map.get("taskId"));
		try {
			MeetingTask meetingTask = meetingDaoService.getMeetingTaskById(map.get("taskId"))
					.orElseThrow(() -> new ResourceNotFoundException("MeetingTask", "taskId", map.get("taskId")));
			EmployeeMaster employee = employeeService.getById(map.get("staffId"))
					.orElseThrow(() -> new ResourceNotFoundException("Employee", "staffId", map.get("staffId")));
			meetingTask.setAssignTo(employee);
			if (nonNull(meetingDaoService.addMeetingTask(meetingTask))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Task Assigned SuccessFully");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Not Assigned");
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.info("Got Exception while assigning the MeetingTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteMeetingTask(Integer taskId) {
		log.info("inside the deleteMeetingTask method...{}", taskId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			MeetingTask meetingTask = meetingDaoService.getMeetingTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException("MeetingTask", "taskId", taskId));
			meetingTask.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
			meetingTask.setDeletedDate(now());
			if (nonNull(meetingDaoService.addMeetingTask(meetingTask))) {
				result.put(MESSAGE, "Meeting Task Deleted SuccessFully.");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Meeting Task Not delete.");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);

		} catch (Exception e) {
			log.error("Got Exception while deleting the meeting task by id..{} " + taskId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignMeeting(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign meeting staffId: {} meetingId:{}", map.get("staffId"), map.get("meetingId"));
		try {
			Meetings meetings = meetingDaoService.getMeetingById(map.get("meetingId"))
					.orElseThrow(() -> new ResourceNotFoundException("Meetings", "meetingId", map.get("meetingId")));
			EmployeeMaster employee = employeeService.getById(map.get("staffId"))
					.orElseThrow(() -> new ResourceNotFoundException("Employee", "staffId", map.get("staffId")));
			meetings.getMeetingTasks().stream()
					.filter(e -> meetings.getAssignTo().getStaffId().equals(e.getAssignTo().getStaffId())).map(e -> {
						e.setAssignTo(employee);
						return e;
					}).forEach(meetingDaoService::addMeetingTask);
			meetings.setAssignTo(employee);
			if (nonNull(meetingDaoService.addMeeting(meetings))) {
				resultMap.put(SUCCESS, true);
				resultMap.put(MESSAGE, "Meeting Assigned SuccessFully");
			} else {
				resultMap.put(SUCCESS, false);
				resultMap.put(MESSAGE, "Meeting Not Assigned");
			}
			return new ResponseEntity<>(resultMap, OK);
		} catch (Exception e) {
			log.info("Got Exception while assign the Meeting..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
