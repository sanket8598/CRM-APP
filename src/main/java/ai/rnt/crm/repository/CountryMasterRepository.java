package ai.rnt.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.CountryMaster;

public interface CountryMasterRepository extends JpaRepository<CountryMaster, Integer> {

	Optional<CountryMaster> findTopByCountry(String countryName);

	boolean existsByCountry(String country);

	List<CountryMaster> findByOrderByCountryAsc();

	boolean existsByCountryAndCountryIdNot(String country, Integer countryId);
}
