package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LeadSortFilterDtoTest {

	LeadSortFilterDto dto = new LeadSortFilterDto();
	LeadSortFilterDto dto1 = new LeadSortFilterDto();

	@Test
	void testLeadSortFilterDto() {
		LeadSortFilterDto leadSortFilterDto = new LeadSortFilterDto();
		Integer leadSortFilterId = 1;
		String primaryFilter = "Topic";
		String secondaryFilter = "Company";
		EmployeeDto employee = new EmployeeDto();
		leadSortFilterDto.setLeadSortFilterId(leadSortFilterId);
		leadSortFilterDto.setPrimaryFilter(primaryFilter);
		leadSortFilterDto.setSecondaryFilter(secondaryFilter);
		leadSortFilterDto.setEmployee(employee);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(leadSortFilterId, leadSortFilterDto.getLeadSortFilterId());
		assertEquals(primaryFilter, leadSortFilterDto.getPrimaryFilter());
		assertEquals(secondaryFilter, leadSortFilterDto.getSecondaryFilter());
		assertEquals(employee, leadSortFilterDto.getEmployee());
	}
}
