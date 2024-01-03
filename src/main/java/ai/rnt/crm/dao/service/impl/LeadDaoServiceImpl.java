package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.LEADS;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	@CacheEvict(value = LEADS,allEntries = true)
	public Leads addLead(Leads leads) {
		return leadsRepository.save(leads);
	}

	@Override
	@Cacheable(value = LEADS)
	public List<Leads> getLeadsByStatus(String leadsStatus) {
		return leadsRepository.findByStatusOrderByCreatedDateDesc(leadsStatus);
	}

	@Override
	@Cacheable(value = LEADS)
	public List<Leads> getAllLeads() {
		return leadsRepository.findByOrderByCreatedDateDesc();
	}

	@Override
	@Cacheable(value = LEADS)
	public List<Leads> getLeadDashboardData() {
		return leadsRepository.findByOrderByCreatedDateDesc();
	}

	@Override
	public Optional<Leads> getLeadById(Integer leadId) {
		return leadsRepository.findById(leadId);
	}

	@Override
	public Optional<LeadImportant> addImportantLead(LeadImportant leadImportant) {
		return ofNullable(impLeadRepositroy.save(leadImportant));
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

	@Override
	public List<Leads> getFollowUpLeads(Date todayAsDate, String time) {
		return leadsRepository.findByRemainderDueOnAndRemainderDueAtAndIsFollowUpRemainder(todayAsDate, time, true);
	}
}
