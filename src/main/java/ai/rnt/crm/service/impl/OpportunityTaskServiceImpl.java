package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.EMPLOYEE;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityTaskDtoMapper.TO_GET_OPPORTUNITY_TASK_DTO;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityTaskDtoMapper.TO_OPPORTUNITY_TASK;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.TaskUtil.checkDuplicateOptyTask;
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

import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.OpportunityTaskDaoService;
import ai.rnt.crm.dto.opportunity.GetOpportunityTaskDto;
import ai.rnt.crm.dto.opportunity.OpportunityTaskDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.OpportunityTask;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.service.OpportunityTaskService;
import ai.rnt.crm.util.AuditAwareUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 19/02/2024
 * @version 1.2
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OpportunityTaskServiceImpl implements OpportunityTaskService {

	private final OpportunityDaoService opportunityDaoService;
	private final AuditAwareUtil auditAwareUtil;
	private final EmployeeService employeeService;
	private final OpportunityTaskDaoService opportunityTaskDaoService;

	public static final String OPPORTUNITY_TASK = "OpportunityTask";
	public static final String TASK_ID = "taskId";

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addOpportunityTask(@Valid OpportunityTaskDto dto,
			Integer optyId) {
		log.info("inside the addOpportunityTask method...{}", optyId);
		EnumMap<ApiResponse, Object> addOpptTaskMap = new EnumMap<>(ApiResponse.class);
		try {
			OpportunityTask opportunityTask = TO_OPPORTUNITY_TASK.apply(dto)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY_TASK, TASK_ID, dto.getOptyTaskId()));
			Opportunity opportunity = opportunityDaoService.findOpportunity(optyId)
					.orElseThrow(() -> new ResourceNotFoundException("Opportunity", "optyId", optyId));
			employeeService.getById(auditAwareUtil.getLoggedInStaffId()).ifPresent(opportunityTask::setAssignTo);
			opportunityTask.setOpportunity(opportunity);
			if (checkDuplicateOptyTask(opportunityTaskDaoService.getAllTask(), opportunityTask)) {
				addOpptTaskMap.put(SUCCESS, false);
				addOpptTaskMap.put(MESSAGE, "Task Already Exists !!");
			} else if (nonNull(opportunityTaskDaoService.addOptyTask(opportunityTask))) {
				addOpptTaskMap.put(SUCCESS, true);
				addOpptTaskMap.put(MESSAGE, "Task Added Successfully");
			} else {
				addOpptTaskMap.put(SUCCESS, false);
				addOpptTaskMap.put(MESSAGE, "Task Not Added");
			}
			return new ResponseEntity<>(addOpptTaskMap, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while adding the opportunity task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getOpportunityTask(Integer taskId) {
		log.info("inside the getOpportunityTask method...{}", taskId);
		EnumMap<ApiResponse, Object> opptTaskMap = new EnumMap<>(ApiResponse.class);
		try {
			opptTaskMap.put(DATA, TO_GET_OPPORTUNITY_TASK_DTO.apply(opportunityTaskDaoService.getOptyTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY_TASK, TASK_ID, taskId))));
			opptTaskMap.put(SUCCESS, true);
			return new ResponseEntity<>(opptTaskMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting the opportunity task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateOpportunityTask(GetOpportunityTaskDto dto,
			Integer taskId) {
		log.info("inside the updateOpportunityTask method...{}", taskId);
		EnumMap<ApiResponse, Object> updOpptTaskMap = new EnumMap<>(ApiResponse.class);
		try {
			OpportunityTask opportunityTask = opportunityTaskDaoService.getOptyTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY_TASK, TASK_ID, taskId));
			opportunityTask.setSubject(dto.getSubject());
			opportunityTask.setStatus(dto.getStatus());
			opportunityTask.setPriority(dto.getPriority());
			opportunityTask.setDueDate(dto.getUpdateDueDate());
			opportunityTask.setDueTime(dto.getDueTime());
			opportunityTask.setRemainderOn(dto.isRemainderOn());
			opportunityTask.setRemainderDueOn(dto.getUpdatedRemainderDueOn());
			opportunityTask.setRemainderDueAt(dto.getRemainderDueAt());
			opportunityTask.setRemainderVia(dto.getRemainderVia());
			opportunityTask.setDescription(dto.getDescription());
			if (nonNull(opportunityTaskDaoService.addOptyTask(opportunityTask))) {
				updOpptTaskMap.put(SUCCESS, true);
				updOpptTaskMap.put(MESSAGE, "Task Updated Successfully");
			} else {
				updOpptTaskMap.put(SUCCESS, false);
				updOpptTaskMap.put(MESSAGE, "Task Not Updated");
			}
			return new ResponseEntity<>(updOpptTaskMap, CREATED);

		} catch (Exception e) {
			log.error("Got Exception while updating the opportunity task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignOpportunityTask(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> asgnOpptTaskMap = new EnumMap<>(ApiResponse.class);
		log.info("inside the assignOpportunityTask staffId: {} taskId:{}", map.get(STAFF_ID), map.get(TASK_ID));
		try {
			OpportunityTask opportunityTask = opportunityTaskDaoService.getOptyTaskById(map.get(TASK_ID))
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY_TASK, TASK_ID, map.get(TASK_ID)));
			EmployeeMaster employee = employeeService.getById(map.get(STAFF_ID))
					.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, map.get(STAFF_ID)));
			opportunityTask.setAssignTo(employee);
			if (nonNull(opportunityTaskDaoService.addOptyTask(opportunityTask))) {
				asgnOpptTaskMap.put(SUCCESS, true);
				asgnOpptTaskMap.put(MESSAGE, "Task Assigned Successfully");
			} else {
				asgnOpptTaskMap.put(SUCCESS, false);
				asgnOpptTaskMap.put(MESSAGE, "Task Not Assigned");
			}
			return new ResponseEntity<>(asgnOpptTaskMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while assigning the opportunity task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteOpportunityTask(Integer taskId) {
		log.info("inside the deleteOpportunityTask method...{}", taskId);
		EnumMap<ApiResponse, Object> delOpptTaskMap = new EnumMap<>(ApiResponse.class);
		try {
			OpportunityTask opportunityTask = opportunityTaskDaoService.getOptyTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(OPPORTUNITY_TASK, TASK_ID, taskId));
			opportunityTask.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
			opportunityTask.setDeletedDate(
					now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
			if (nonNull(opportunityTaskDaoService.addOptyTask(opportunityTask))) {
				delOpptTaskMap.put(SUCCESS, true);
				delOpptTaskMap.put(MESSAGE, "Task deleted Successfully");
			} else {
				delOpptTaskMap.put(SUCCESS, false);
				delOpptTaskMap.put(MESSAGE, "Task Not delete.");
			}
			return new ResponseEntity<>(delOpptTaskMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while deleting the opportunity task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
