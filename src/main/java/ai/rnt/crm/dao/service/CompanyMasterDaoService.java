package ai.rnt.crm.dao.service;

import java.util.Optional;

import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.entity.CompanyMaster;

public interface CompanyMasterDaoService extends CrudService<CompanyMaster, CompanyDto>{

	@Override
	Optional<CompanyDto> getById(Integer companyId);
	
	@Override
	Optional<CompanyDto> save(CompanyMaster companyMaster);

	Optional<CompanyDto> findByCompanyName(String companyName);

}
