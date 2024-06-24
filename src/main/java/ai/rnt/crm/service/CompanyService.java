package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.enums.ApiResponse;

public interface CompanyService {

	ResponseEntity<EnumMap<ApiResponse, Object>> companyList();

	ResponseEntity<EnumMap<ApiResponse, Object>> getCompany(Integer companyId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateCompany(CompanyDto dto, Integer companyId);

	ResponseEntity<EnumMap<ApiResponse, Object>> addCompany(CompanyDto dto);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteCompany(Integer companyId);
}
