package ai.rnt.crm.dao.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.repository.LeadSourceMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeadSourceDaoServiceImpl implements LeadSourceDaoService {

	private final LeadSourceMasterRepository leadSourceMasterRepository;

	@Override
	@Cacheable(value = "leadSource",key = "#leadSourceId")
	public Optional<LeadSourceMaster> getLeadSourceById(Integer leadSourceId) {
		return leadSourceMasterRepository.findById(leadSourceId);
	}

	@Override
	@Cacheable(value = "leadSources")
	public List<LeadSourceMaster> getAllLeadSource() {
		return leadSourceMasterRepository.findByDeletedDateIsNullOrderBySourceNameAsc();
	}

	@Override
	@Cacheable(value = "leadSource")
	public Optional<LeadSourceMaster> getByName(String leadSource) {
		return leadSourceMasterRepository.findBySourceName(leadSource);
	}

}
