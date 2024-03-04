package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.EMPLOYEE;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.dto_mapper.LeadTaskDtoMapper.TO_GET_LEAD_TASK_DTO;
import static ai.rnt.crm.dto_mapper.LeadTaskDtoMapper.TO_LEAD_TASK;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.TaskUtil.checkDuplicateLeadTask;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


import java.util.EnumMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadTaskDaoService;
import ai.rnt.crm.dto.GetLeadTaskDto;
import ai.rnt.crm.dto.LeadTaskDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.service.LeadTaskService;
import ai.rnt.crm.util.AuditAwareUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 07/12/2023
 * @version 1.0
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LeadTaskServiceImpl implements LeadTaskService {

	private final LeadTaskDaoService leadTaskDaoService;
	private final LeadDaoService leadDaoService;
	private final AuditAwareUtil auditAwareUtil;
	private final EmployeeService employeeService;

	public static final String TASK_ID = "taskId";
	public static final String LEAD_TASK = "LeadTask";

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addLeadTask(@Valid LeadTaskDto dto, Integer leadsId) {
		log.info("inside the addLeadTask method...{}", leadsId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			LeadTask leadTask = TO_LEAD_TASK.apply(dto)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD_TASK, "leadTaskId", dto.getLeadTaskId()));
			Leads lead = leadDaoService.getLeadById(leadsId)
					.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", leadsId));
			employeeService.getById(auditAwareUtil.getLoggedInStaffId()).ifPresent(leadTask::setAssignTo);
			leadTask.setLead(lead);
			if (checkDuplicateLeadTask(leadTaskDaoService.getAllTask(), leadTask)) {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Already Exists !!");
			} else if (nonNull(leadTaskDaoService.addTask(leadTask))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Task Added Successfully");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Not Added");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while adding the task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadTask(Integer taskId) {
		log.info("inside the getLeadTask method...{}", taskId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			result.put(DATA, TO_GET_LEAD_TASK_DTO.apply(leadTaskDaoService.getTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD_TASK, TASK_ID, taskId))));
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting the lead task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateLeadTask(GetLeadTaskDto dto, Integer taskId) {
		log.info("inside the updateLeadTask method...{}", taskId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			LeadTask leadTask = leadTaskDaoService.getTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD_TASK, TASK_ID, taskId));
			leadTask.setSubject(dto.getSubject());
			leadTask.setStatus(dto.getStatus());
			leadTask.setPriority(dto.getPriority());
			leadTask.setDueDate(dto.getUpdateDueDate());
			leadTask.setDueTime(dto.getDueTime());
			leadTask.setRemainderOn(dto.isRemainderOn());
			leadTask.setRemainderDueOn(dto.getUpdatedRemainderDueOn());
			leadTask.setRemainderDueAt(dto.getRemainderDueAt());
			leadTask.setRemainderVia(dto.getRemainderVia());
			leadTask.setDescription(dto.getDescription());
			if (nonNull(leadTaskDaoService.addTask(leadTask))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Task Updated Successfully");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Not Updated");
			}
			return new ResponseEntity<>(result, CREATED);

		} catch (Exception e) {
			log.error("Got Exception while updating the lead task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignLeadTask(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		log.info("inside assign task staffId: {} taskId:{}", map.get(STAFF_ID), map.get(TASK_ID));
		try {
			LeadTask leadTask = leadTaskDaoService.getTaskById(map.get(TASK_ID))
					.orElseThrow(() -> new ResourceNotFoundException(LEAD_TASK, TASK_ID, map.get(TASK_ID)));
			EmployeeMaster employee = employeeService.getById(map.get(STAFF_ID))
					.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, map.get(STAFF_ID)));
			leadTask.setAssignTo(employee);
			if (nonNull(leadTaskDaoService.addTask(leadTask))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Task Assigned SuccessFully");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Not Assigned");
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.error("Got Exception while assigning the lead task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteLeadTask(Integer taskId) {
		log.info("inside the deleteLeadTask method...{}", taskId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			LeadTask leadTask = leadTaskDaoService.getTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD_TASK, TASK_ID, taskId));
			leadTask.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
			leadTask.setDeletedDate(now().atZone(systemDefault())
		            .withZoneSameInstant(of(INDIA_ZONE))
		            .toLocalDateTime());
			if (nonNull(leadTaskDaoService.addTask(leadTask))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Task deleted SuccessFully");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Not delete.");
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.error("Got Exception while deleting the lead task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
