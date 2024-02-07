package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.CityDtoMapper.TO_CITY_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.CityService;
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
}
