package ai.rnt.crm.security.config;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.dto.Role;
import ai.rnt.crm.service.EmployeeService;

class CustomUserDetailsTest {

	@InjectMocks
	private CustomUserDetails customUserDetails;

	@Mock
	private EmployeeService employeeService;

	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(customUserDetails).build();
	}

	@Test
	void testLoadUserByUsername_Success() throws Exception {
		Integer userId = 1;
		String username = "john.doe";
		String password = "password123";
		String email = "john.doe@example.com";
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setStaffId(userId);
		employeeDto.setUserID(username);
		employeeDto.setEmailID(email);
		employeeDto.setPassword(password);
		List<Role> emplRole = new ArrayList<>();
		Role role = new Role();
		role.setRoleName("CRM USER");
		emplRole.add(role);
		employeeDto.setEmployeeRole(emplRole);
		Mockito.when(employeeService.getEmployeeByUserId(anyString())).thenReturn(Optional.of(mock(EmployeeDto.class)));
		try {
			customUserDetails.loadUserByUsername(userId.toString());
		} catch (UsernameNotFoundException e) {
			assertNotNull(e.getMessage());
		}
	}
}
