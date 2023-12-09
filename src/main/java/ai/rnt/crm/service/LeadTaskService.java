package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.GetLeadTaskDto;
import ai.rnt.crm.dto.LeadTaskDto;
import ai.rnt.crm.enums.ApiResponse;

public interface LeadTaskService {

	ResponseEntity<EnumMap<ApiResponse, Object>> addTask(@Valid LeadTaskDto dto, Integer leadsId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getLeadTask(Integer taskId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateTask(GetLeadTaskDto dto, Integer taskId);

	ResponseEntity<EnumMap<ApiResponse, Object>> assignTask(Map<String, Integer> map);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteTask(Integer taskId);

}
