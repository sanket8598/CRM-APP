package ai.rnt.crm.dao.service;

import java.util.Optional;

import ai.rnt.crm.entity.LeadSourceMaster;

public interface LeadSourceDaoService {

	Optional<LeadSourceMaster> getById(Integer leadSourceId);


}
