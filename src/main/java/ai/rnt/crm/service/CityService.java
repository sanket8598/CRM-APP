package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.enums.ApiResponse;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 * @return city
 */

public interface CityService {

	ResponseEntity<EnumMap<ApiResponse, Object>> getAllCity();

	ResponseEntity<EnumMap<ApiResponse, Object>> addCity(CityDto dto);

	ResponseEntity<EnumMap<ApiResponse, Object>> getCity(Integer cityId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateCity(CityDto dto);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteCity(Integer cityId);
}
