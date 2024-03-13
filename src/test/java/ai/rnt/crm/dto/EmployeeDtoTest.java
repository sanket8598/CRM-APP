package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class EmployeeDtoTest {

	@Test
	void testEmployeeDto() {
		EmployeeDto employeeDto = new EmployeeDto();
		EmployeeDto dto = new EmployeeDto();
		EmployeeDto dto1 = new EmployeeDto();
		Integer staffId = 1;
		String userID = "user123";
		String password = "password123";
		String firstName = "John";
		String middleName = "Doe";
		String lastName = "Smith";
		String emailID = "john.doe@example.com";
		String employeeJobTitle = "Software Engineer";
		List<Role> employeeRole = new ArrayList<>();

		// Act
		employeeDto.setStaffId(staffId);
		employeeDto.setUserID(userID);
		employeeDto.setPassword(password);
		employeeDto.setFirstName(firstName);
		employeeDto.setMiddleName(middleName);
		employeeDto.setLastName(lastName);
		employeeDto.setEmailID(emailID);
		employeeDto.setEmployeeJobTitle(employeeJobTitle);
		employeeDto.setEmployeeRole(employeeRole);
		dto1.setStaffId(staffId);
		dto1.setUserID(userID);
		dto1.setPassword(password);
		dto1.setFirstName(firstName);
		dto1.setMiddleName(middleName);
		dto1.setLastName(lastName);
		dto1.setEmailID(emailID);
		dto1.setEmployeeJobTitle(employeeJobTitle);
		dto1.setEmployeeRole(employeeRole);
		employeeDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertEquals(staffId, employeeDto.getStaffId());
		assertEquals(userID, employeeDto.getUserID());
		assertEquals(password, employeeDto.getPassword());
		assertEquals(firstName, employeeDto.getFirstName());
		assertEquals(middleName, employeeDto.getMiddleName());
		assertEquals(lastName, employeeDto.getLastName());
		assertEquals(emailID, employeeDto.getEmailID());
		assertEquals(employeeJobTitle, employeeDto.getEmployeeJobTitle());
		assertEquals(employeeRole, employeeDto.getEmployeeRole());
	}
}
