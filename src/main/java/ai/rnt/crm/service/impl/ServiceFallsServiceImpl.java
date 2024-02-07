package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER_DTOS;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.ServiceFallsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceFallsServiceImpl implements ServiceFallsService {

	private final ServiceFallsDaoSevice serviceFallsDaoSevice;

	/**
	 * @author Nikhil Gaikwad
	 * @Version 1.0
	 * @since 07-09-2023
	 *
	 */
	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllSerciveFalls() {
		log.info("inside the getAllSerciveFalls method...");
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		try {
			resultMap.put(ApiResponse.SUCCESS, true);
			resultMap.put(ApiResponse.DATA,
					TO_SERVICE_FALL_MASTER_DTOS.apply(serviceFallsDaoSevice.getAllSerciveFalls()));
			return new ResponseEntity<>(resultMap, OK);
		} catch (Exception e) {
			log.info("Got Exception while getting the service falls data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
