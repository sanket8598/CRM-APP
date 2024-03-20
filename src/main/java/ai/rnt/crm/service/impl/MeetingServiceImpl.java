package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.EMPLOYEE;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.DateFormatterConstant.END_TIME;
import static ai.rnt.crm.constants.DateFormatterConstant.START_TIME;
import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
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
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
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

	public static final String MEETINGS = "Meetings";
	public static final String MEETING_TASK = "MeetingTask";
	public static final String MEETING_ID = "meetingId";
	public static final String TASK_ID = "taskId";

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addMeeting(@Valid MeetingDto dto, Integer leadsId) {
		log.info("inside the addMeeting method...{}", leadsId);
		EnumMap<ApiResponse, Object> addMeetMap = new EnumMap<>(ApiResponse.class);
		try {
			boolean saveStatus = false;
			Meetings meeting = TO_MEETING.apply(dto).orElseThrow(ResourceNotFoundException::new);
			meeting.setParticipates(dto.getParticipates().stream().collect(Collectors.joining(",")));
			meeting.setAssignTo(employeeService.getById(auditAwareUtil.getLoggedInStaffId()).orElseThrow(
					() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, auditAwareUtil.getLoggedInStaffId())));
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
				addMeetMap.put(SUCCESS, true);
				addMeetMap.put(MESSAGE, "Meeting Added Successfully..!!");
			} else {
				addMeetMap.put(SUCCESS, false);
				addMeetMap.put(MESSAGE, "Meeting Not Added..!!");
			}
			return new ResponseEntity<>(addMeetMap, CREATED);
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
					.orElseThrow(() -> new ResourceNotFoundException("Meeting", MEETING_ID, meetingId))));
			return new ResponseEntity<>(meeting, OK);
		} catch (Exception e) {
			log.error("Got Exception while geting meeting for edit..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateMeeting(MeetingDto dto, Integer meetingId,
			String status) {
		log.info("inside the updateMeeting method...{} {}", meetingId, status);
		EnumMap<ApiResponse, Object> updMeetMap = new EnumMap<>(ApiResponse.class);
		try {
			boolean meetingStatus = false;
			Meetings saveMeeting = null;
			Meetings meetings = meetingDaoService.getMeetingById(meetingId)
					.orElseThrow(() -> new ResourceNotFoundException(MEETINGS, MEETING_ID, meetingId));
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
							data.setDeletedDate(now().atZone(systemDefault())
						            .withZoneSameInstant(of(INDIA_ZONE))
						            .toLocalDateTime());
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
				updMeetMap.put(SUCCESS, true);
				updMeetMap.put(MESSAGE, "Meeting Updated Successfully");
			} else if (meetingStatus && COMPLETE.equalsIgnoreCase(status)) {
				updMeetMap.put(SUCCESS, true);
				updMeetMap.put(MESSAGE, "Meeting Updated And Completed Successfully");
			} else {
				updMeetMap.put(SUCCESS, false);
				updMeetMap.put(MESSAGE, "Meeting Not Updated");
			}
			return new ResponseEntity<>(updMeetMap, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while updating the meeting by id..{} " + meetingId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteMeeting(Integer meetingId) {
		log.info("inside the deleteMeeting method...{}", meetingId);
		EnumMap<ApiResponse, Object> delMeetMap = new EnumMap<>(ApiResponse.class);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			Meetings meetings = meetingDaoService.getMeetingById(meetingId)
					.orElseThrow(() -> new ResourceNotFoundException(MEETINGS, MEETING_ID, meetingId));
			meetings.getMeetingTasks().stream().forEach(e -> {
				e.setDeletedBy(loggedInStaffId);
				e.setDeletedDate(now().atZone(systemDefault())
			            .withZoneSameInstant(of(INDIA_ZONE))
			            .toLocalDateTime());
				meetingDaoService.addMeetingTask(e);
			});
			meetings.getMeetingAttachments().stream().forEach(e -> {
				e.setDeletedBy(loggedInStaffId);
				e.setDeletedDate(now().atZone(systemDefault())
			            .withZoneSameInstant(of(INDIA_ZONE))
			            .toLocalDateTime());
				meetingAttachmetDaoService.addMeetingAttachment(e);
			});
			meetings.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
			meetings.setDeletedDate(now().atZone(systemDefault())
		            .withZoneSameInstant(of(INDIA_ZONE))
		            .toLocalDateTime());
			if (nonNull(meetingDaoService.addMeeting(meetings))) {
				delMeetMap.put(MESSAGE, "Meeting deleted SuccessFully.");
				delMeetMap.put(SUCCESS, true);
			} else {
				delMeetMap.put(MESSAGE, "Meeting Not delete.");
				delMeetMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(delMeetMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while deleting the meeting by id..{} " + meetingId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addMeetingTask(@Valid MeetingTaskDto dto, Integer leadsId,
			Integer meetingId) {
		log.info("inside the addMeetingTask method...{} {}", leadsId, meetingId);
		EnumMap<ApiResponse, Object> addMeetTaskMap = new EnumMap<>(ApiResponse.class);
		try {
			MeetingTask meetingTask = TO_MEETING_TASK.apply(dto).orElseThrow(ResourceNotFoundException::new);
			Optional<Leads> lead = leadDaoService.getLeadById(leadsId);
			if (lead.isPresent())
				meetingTask.setAssignTo(lead.get().getEmployee());
			meetingDaoService.getMeetingById(meetingId).ifPresent(meetingTask::setMeetings);
			if (checkDuplicateMeetingTask(meetingDaoService.getAllMeetingTask(), meetingTask)) {
				addMeetTaskMap.put(SUCCESS, false);
				addMeetTaskMap.put(MESSAGE, "Task Already Exists !!");
			} else if (nonNull(meetingDaoService.addMeetingTask(meetingTask))) {
				addMeetTaskMap.put(SUCCESS, true);
				addMeetTaskMap.put(MESSAGE, "Task Added Successfully..!!");
			} else {
				addMeetTaskMap.put(SUCCESS, false);
				addMeetTaskMap.put(MESSAGE, "Task Not Added");
			}
			return new ResponseEntity<>(addMeetTaskMap, CREATED);
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
					.orElseThrow(() -> new ResourceNotFoundException(MEETING_TASK, "meetingTaskId", taskId))));
			return new ResponseEntity<>(meetingTask, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting the meeting task by id..{} " + taskId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateMeetingTask(GetMeetingTaskDto dto, Integer taskId) {
		log.info("inside the updateMeetingTask method...{}", taskId);
		EnumMap<ApiResponse, Object> updMeetTaskMap = new EnumMap<>(ApiResponse.class);
		try {
			MeetingTask meetingTask = meetingDaoService.getMeetingTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(MEETING_TASK, TASK_ID, taskId));
			meetingTask.setSubject(dto.getSubject());
			meetingTask.setStatus(dto.getStatus());
			meetingTask.setPriority(dto.getPriority());
			meetingTask.setDueDate(dto.getUpdateDueDate());
			meetingTask.setDueTime(dto.getDueTime());
			meetingTask.setRemainderOn(dto.isRemainderOn());
			meetingTask.setRemainderDueAt(dto.getRemainderDueAt());
			meetingTask.setRemainderDueOn(dto.getUpdatedRemainderDueOn());
			meetingTask.setRemainderVia(dto.getRemainderVia());
			meetingTask.setDescription(dto.getDescription());
			if (nonNull(meetingDaoService.addMeetingTask(meetingTask))) {
				updMeetTaskMap.put(SUCCESS, true);
				updMeetTaskMap.put(MESSAGE, "Task Updated Successfully..!!");
			} else {
				updMeetTaskMap.put(SUCCESS, false);
				updMeetTaskMap.put(MESSAGE, "Task Not Updated");
			}
			return new ResponseEntity<>(updMeetTaskMap, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while updating the meeting task by id..{} " + taskId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignMeetingTask(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> asgnMeetTaskMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign task staffId: {} taskId:{}", map.get(STAFF_ID), map.get(TASK_ID));
		try {
			MeetingTask meetingTask = meetingDaoService.getMeetingTaskById(map.get(TASK_ID))
					.orElseThrow(() -> new ResourceNotFoundException(MEETING_TASK, TASK_ID, map.get(TASK_ID)));
			EmployeeMaster employee = employeeService.getById(map.get(STAFF_ID))
					.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, map.get(STAFF_ID)));
			meetingTask.setAssignTo(employee);
			if (nonNull(meetingDaoService.addMeetingTask(meetingTask))) {
				asgnMeetTaskMap.put(SUCCESS, true);
				asgnMeetTaskMap.put(MESSAGE, "Task Assigned SuccessFully");
			} else {
				asgnMeetTaskMap.put(SUCCESS, false);
				asgnMeetTaskMap.put(MESSAGE, "Task Not Assigned");
			}
			return new ResponseEntity<>(asgnMeetTaskMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while assigning the MeetingTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteMeetingTask(Integer taskId) {
		log.info("inside the deleteMeetingTask method...{}", taskId);
		EnumMap<ApiResponse, Object> delMeetTaskMap = new EnumMap<>(ApiResponse.class);
		try {
			MeetingTask meetingTask = meetingDaoService.getMeetingTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(MEETING_TASK, TASK_ID, taskId));
			meetingTask.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
			meetingTask.setDeletedDate(now().atZone(systemDefault())
		            .withZoneSameInstant(of(INDIA_ZONE))
		            .toLocalDateTime());
			if (nonNull(meetingDaoService.addMeetingTask(meetingTask))) {
				delMeetTaskMap.put(MESSAGE, "Meeting Task Deleted SuccessFully.");
				delMeetTaskMap.put(SUCCESS, true);
			} else {
				delMeetTaskMap.put(MESSAGE, "Meeting Task Not delete.");
				delMeetTaskMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(delMeetTaskMap, OK);

		} catch (Exception e) {
			log.error("Got Exception while deleting the meeting task by id..{} " + taskId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignMeeting(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign meeting staffId: {} meetingId:{}", map.get(STAFF_ID), map.get(MEETING_ID));
		try {
			Meetings meetings = meetingDaoService.getMeetingById(map.get(MEETING_ID))
					.orElseThrow(() -> new ResourceNotFoundException(MEETINGS, MEETING_ID, map.get(MEETING_ID)));
			EmployeeMaster employee = employeeService.getById(map.get(STAFF_ID))
					.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, map.get(STAFF_ID)));
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
			log.error("Got Exception while assign the Meeting..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
