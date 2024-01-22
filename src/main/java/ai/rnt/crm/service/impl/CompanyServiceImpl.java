package ai.rnt.crm.service.impl;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CompanyService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllCompany() {
		return null;
	}

}
