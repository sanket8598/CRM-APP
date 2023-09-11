package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.enums.ApiResponse;

public interface StateService {

	ResponseEntity<EnumMap<ApiResponse, Object>> getAllState();

}
