package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.CountryDto;
import ai.rnt.crm.enums.ApiResponse;

public interface CountryService {

	ResponseEntity<EnumMap<ApiResponse, Object>> getAllCountry();

	ResponseEntity<EnumMap<ApiResponse, Object>> addCountry(CountryDto dto);

	ResponseEntity<EnumMap<ApiResponse, Object>> getCountry(Integer countryId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateCountry(CountryDto dto, Integer countryId);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteCountry(Integer countryId);
}
