package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class RoleTest {

	Role role = new Role();
	Role role1 = new Role();

	@Test
	void testSetAndGetRoleData() {
		Role role = new Role();
		role.setRoleId(1);
		role.setRoleName("Administrator");
		Integer roleId = role.getRoleId();
		String roleName = role.getRoleName();
		role.canEqual(role);
		role.equals(role1);
		role.hashCode();
		role.toString();
		assertNotNull(roleId);
		assertEquals(1, roleId);
		assertEquals("Administrator", roleName);
	}
}
