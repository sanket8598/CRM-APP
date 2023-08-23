package ai.rnt.crm.service;

import java.util.Optional;

import ai.rnt.crm.dto.EmployeeDto;

public interface EmployeeService{

	Optional<EmployeeDto> getEmployeeByUserId(String userId);
}
