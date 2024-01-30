package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.EMPLOYEE;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.DateFormatterConstant.END_TIME;
import static ai.rnt.crm.constants.DateFormatterConstant.START_TIME;
import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.constants.StatusConstants.COMPLETE;
import static ai.rnt.crm.constants.StatusConstants.SAVE;
import static ai.rnt.crm.dto_mapper.VisitDtoMapper.TO_GET_VISIT_DTO;
import static ai.rnt.crm.dto_mapper.VisitDtoMapper.TO_VISIT;
import static ai.rnt.crm.dto_mapper.VisitTaskDtoMapper.TO_GET_VISIT_TASK_DTO;
import static ai.rnt.crm.dto_mapper.VisitTaskDtoMapper.TO_VISIT_TASK;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.TaskUtil.checkDuplicateVisitTask;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.dto.GetVisitTaskDto;
import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.dto.VisitTaskDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.service.VisitService;
import ai.rnt.crm.util.AuditAwareUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 14-09-2023.
 *
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitServiceImpl implements VisitService {

	private final VisitDaoService visitDaoService;
	private final LeadDaoService leadDaoService;
	private final EmployeeService employeeService;
	private final AuditAwareUtil auditAwareUtil;

	public static final String TASK_ID = "taskId";
	public static final String VISIT_TASK = "VisitTask";
	public static final String VISIT_ID = "visitId";
	public static final String VISIT = "Visit";

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> saveVisit(VisitDto dto) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		log.info("inside add visit leadId:{}", dto.getLeadId());
		try {
			Visit visit = TO_VISIT.apply(dto).orElseThrow(null);
			visit.setParticipates(dto.getParticipates().stream().collect(Collectors.joining(",")));
			Leads lead = leadDaoService.getLeadById(dto.getLeadId())
					.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", dto.getLeadId()));
			visit.setLead(lead);
			visit.setVisitBy(employeeService.getById(auditAwareUtil.getLoggedInStaffId()).orElseThrow(
					() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, auditAwareUtil.getLoggedInStaffId())));
			if (dto.isAllDay()) {
				visit.setStartTime(START_TIME);
				visit.setEndTime(END_TIME);
			}
			visit.setStatus(SAVE);
			if (nonNull(visitDaoService.saveVisit(visit)))
				result.put(MESSAGE, "Visit Added Successfully");
			else
				result.put(MESSAGE, "Visit Not Added");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while adding the visit..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> editVisit(Integer visitId) {
		log.info("inside editVisit method...{}", visitId);
		EnumMap<ApiResponse, Object> visit = new EnumMap<>(ApiResponse.class);
		try {
			visit.put(SUCCESS, true);
			visit.put(DATA, TO_GET_VISIT_DTO.apply(visitDaoService.getVisitsByVisitId(visitId)
					.orElseThrow(() -> new ResourceNotFoundException(VISIT, VISIT_ID, visitId))));
			return new ResponseEntity<>(visit, OK);
		} catch (Exception e) {
			log.error("Got Exception while get visit for edit..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateVisit(VisitDto dto, Integer visitId, String status) {
		log.info("inside updateVisit method...{} {}", visitId, status);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			Visit visit = visitDaoService.getVisitsByVisitId(visitId)
					.orElseThrow(() -> new ResourceNotFoundException(VISIT, VISIT_ID, visitId));
			visit.setLocation(dto.getLocation());
			visit.setSubject(dto.getSubject());
			visit.setContent(dto.getContent());
			visit.setComment(dto.getComment());
			visit.setDuration(dto.getDuration());
			visit.setStartDate(dto.getStartDate());
			visit.setEndDate(dto.getEndDate());
			if (dto.isAllDay()) {
				visit.setStartTime(START_TIME);
				visit.setEndTime(END_TIME);
			} else {
				visit.setStartTime(dto.getStartTime());
				visit.setEndTime(dto.getEndTime());
			}
			visit.setParticipates(dto.getParticipates().stream().collect(Collectors.joining(",")));
			visit.setAllDay(dto.isAllDay());
			visit.setStatus(status);
			if (nonNull(visitDaoService.saveVisit(visit))) {
				if (status.equalsIgnoreCase(SAVE))
					result.put(MESSAGE, "Visit Updated Successfully");
				else
					result.put(MESSAGE, "Visit Updated And Completed Successfully");
			} else
				result.put(MESSAGE, "Visit Not Updated");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while update the visit..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignVisit(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign Visit staffId: {} visitId:{}", map.get(STAFF_ID), map.get(VISIT_ID));
		try {
			Visit visit = visitDaoService.getVisitsByVisitId(map.get(VISIT_ID))
					.orElseThrow(() -> new ResourceNotFoundException(VISIT, VISIT_ID, map.get(VISIT_ID)));
			EmployeeMaster employee = employeeService.getById(map.get(STAFF_ID))
					.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, map.get(STAFF_ID)));
			visit.getVisitTasks().stream()
					.filter(e -> visit.getVisitBy().getStaffId().equals(e.getAssignTo().getStaffId())).forEach(e -> {
						e.setAssignTo(employee);
						visitDaoService.addVisitTask(e);
					});
			visit.setVisitBy(employee);
			if (nonNull(visitDaoService.saveVisit(visit))) {
				resultMap.put(MESSAGE, "Visit Assigned SuccessFully");
				resultMap.put(SUCCESS, true);
			} else {
				resultMap.put(MESSAGE, "Visit Not Assigned");
				resultMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(resultMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while assign the visit..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> visitMarkAsCompleted(Integer visitId) {
		log.info("inside visitMarkAsCompleted method...{}", visitId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			Visit visit = visitDaoService.getVisitsByVisitId(visitId)
					.orElseThrow(() -> new ResourceNotFoundException(VISIT, VISIT_ID, visitId));
			visit.setStatus(COMPLETE);
			if (nonNull(visitDaoService.saveVisit(visit))) {
				result.put(MESSAGE, "Visit updated SuccessFully");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Visit Not updated");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.error("Got Exception while visitMarkAsCompleted..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteVisit(Integer visitId) {
		log.info("inside deleteVisit method...{}", visitId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			Visit visit = visitDaoService.getVisitsByVisitId(visitId)
					.orElseThrow(() -> new ResourceNotFoundException(VISIT, VISIT_ID, visitId));
			visit.getVisitTasks().stream().forEach(e -> {
				e.setDeletedBy(loggedInStaffId);
				e.setDeletedDate(now().atZone(systemDefault())
			            .withZoneSameInstant(of(INDIA_ZONE))
			            .toLocalDateTime());
				visitDaoService.addVisitTask(e);
			});
			visit.setDeletedBy(loggedInStaffId);
			visit.setDeletedDate(now().atZone(systemDefault())
		            .withZoneSameInstant(of(INDIA_ZONE))
		            .toLocalDateTime());
			if (nonNull(visitDaoService.saveVisit(visit))) {
				result.put(MESSAGE, "Visit deleted SuccessFully.");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Visit Not delete.");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.error("Got Exception while deleting the visit..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addVisitTask(@Valid VisitTaskDto dto, Integer leadsId,
			Integer visitId) {
		log.info("inside addVisitTask method...{} {}", leadsId, visitId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			VisitTask visitTask = TO_VISIT_TASK.apply(dto).orElseThrow(ResourceNotFoundException::new);
			Optional<Leads> lead = leadDaoService.getLeadById(leadsId);
			if (lead.isPresent())
				visitTask.setAssignTo(lead.get().getEmployee());
			visitDaoService.getVisitsByVisitId(visitId).ifPresent(visitTask::setVisit);
			if (checkDuplicateVisitTask(visitDaoService.getAllTask(), visitTask)) {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Already Exists !!");
			} else if (nonNull(visitDaoService.addVisitTask(visitTask))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Task Added Successfully..!!");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Not Added");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("error occured while adding visit tasks..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getVisitTask(Integer taskId) {
		log.info("inside getVisitTask method...{}", taskId);
		EnumMap<ApiResponse, Object> visitTask = new EnumMap<>(ApiResponse.class);
		try {
			visitTask.put(SUCCESS, true);
			visitTask.put(DATA, TO_GET_VISIT_TASK_DTO.apply(visitDaoService.getVisitTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(VISIT_TASK, TASK_ID, taskId))));
			return new ResponseEntity<>(visitTask, OK);
		} catch (Exception e) {
			log.error("error occured while getting visit task by id..{}", +taskId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateVisitTask(GetVisitTaskDto dto, Integer taskId) {
		log.info("inside updateVisitTask method...{}", taskId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			VisitTask visitTask = visitDaoService.getVisitTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(VISIT_TASK, TASK_ID, taskId));
			visitTask.setSubject(dto.getSubject());
			visitTask.setStatus(dto.getStatus());
			visitTask.setPriority(dto.getPriority());
			visitTask.setDueDate(dto.getUpdateDueDate());
			visitTask.setDueTime(dto.getDueTime());
			visitTask.setRemainderDueAt(dto.getRemainderDueAt());
			visitTask.setRemainderDueOn(dto.getUpdatedRemainderDueOn());
			visitTask.setRemainderOn(dto.isRemainderOn());
			visitTask.setRemainderVia(dto.getRemainderVia());
			visitTask.setDescription(dto.getDescription());
			if (nonNull(visitDaoService.addVisitTask(visitTask))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Task Updated Successfully..!!");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Not Updated");
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.error("error occured while updating visit task by id..{}", +taskId, e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignVisitTask(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		log.info("inside assign task staffId: {} taskId:{}", map.get(STAFF_ID), map.get(TASK_ID));
		try {
			VisitTask visitTask = visitDaoService.getVisitTaskById(map.get(TASK_ID))
					.orElseThrow(() -> new ResourceNotFoundException(VISIT_TASK, TASK_ID, map.get(TASK_ID)));
			EmployeeMaster employee = employeeService.getById(map.get(STAFF_ID))
					.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, map.get(STAFF_ID)));
			visitTask.setAssignTo(employee);
			if (nonNull(visitDaoService.addVisitTask(visitTask))) {
				result.put(SUCCESS, true);
				result.put(MESSAGE, "Task Assigned SuccessFully");
			} else {
				result.put(SUCCESS, false);
				result.put(MESSAGE, "Task Not Assigned");
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.error("Got Exception while assigning the visitTask..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteVisitTask(Integer taskId) {
		log.info("inside deleteVisitTask method...{}", taskId);
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			VisitTask visitTask = visitDaoService.getVisitTaskById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException(VISIT_TASK, TASK_ID, taskId));
			visitTask.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
			visitTask.setDeletedDate(now().atZone(systemDefault())
		            .withZoneSameInstant(of(INDIA_ZONE))
		            .toLocalDateTime());
			if (nonNull(visitDaoService.addVisitTask(visitTask))) {
				result.put(MESSAGE, "Visit Task Deleted SuccessFully.");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Visit Task Not delete.");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);

		} catch (Exception e) {
			log.error("error occured while deleting visit task by id..{}", +taskId, e.getMessage());
			throw new CRMException(e);
		}
	}
}
