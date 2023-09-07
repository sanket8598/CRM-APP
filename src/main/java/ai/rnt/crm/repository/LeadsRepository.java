package ai.rnt.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Leads;

public interface LeadsRepository extends JpaRepository<Leads, Integer>{


	List<Leads> findByStatus(String leadsStatus);

}
