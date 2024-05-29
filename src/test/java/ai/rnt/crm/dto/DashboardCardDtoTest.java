package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DashboardCardDtoTest {

	@Test
	void testDashboardCardDto() {
		Integer leadId = 1;
		String shortName = "Short Name";
		String status = "Active";
		DashboardCardDto dashboardCardDto = new DashboardCardDto();
		LeadDashboardDto leadDashboardDto = new LeadDashboardDto();
		dashboardCardDto.setLeadId(leadId);
		dashboardCardDto.setShortName(shortName);
		dashboardCardDto.setStatus(status);
		dashboardCardDto.setLeads(leadDashboardDto);
		assertEquals(leadId, dashboardCardDto.getLeadId());
		assertEquals(shortName, dashboardCardDto.getShortName());
		assertEquals(status, dashboardCardDto.getStatus());
		assertEquals(leadDashboardDto, dashboardCardDto.getLeads());
	}
}
