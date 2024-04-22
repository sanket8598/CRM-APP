package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ProposalServicesTest {

	@Test
	void testConstructorAndGettersSetters() {
		ProposalServices proposalServices = new ProposalServices();
		proposalServices.setPropServiceId(1);
		proposalServices.setServicePrice("1000");
		Proposal proposal = new Proposal();
		proposal.setPropId(1);
		proposalServices.setProposal(proposal);
		ServiceFallsMaster serviceFallsMaster = new ServiceFallsMaster();
		serviceFallsMaster.setServiceFallsId(1);
		proposalServices.setServiceFallsMaster(serviceFallsMaster);
		assertEquals(1, proposalServices.getPropServiceId());
		assertEquals("1000", proposalServices.getServicePrice());
		assertEquals(proposal, proposalServices.getProposal());
		assertEquals(serviceFallsMaster, proposalServices.getServiceFallsMaster());
	}

	@Test
	void testNoArgsConstructor() {
		ProposalServices proposalServices = new ProposalServices();
		assertNotNull(proposalServices);
	}
}
