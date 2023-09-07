package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.entity.LeadSourceMaster;

public interface LeadSourceDaoService {

	Optional<LeadSourceMaster> getById(Integer leadSourceId);

	List<LeadSourceMaster> getAllLeadSource();

}
