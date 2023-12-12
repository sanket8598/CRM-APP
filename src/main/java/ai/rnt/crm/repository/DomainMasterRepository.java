package ai.rnt.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.DomainMaster;

public interface DomainMasterRepository extends JpaRepository<DomainMaster, Integer> {

	Optional<DomainMaster> findByDomainName(String serviceFalls);

}
