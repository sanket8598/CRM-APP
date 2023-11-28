package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.CallDtoMapper.TO_CALL;
import static ai.rnt.crm.dto_mapper.CallDtoMapper.TO_EDIT_CALL_DTO;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.CallDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.CallService;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;
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
			call.setStatus("Save");
			if (nonNull(callDaoService.call(call)))
				result.put(MESSAGE, "Call Added Successfully");
			else
				result.put(MESSAGE, "Call Not Added");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.info("Got Exception while adding the call..{}",e.getMessage());
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
			log.info("Got Exception while assign the call..{}" ,e.getMessage());
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
			call.setStatus("complete");
			if (nonNull(callDaoService.call(call))) {
				result.put(MESSAGE, "Call updated SuccessFully");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Call Not updated");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.info("Got Exception while markAsCompleted the call..{}" ,e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCall(Integer callId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			Call call = callDaoService.getCallById(callId)
					.orElseThrow(() -> new ResourceNotFoundException("Call", "callId", callId));
			call.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
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
			log.info("Got Exception while delete the call..{}" ,e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> editCall(Integer callId) {
		EnumMap<ApiResponse, Object> call = new EnumMap<>(ApiResponse.class);
		try {
			call.put(DATA, TO_EDIT_CALL_DTO.apply(callDaoService.getCallById(callId)
					.orElseThrow(() -> new ResourceNotFoundException("AddCall", "callId", callId))));
			call.put(SUCCESS, true);
			return new ResponseEntity<>(call, FOUND);
		} catch (Exception e) {
			log.info("Got exception while get call data for edit..{}",e.getMessage());
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
			call.setDirection(dto.getDirection());
			call.setPhoneNo(dto.getPhoneNo());
			call.setComment(dto.getComment());
			call.setDuration(dto.getDuration());
			call.setDueDate(dto.getDueDate());
			call.setStatus(status);
			call.setUpdatedDate(LocalDateTime.now());
			if (nonNull(callDaoService.call(call))) {
				if (status.equalsIgnoreCase("Save"))
					result.put(MESSAGE, "Call Updated Successfully");
				else
					result.put(MESSAGE, "Call Updated And Completed Successfully");
			} else
				result.put(MESSAGE, "Call Not Updated");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.info("Got exception while update call..{}" ,e.getMessage());
			throw new CRMException(e);
		}
	}
}