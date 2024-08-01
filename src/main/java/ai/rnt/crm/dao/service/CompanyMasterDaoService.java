package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.entity.CompanyMaster;

public interface CompanyMasterDaoService extends CrudService<CompanyMaster, CompanyDto> {

	//@Override
	//Optional<CompanyDto> getById(Integer companyId);

	//Optional<CompanyDto> save(CompanyMaster companyMaster);

	Optional<CompanyDto> findByCompanyName(String companyName);

	List<CompanyDto> findAllCompanies();

	Optional<CompanyMaster> findCompanyById(Integer companyId);

	List<CompanyMaster> findByCountryId(Integer countryId);

	List<CompanyMaster> findByStateId(Integer stateId);

	List<CompanyMaster> findByCityId(Integer cityId);

	boolean isCompanyPresent(String company, Integer countryId, Integer stateId);
}
