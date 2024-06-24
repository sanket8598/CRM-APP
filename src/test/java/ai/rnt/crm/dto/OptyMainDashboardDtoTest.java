package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.opportunity.OpportunityDto;

class OptyMainDashboardDtoTest {

	@Test
	void testGettersAndSetters() {
		OptyMainDashboardDto dto = new OptyMainDashboardDto();

		Integer opportunityId = 1;
		String shortName = "Short Name";
		String status = "Open";
		OpportunityDto opportunityDto = new OpportunityDto();
		dto.setOpportunityId(opportunityId);
		dto.setShortName(shortName);
		dto.setStatus(status);
		dto.setOpty(opportunityDto);
		assertEquals(opportunityId, dto.getOpportunityId());
		assertEquals(shortName, dto.getShortName());
		assertEquals(status, dto.getStatus());
		assertEquals(opportunityDto, dto.getOpty());
	}

}
