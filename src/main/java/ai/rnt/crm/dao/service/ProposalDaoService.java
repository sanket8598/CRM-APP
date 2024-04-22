package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.entity.Proposal;

public interface ProposalDaoService extends CrudService<Proposal, ProposalDto> {

	Proposal saveProposal(Proposal proposal);

	List<Proposal> getProposalsByOptyId(Integer optyId);

	Optional<Proposal> findProposalById(Integer propId);

}
