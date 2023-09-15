package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.VisitDtoMapper.TO_VISIT;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.VisitService;
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

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> saveVisit(VisitDto dto) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		log.info("inside add visit leadId:{}", dto.getLeadId());
		try {
			Visit visit = TO_VISIT.apply(dto).orElseThrow(null);
			Leads lead = leadDaoService.getLeadById(dto.getLeadId())
					.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", dto.getLeadId()));
			visit.setLead(lead);
			if (nonNull(visitDaoService.saveVisit(visit)))
				result.put(MESSAGE, "Visit Added Successfully");
			else
				result.put(MESSAGE, "Visit Not Added");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}
}
