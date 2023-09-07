package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICEFALLMASTER_DTOS;

import java.util.EnumMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.ServiceFallsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceFallsServiceImpl implements ServiceFallsService {

	private final ServiceFallsDaoSevice serviceFallsDaoSevice;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllSerciveFalls() {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		try {
			resultMap.put(ApiResponse.SUCCESS, true);
			resultMap.put(ApiResponse.DATA,
					TO_SERVICEFALLMASTER_DTOS.apply(serviceFallsDaoSevice.getAllSerciveFalls()));
			return new ResponseEntity<>(resultMap, HttpStatus.FOUND);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}
}
