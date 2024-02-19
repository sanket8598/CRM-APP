package ai.rnt.crm.service;

import java.util.EnumMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.opportunity.OpportunityTaskDto;
import ai.rnt.crm.enums.ApiResponse;

public interface OpportunityTaskService {

	ResponseEntity<EnumMap<ApiResponse, Object>> addOpportunityTask(@Valid OpportunityTaskDto dto, Integer optyId);

}
