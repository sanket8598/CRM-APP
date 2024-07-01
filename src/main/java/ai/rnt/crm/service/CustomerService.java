package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.enums.ApiResponse;

public interface CustomerService {

	ResponseEntity<EnumMap<ApiResponse, Object>> customerDashBoardData();

	ResponseEntity<EnumMap<ApiResponse, Object>> editCustomer(String field, Integer customerId);
}
