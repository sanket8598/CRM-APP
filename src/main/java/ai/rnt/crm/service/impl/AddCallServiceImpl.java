package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.AddCallDtoMapper.TO_CALL;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_LEAD;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.AddCallDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.AddCallDto;
import ai.rnt.crm.entity.AddCall;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.AddCallService;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 */
@Service
@RequiredArgsConstructor
public class AddCallServiceImpl implements AddCallService {

	private final AddCallDaoService addCallDaoService;
	private final LeadDaoService leadDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCall(AddCallDto dto, Integer leadsId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			AddCall addCall = TO_CALL.apply(dto).orElseThrow(null);
			TO_LEAD.apply(leadDaoService.getById(leadsId).orElseThrow(null)).ifPresent(addCall::setLead);
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
}
