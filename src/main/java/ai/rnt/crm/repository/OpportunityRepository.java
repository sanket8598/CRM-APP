package ai.rnt.crm.repository;

import org.springframework.data.repository.CrudRepository;

import ai.rnt.crm.entity.Leads;

public interface OpportunityRepository extends CrudRepository<Leads, Integer>{

}
