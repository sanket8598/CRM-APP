package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.StateDto;
import ai.rnt.crm.enums.ApiResponse;

public interface StateService {

	ResponseEntity<EnumMap<ApiResponse, Object>> getAllState();

	ResponseEntity<EnumMap<ApiResponse, Object>> addState(StateDto dto);

	ResponseEntity<EnumMap<ApiResponse, Object>> getState(Integer stateId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateState(StateDto dto);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteState(Integer stateId);
}
