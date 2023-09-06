package ai.rnt.crm.service;

import java.util.Optional;

import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.entity.EmployeeMaster;

public interface EmployeeService{

	Optional<EmployeeDto> getEmployeeByUserId(String userId);

	Optional<EmployeeMaster> getById(Integer assignTo);
}
