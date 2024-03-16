package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.RoleMaster;

class EmployeeToDtoMapperTest {

	@Test
	void testToEmployee() {
		EmployeeMaster employeeMaster = new EmployeeMaster();
		List<RoleMaster> roles = new ArrayList<>();
		RoleMaster role = new RoleMaster();
		role.setRoleName("Role");
		roles.add(role);
		employeeMaster.setEmployeeRole(roles);
		Function<EmployeeMaster, Optional<EmployeeDto>> mapper = EmployeeToDtoMapper.TO_EMPLOYEE;
		Optional<EmployeeDto> result = mapper.apply(employeeMaster);
		assertTrue(result.isPresent());
		assertEquals("Role", result.get().getEmployeeRole().get(0).getRoleName());
	}
}
