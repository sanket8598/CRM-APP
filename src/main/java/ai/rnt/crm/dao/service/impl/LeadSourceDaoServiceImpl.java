package ai.rnt.crm.dao.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.repository.LeadSourceMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeadSourceDaoServiceImpl implements LeadSourceDaoService{
	
	private final LeadSourceMasterRepository leadSourceMasterRepository;
	
	@Override
	public Optional<LeadSourceMaster> getById(Integer leadSourceId) {
		return leadSourceMasterRepository.findById(leadSourceId);
	}

}
