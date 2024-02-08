package ai.rnt.crm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ai.rnt.crm.entity.Opportunity;

public interface OpportunityRepository extends CrudRepository<Opportunity, Integer> {

	List<Opportunity> findByOrderByCreatedDateDesc();

	List<Opportunity> findByStatusOrderByCreatedDateDesc(String status);

	List<Opportunity> findByStatusInOrderByCreatedDateDesc(List<String> status);

}
