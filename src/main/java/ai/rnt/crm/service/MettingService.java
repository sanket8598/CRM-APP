package ai.rnt.crm.service;

import java.util.EnumMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.MettingDto;
import ai.rnt.crm.enums.ApiResponse;

public interface MettingService {

	ResponseEntity<EnumMap<ApiResponse, Object>> addMetting(@Valid MettingDto dto, Integer leadsId);

}
