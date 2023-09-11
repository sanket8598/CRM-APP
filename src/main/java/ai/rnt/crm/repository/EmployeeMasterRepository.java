package ai.rnt.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.EmployeeMaster;


public interface EmployeeMasterRepository extends JpaRepository<EmployeeMaster, Integer> {
	
	Optional<EmployeeMaster> findByUserId(String userId);
}
