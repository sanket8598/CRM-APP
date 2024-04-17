package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Proposal;

public interface ProposalRepository extends JpaRepository<Proposal, Integer> {

}
