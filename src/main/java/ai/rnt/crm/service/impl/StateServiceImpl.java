package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.dto_mapper.StateDtoMapper.TO_STATE;
import static ai.rnt.crm.dto_mapper.StateDtoMapper.TO_STATE_DTO;
import static ai.rnt.crm.dto_mapper.StateDtoMapper.TO_STATE_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dto.StateDto;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.StateService;
import ai.rnt.crm.util.AuditAwareUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateServiceImpl implements StateService {

	private final StateDaoService stateDaoService;
	private final CountryDaoService countryDaoService;
	private final AuditAwareUtil auditAwareUtil;
	private final CompanyMasterDaoService companyMasterDaoService;
	private final CityDaoService cityDaoService;

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

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addState(StateDto dto) {
		log.info("inside the addState method...");
		EnumMap<ApiResponse, Object> addState = new EnumMap<>(ApiResponse.class);
		try {
			if (stateDaoService.isStatePresent(dto.getState().trim(), dto.getCountry().getCountryId())) {
				addState.put(SUCCESS, false);
				addState.put(MESSAGE, "This State Is Already Present !!");
				return new ResponseEntity<>(addState, OK);
			} else {
				StateMaster state = TO_STATE.apply(dto).orElseThrow(ResourceNotFoundException::new);
				if (nonNull(stateDaoService.addState(state))) {
					addState.put(MESSAGE, "State Added Successfully");
					addState.put(SUCCESS, true);
				} else {
					addState.put(MESSAGE, "State Not Added");
					addState.put(SUCCESS, false);
				}
			}
			return new ResponseEntity<>(addState, CREATED);
		} catch (Exception e) {
			log.info("Got Exception while adding the state..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getState(Integer stateId) {
		log.info("inside the getState method...{}", stateId);
		EnumMap<ApiResponse, Object> stateData = new EnumMap<>(ApiResponse.class);
		try {
			stateData.put(DATA, TO_STATE_DTO.apply(stateDaoService.findStateById(stateId)
					.orElseThrow(() -> new ResourceNotFoundException("StateMaster", "stateId", stateId))));
			stateData.put(SUCCESS, true);
			return new ResponseEntity<>(stateData, OK);
		} catch (Exception e) {
			log.info("Got Exception while getting the state by id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateState(StateDto dto) {
		log.info("inside the updatetState method...{}", dto.getStateId());
		EnumMap<ApiResponse, Object> updateData = new EnumMap<>(ApiResponse.class);
		updateData.put(SUCCESS, false);
		try {
			StateMaster stateById = stateDaoService.findStateById(dto.getStateId())
					.orElseThrow(() -> new ResourceNotFoundException("StateMaster", "stateId", dto.getStateId()));
			CountryMaster country = countryDaoService.findCountryById(dto.getCountry().getCountryId()).orElseThrow(
					() -> new ResourceNotFoundException("CountryMaster", "countryId", dto.getCountry().getCountryId()));
			stateById.setState(dto.getState());
			stateById.setCountry(country);
			if (nonNull(stateDaoService.addState(stateById))) {
				updateData.put(MESSAGE, "State Updated Successfully");
				updateData.put(SUCCESS, true);
			} else
				updateData.put(MESSAGE, "State Not Update.");
			return new ResponseEntity<>(updateData, CREATED);
		} catch (Exception e) {
			log.info("Got Exception while updating the State by id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteState(Integer stateId) {
		log.info("inside the deleteState method...{}", stateId);
		EnumMap<ApiResponse, Object> deleteData = new EnumMap<>(ApiResponse.class);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			StateMaster state = stateDaoService.findStateById(stateId)
					.orElseThrow(() -> new ResourceNotFoundException("StateMaster", "stateId", stateId));
			if (!companyMasterDaoService.findByStateId(stateId).isEmpty()
					|| !cityDaoService.findByStateId(stateId).isEmpty()) {
				deleteData.put(MESSAGE, "This state is in use, You can't delete.");
				deleteData.put(SUCCESS, false);
				return new ResponseEntity<>(deleteData, OK);
			}
			state.setDeletedBy(loggedInStaffId);
			state.setDeletedDate(now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
			if (nonNull(stateDaoService.addState(state))) {
				deleteData.put(MESSAGE, "State Deleted Successfully");
				deleteData.put(SUCCESS, true);
			} else
				deleteData.put(MESSAGE, "State Not Delete.");
			return new ResponseEntity<>(deleteData, OK);
		} catch (Exception e) {
			log.info("Got Exception while deleting the State by id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
