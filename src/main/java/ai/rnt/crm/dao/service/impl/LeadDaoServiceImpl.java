package ai.rnt.crm.dao.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.LeadDaoService;
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
	public List<Leads> getLeadsByStatus(String leadsStatus) {
		return leadsRepository.findByStatus(leadsStatus);
	}

	@Override
	public List<Leads> getAllLeads() {
		return leadsRepository.findAll();
	}

	@Override
	public List<Leads> getLeadDashboardData() {
		return leadsRepository.findAll();
	}
}
