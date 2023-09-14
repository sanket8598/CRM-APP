package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.AddCallDto;
import ai.rnt.crm.enums.ApiResponse;

public interface AddCallService {

	ResponseEntity<EnumMap<ApiResponse, Object>> addCall(AddCallDto dto, Integer leadsId);

	ResponseEntity<EnumMap<ApiResponse, Object>> assignCall(Map<String, Object> map);

}
