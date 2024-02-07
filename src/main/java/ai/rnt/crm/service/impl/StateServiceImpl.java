package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.StateDtoMapper.TO_STATE_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.springframework.http.HttpStatus.FOUND;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.StateDaoService;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.StateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateServiceImpl implements StateService {

	private final StateDaoService stateDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllState() {
		log.info("inside the getAllState method...");
		EnumMap<ApiResponse, Object> allState = new EnumMap<>(ApiResponse.class);
		try {
			allState.put(SUCCESS, true);
			allState.put(DATA, TO_STATE_DTOS.apply(stateDaoService.getAllState()));
			return new ResponseEntity<>(allState, FOUND);
		} catch (Exception e) {
			log.info("Got Exception while getting the state..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
