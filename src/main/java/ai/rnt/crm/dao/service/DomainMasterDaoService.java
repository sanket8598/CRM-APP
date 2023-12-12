package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.DomainMasterDto;
import ai.rnt.crm.entity.DomainMaster;

public interface DomainMasterDaoService extends CrudService<DomainMaster, DomainMasterDto>{

	Optional<DomainMaster> findByName(String domainName);

	Optional<DomainMaster> addDomain(DomainMaster domainMaster);

	List<DomainMaster> getAllDomains();

	Optional<DomainMaster> findById(Integer domainId);

}
