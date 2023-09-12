package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_EDITLEAD_DTO;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.EditLeadDto;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.exception.ResourceNotFoundException;
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
		return leadsRepository.findByStatusOrderByCreatedDateDesc(leadsStatus);
	}

	@Override
	public List<Leads> getAllLeads() {
		return leadsRepository.findAll();
	}

	@Override
	public List<Leads> getLeadDashboardData() {
		return leadsRepository.findByOrderByCreatedDateDesc();
	}

	@Override
	public Optional<EditLeadDto> getLeadById(Integer id) {
		return TO_EDITLEAD_DTO.apply(leadsRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Lead","leadId",id)));
	}
}
