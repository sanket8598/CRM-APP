package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.GetVisitTaskDto;
import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.dto.VisitTaskDto;
import ai.rnt.crm.enums.ApiResponse;

public interface VisitService {

	ResponseEntity<EnumMap<ApiResponse, Object>> saveVisit(VisitDto dto);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteVisit(Integer visitId);

	ResponseEntity<EnumMap<ApiResponse, Object>> visitMarkAsCompleted(Integer visitId);

	ResponseEntity<EnumMap<ApiResponse, Object>> assignVisit(Map<String, Integer> map);

	ResponseEntity<EnumMap<ApiResponse, Object>> editVisit(Integer visitId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateVisit(VisitDto dto, Integer visitId, String status);

	ResponseEntity<EnumMap<ApiResponse, Object>> addVisitTask(@Valid VisitTaskDto dto, Integer leadsId,
			Integer visitId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getVisitTask(Integer taskId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateVisitTask(GetVisitTaskDto dto, Integer taskId);

}
