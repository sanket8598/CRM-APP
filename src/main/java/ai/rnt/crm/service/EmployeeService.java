package ai.rnt.crm.service;

import java.util.EnumMap;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.enums.ApiResponse;

public interface EmployeeService {

	Optional<EmployeeDto> getEmployeeByUserId(String userId);

	Optional<EmployeeMaster> getById(Integer assignTo);

	ResponseEntity<EnumMap<ApiResponse, Object>> getAdminAndUser(String mail);

	Optional<EmployeeMaster> findByName(String firstName, String lastName);

	Integer findByEmailId(String email);

	ResponseEntity<EnumMap<ApiResponse, Object>> getCRMUser();
}
