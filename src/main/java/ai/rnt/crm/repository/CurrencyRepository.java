package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.CurrencyMaster;

public interface CurrencyRepository extends JpaRepository<CurrencyMaster, Integer>{

}
