package ai.rnt.crm.dao.service.impl;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.entity.LeadImportant;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.repository.ImpLeadRepository;
import ai.rnt.crm.repository.LeadsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadDaoServiceImpl implements LeadDaoService {
	private final LeadsRepository leadsRepository;
	private final ImpLeadRepository impLeadRepositroy;

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
		return leadsRepository.findByOrderByCreatedDateDesc();
	}

	@Override
	public List<Leads> getLeadDashboardData() {
		return leadsRepository.findByOrderByCreatedDateDesc();
	}

	@Override
	public Optional<Leads> getLeadById(Integer id) {
		return leadsRepository.findById(id);
	}

	@Override
	public Optional<LeadImportant> addImportantLead(LeadImportant leadImportant) {
		return Optional.of(impLeadRepositroy.save(leadImportant));
	}

	@Override
	public boolean deleteImportantLead(Integer leadId,Integer staffId) {
		 Optional<LeadImportant> impLead = impLeadRepositroy.findByLeadLeadIdAndEmployeeStaffId(leadId,staffId); 
		 if(impLead.isPresent() && nonNull(impLead)){
			 impLeadRepositroy.deleteById(impLead.get().getId());
			 return true;
		 }
		 return false;
	}

	@Override
	public List<LeadImportant> findLeadByEmployeeStaffId(Integer loggedInStaffId) {
		return impLeadRepositroy.findByEmployeeStaffId(loggedInStaffId);
	}
}
