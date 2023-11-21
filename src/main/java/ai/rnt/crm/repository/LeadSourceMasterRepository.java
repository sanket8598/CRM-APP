package ai.rnt.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.LeadSourceMaster;

public interface LeadSourceMasterRepository extends JpaRepository<LeadSourceMaster, Integer>{

	Optional<LeadSourceMaster> findBySourceName(String leadSource);

}
