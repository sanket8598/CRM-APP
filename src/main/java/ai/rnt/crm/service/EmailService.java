package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.enums.ApiResponse;

public interface EmailService {

	ResponseEntity<EnumMap<ApiResponse, Object>> addEmail(EmailDto dto, Integer leadId);

}
