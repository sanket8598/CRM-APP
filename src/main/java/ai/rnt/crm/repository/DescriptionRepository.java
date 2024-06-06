package ai.rnt.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Description;

public interface DescriptionRepository extends JpaRepository<Description, Integer> {

	List<Description> findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(Integer leadId, boolean b);

	List<Description> findByLeadLeadIdOrderByCreatedDateDesc(Integer leadId);

}
