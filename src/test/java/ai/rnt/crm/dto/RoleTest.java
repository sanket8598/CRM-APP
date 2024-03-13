package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class RoleTest {

	@Test
	void testSetAndGetRoleData() {
		Role role = new Role();
		Role role1 = new Role();
		role.setRoleId(1);
		role1.setRoleId(1);
		role.setRoleName("Admin");
		role1.setRoleName("Admin");
		Integer roleId = role.getRoleId();
		String roleName = role.getRoleName();
		role.canEqual(role);
		role.equals(role1);
		role.hashCode();
		role.toString();
		assertNotNull(roleId);
		assertEquals(1, roleId);
		assertEquals("Admin", roleName);
	}
}
