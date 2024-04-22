package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.dto.opportunity.AnalysisOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseAsLostOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseOpportunityDto;
import ai.rnt.crm.dto.opportunity.ProposeOpportunityDto;
import ai.rnt.crm.enums.ApiResponse;

public interface OpportunityService {

	ResponseEntity<EnumMap<ApiResponse, Object>> getOpportunityDataByStatus(String status);

	ResponseEntity<EnumMap<ApiResponse, Object>> getDashBoardData(Integer staffId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getOpportunityData(Integer optId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getQualifyPopUpData(Integer leadId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getAnalysisPopUpData(Integer opportunityId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateAnalysisPopUpData(AnalysisOpportunityDto dto,
			Integer opportunityId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getProposePopUpData(Integer opportunityId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateProposePopUpData(ProposeOpportunityDto dto,
			Integer opportunityId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getClosePopUpData(Integer opportunityId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateClosePopUpData(CloseOpportunityDto dto, Integer opportunityId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateOpportunity(UpdateLeadDto dto, Integer opportunityId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateCloseAsLostData(CloseAsLostOpportunityDto dto,
			Integer opportunityId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getCloseAsLostData(Integer opportunityId);

	ResponseEntity<EnumMap<ApiResponse, Object>> assignOpportunity(Map<String, Integer> map);

	ResponseEntity<EnumMap<ApiResponse, Object>> reactiveOpty(Integer optyId);
}
