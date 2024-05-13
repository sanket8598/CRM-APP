package ai.rnt.crm.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.enums.ApiResponse;

public interface DashboardService {

	ResponseEntity<EnumMap<ApiResponse, Object>> getDashboardData();

}
