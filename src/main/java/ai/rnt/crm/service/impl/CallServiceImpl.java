package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.StatusConstants.COMPLETE;
import static ai.rnt.crm.constants.StatusConstants.SAVE;
import static ai.rnt.crm.dto_mapper.CallDtoMapper.TO_CALL;
import static ai.rnt.crm.dto_mapper.CallDtoMapper.TO_GET_CALL_DTO;
import static ai.rnt.crm.dto_mapper.CallTaskDtoMapper.TO_CALL_TASK;
import static ai.rnt.crm.dto_mapper.CallTaskDtoMapper.TO_GET_CALL_TASK_DTO;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.TaskUtil.checkDuplicateTask;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDateTime;
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

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCall(CallDto dto, Integer leadsId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			Call call = TO_CALL.apply(dto)
					.orElseThrow(() -> new ResourceNotFoundException("Call", "callId", dto.getCallId()));
			Leads lead = leadDaoService.getLeadById(leadsId)
					.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", leadsId));
			call.setLead(lead);
			call.setCallFrom(employeeService.getById(dto.getCallFrom().getStaffId()).orElseThrow(
					() -> new ResourceNotFoundException("Employee", "staffId", dto.getCallFrom().getStaffId())));
			call.setStatus(SAVE);
			if (nonNull(callDaoService.call(call)))
				result.put(MESSAGE, "Call Added Successfully");
			else
				result.put(MESSAGE, "Call Not Added");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.info("Got Exception while adding the call..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignCall(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign call staffId: {} callId:{}", map.get("staffId"), map.get("addCallId"));
		try {
			Call call = callDaoService.getCallById(map.get("addCallId"))
					.orElseThrow(() -> new ResourceNotFoundException("AddCall", "callId", map.get("addCallId")));
			EmployeeMaster employee = employeeService.getById(map.get("staffId"))
					.orElseThrow(() -> new ResourceNotFoundException("Employee", "staffId", map.get("staffId")));
			call.setCallFrom(employee);
			if (nonNull(callDaoService.call(call))) {
				resultMap.put(MESSAGE, "Call Assigned SuccessFully");
				resultMap.put(SUCCESS, true);
			} else {
				resultMap.put(MESSAGE, "Call Not Assigned");
				resultMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(resultMap, OK);
		} catch (Exception e) {
			log.info("Got Exception while assign the call..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> markAsCompleted(Integer callId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			Call call = callDaoService.getCallById(callId)
					.orElseThrow(() -> new ResourceNotFoundException("Call", "callId", callId));
			call.setUpdatedDate(LocalDateTime.now());
			call.setStatus(COMPLETE);
			if (nonNull(callDaoService.call(call))) {
				result.put(MESSAGE, "Call updated SuccessFully");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Call Not updated");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.info("Got Exception while markAsCompleted the call..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCall(Integer callId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			Call call = callDaoService.getCallById(callId)
					.orElseThrow(() -> new ResourceNotFoundException("Call", "callId", callId));
			call.getCallTasks().stream().forEach(e -> {
				e.setDeletedBy(loggedInStaffId);
				e.setDeletedDate(LocalDateTime.now());
				callDaoService.addCallTask(e);
			});
			call.setDeletedBy(loggedInStaffId);
			call.setDeletedDate(LocalDateTime.now());
			if (nonNull(callDaoService.call(call))) {
				result.put(MESSAGE, "Call deleted SuccessFully.");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Call Not delete.");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.info("Got Exception while delete the call..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> editCall(Integer callId) {
		EnumMap<ApiResponse, Object> call = new EnumMap<>(ApiResponse.class);
		try {
			call.put(DATA, TO_GET_CALL_DTO.apply(callDaoService.getCallById(callId)
					.orElseThrow(() -> new ResourceNotFoundException("AddCall", "callId", callId))));
			call.put(SUCCESS, true);
			return new ResponseEntity<>(call, FOUND);
		} catch (Exception e) {
			log.info("Got exception while get call data for edit..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCall(CallDto dto, Integer callId, String status) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			Call call = callDaoService.getCallById(callId)
					.orElseThrow(() -> new ResourceNotFoundException("AddCall", "callId", dto.getCallId()));
			call.setCallFrom(employeeService.getById(dto.getCallFrom().getStaffId()).orElseThrow(
					() -> new ResourceNotFoundException("Employee", "staffId", dto.getCallFrom().getStaffId())));
			call.setCallTo(dto.getCallTo());
			call.setSubject(dto.getSubject());
			call.setStartDate(dto.getStartDate());
			call.setEndDate(dto.getEndDate());
			call.setStartTime(dto.getStartTime());
			call.setEndTime(dto.getEndTime());
			call.setDirection(dto.getDirection());
			call.setPhoneNo(dto.getPhoneNo());
			call.setComment(dto.getComment());
			call.setDuration(dto.getDuration());
			call.setAllDay(dto.isAllDay());
			call.setStatus(status);
			call.setUpdatedDate(LocalDateTime.now());
			if (nonNull(callDaoService.call(call))) {
				if (status.equalsIgnoreCase(SAVE))
					result.put(MESSAGE, "Call Updated Successfully");
				else
					result.put(MESSAGE, "Call Updated And Completed Successfully");
			} else
				result.put(MESSAGE, "Call Not Updated");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.info("Got exception while update call..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCallTask(@Valid CallTaskDto dto, Integer leadsId,
			Integer callId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			PhoneCallTask phoneCallTask = TO_CALL_TASK.apply(dto).orElseThrow(ResourceNotFoundException::new);
			Optional<Leads> lead = leadDaoService.getLeadById(leadsId);
			if (lead.isPresent())
				phoneCallTask.setAssignTo(lead.get().getEmployee());
			callDaoService.getCallById(callId).ifPresent(phoneCallTask::setCall);
			if (checkDuplicateTask(callDaoService.getAllTask(), phoneCallTask)) {
				result.put(MESSAGE, "Task Already Exists !!");
				result.put(SUCCESS, false);
			} 
			if (nonNull(callDaoService.addCallTask(phoneCallTask))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Task Added Successfully..!!");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Not Added");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("error occured while adding phone call tasks..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCallTask(Integer taskId) {
		EnumMap<ApiResponse, Object> callTask = new EnumMap<>(ApiResponse.class);
		try {
			callTask.put(DATA, TO_GET_CALL_TASK_DTO.apply(callDaoService.getCallTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException("PhoneCallTask", "taskId", taskId))));
			callTask.put(SUCCESS, true);
			return new ResponseEntity<>(callTask, FOUND);
		} catch (Exception e) {
			log.error("error occured while getting phone call task by id..{}", +taskId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCallTask(GetCallTaskDto dto, Integer taskId) {
		EnumMap<ApiResponse, Object> callTask = new EnumMap<>(ApiResponse.class);
		try {
			PhoneCallTask phoneCallTask = callDaoService.getCallTaskById(taskId).orElseThrow(
					() -> new ResourceNotFoundException("PhoneCallTask", "callTaskId", dto.getCallTaskId()));
			phoneCallTask.setSubject(dto.getSubject());
			phoneCallTask.setStatus(dto.getStatus());
			phoneCallTask.setDueDate(dto.getUpdateDueDate());
			phoneCallTask.setPriority(dto.getPriority());
			phoneCallTask.setRemainderOn(dto.isRemainderOn());
			phoneCallTask.setRemainderDueOn(dto.getRemainderDueOn());
			phoneCallTask.setRemainderVia(dto.getRemainderVia());
			phoneCallTask.setRemainderDueAt(dto.getRemainderDueAt());
			phoneCallTask.setDescription(dto.getDescription());
			phoneCallTask.setUpdatedDate(LocalDateTime.now());
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
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCallTask(Integer taskId) {
		EnumMap<ApiResponse, Object> deleteTask = new EnumMap<>(ApiResponse.class);
		try {
			PhoneCallTask callTask = callDaoService.getCallTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException("PhoneCallTask", "taskId", taskId));
			callTask.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
			callTask.setDeletedDate(LocalDateTime.now());
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