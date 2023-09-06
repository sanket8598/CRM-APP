package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Leads;

public interface LeadsRepository extends JpaRepository<Leads, Integer>{

	Leads findLeadsByStatus(String leadsStatus);

}
