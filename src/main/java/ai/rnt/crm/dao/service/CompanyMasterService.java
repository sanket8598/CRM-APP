package ai.rnt.crm.dao.service;

import java.util.Optional;

import ai.rnt.crm.entity.CompanyMaster;

public interface CompanyMasterService {

	Optional<CompanyMaster> getById(Integer companyId);

}
