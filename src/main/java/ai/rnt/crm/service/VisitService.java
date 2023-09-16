package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.enums.ApiResponse;

public interface VisitService {

	ResponseEntity<EnumMap<ApiResponse, Object>> saveVisit(VisitDto dto);

}