package ai.rnt.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.StateMaster;

public interface StateMasterRepository extends JpaRepository<StateMaster, Integer> {

	Optional<StateMaster> findByState(String state);

}
