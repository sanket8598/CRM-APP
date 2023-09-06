package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.enums.ApiResponse;

public interface LeadService  {

	public ResponseEntity<EnumMap<ApiResponse,Object>> createLead(LeadDto leadDto);
}
