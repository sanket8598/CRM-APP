package ai.rnt.crm.service;

import java.io.Serializable;
import java.util.Optional;

import ai.rnt.crm.dto.EmployeeDto;

public interface EmployeeService extends Serializable{

	Optional<EmployeeDto> getEmployeeByUserId(String userId);
}
