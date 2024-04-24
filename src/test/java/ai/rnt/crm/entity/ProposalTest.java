package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ProposalTest {

	@Test
	void testGettersAndSetters() {
		Proposal proposal = new Proposal();
		proposal.setPropId(1);
		proposal.setGenPropId("GEN123");
		proposal.setEffectiveFrom(LocalDate.now());
		proposal.setEffectiveTo(LocalDate.now());
		List<ProposalServices> proposalServicesList = new ArrayList<>();
		ProposalServices proposalServices = new ProposalServices();
		proposalServicesList.add(proposalServices);
		proposal.setProposalServices(proposalServicesList);
		Opportunity opportunity = new Opportunity();
		proposal.setOpportunity(opportunity);
		assertEquals(1, proposal.getPropId());
		assertEquals("GEN123", proposal.getGenPropId());
		proposal.getEffectiveFrom();
		proposal.getEffectiveTo();
		assertEquals(opportunity, proposal.getOpportunity());
		assertEquals(proposalServicesList, proposal.getProposalServices());
	}

	@Test
	void testNoArgsConstructor() {
		Proposal proposal = new Proposal();
		assertNotNull(proposal);
	}
}
