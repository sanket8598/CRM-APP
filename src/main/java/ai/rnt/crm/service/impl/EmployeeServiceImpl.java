package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_Employee;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_EmployeeMaster;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import lombok.RequiredArgsConstructor;

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
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeDaoService employeeDaoService;

	@Override
	public Optional<EmployeeDto> getEmployeeByUserId(String userId) {
		return TO_Employee.apply(employeeDaoService.getEmployeebyUserId(userId).orElseThrow(()->new ResourceNotFoundException("Employee", "userId", userId)));
	}

	@Override
	public Optional<EmployeeMaster> getById(Integer assignTo) {
		try {
			return TO_EmployeeMaster.apply(employeeDaoService.getById(assignTo).get());
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}

}
//@formatter:on