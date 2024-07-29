package ai.rnt.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.CurrencyMaster;

public interface CurrencyRepository extends JpaRepository<CurrencyMaster, Integer>{

	Optional<CurrencyMaster> findTopByCurrencyName(String currencyName);

	Optional<CurrencyMaster> findTopByCurrencySymbol(String currencySymbol);

}
