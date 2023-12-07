package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.LeadTask;

public interface LeadTaskRepository extends JpaRepository<LeadTask, Integer> {

}
