package ai.rnt.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.CityMaster;

public interface CityMasterRepository extends JpaRepository<CityMaster, Integer> {

	Optional<CityMaster> findByCity(String cityName);

}
