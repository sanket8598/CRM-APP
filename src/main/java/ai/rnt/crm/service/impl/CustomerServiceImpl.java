package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	private final ContactDaoService contactDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> customerDashBoardData() {
		log.info("inside the customerDashBoardData method...");
		EnumMap<ApiResponse, Object> data = new EnumMap<>(ApiResponse.class);
		try {
			data.put(DATA, TO_CONTACT_DTOS.apply(contactDaoService.findAllPrimaryContacts()));
			data.put(SUCCESS, true);
			return new ResponseEntity<>(data, CREATED);
		} catch (Exception e) {
			data.put(SUCCESS, false);
			log.error("error occured while getting customer list..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
