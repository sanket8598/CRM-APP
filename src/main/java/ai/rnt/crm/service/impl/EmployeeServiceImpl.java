package ai.rnt.crm.service.impl;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.service.EmployeeService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDaoService employeeDaoService;
	
	@Override
	public Optional<EmployeeDto> getEmployeeByUserId(String userId) {
		return evalMapper(employeeDaoService.getEmployeebyUserId(userId), EmployeeDto.class);
	}

	
}
