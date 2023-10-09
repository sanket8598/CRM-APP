package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.AddCallDtoMapper.TO_CALL;
import static ai.rnt.crm.dto_mapper.AddCallDtoMapper.TO_EDIT_CALL_DTO;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.AddCallDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.AddCallDto;
import ai.rnt.crm.entity.AddCall;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.security.UserDetail;
import ai.rnt.crm.service.AddCallService;
import ai.rnt.crm.service.EmployeeService;
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
public class AddCallServiceImpl implements AddCallService {

	private final AddCallDaoService addCallDaoService;
	private final LeadDaoService leadDaoService;
	private final EmployeeService employeeService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCall(AddCallDto dto, Integer leadsId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			AddCall addCall = TO_CALL.apply(dto).orElseThrow(null);
			Leads lead = leadDaoService.getLeadById(leadsId)
					.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", leadsId));
			addCall.setLead(lead);
			addCall.setCallFrom(employeeService.getById(dto.getCallFrom().getStaffId()).orElseThrow(
					() -> new ResourceNotFoundException("Employee", "staffId", dto.getCallFrom().getStaffId())));
			addCall.setStatus("Save");
			if (nonNull(addCallDaoService.addCall(addCall)))
				result.put(MESSAGE, "Call Added Successfully");
			else
				result.put(MESSAGE, "Call Not Added");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignCall(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign call staffId: {} callId:{}", map.get("staffId"), map.get("addCallId"));
		try {
			AddCall call = addCallDaoService.getCallById(map.get("addCallId"))
					.orElseThrow(() -> new ResourceNotFoundException("AddCall", "callId", map.get("addCallId")));
			EmployeeMaster employee = employeeService.getById(map.get("staffId"))
					.orElseThrow(() -> new ResourceNotFoundException("Employee", "staffId", map.get("staffId")));
			call.setCallFrom(employee);
			if (nonNull(addCallDaoService.addCall(call))) {
				resultMap.put(MESSAGE, "Call Assigned SuccessFully");
				resultMap.put(SUCCESS, true);
			} else {
				resultMap.put(MESSAGE, "Call Not Assigned");
				resultMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(resultMap, OK);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> markAsCompleted(Integer callId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			AddCall call = addCallDaoService.getCallById(callId).orElseThrow(null);
			call.setUpdatedDate(LocalDateTime.now());
			if (nonNull(addCallDaoService.addCall(call))) {
				result.put(MESSAGE, "Call updated SuccessFully");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Call Not updated");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCall(Integer callId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			AddCall call = addCallDaoService.getCallById(callId).orElseThrow(null);
			if (nonNull(getContext()) && nonNull(getContext().getAuthentication())
					&& nonNull(getContext().getAuthentication().getDetails())) {
				UserDetail details = (UserDetail) getContext().getAuthentication().getDetails();
				call.setDeletedBy(details.getStaffId());
				call.setDeletedDate(LocalDateTime.now());
			}
			if (nonNull(addCallDaoService.addCall(call))) {
				result.put(MESSAGE, "Call deleted SuccessFully.");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Call Not delete.");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> editCall(Integer callId) {
		EnumMap<ApiResponse, Object> call = new EnumMap<>(ApiResponse.class);
		try {
			call.put(DATA, TO_EDIT_CALL_DTO.apply(addCallDaoService.getCallById(callId).orElseThrow(null)));
			call.put(SUCCESS, true);
			return new ResponseEntity<>(call, FOUND);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCall(AddCallDto dto, Integer callId, String status) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			AddCall call = addCallDaoService.getCallById(callId).orElseThrow(null);
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
			if (nonNull(addCallDaoService.addCall(call))) {
				if (status.equalsIgnoreCase("Save"))
					result.put(MESSAGE, "Call Updated Successfully");
				else
					result.put(MESSAGE, "Call Updated And Completed Successfully");
			} else
				result.put(MESSAGE, "Call Not Updated");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}
}