package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.dto_mapper.CityDtoMapper.TO_CITY;
import static ai.rnt.crm.dto_mapper.CityDtoMapper.TO_CITY_DTO;
import static ai.rnt.crm.dto_mapper.CityDtoMapper.TO_CITY_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.CityService;
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
public class CityServiceImpl implements CityService {

	private final CityDaoService cityDaoService;
	private final StateDaoService stateDaoService;
	private final AuditAwareUtil auditAwareUtil;
	private final CompanyMasterDaoService companyMasterDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllCity() {
		log.info("inside the getAllCity method...{}");
		EnumMap<ApiResponse, Object> allCity = new EnumMap<>(ApiResponse.class);
		try {
			allCity.put(SUCCESS, true);
			allCity.put(DATA, TO_CITY_DTOS.apply(cityDaoService.getAllCity()));
			return new ResponseEntity<>(allCity, OK);
		} catch (Exception e) {
			log.info("Got Exception while getting the City..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCity(CityDto dto) {
		log.info("inside the addCity method...{}");
		EnumMap<ApiResponse, Object> addCityData = new EnumMap<>(ApiResponse.class);
		try {
			if (cityDaoService.isCityPresent(dto.getCity().trim(), dto.getState().getStateId())) {
				addCityData.put(SUCCESS, false);
				addCityData.put(MESSAGE, "This City Is Already Present !!");
				return new ResponseEntity<>(addCityData, OK);
			} else {
				CityMaster city = TO_CITY.apply(dto).orElseThrow(ResourceNotFoundException::new);
				if (nonNull(cityDaoService.addCity(city))) {
					addCityData.put(MESSAGE, "City Added Successfully");
					addCityData.put(SUCCESS, true);
				} else {
					addCityData.put(MESSAGE, "City Not Added");
					addCityData.put(SUCCESS, false);
				}
				return new ResponseEntity<>(addCityData, CREATED);
			}
		} catch (Exception e) {
			log.info("Got Exception while adding the City..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCity(Integer cityId) {
		log.info("inside the getCity method...{}", cityId);
		EnumMap<ApiResponse, Object> getCityData = new EnumMap<>(ApiResponse.class);
		try {
			getCityData.put(DATA, TO_CITY_DTO.apply(cityDaoService.findCityById(cityId)
					.orElseThrow(() -> new ResourceNotFoundException("CityMaster", "cityId", cityId))));
			getCityData.put(SUCCESS, true);
			return new ResponseEntity<>(getCityData, OK);
		} catch (Exception e) {
			getCityData.put(SUCCESS, false);
			log.info("Got Exception while getting the City by id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCity(CityDto dto) {
		log.info("inside the updateCity method...{}", dto.getCityId());
		EnumMap<ApiResponse, Object> updateCityData = new EnumMap<>(ApiResponse.class);
		updateCityData.put(SUCCESS, false);
		try {
			CityMaster cityById = cityDaoService.findCityById(dto.getCityId())
					.orElseThrow(() -> new ResourceNotFoundException("CityMaster", "cityId", dto.getCityId()));
			StateMaster stateById = stateDaoService.findStateById(dto.getState().getStateId()).orElseThrow(
					() -> new ResourceNotFoundException("StateMaster", "stateId", dto.getState().getStateId()));
			cityById.setCity(dto.getCity());
			cityById.setState(stateById);
			if (nonNull(cityDaoService.addCity(cityById))) {
				updateCityData.put(MESSAGE, "City Updated Successfully");
				updateCityData.put(SUCCESS, true);
			} else
				updateCityData.put(MESSAGE, "City Not Update.");
			return new ResponseEntity<>(updateCityData, CREATED);
		} catch (Exception e) {
			log.info("Got Exception while updating the City data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCity(Integer cityId) {
		log.info("inside the deleteCity method...{}", cityId);
		EnumMap<ApiResponse, Object> deleteCityData = new EnumMap<>(ApiResponse.class);
		deleteCityData.put(SUCCESS, false);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			CityMaster city = cityDaoService.findCityById(cityId)
					.orElseThrow(() -> new ResourceNotFoundException("CityMaster", "cityId", cityId));
			if (!companyMasterDaoService.findByCityId(cityId).isEmpty()) {
				deleteCityData.put(MESSAGE, "You Can't Delete This City Is In Use!!");
				deleteCityData.put(SUCCESS, false);
				return new ResponseEntity<>(deleteCityData, OK);
			}
			city.setDeletedBy(loggedInStaffId);
			city.setDeletedDate(now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
			if (nonNull(cityDaoService.addCity(city))) {
				deleteCityData.put(MESSAGE, "City Deleted Successfully");
				deleteCityData.put(SUCCESS, true);
			} else
				deleteCityData.put(MESSAGE, "City Not Delete.");
			return new ResponseEntity<>(deleteCityData, OK);
		} catch (Exception e) {
			log.info("Got Exception while deleting the City data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
