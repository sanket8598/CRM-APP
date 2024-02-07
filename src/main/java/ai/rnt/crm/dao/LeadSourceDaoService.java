package ai.rnt.crm.dao;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.LeadSourceDto;
import ai.rnt.crm.entity.LeadSourceMaster;

public interface LeadSourceDaoService extends CrudService<LeadSourceMaster, LeadSourceDto>{

	Optional<LeadSourceMaster> getLeadSourceById(Integer leadSourceId);

	List<LeadSourceMaster> getAllLeadSource();

	Optional<LeadSourceMaster> getByName(String leadSource);

}
