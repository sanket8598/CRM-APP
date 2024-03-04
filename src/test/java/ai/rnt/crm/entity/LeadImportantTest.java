package ai.rnt.crm.entity;

import org.junit.jupiter.api.Test;

class LeadImportantTest {

	LeadImportant important = new LeadImportant();

	@Test
	void getterTest() {
		important.getId();
		important.getLead();
		important.getEmployee();

	}

	EmployeeMaster employeeMaster = new EmployeeMaster();
	Leads leads = new Leads();

	@Test
	void setterTest() {
		important.setId(1);
		important.setLead(leads);
		important.setEmployee(employeeMaster);

	}
}
