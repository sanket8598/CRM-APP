package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.LEADS;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.entity.Description;
import ai.rnt.crm.entity.LeadImportant;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.repository.DescriptionRepository;
import ai.rnt.crm.repository.ImpLeadRepository;
import ai.rnt.crm.repository.LeadsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LeadDaoServiceImpl implements LeadDaoService {
	private final LeadsRepository leadsRepository;
	private final ImpLeadRepository impLeadRepositroy;
	private final DescriptionRepository descriptionRepository;

	@Override
	@CacheEvict(value = LEADS, allEntries = true)
	public Leads addLead(Leads leads) {
		log.info("inside the addLead method...");
		return leadsRepository.save(leads);
	}

	@Override
	@Cacheable(value = LEADS)
	public List<Leads> getLeadsByStatus(String leadsStatus) {
		log.info("inside the getLeadsByStatus method...{}", leadsStatus);
		return leadsRepository.findByStatusOrderByCreatedDateDesc(leadsStatus);
	}

	@Override
	@Cacheable(value = LEADS)
	public List<Leads> getAllLeads() {
		log.info("inside the getAllLeads method...");
		return leadsRepository.findByOrderByCreatedDateDesc();
	}

	@Override
	@Cacheable(value = LEADS)
	public List<Leads> getLeadDashboardData() {
		log.info("inside the getLeadDashboardData method...");
		return leadsRepository.findByOrderByCreatedDateDesc();
	}

	@Override
	public Optional<Leads> getLeadById(Integer leadId) {
		log.info("inside the getLeadById method...{}", leadId);
		return leadsRepository.findById(leadId);
	}

	@Override
	public Optional<LeadImportant> addImportantLead(LeadImportant leadImportant) {
		log.info("inside the addImportantLead method...");
		return ofNullable(impLeadRepositroy.save(leadImportant));
	}

	@Override
	public boolean deleteImportantLead(Integer leadId, Integer staffId) {
		log.info("inside the deleteImportantLead method...{} {}", leadId, staffId);
		Optional<LeadImportant> impLead = impLeadRepositroy.findByLeadLeadIdAndEmployeeStaffId(leadId, staffId);
		if (impLead.isPresent() && nonNull(impLead)) {
			impLeadRepositroy.deleteById(impLead.get().getId());
			return true;
		}
		return false;
	}

	@Override
	public List<LeadImportant> findLeadByEmployeeStaffId(Integer loggedInStaffId) {
		log.info("inside the findLeadByEmployeeStaffId method...{}", loggedInStaffId);
		return impLeadRepositroy.findByEmployeeStaffId(loggedInStaffId);
	}

	@Override
	public List<Leads> getFollowUpLeads(LocalDate todayAsDate, String time) {
		log.info("inside the getFollowUpLeads method...{}{}", todayAsDate, time);
		return leadsRepository.findByRemainderDueOnAndRemainderDueAtAndIsFollowUpRemainder(todayAsDate, time, true);
	}

	@Override
	// @Cacheable(value = "leadSourceOfAdmin")
	public List<Map<String, Integer>> getLeadSourceCount() {
		log.info("inside the getLeadSourceCount method...{}");
		return leadsRepository.getLeadSourceCount();
	}

	@Override
	// @Cacheable(value = "leadSourceOfUser")
	public List<Map<String, Integer>> getLeadSourceCount(Integer loggedInStaffId) {
		log.info("inside the getLeadSourceCount method...{}", loggedInStaffId);
		return leadsRepository.getLeadSourceCount(loggedInStaffId);
	}

	@Override
	public Description addDesc(Description description) {
		log.info("inside the addDesc method...{}");
		return descriptionRepository.save(description);
	}

	@Override
	public List<Description> getDescriptionByLeadIdAndIsOpportunity(Integer leadId) {
		log.info("inside the getDescriptionByLeadIdAndIsOpportunity method...{}");
		return descriptionRepository.findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(leadId, false);
	}

	@Override
	public List<Description> getDescriptionByLeadId(Integer leadId) {
		log.info("inside the getDescriptionByLeadId method...{}");
		return descriptionRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}
}
