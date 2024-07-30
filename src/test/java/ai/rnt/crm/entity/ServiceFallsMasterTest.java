package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ServiceFallsMasterTest {

	@Test
	void testGettersAndSetters() {
		ServiceFallsMaster serviceFallsMaster = new ServiceFallsMaster();
		Integer serviceFallsId = 1;
		String serviceName = "Test Service";
		List<Leads> leads = new ArrayList<>();
		List<ProposalServices> proposalServices = new ArrayList<>();
		serviceFallsMaster.setServiceFallsId(serviceFallsId);
		serviceFallsMaster.setServiceName(serviceName);
		serviceFallsMaster.setLeads(leads);
		serviceFallsMaster.setProposalServices(proposalServices);
		assertEquals(serviceFallsId, serviceFallsMaster.getServiceFallsId());
		assertEquals(serviceName, serviceFallsMaster.getServiceName());
		assertEquals(leads, serviceFallsMaster.getLeads());
		assertEquals(proposalServices, serviceFallsMaster.getProposalServices());
	}
}
