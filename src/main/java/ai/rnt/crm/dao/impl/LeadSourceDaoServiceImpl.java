package ai.rnt.crm.dao.impl;

import static ai.rnt.crm.constants.CacheConstant.LEAD_SOURCE;
import static ai.rnt.crm.constants.CacheConstant.LEAD_SOURCE_ID;
import static ai.rnt.crm.dto_mapper.LeadSourceDtoMapper.TO_LEAD_SOURCE_DTO;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.LeadSourceDaoService;
import ai.rnt.crm.dto.LeadSourceDto;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.repository.LeadSourceMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeadSourceDaoServiceImpl implements LeadSourceDaoService {

	private final LeadSourceMasterRepository leadSourceMasterRepository;

	@Override
	@Cacheable(value = LEAD_SOURCE,key = LEAD_SOURCE_ID)
	public Optional<LeadSourceMaster> getLeadSourceById(Integer leadSourceId) {
		return leadSourceMasterRepository.findById(leadSourceId);
	}

	@Override
	@Cacheable(value = LEAD_SOURCE)
	public List<LeadSourceMaster> getAllLeadSource() {
		return leadSourceMasterRepository.findByDeletedDateIsNullOrderBySourceNameAsc();
	}

	@Override
	@Cacheable(value =LEAD_SOURCE)
	public Optional<LeadSourceMaster> getByName(String leadSource) {
		return leadSourceMasterRepository.findBySourceName(leadSource);
	}

	@Override
	@CacheEvict(value =LEAD_SOURCE,allEntries = true)
	public Optional<LeadSourceDto> save(LeadSourceMaster leadSource) throws Exception {
		return TO_LEAD_SOURCE_DTO.apply(leadSourceMasterRepository.save(leadSource));
	}

}
