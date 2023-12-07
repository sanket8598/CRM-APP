package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.StatusConstants.SAVE;
import static ai.rnt.crm.dto_mapper.LeadTaskDtoMapper.TO_LEAD_TASK;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.EnumMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadTaskDaoService;
import ai.rnt.crm.dto.LeadTaskDto;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class LeadTaskServiceImpl implements LeadTaskService {

	private final LeadTaskDaoService leadTaskDaoService;
	private final LeadDaoService leadDaoService;
	private final AuditAwareUtil auditAwareUtil;
	private final EmployeeService employeeService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addTask(@Valid LeadTaskDto dto, Integer leadsId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			LeadTask leadTask = TO_LEAD_TASK.apply(dto)
					.orElseThrow(() -> new ResourceNotFoundException("LeadTask", "leadTaskId", dto.getLeadTaskId()));
			Leads lead = leadDaoService.getLeadById(leadsId)
					.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", leadsId));
			employeeService.getById(auditAwareUtil.getLoggedInStaffId()).ifPresent(leadTask::setAssignTo);
			leadTask.setLead(lead);
			leadTask.setStatus(SAVE);
			if (nonNull(leadTaskDaoService.addTask(leadTask)))
				result.put(MESSAGE, "Task Added Successfully");
			else
				result.put(MESSAGE, "Task Not Added");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.info("Got Exception while adding the task..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
