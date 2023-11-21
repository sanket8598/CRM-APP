package ai.rnt.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.ServiceFallsMaster;

public interface ServiceFallRepository extends JpaRepository<ServiceFallsMaster, Integer>{

	Optional<ServiceFallsMaster> findByServiceName(String serviceFalls);

}
