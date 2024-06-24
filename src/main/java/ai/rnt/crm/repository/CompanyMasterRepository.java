package ai.rnt.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.CompanyMaster;

public interface CompanyMasterRepository extends JpaRepository<CompanyMaster, Integer> {

	CompanyMaster findTopByCompanyName(String companyName);

	List<CompanyMaster> findByCountryCountryId(Integer countryId);

	List<CompanyMaster> findByStateStateId(Integer stateId);

	List<CompanyMaster> findByCityCityId(Integer cityId);

	boolean existsByCompanyNameAndCountryCountryIdAndStateStateId(String company, Integer countryId, Integer stateId);
}
