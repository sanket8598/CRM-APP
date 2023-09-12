package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.CountryDtoMapper.TO_COUNTRY_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.springframework.http.HttpStatus.FOUND;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.CountryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

	private final CountryDaoService countryDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllCountry() {
		EnumMap<ApiResponse, Object> allCountry = new EnumMap<>(ApiResponse.class);
		try {
			allCountry.put(SUCCESS, true);
			allCountry.put(DATA, TO_COUNTRY_DTOS.apply(countryDaoService.getAllCountry()));
			return new ResponseEntity<>(allCountry, FOUND);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}
}
