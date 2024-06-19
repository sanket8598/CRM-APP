package ai.rnt.crm.service.impl;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> customerDashBoardData() {
		// TODO Auto-generated method stub
		return null;
	}

}
