package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.entity.EmployeeMaster;

public interface EmployeeDaoService extends CrudService<EmployeeMaster, EmployeeDto> {

	Optional<EmployeeMaster> getEmployeebyUserId(String userId);

	Optional<EmployeeDto> getById(Integer assignTo);

	Optional<EmployeeMaster> findByName(String firstName, String lastName);
	
	List<String> activeEmployeeEmailIds();

}
