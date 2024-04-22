package ai.rnt.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Proposal;

public interface ProposalRepository extends JpaRepository<Proposal, Integer> {

	List<Proposal> findProposalByOpportunityOpportunityId(Integer optyId);

}
