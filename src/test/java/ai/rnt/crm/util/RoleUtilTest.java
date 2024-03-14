package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class RoleUtilTest {

	@InjectMocks
	private RoleUtil roleUtil;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	private boolean isAdmin(String role) {
		return RoleUtil.CHECK_ADMIN.test(role);
	}

	private boolean isUser(String role) {
		return RoleUtil.CHECK_USER.test(role);
	}

	@Test
	void testIsAdminRole() {
		assertFalse(isAdmin(null));
		assertTrue(isAdmin("CRM Admin"));
		assertFalse(isAdmin("user"));
	}

	@Test
	void testIsUserRole() {
		assertFalse(isUser(null));
		assertTrue(isUser("CRM User"));
		assertFalse(isUser("user"));
	}

	@Test
	void testGetSingleRoleWithAdminRole() {
		List<String> roles = Arrays.asList("CRM Admin", "CRM User");
		assertEquals("CRM Admin", RoleUtil.getSingleRole(roles));
		List<String> noRoles = Arrays.asList();
		assertEquals("Don't Have Role", RoleUtil.getSingleRole(noRoles));
	}

	@Test
	void testAllowRolesWithValidRole() {
		assertTrue(RoleUtil.ALLOW_ROLES.test("CRM Admin"));
		assertFalse(RoleUtil.ALLOW_ROLES.test("manager"));
		assertFalse(RoleUtil.ALLOW_ROLES.test(null));
	}

	@Test
	void testAppRolesSupplier() {
		List<String> expectedRoles = Arrays.asList("CRM Admin", "CRM User");
		assertEquals(expectedRoles, RoleUtil.APP_ROLES.get());
	}

	@Test
	void testGetRoleWithAdminRole() {
		assertEquals("CRM Admin", RoleUtil.GET_ROLE.apply("CRM Admin"));
		assertEquals("CRM User", RoleUtil.GET_ROLE.apply("CRM User"));
		assertEquals("Don't Have Role", RoleUtil.GET_ROLE.apply("manager"));
		assertEquals("Don't Have Role", RoleUtil.GET_ROLE.apply(null));
	}
}
