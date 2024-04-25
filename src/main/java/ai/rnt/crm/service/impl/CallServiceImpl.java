package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.EMPLOYEE;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.DateFormatterConstant.END_TIME;
import static ai.rnt.crm.constants.DateFormatterConstant.START_TIME;
import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.constants.StatusConstants.COMPLETE;
import static ai.rnt.crm.constants.StatusConstants.SAVE;
import static ai.rnt.crm.dto_mapper.CallDtoMapper.TO_CALL;
import static ai.rnt.crm.dto_mapper.CallDtoMapper.TO_GET_CALL_DTO;
import static ai.rnt.crm.dto_mapper.CallTaskDtoMapper.TO_CALL_TASK;
import static ai.rnt.crm.dto_mapper.CallTaskDtoMapper.TO_GET_CALL_TASK_DTO;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.CallDto;
import ai.rnt.crm.dto.CallTaskDto;
import ai.rnt.crm.dto.GetCallTaskDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.CallService;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;
import ai.rnt.crm.util.TaskUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CallServiceImpl implements CallService {

	private final CallDaoService callDaoService;
	private final LeadDaoService leadDaoService;
	private final EmployeeService employeeService;
	private final AuditAwareUtil auditAwareUtil;
	private final TaskUtil taskUtil;

	private static final String ADD_CALL = "AddCall";
	private static final String TASK_ID = "taskId";
	private static final String PHONE_CALL_TASK = "PhoneCallTask";
	private static final String ADD_CALL_ID = "addCallId";
	private static final String CALL_ID = "callId";

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCall(CallDto dto, Integer leadsId) {
		log.info("inside the add call method...{}", leadsId);
		EnumMap<ApiResponse, Object> callMap = new EnumMap<>(ApiResponse.class);
		try {
			Call call = TO_CALL.apply(dto)
					.orElseThrow(() -> new ResourceNotFoundException("Call", CALL_ID, dto.getCallId()));
			Leads lead = leadDaoService.getLeadById(leadsId)
					.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", leadsId));
			call.setLead(lead);
			call.setCallFrom(employeeService.getById(dto.getCallFrom().getStaffId()).orElseThrow(
					() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, dto.getCallFrom().getStaffId())));
			if (dto.isAllDay()) {
				call.setStartTime(START_TIME);
				call.setEndTime(END_TIME);
			}
			call.setStatus(SAVE);
			if (nonNull(callDaoService.call(call)))
				callMap.put(MESSAGE, "Call Added Successfully");
			else
				callMap.put(MESSAGE, "Call Not Added");
			callMap.put(SUCCESS, true);
			return new ResponseEntity<>(callMap, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while adding the call..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> editCall(Integer callId) {
		log.info("inside the edit call method...{}", callId);
		EnumMap<ApiResponse, Object> call = new EnumMap<>(ApiResponse.class);
		try {
			call.put(DATA, TO_GET_CALL_DTO.apply(callDaoService.getCallById(callId)
					.orElseThrow(() -> new ResourceNotFoundException(ADD_CALL, CALL_ID, callId))));
			call.put(SUCCESS, true);
			return new ResponseEntity<>(call, OK);
		} catch (Exception e) {
			log.error("Got exception while get call data for edit..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCall(CallDto dto, Integer callId, String status) {
		log.info("inside the update call method...{} {}", callId, status);
		EnumMap<ApiResponse, Object> updateCallMap = new EnumMap<>(ApiResponse.class);
		try {
			Call call = callDaoService.getCallById(callId)
					.orElseThrow(() -> new ResourceNotFoundException(ADD_CALL, CALL_ID, dto.getCallId()));
			call.setCallFrom(employeeService.getById(dto.getCallFrom().getStaffId()).orElseThrow(
					() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, dto.getCallFrom().getStaffId())));
			call.setCallTo(dto.getCallTo());
			call.setSubject(dto.getSubject());
			call.setStartDate(dto.getStartDate());
			call.setEndDate(dto.getEndDate());
			if (dto.isAllDay()) {
				call.setStartTime(START_TIME);
				call.setEndTime(END_TIME);
			} else {
				call.setStartTime(dto.getStartTime());
				call.setEndTime(dto.getEndTime());
			}
			call.setDirection(dto.getDirection());
			call.setPhoneNo(dto.getPhoneNo());
			call.setComment(dto.getComment());
			call.setDuration(dto.getDuration());
			call.setAllDay(dto.isAllDay());
			call.setStatus(status);
			if (nonNull(callDaoService.call(call))) {
				if (SAVE.equalsIgnoreCase(status))
					updateCallMap.put(MESSAGE, "Call Updated Successfully");
				else
					updateCallMap.put(MESSAGE, "Call Updated And Completed Successfully");
			} else
				updateCallMap.put(MESSAGE, "Call Not Updated");
			updateCallMap.put(SUCCESS, true);
			return new ResponseEntity<>(updateCallMap, CREATED);
		} catch (Exception e) {
			log.error("Got exception while update call..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignCall(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> asgnCallMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign call staffId: {} callId:{}", map.get(STAFF_ID), map.get(ADD_CALL_ID));
		try {
			Call call = callDaoService.getCallById(map.get(ADD_CALL_ID))
					.orElseThrow(() -> new ResourceNotFoundException(ADD_CALL, CALL_ID, map.get(ADD_CALL_ID)));
			EmployeeMaster employee = employeeService.getById(map.get(STAFF_ID))
					.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, map.get(STAFF_ID)));
			call.getCallTasks().stream()
					.filter(e -> call.getCallFrom().getStaffId().equals(e.getAssignTo().getStaffId())).forEach(e -> {
						e.setAssignTo(employee);
						callDaoService.addCallTask(e);
					});
			call.setCallFrom(employee);
			if (nonNull(callDaoService.call(call))) {
				asgnCallMap.put(MESSAGE, "Call Assigned Successfully");
				asgnCallMap.put(SUCCESS, true);
			} else {
				asgnCallMap.put(MESSAGE, "Call Not Assigned");
				asgnCallMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(asgnCallMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while assign the call..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> markAsCompleted(Integer callId) {
		log.info("inside the markasComplete call method...{}", callId);
		EnumMap<ApiResponse, Object> markCompllMap = new EnumMap<>(ApiResponse.class);
		try {
			Call call = callDaoService.getCallById(callId)
					.orElseThrow(() -> new ResourceNotFoundException("Call", CALL_ID, callId));
			call.setUpdatedDate(now());
			call.setStatus(COMPLETE);
			if (nonNull(callDaoService.call(call))) {
				markCompllMap.put(MESSAGE, "Call updated Successfully");
				markCompllMap.put(SUCCESS, true);
			} else {
				markCompllMap.put(MESSAGE, "Call Not updated");
				markCompllMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(markCompllMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while markAsCompleted the call..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCall(Integer callId) {
		log.info("inside the delete call method...{}", callId);
		EnumMap<ApiResponse, Object> delCallMap = new EnumMap<>(ApiResponse.class);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			Call call = callDaoService.getCallById(callId)
					.orElseThrow(() -> new ResourceNotFoundException("Call", CALL_ID, callId));
			call.getCallTasks().stream().forEach(e -> {
				e.setDeletedBy(loggedInStaffId);
				e.setDeletedDate(now().atZone(systemDefault())
			            .withZoneSameInstant(of(INDIA_ZONE))
			            .toLocalDateTime());
				callDaoService.addCallTask(e);
			});
			call.setDeletedBy(loggedInStaffId);
			call.setDeletedDate(now().atZone(systemDefault())
		            .withZoneSameInstant(of(INDIA_ZONE))
		            .toLocalDateTime());
			if (nonNull(callDaoService.call(call))) {
				delCallMap.put(MESSAGE, "Call deleted Successfully.");
				delCallMap.put(SUCCESS, true);
			} else {
				delCallMap.put(MESSAGE, "Call Not delete.");
				delCallMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(delCallMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while delete the call..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCallTask(@Valid CallTaskDto dto, Integer leadsId,
			Integer callId) {
		log.info("inside the add call task method...{} {}", leadsId, callId);
		EnumMap<ApiResponse, Object> addCallTaskMap = new EnumMap<>(ApiResponse.class);
		try {
			PhoneCallTask phoneCallTask = TO_CALL_TASK.apply(dto).orElseThrow(ResourceNotFoundException::new);
			Optional<Leads> lead = leadDaoService.getLeadById(leadsId);
			if (lead.isPresent())
				phoneCallTask.setAssignTo(lead.get().getEmployee());
			callDaoService.getCallById(callId).ifPresent(phoneCallTask::setCall);
			if (taskUtil.checkDuplicateTask(callDaoService.getAllTask(), phoneCallTask)) {
				addCallTaskMap.put(SUCCESS, false);
				addCallTaskMap.put(MESSAGE, "Task Already Exists !!");
			} else if (nonNull(callDaoService.addCallTask(phoneCallTask))) {
				addCallTaskMap.put(SUCCESS, true);
				addCallTaskMap.put(MESSAGE, "Task Added Successfully..!!");
			} else {
				addCallTaskMap.put(SUCCESS, false);
				addCallTaskMap.put(MESSAGE, "Task Not Added");
			}
			return new ResponseEntity<>(addCallTaskMap, CREATED);
		} catch (Exception e) {
			log.error("error occured while adding phone call tasks..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCallTask(Integer taskId) {
		log.info("inside the getCallTask method...{}", taskId);
		EnumMap<ApiResponse, Object> callTask = new EnumMap<>(ApiResponse.class);
		try {
			callTask.put(DATA, TO_GET_CALL_TASK_DTO.apply(callDaoService.getCallTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(PHONE_CALL_TASK, TASK_ID, taskId))));
			callTask.put(SUCCESS, true);
			return new ResponseEntity<>(callTask, OK);
		} catch (Exception e) {
			log.error("error occured while getting phone call task by id..{}", +taskId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCallTask(GetCallTaskDto dto, Integer taskId) {
		log.info("inside the updateCallTask method...{}", taskId);
		EnumMap<ApiResponse, Object> callTask = new EnumMap<>(ApiResponse.class);
		try {
			PhoneCallTask phoneCallTask = callDaoService.getCallTaskById(taskId).orElseThrow(
					() -> new ResourceNotFoundException(PHONE_CALL_TASK, "callTaskId", dto.getCallTaskId()));
			phoneCallTask.setSubject(dto.getSubject());
			phoneCallTask.setStatus(dto.getStatus());
			phoneCallTask.setDueDate(dto.getUpdateDueDate());
			phoneCallTask.setDueTime(dto.getDueTime());
			phoneCallTask.setPriority(dto.getPriority());
			phoneCallTask.setRemainderOn(dto.isRemainderOn());
			phoneCallTask.setRemainderDueOn(dto.getUpdatedRemainderDueOn());
			phoneCallTask.setRemainderVia(dto.getRemainderVia());
			phoneCallTask.setRemainderDueAt(dto.getRemainderDueAt());
			phoneCallTask.setDescription(dto.getDescription());
			if (nonNull(callDaoService.addCallTask(phoneCallTask))) {
				callTask.put(SUCCESS, true);
				callTask.put(MESSAGE, "Task Updated Successfully..!!");
			} else {
				callTask.put(SUCCESS, false);
				callTask.put(MESSAGE, "Task Not Updated");
			}
			return new ResponseEntity<>(callTask, CREATED);
		} catch (Exception e) {
			log.error("error occured while updating phone call task by id..{}", +taskId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignCallTask(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> asgnCallTaskMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign task staffId: {} taskId:{}", map.get(STAFF_ID), map.get(TASK_ID));
		try {
			PhoneCallTask callTask = callDaoService.getCallTaskById(map.get(TASK_ID))
					.orElseThrow(() -> new ResourceNotFoundException(PHONE_CALL_TASK, TASK_ID, map.get(TASK_ID)));
			EmployeeMaster employee = employeeService.getById(map.get(STAFF_ID))
					.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, map.get(STAFF_ID)));
			callTask.setAssignTo(employee);
			if (nonNull(callDaoService.addCallTask(callTask))) {
				asgnCallTaskMap.put(SUCCESS, true);
				asgnCallTaskMap.put(MESSAGE, "Task Assigned Successfully");
			} else {
				asgnCallTaskMap.put(SUCCESS, false);
				asgnCallTaskMap.put(MESSAGE, "Task Not Assigned");
			}
			return new ResponseEntity<>(asgnCallTaskMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while assigning the PhoneCallTask task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCallTask(Integer taskId) {
		log.info("inside the deleteCallTask method...{}", taskId);
		EnumMap<ApiResponse, Object> deleteTask = new EnumMap<>(ApiResponse.class);
		try {
			PhoneCallTask callTask = callDaoService.getCallTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(PHONE_CALL_TASK, TASK_ID, taskId));
			callTask.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
			callTask.setDeletedDate(now().atZone(systemDefault())
		            .withZoneSameInstant(of(INDIA_ZONE))
		            .toLocalDateTime());
			if (nonNull(callDaoService.addCallTask(callTask))) {
				deleteTask.put(MESSAGE, "Call Task Deleted SuccessFully.");
				deleteTask.put(SUCCESS, true);
			} else {
				deleteTask.put(MESSAGE, "Call Task Not deleted.");
				deleteTask.put(SUCCESS, false);
			}
			return new ResponseEntity<>(deleteTask, OK);
		} catch (Exception e) {
			log.error("error occured while deleting the phone call task by id..{}", +taskId, e.getMessage());
			throw new CRMException(e);
		}
	}
}