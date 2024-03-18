package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.LeadSortFilterDto;
import ai.rnt.crm.entity.LeadSortFilter;

class LeadSortFilterDtoMapperTest {

	@Test
	void testToLeadSortFilterDto() {
		LeadSortFilter leadSortFilter = new LeadSortFilter();
		Optional<LeadSortFilterDto> leadSortFilterDtoOptional = LeadSortFilterDtoMapper.TO_LEAD_SORT_FILTER_DTO
				.apply(leadSortFilter);
		assertNotNull(leadSortFilterDtoOptional);
	}

	@Test
	void testToLeadSortFilter() {
		LeadSortFilterDto leadSortFilterDto = new LeadSortFilterDto();
		Optional<LeadSortFilter> leadSortFilterOptional = LeadSortFilterDtoMapper.TO_LEAD_SORT_FILTER
				.apply(leadSortFilterDto);
		assertNotNull(leadSortFilterOptional);
	}
}
