package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.enums.ApiResponse;

public interface VisitService {

	ResponseEntity<EnumMap<ApiResponse, Object>> saveVisit(VisitDto dto);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteVisit(Integer visitId);

	ResponseEntity<EnumMap<ApiResponse, Object>> visitMarkAsCompleted(Integer visitId);

	ResponseEntity<EnumMap<ApiResponse, Object>> assignVisit(Map<String, Integer> map);

}
