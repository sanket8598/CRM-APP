package ai.rnt.crm.dao.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.ProposalDaoService;
import ai.rnt.crm.entity.Proposal;
import ai.rnt.crm.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 16-04-2024.
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProposalDaoServiceImpl implements ProposalDaoService {

	private final ProposalRepository proposalRepository;

	@Override
	public Proposal saveProposal(Proposal proposal) {
		return proposalRepository.save(proposal);
	}

	@Override
	public List<Proposal> getProposalsByOptyId(Integer optyId) {
		return proposalRepository.findProposalByOpportunityOpportunityId(optyId);
	}

	@Override
	public Optional<Proposal> findProposalById(Integer propId) {
		return proposalRepository.findById(propId);
	}
}
