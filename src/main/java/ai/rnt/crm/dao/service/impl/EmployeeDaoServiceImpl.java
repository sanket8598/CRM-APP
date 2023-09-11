package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_Employee;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.repository.EmployeeMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeDaoServiceImpl implements EmployeeDaoService{

	private final EmployeeMasterRepository employeeMasterRepository;
	
	@Override
	public Optional<EmployeeMaster> getEmployeebyUserId(String userId) {
		return employeeMasterRepository.findByUserId(userId);
	}

	@Override
	public Optional<EmployeeDto> getById(Integer assignTo) {
		return TO_Employee.apply(employeeMasterRepository.findById(assignTo).orElseThrow(()->new ResourceNotFoundException("Employee", "staffId", assignTo)));
	}


}
