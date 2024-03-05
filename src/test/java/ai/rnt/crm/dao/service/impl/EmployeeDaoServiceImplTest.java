package ai.rnt.crm.dao.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.projection.EmailIdProjection;
import ai.rnt.crm.projection.StaffIdProjection;
import ai.rnt.crm.repository.EmployeeMasterRepository;

/**
 * @author Nikhil Gaikwad
 * @since 05/03/2024.
 *
 */
class EmployeeDaoServiceImplTest {

	@InjectMocks
	EmployeeDaoServiceImpl employeeDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	EmployeeMaster employeeMaster;

	@Mock
	private EmployeeMasterRepository employeeMasterRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(employeeDaoServiceImpl).build();
	}

	@Test
	void getEmployeebyUserIdTest() {
		String userId = "testUserId";
		EmployeeMaster employee = new EmployeeMaster();
		employee.setStaffId(1477);
		employee.setUserId(userId);
		when(employeeMasterRepository.findByUserId(userId)).thenReturn(Optional.of(employee));
		Optional<EmployeeMaster> retrievedEmployee = employeeDaoServiceImpl.getEmployeebyUserId(userId);
		assertTrue(retrievedEmployee.isPresent());
		assertEquals(employee, retrievedEmployee.get());
	}

	@Test
	void getByIdTest() {
		Integer assignTo = 1477;
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setStaffId(assignTo);
		when(employeeMasterRepository.findById(assignTo)).thenReturn(Optional.of(employeeMaster));
		Optional<EmployeeDto> retrievedEmployeeDto = employeeDaoServiceImpl.getById(assignTo);
		assertTrue(retrievedEmployeeDto.isPresent());
		assertEquals(assignTo, retrievedEmployeeDto.get().getStaffId());
	}

	@Test
	void findByNameTest() {
		String firstName = "John";
		String lastName = "Doe";
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setStaffId(1477);
		employeeMaster.setFirstName(firstName);
		employeeMaster.setLastName(lastName);
		when(employeeMasterRepository.findByFirstNameAndLastName(firstName, lastName))
				.thenReturn(Optional.of(employeeMaster));
		Optional<EmployeeMaster> retrievedEmployeeMaster = employeeDaoServiceImpl.findByName(firstName, lastName);
		assertTrue(retrievedEmployeeMaster.isPresent());
		assertEquals(employeeMaster, retrievedEmployeeMaster.get());
	}

	@Test
	void activeEmployeeEmailIdsTest() {
		List<EmailIdProjection> emailIds = new ArrayList<>();
		when(employeeMasterRepository.findEmailIdByDepartureDateIsNullOrDepartureDateBefore(LocalDate.now()))
				.thenReturn(emailIds);
		List<String> retrievedEmailIds = employeeDaoServiceImpl.activeEmployeeEmailIds();
		assertEquals(emailIds.size(), retrievedEmailIds.size());
	}

	@Test
	void getEmailIdTest() {
		Integer staffId = 1;
		String email = "test@example.com";
		EmailIdProjection emailIdProjection = mock(EmailIdProjection.class);
		when(emailIdProjection.getEmailId()).thenReturn(email);
		when(employeeMasterRepository.findEmailIdByStaffId(staffId)).thenReturn(emailIdProjection);
		String retrievedEmail = employeeDaoServiceImpl.getEmailId(staffId);
		assertEquals(email, retrievedEmail);
	}

	@Test
	void findTopStaffIdByEmailIdTest() {
		String email = "test@example.com";
		Integer staffId = 1;
		StaffIdProjection staffIdProjection = mock(StaffIdProjection.class);
		when(staffIdProjection.getStaffId()).thenReturn(staffId);
		when(employeeMasterRepository.findTopStaffIdByEmailId(email)).thenReturn(staffIdProjection);
		Integer retrievedStaffId = employeeDaoServiceImpl.findTopStaffIdByEmailId(email);
		assertEquals(staffId, retrievedStaffId);
	}
}
