package ai.rnt.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.CityMaster;

public interface CityMasterRepository extends JpaRepository<CityMaster, Integer> {

	Optional<CityMaster> findTopByCity(String cityName);

	List<CityMaster> findByStateStateId(Integer stateId);

	boolean existsByCityAndStateStateId(String city, Integer stateId);

	List<CityMaster> findByOrderByCityAsc();

}
