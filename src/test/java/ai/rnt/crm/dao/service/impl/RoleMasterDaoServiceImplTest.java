package ai.rnt.crm.dao.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.repository.RoleMasterRepository;

/**
 * @author Nikhil Gaikwad
 * @since 06/03/2024.
 *
 */
class RoleMasterDaoServiceImplTest {

	@InjectMocks
	RoleMasterDaoServiceImpl roleMasterDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	EmployeeMaster employeeMaster;

	@Mock
	private RoleMasterRepository roleMasterRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(roleMasterDaoServiceImpl).build();
	}

	@Test
	void testGetAdminAndUser() {
		List<String> appRoles = Arrays.asList("CRM Admin", "CRM User");
		List<EmployeeMaster> employeeList = new ArrayList<>();
		EmployeeMaster employee1 = new EmployeeMaster();
		employeeList.add(employee1);
		when(roleMasterRepository.findByEmployeeRoleIn(appRoles)).thenReturn(employeeList);
		roleMasterDaoServiceImpl.getAdminAndUser();
		verify(roleMasterRepository).findByEmployeeRoleIn(appRoles);
	}

	@Test
	void testGetUsers() {
		List<String> crmUserList = Arrays.asList("CRM User");
		List<EmployeeMaster> employeeList = new ArrayList<>();
		EmployeeMaster employee1 = new EmployeeMaster();
		employeeList.add(employee1);
		when(roleMasterRepository.findByEmployeeRoleIn(crmUserList)).thenReturn(employeeList);
		roleMasterDaoServiceImpl.getUsers();
		verify(roleMasterRepository).findByEmployeeRoleIn(crmUserList);
	}
}
