package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.enums.ApiResponse;

/**@author Nikhil Gaikwad 
 * @version 1.0
 * @since 11/09/2023.
 * @return city
 */

public interface CityService {

	ResponseEntity<EnumMap<ApiResponse, Object>> getAllCity();

}
