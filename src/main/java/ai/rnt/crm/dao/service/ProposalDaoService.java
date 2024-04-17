package ai.rnt.crm.dao.service;

import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.entity.Proposal;

public interface ProposalDaoService extends CrudService<Proposal, ProposalDto> {

	Proposal saveProposal(Proposal proposal);

}
