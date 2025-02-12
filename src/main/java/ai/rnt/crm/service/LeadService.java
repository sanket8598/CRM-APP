package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ai.rnt.crm.dto.DescriptionDto;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.LeadSortFilterDto;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.enums.ApiResponse;

public interface LeadService {

	public ResponseEntity<EnumMap<ApiResponse, Object>> createLead(LeadDto leadDto);

	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadsByStatus(String leadsStatus);

	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllLeadSource();

	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllDropDownData();

	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadDashboardData();

	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadDashboardDataByStatus(String leadsStatus);

	public ResponseEntity<EnumMap<ApiResponse, Object>> editLead(Integer leadId);

	public ResponseEntity<EnumMap<ApiResponse, Object>> qualifyLead(Integer leadId, QualifyLeadDto dto);

	public ResponseEntity<EnumMap<ApiResponse, Object>> assignLead(Map<String, Integer> map);

	public ResponseEntity<EnumMap<ApiResponse, Object>> disQualifyLead(Integer leadId, LeadDto dto);

	public ResponseEntity<EnumMap<ApiResponse, Object>> updateLeadContact(Integer leadId, UpdateLeadDto dto);

	public ResponseEntity<EnumMap<ApiResponse, Object>> importantLead(Integer leadId, boolean status);

	public ResponseEntity<EnumMap<ApiResponse, Object>> reactiveLead(Integer leadId);

	public ResponseEntity<EnumMap<ApiResponse, Object>> addSortFilterForLeads(LeadSortFilterDto sortFilter);

	public ResponseEntity<EnumMap<ApiResponse, Object>> uploadExcel(MultipartFile file);

	public ResponseEntity<EnumMap<ApiResponse, Object>> getForQualifyLead(Integer leadId);

	public void updateLeadsStatus(Integer leadId);

	public ResponseEntity<EnumMap<ApiResponse, Object>> addDescription(DescriptionDto dto, Integer leadId);

	public ResponseEntity<EnumMap<ApiResponse, Object>> getContactInfo(Integer leadId);

}
