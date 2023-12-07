package ai.rnt.crm.service;

import java.util.EnumMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.LeadTaskDto;
import ai.rnt.crm.enums.ApiResponse;

public interface LeadTaskService {

	ResponseEntity<EnumMap<ApiResponse, Object>> addTask(@Valid LeadTaskDto dto, Integer leadsId);

}
