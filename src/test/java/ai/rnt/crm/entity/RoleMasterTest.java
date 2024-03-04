package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleMasterTest {

	private RoleMaster roleMaster;

	@BeforeEach
	void setUp() {
		roleMaster = new RoleMaster();
	}

	@Test
	void testSetAndGetRoleId() {
		roleMaster.setRoleId(1);
		assertEquals(1, roleMaster.getRoleId());
	}

	@Test
	void testSetAndGetRoleName() {
		roleMaster.setRoleName("Admin");
		assertEquals("Admin", roleMaster.getRoleName());
	}

	@Test
	void testSetAndGetEmployees() {
		List<EmployeeMaster> employees = new ArrayList<>();
		EmployeeMaster employee1 = new EmployeeMaster();
		employee1.setStaffId(1);
		employee1.setFirstName("John");
		employee1.setLastName("Doe");

		EmployeeMaster employee2 = new EmployeeMaster();
		employee2.setStaffId(2);
		employee2.setFirstName("Jane");
		employee2.setLastName("Doe");

		employees.add(employee1);
		employees.add(employee2);

		roleMaster.setEmployees(employees);

		assertNotNull(roleMaster.getEmployees());
		assertEquals(2, roleMaster.getEmployees().size());
	}
}
