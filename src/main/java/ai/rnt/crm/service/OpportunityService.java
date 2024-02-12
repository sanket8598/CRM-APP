package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.enums.ApiResponse;

public interface OpportunityService {

	ResponseEntity<EnumMap<ApiResponse, Object>> getOpportunityDataByStatus(String status);

	ResponseEntity<EnumMap<ApiResponse, Object>> getDashBoardData(Integer staffId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getOpportunityData(Integer optId);

}
