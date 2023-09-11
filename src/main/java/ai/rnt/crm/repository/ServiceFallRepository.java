package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.ServiceFallsMaster;

public interface ServiceFallRepository extends JpaRepository<ServiceFallsMaster, Integer>{

}
