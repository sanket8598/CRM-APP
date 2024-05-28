package ai.rnt.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Visit;

public interface VisitRepository extends JpaRepository<Visit, Integer>{

	List<Visit> findByLeadLeadIdOrderByCreatedDateDesc(Integer leadId);

	List<Visit> findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(Integer leadId, boolean flag);

	List<Visit> findByIsOpportunityOrderByCreatedDateDesc(boolean isOpportunity);

}
