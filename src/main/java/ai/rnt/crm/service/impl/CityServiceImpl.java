package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.CityDtoMapper.TO_CITY_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.springframework.http.HttpStatus.FOUND;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.CityService;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 *@version 1.0
 *@since 11/09/2023.
 */
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

	private final CityDaoService cityDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllCity() {
		EnumMap<ApiResponse, Object> allCity = new EnumMap<>(ApiResponse.class);
		try {
			allCity.put(SUCCESS, true);
			allCity.put(DATA, TO_CITY_DTOS.apply(cityDaoService.getAllCity()));
			return new ResponseEntity<>(allCity, FOUND);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}
}
