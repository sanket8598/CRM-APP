package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.enums.ApiResponse;

public interface EmailService {

	ResponseEntity<EnumMap<ApiResponse, Object>> addEmail(EmailDto dto, Integer leadId, String status);

	ResponseEntity<EnumMap<ApiResponse, Object>> checkMailId(Integer addMailId, Integer leadId);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteEmail(Integer mailId);

	ResponseEntity<EnumMap<ApiResponse, Object>> assignEmail(Map<String, Integer> map);

}
