package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.Role;
import ai.rnt.crm.entity.RoleMaster;

class RoleDtoMapperTest {

	@Test
	void testToRole() {
		RoleMaster roleMaster = new RoleMaster();
		Optional<Role> roleOptional = RoleDtoMapper.TO_Role.apply(roleMaster);
		assertNotNull(roleOptional);
	}

	@Test
	void testToRoles() {
		Collection<RoleMaster> roleMasterCollection = new ArrayList<>();
		List<Role> roleList = RoleDtoMapper.TO_Roles.apply(roleMasterCollection);
		assertNotNull(roleList);
	}
}
