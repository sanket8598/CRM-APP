package ai.rnt.crm.entity;

import org.junit.jupiter.api.Test;

class LeadSortFilterTest {

	LeadSortFilter filter = new LeadSortFilter();

	@Test
	void getterTest() {
		filter.getLeadSortFilterId();
		filter.getPrimaryFilter();
		filter.getSecondaryFilter();
		filter.getEmployee();
	}

	EmployeeMaster employee = new EmployeeMaster();

	@Test
	void setterTest() {
		filter.setLeadSortFilterId(1);
		filter.setPrimaryFilter("yes");
		filter.setSecondaryFilter("No");
		filter.setEmployee(employee);
	}
}
