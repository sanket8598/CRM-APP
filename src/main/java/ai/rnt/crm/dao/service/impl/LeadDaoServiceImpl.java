package ai.rnt.crm.dao.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.repository.LeadsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadDaoServiceImpl implements LeadDaoService {
	private final LeadsRepository leadsRepository;

	@Override
	public Leads addLead(Leads leads) {
		return leadsRepository.save(leads);
	}

	@Override
	public Leads getLeadsByStatus(String leadsStatus) {
		return leadsRepository.findLeadsByStatus(leadsStatus);
	}

	@Override
	public List<LeadDto> getAll() {
		return leadsRepository.findAll();
	}
}
