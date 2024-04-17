package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class EmployeeMasterTest {

	EmployeeMaster employeeMaster = new EmployeeMaster();

	@Test
	void getterTest() {
		employeeMaster.getStaffId();
		employeeMaster.getPassword();
		employeeMaster.getUserId();
		employeeMaster.getFirstName();
		employeeMaster.getMiddleName();
		employeeMaster.getLastName();
		employeeMaster.getEmailId();
		employeeMaster.getManagerId();
		employeeMaster.getEmployeeJobTitle();
		employeeMaster.getDepartureDate();
		employeeMaster.getEmployeeRole();
		employeeMaster.getLeads();
		employeeMaster.getOpportunity();
		employeeMaster.getImpLead();
		employeeMaster.getAssignLeads();
		assertNull(employeeMaster.getFirstName());
	}

	List<RoleMaster> employeeRole = new ArrayList<>();
	List<Leads> leads = new ArrayList<>();
	List<Opportunity> opportunity = new ArrayList<>();
	Set<LeadImportant> impLead;

	@Test
	void setterTest() {
		employeeMaster.setStaffId(1477);
		employeeMaster.setPassword("ertyujhfg");
		employeeMaster.setUserId("dfghjuyt");
		employeeMaster.setFirstName("cvbn");
		employeeMaster.setMiddleName("rtyjm");
		employeeMaster.setLastName("rtyj");
		employeeMaster.setEmailId("erty@rnt.ai");
		employeeMaster.setManagerId(1);
		employeeMaster.setEmployeeJobTitle("Developer");
		LocalDate localDate = LocalDate.of(2024, 1, 1);
		employeeMaster.setDepartureDate(localDate);
		employeeMaster.setEmployeeRole(employeeRole);
		employeeMaster.setLeads(leads);
		employeeMaster.setAssignLeads(leads);
		employeeMaster.setOpportunity(opportunity);
		employeeMaster.setImpLead(impLead);
		assertEquals(1477, employeeMaster.getStaffId());
	}

	@Test
	void testEmployeeMasterConstructor() {
		Integer staffId = 1477;
		String firstName = "John";
		String lastName = "Doe";
		LocalDate departureDate = LocalDate.of(2023, 12, 31);
		String emailId = "john.doe@example.com";
		EmployeeMaster employee = new EmployeeMaster(staffId, firstName, lastName, departureDate, emailId);
		assertEquals(staffId, employee.getStaffId());
		assertEquals(firstName, employee.getFirstName());
		assertEquals(lastName, employee.getLastName());
		assertEquals(departureDate, employee.getDepartureDate());
		assertEquals(emailId, employee.getEmailId());
	}
}
