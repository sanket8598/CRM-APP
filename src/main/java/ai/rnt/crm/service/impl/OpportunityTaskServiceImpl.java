package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto.opportunity.mapper.OpportunityTaskDtoMapper.TO_OPPORTUNITY_TASK;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.TaskUtil.checkDuplicateOptyTask;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.EnumMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.OpportunityTaskDaoService;
import ai.rnt.crm.dto.opportunity.OpportunityTaskDto;
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

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addOpportunityTask(@Valid OpportunityTaskDto dto,
			Integer optyId) {
		log.info("inside the addOpportunityTask method...{}", optyId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			OpportunityTask opportunityTask = TO_OPPORTUNITY_TASK.apply(dto).orElseThrow(
					() -> new ResourceNotFoundException("OpportunityTask", "optyTaskId", dto.getOptyTaskId()));
			Opportunity opportunity = opportunityDaoService.findOpportunity(optyId)
					.orElseThrow(() -> new ResourceNotFoundException("Opportunity", "optyId", optyId));
			employeeService.getById(auditAwareUtil.getLoggedInStaffId()).ifPresent(opportunityTask::setAssignTo);
			opportunityTask.setOpportunity(opportunity);
			if (checkDuplicateOptyTask(opportunityTaskDaoService.getAllTask(), opportunityTask)) {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Already Exists !!");
			} else if (nonNull(opportunityTaskDaoService.addOptyTask(opportunityTask))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Task Added Successfully");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Not Added");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while adding the opportunity task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
