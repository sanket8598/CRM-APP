package ai.rnt.crm.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.StateMaster;

public interface StateMasterRepository extends JpaRepository<StateMaster, Integer> {

	Optional<StateMaster> findTopByState(String state);

	List<StateMaster> findByCountryCountryId(@Min(1) Integer countryId);

	boolean existsByStateAndCountryCountryId(String state, Integer countryId);

	List<StateMaster> findByOrderByStateAsc();

}
