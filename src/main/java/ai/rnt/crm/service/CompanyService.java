package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.enums.ApiResponse;

public interface CompanyService {

	ResponseEntity<EnumMap<ApiResponse, Object>> getAllCompany();

}
