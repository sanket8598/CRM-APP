package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_EMPLOYEE;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_EmployeeMaster;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_Employees;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.springframework.http.HttpStatus.FOUND;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dao.service.RoleMasterDaoService;
import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @// @formatter:off

/**
 * This class provides the the implentation of <code>EmployeeService</code> and
 * also BussinessLogic.
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 19-08-2023
 * 
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeDaoService employeeDaoService;
	
	private final RoleMasterDaoService roleMasterDaoService;

	@Override
	public Optional<EmployeeDto> getEmployeeByUserId(String userId) {
		log.info("inside the getEmployeeByUserId method...{}",userId);
		return TO_EMPLOYEE.apply(employeeDaoService.getEmployeebyUserId(userId).orElseThrow(()->new ResourceNotFoundException("Employee", "userId", userId)));
	}

	@Override
	public Optional<EmployeeMaster> getById(Integer assignTo) {
		log.info("inside the getById method...{}",assignTo);
		try {
			return TO_EmployeeMaster.apply(employeeDaoService.getById(assignTo).get());
		} catch (Exception e) {
			log.info("Got Exception while getting the user..{}" ,e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAdminAndUser(String email) {
		log.info("inside the getAdminAndUser method...{}",email);
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		try{
			resultMap.put(SUCCESS, true);
			if(Objects.nonNull(email)) {
				Map<String,Object> emailMap=new HashMap<>();
				emailMap.put("Emails", employeeDaoService.activeEmployeeEmailIds());
			  resultMap.put(DATA, emailMap);
			}else
			 resultMap.put(DATA, TO_Employees.apply(roleMasterDaoService.getAdminAndUser()));
			return new ResponseEntity<>(resultMap, FOUND);
		}catch (Exception e) {
			log.info("Got Exception while getting admin and user..{}" ,e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public Optional<EmployeeMaster> findByName(String firstName, String lastName) {
		return employeeDaoService.findByName(firstName,lastName);
	}
	
	@Override
	public Integer findByEmailId(String email) {
		return employeeDaoService.findTopStaffIdByEmailId(email);
	}
}
//@formatter:on