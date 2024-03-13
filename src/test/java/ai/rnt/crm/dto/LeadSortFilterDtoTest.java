package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class LeadSortFilterDtoTest {

	@Test
	void testLeadSortFilterDto() {
		LeadSortFilterDto leadSortFilterDto = new LeadSortFilterDto();
		LeadSortFilterDto dto = new LeadSortFilterDto();
		LeadSortFilterDto dto1 = new LeadSortFilterDto();
		Integer leadSortFilterId = 1;
		String primaryFilter = "Topic";
		String secondaryFilter = "Company";
		EmployeeDto employee = new EmployeeDto();
		LeadSortFilterDto leadSortFilterDto1 = new LeadSortFilterDto(leadSortFilterId, primaryFilter, secondaryFilter,
				employee);
		assertNotNull(leadSortFilterDto1);
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
