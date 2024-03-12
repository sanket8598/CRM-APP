package ai.rnt.crm.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dao.service.RoleMasterDaoService;
import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.ResourceNotFoundException;

class EmployeeServiceImplTest {

	@Mock
	private RoleMasterDaoService roleMasterDaoService;

	@Mock
	private EmployeeDaoService employeeDaoService;

	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@Mock
	EmployeeDto employeeDto;

	@Mock
	EmployeeMaster employeeMaster;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(employeeServiceImpl).build();
	}

	@Test
	void testGetEmployeeByUserId_Found() throws Exception {
		employeeMaster.setStaffId(1375);
		employeeMaster.setFirstName("Sanket");
		employeeMaster.setLastName("Wakankar");
		Optional<EmployeeMaster> optionalEmployee = Optional.of(employeeMaster);
		when(employeeDaoService.getEmployeebyUserId("user123")).thenReturn(optionalEmployee);
		Optional<EmployeeDto> employeeDto = employeeServiceImpl.getEmployeeByUserId("user123");
		assertEquals(employeeDto.get().getFirstName(), employeeMaster.getFirstName());
	}

	@Test
	     void testGetEmployeeByUserId_NotFound() throws Exception {
	        when(employeeDaoService.getEmployeebyUserId("user456")).thenReturn(Optional.empty());
	        try {
	        	employeeServiceImpl.getEmployeeByUserId("user456");
	            fail("Expected ResourceNotFoundException");
	        } catch (ResourceNotFoundException e) {
	            assertNotNull(e);
	        }
	    }

	@Test
	void testGetById_Found() throws Exception {
		employeeDto.setStaffId(1);
		employeeDto.setFirstName("Sanket");
		employeeDto.setLastName("Wakankar");

		when(employeeDaoService.getById(1)).thenReturn(Optional.of(employeeDto));
		Optional<EmployeeMaster> employeeMaster = employeeServiceImpl.getById(1);
		assertEquals(employeeDto.getStaffId(), employeeMaster.get().getStaffId());
	}

	@Test
    void testGetById_NotFound() throws Exception {
       when(employeeDaoService.getById(1)).thenReturn(Optional.empty());
       try {
       	employeeServiceImpl.getById(1);
       } catch (Exception e) {
           assertNotNull(e);
       }
   }

	@Test
	public void testFindByName_Found() throws Exception {
		employeeMaster.setStaffId(1375);
		employeeMaster.setFirstName("Sanket");
		employeeMaster.setLastName("Wakankar");
		when(employeeDaoService.findByName("Sanket", "Wakankar")).thenReturn(Optional.of(employeeMaster));
		Optional<EmployeeMaster> employeeMaster1 = employeeServiceImpl.findByName("Sanket", "Wakankar");
		assertEquals(employeeMaster1.get().getFirstName(), employeeMaster.getFirstName());
	}

	@Test
	public void testFindByName_NotFound() throws Exception {
	    when(employeeDaoService.findByName("Jane", "Doe")).thenReturn(Optional.empty());
	    Optional<EmployeeMaster> employeeMaster = employeeServiceImpl.findByName("Jane", "Doe");
	    assertFalse(employeeMaster.isPresent());
	}

	@Test
	public void testFindByEmailId_Found() throws Exception {
		int expectedId = 123;
		when(employeeDaoService.findTopStaffIdByEmailId("john.doe@example.com")).thenReturn(expectedId);
		int staffId = employeeServiceImpl.findByEmailId("john.doe@example.com");

		assertEquals(staffId, expectedId);
	}

	@Test
	public void testFindByEmailId_NotFound() throws Exception {
	    when(employeeDaoService.findTopStaffIdByEmailId("jane.doe@example.com"))
	            .thenReturn(null); 
	    Integer staffId = employeeServiceImpl.findByEmailId("jane.doe@example.com");
	    assertEquals(staffId, null); 
	}

	@Test
	public void testGetAdminAndUser_WithEmail_Success() throws Exception {
		List<String> activeEmails = Arrays.asList("john.doe@example.com", "jane.doe@example.com");
		when(employeeDaoService.activeEmployeeEmailIds()).thenReturn(activeEmails);
		String email = "test@example.com";
		ResponseEntity<EnumMap<ApiResponse, Object>> response = employeeServiceImpl.getAdminAndUser(email);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testGetAdminAndUser_NoEmail_Success() throws Exception {
		List<EmployeeMaster> adminAndUsers = new ArrayList<>();
		employeeMaster.setStaffId(1375);
		employeeMaster.setFirstName("Sanket");
		employeeMaster.setLastName("Wakankar");
		adminAndUsers.add(employeeMaster);
		when(roleMasterDaoService.getAdminAndUser()).thenReturn(adminAndUsers);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = employeeServiceImpl.getAdminAndUser(null);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testGetAdminAndUser_Exception() throws Exception {
		try {
			when(employeeDaoService.activeEmployeeEmailIds()).thenThrow(new RuntimeException("Database error"));
			employeeServiceImpl.getAdminAndUser("test@example.com");
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testGetCRMUser_Success() throws Exception {
		List<EmployeeMaster> users = new ArrayList<>();
		employeeMaster.setStaffId(1375);
		employeeMaster.setFirstName("Sanket");
		employeeMaster.setLastName("Wakankar");
		users.add(employeeMaster);
		when(roleMasterDaoService.getUsers()).thenReturn(users);
		ResponseEntity<EnumMap<ApiResponse, Object>> response = employeeServiceImpl.getCRMUser();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testGetCRMUser_Exception() throws Exception {
		try {
			when(roleMasterDaoService.getUsers()).thenThrow(new RuntimeException("Database error"));
			employeeServiceImpl.getCRMUser();
		} catch (Exception e) {
			assertNotNull(e);
		}
	}
}
