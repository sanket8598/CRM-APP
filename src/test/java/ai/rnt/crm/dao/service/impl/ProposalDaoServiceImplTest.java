package ai.rnt.crm.dao.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import ai.rnt.crm.entity.Proposal;
import ai.rnt.crm.repository.ProposalRepository;

@ExtendWith(MockitoExtension.class)
class ProposalDaoServiceImplTest {

	@InjectMocks
	ProposalDaoServiceImpl proposalDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@Mock
	ProposalRepository proposalRepository;

	private Proposal proposal;
	private Integer optyId;
	private Integer propId;

	@BeforeEach
	void setUp() {
		proposal = new Proposal();
		optyId = 1;
		propId = 1;
	}

	@Test
    void testSaveProposal() {
        when(proposalRepository.save(proposal)).thenReturn(proposal);
        Proposal savedProposal = proposalDaoServiceImpl.saveProposal(proposal);
        assertEquals(proposal, savedProposal);
        verify(proposalRepository, times(1)).save(proposal);
    }

	@Test
	void testGetProposalsByOptyId() {
		List<Proposal> proposals = Arrays.asList();
		when(proposalRepository.findProposalByOpportunityOpportunityId(optyId)).thenReturn(proposals);
		List<Proposal> foundProposals = proposalDaoServiceImpl.getProposalsByOptyId(optyId);
		assertEquals(proposals, foundProposals);
		verify(proposalRepository, times(1)).findProposalByOpportunityOpportunityId(optyId);
	}

	@Test
	void testFindProposalById() {
		Optional<Proposal> optionalProposal = Optional.of(proposal);
		when(proposalRepository.findById(propId)).thenReturn(optionalProposal);
		Optional<Proposal> foundProposal = proposalDaoServiceImpl.findProposalById(propId);
		assertEquals(optionalProposal, foundProposal);
		verify(proposalRepository, times(1)).findById(propId);
	}
}
