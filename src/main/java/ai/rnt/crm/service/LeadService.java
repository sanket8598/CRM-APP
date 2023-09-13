package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.enums.ApiResponse;

public interface LeadService {

	public ResponseEntity<EnumMap<ApiResponse, Object>> createLead(LeadDto leadDto);

	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadsByStatus(String leadsStatus);

	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllLeadSource();

	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllDropDownData();

	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadDashboardData();

	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadDashboardDataByStatus(String leadsStatus);

	public ResponseEntity<EnumMap<ApiResponse, Object>> editLead(Integer leadId);

	public ResponseEntity<EnumMap<ApiResponse, Object>> qualifyLead(Integer leadId,QualifyLeadDto dto);

	public ResponseEntity<EnumMap<ApiResponse, Object>> assignLead(Map<String, Object> map);

}
