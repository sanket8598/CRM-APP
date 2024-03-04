package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LeadDashboardDtoTest {

	LeadDashboardDto dto = new LeadDashboardDto();
	LeadDashboardDto dto1 = new LeadDashboardDto();

	@Test
	void testLeadDashboardDto() {
		LeadDashboardDto leadDashboardDto = new LeadDashboardDto();
		Integer leadId = 1;
		String topic = "Potential project";
		String disqualifyAs = "Not interested";
		EmployeeDto employee = new EmployeeDto();
		String budgetAmount = "5000 USD";
		ServiceFallsDto serviceFallsMaster = new ServiceFallsDto();
		LeadSourceDto leadSourceMaster = new LeadSourceDto();
		DomainMasterDto domainMaster = new DomainMasterDto();
		ContactDto primaryContact = new ContactDto();
		String createdOn = "2024-03-05";
		leadDashboardDto.setLeadId(leadId);
		leadDashboardDto.setTopic(topic);
		leadDashboardDto.setDisqualifyAs(disqualifyAs);
		leadDashboardDto.setEmployee(employee);
		leadDashboardDto.setBudgetAmount(budgetAmount);
		leadDashboardDto.setServiceFallsMaster(serviceFallsMaster);
		leadDashboardDto.setLeadSourceMaster(leadSourceMaster);
		leadDashboardDto.setDomainMaster(domainMaster);
		leadDashboardDto.setPrimaryContact(primaryContact);
		leadDashboardDto.setCreatedOn(createdOn);
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertEquals(leadId, leadDashboardDto.getLeadId());
		assertEquals(topic, leadDashboardDto.getTopic());
		assertEquals(disqualifyAs, leadDashboardDto.getDisqualifyAs());
		assertEquals(employee, leadDashboardDto.getEmployee());
		assertEquals(budgetAmount, leadDashboardDto.getBudgetAmount());
		assertEquals(serviceFallsMaster, leadDashboardDto.getServiceFallsMaster());
		assertEquals(leadSourceMaster, leadDashboardDto.getLeadSourceMaster());
		assertEquals(domainMaster, leadDashboardDto.getDomainMaster());
		assertEquals(primaryContact, leadDashboardDto.getPrimaryContact());
		assertEquals(createdOn, leadDashboardDto.getCreatedOn());
	}
}
