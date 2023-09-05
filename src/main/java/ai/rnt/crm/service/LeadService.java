package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ai.rnt.crm.dto.NewLeadDto;
import ai.rnt.crm.enums.ApiResponse;

public interface LeadService  {

	public ResponseEntity<EnumMap<ApiResponse,Object>> createLead(NewLeadDto leadDto, MultipartFile file);
}
