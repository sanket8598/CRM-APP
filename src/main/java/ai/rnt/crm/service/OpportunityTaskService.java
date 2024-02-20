package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.opportunity.GetOpportunityTaskDto;
import ai.rnt.crm.dto.opportunity.OpportunityTaskDto;
import ai.rnt.crm.enums.ApiResponse;

public interface OpportunityTaskService {

	ResponseEntity<EnumMap<ApiResponse, Object>> addOpportunityTask(@Valid OpportunityTaskDto dto, Integer optyId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getOpportunityTask(Integer taskId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateOpportunityTask(GetOpportunityTaskDto dto, Integer taskId);

	ResponseEntity<EnumMap<ApiResponse, Object>> assignOpportunityTask(Map<String, Integer> map);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteOpportunityTask(Integer taskId);

}
