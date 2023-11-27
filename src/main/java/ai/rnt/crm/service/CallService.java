package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.CallDto;
import ai.rnt.crm.enums.ApiResponse;

public interface CallService {

	ResponseEntity<EnumMap<ApiResponse, Object>> addCall(CallDto dto, Integer leadsId);

	ResponseEntity<EnumMap<ApiResponse, Object>> assignCall(Map<String, Integer> map);

    ResponseEntity<EnumMap<ApiResponse, Object>> markAsCompleted(Integer callId);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteCall(Integer callId);

	ResponseEntity<EnumMap<ApiResponse, Object>> editCall(Integer callId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateCall(CallDto dto, Integer callId, String status);
}
